#!groovy

def version() {
    v = env.TAG_NAME
    if (env.TAG_NAME ==~ /^v[\d.]+/) {
        v = env.TAG_NAME.drop(1)
    }
    env.REAL_VERSION = v
    return v
}

def getContainerName(String name) {
    return name + "-" + env.BUILD_NUMBER + "-" + env.BUILD_ID
}
