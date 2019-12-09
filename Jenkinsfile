pipeline {
    agent any
    stages {
        stage('build') {
    withMaven() {
 
      // Run the maven build
      sh "mvn clean verify"
 
    }
        }
    }
}