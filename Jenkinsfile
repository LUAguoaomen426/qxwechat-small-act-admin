pipeline {
    agent any

    tools {
        maven 'maven3.3.9'
    }

    stages {
          stage('deploy dev') {
            steps {
                sh "mvn clean package"
                echo "开发环境已部署完成！"
            }
          }
          stage('确定') {
            steps {
                  input "Does the staging environment look ok?"
            }
        }
    }
}