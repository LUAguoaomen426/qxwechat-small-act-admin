pipeline {
    agent any

    tools {
        maven 'maven3.3.9'
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn clean package"
            }
        }
    }
}