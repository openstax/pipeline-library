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

## Development

### Development Tools

- Java
- Groovy >=3.5
- Maven >=3.5

On a Mac, these can be installed using: `brew install groovy maven`.

### Feature Development

First read [Jenkins on Jenkins - Unit Testing Shared Libraries](https://relaxdiego.com/2018/02/jenkins-on-jenkins-shared-libraries.html). Use the existing tests and the aforementioned article as a guide for writing new functionality and tests.

You can run the unit-tests by building the project using: `mvn clean install`.

# License

This software is subject to the provisions of the GNU Affero General
Public License Version 3.0 (AGPL). See license.txt for details.
Copyright (c) 2018 Rice University
