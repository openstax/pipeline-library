# OpenStax Jenkins Pipeline Library

This is a library used to extend functionality in a [Jenkins Pipeline](https://jenkins.io/doc/book/pipeline/).

The functions defined in this [Jenkins shared library](https://jenkins.io/doc/book/pipeline/shared-libraries/) can be used to facilitate complex actions within your declarative pipeline configuration (aka `Jenkinsfile`).

## Usage

Within your Jenkins file add the library for use:

```
@Library('pipeline-library') _
```

See the [shared library](https://jenkins.io/doc/book/pipeline/shared-libraries/) documentation more information on this line.

Note, the Jenkins' instance must be preconfigured to use the library. Go to Manage Jenkins > Configure System > Global Pipeline Libraries to add this library to your instance.

# License

This software is subject to the provisions of the GNU Affero General
Public License Version 3.0 (AGPL). See license.txt for details.
Copyright (c) 2018 Rice University
