#!groovy
import org.openstax.VersionInfo

def call() {
    def info = new VersionInfo(this)
    return info.version()
}
