@Library('ised-cicd-lib') _

pipeline {
	agent {
    label 'maven'
  }

  tools {
    jdk 'openjdk-17'
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
