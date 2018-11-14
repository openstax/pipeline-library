#!groovy

def version() {
    return getVersion()
}

def getContainerName() {
    return env.BUILD_TAG.replace('%2F', '-')
}
