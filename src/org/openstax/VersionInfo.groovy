package org.openstax

class VersionInfo implements Serializable {

    def script

    def VersionInfo(script) {
        this.script = script
    }

    def version() {
        def v = this.script.env.TAG_NAME
        if (v ==~ /^v[\d.]+/) {
            v = v.drop(1)
        }
        this.script.env.REAL_VERSION = v
        return v
    }
}