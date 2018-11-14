pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
    }
  }
  stages {
    stage('Build and Test') {
      steps {
        sh 'mvn -Dmaven.test.failure.ignore=true clean install'
        junit 'target/surefire-reports/*.xml'
      }
    }
  }
}