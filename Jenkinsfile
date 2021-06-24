@Library('ised-cicd-lib') _

pipeline {
	agent {
       		label 'maven'
   	}
   	
    options {
        disableConcurrentBuilds()
    }
  
    stages {
    	stage('build') {
		steps {
			script{
				builder.buildMavenLibrary()
			}
		}
    	}
    }
}
