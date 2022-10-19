@Library('ised-cicd-lib') _

pipeline {
	agent {
    label 'maven'
  }

  tools {
    jdk 'openjdk-11'
  }
   	
  options {
    disableConcurrentBuilds()
  }

  stages {
    stage('build') {
      steps {
        script {
          builder.buildMavenLibrary()
        }
      }
    }
  }
}
