package org.openstax

class VersionInfoTest extends GroovyTestCase {

    // We're stubbing out a pipeline script.
    // This pretends to be a script that is running against
    // a tag build (i.e. buildingTag() == true ).
    class TagBuildPipelineScript {
        def tag = ""

        def env = [
            'TAG_NAME': tag,
        ]

        def TagBuildPipelineScript(tag) {
            this.tag = tag
        }
    }

    //
    // Tests
    //

    void testValidSemanticVersion() {
        def tag = 'v1.2.3'
        def pipeline = new TagBuildPipelineScript(tag)
        def expectedVersion = tag.drop(1)

        def version = new VersionInfo(pipeline).version()

        assert expectedVersion == version
        // and we create the environment variable for good measure
        assert expectedVersion == pipeline.env.REAL_VERSION
    }

    /**
      * Check that non-semantic versioned tags are not modified
      */
    void testTextTagName() {
        def tag = 'verglous-minubus'
        def pipeline = new TagBuildPipelineScript(tag)
        def expectedVersion = tag

        def version = new VersionInfo(pipeline).version()

        assert expectedVersion == version
        // and we create the environment variable for good measure
        assert expectedVersion == pipeline.env.REAL_VERSION
    }
}