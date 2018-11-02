#!groovy

def version() {
    v = env.TAG_NAME
    if (env.TAG_NAME ==~ /^v[\d.]+/) {
        v = env.TAG_NAME.drop(1)
    }
    env.REAL_VERSION = v
    return v
}

def getContainerName() {
    return env.BUILD_TAG.replace('%2F', '-')
}
