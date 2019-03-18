#!groovy


/**
 * Runs functional tests against the given domain
 *
 * Runs the functional tests and reports the results.
 * On failure, a message is sent to the #cnx slack channel to notify developers of said failure.
 *
 * Named parameters:
 *
 *     `testingDomain` (required): hostname and port of the docker swarm endpoint
 *                                 (e.g. tcp://cluster.cnx.org:2375)
 */

def call(Map parameters = [:]) {
    testingDomain = parameters.testingDomain
    if (!testingDomain) {
        error "You need to supply the domain to test against. Hint: give me the DOCKER_HOST value"
    }
    area = parameters.area
    if (!area) {
       area = 'webview'
    }

    // Create a location to store the xml-report
    sh "mkdir -p ${env.WORKSPACE}/xml-report"
    // Set up an environment variable list
    // FIXME: temporary hardcoded domain added to allow tests to run
    sh """cat << EOF > ${env.WORKSPACE}/env.list
DISABLE_DEV_SHM_USAGE=true
HEADLESS=true
NO_SANDBOX=true
PRINT_PAGE_SOURCE_ON_FAILURE=true
ARCHIVE_BASE_URL=https://archive-staged.cnx.org
WEBVIEW_BASE_URL=https://staged.cnx.org
LEGACY_BASE_URL=https://legacy-staged.cnx.org
NEB_ENV=staged
EOF
"""

    /**
     * The following doesn't work, but it's close to working. Maybe somebody will find a way to fix it.
     * The issue is executing a secondary sequence of commands inside the container as root rather than
     * the default process user, which is not root.
     * In the mean time the shell based version of this follows.
     */
    // image = docker.image('openstax/cnx-automation:latest')
    // try {
    //     commonArgs = "-v ${env.WORKSPACE}/xml-report:/xml-report:z --env-file ${env.WORKSPACE}/env.list"
    //     // image.run("-v ${env.WORKSPACE}/xml-report:/xml-report:z --env-file ${env.WORKSPACE}/env.list")
    //     // Run the tests
    //     image.inside(commonArgs) { c ->
    //         sh "pytest -m 'webview and not (requires_deployment or requires_varnish_routing or legacy)' --junitxml=/code/report.xml"
    //     }
    //     // Move the report to a location that is both accessible and writable
    //     image.inside(commonArgs + ' -u root') { c ->
    //         sh "cp /code/report.xml /xml-report"
    //     }
    // } catch (err) {
    //     echo 'Something failed, I should sound the klaxons!'
    //     slackSend(channel: '#jenkins', message: "Build had a hard failure: ${env.BUILD_URL}")
    //     throw err
    // } finally {
    //     // Destroy the testing container
    //     image.stop()
    //     // Report test results
    //     junit "xml-report/report.xml"
    // }

    containerName = meta.containerName()
    try {
        // Start the testing container
        sh "docker run -d -v ${env.WORKSPACE}/xml-report:/xml-report:rw,z --env-file ${env.WORKSPACE}/env.list --name ${containerName} openstax/cnx-automation:latest"
        // Run the tests
        sh "docker exec ${containerName} pytest -n 4 -m '${area} and not (requires_deployment or legacy)' --junitxml=/code/report.xml"
     } finally {
        // Move the report to a location that is both accessible and writable
        sh "docker exec -u root ${containerName} cp /code/report.xml /xml-report"
        // Destroy the testing container
        sh "docker stop ${containerName} && docker rm -f ${containerName}"
        // Report test results
        junit "xml-report/report.xml"

        if (currentBuild.currentResult == 'SUCCESS') {
            currentBuild.result = 'SUCCESS'
        } else {
            slackSend(channel: '#qa-stream', color: 'warning', message: "A recent change to '${env.GIT_URL}@${env.GIT_BRANCH}' is causing functional test failures. For details see ${env.BUILD_URL}")
        }
    }
}
