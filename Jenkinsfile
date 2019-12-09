pipeline {
    agent any

    tools {
        maven 'maven3.3.9'
    }

    stages {
        stage('Deploy DEV') {
            steps {
                sh "mvn clean package"
                echo "开发环境已部署完成！"
            }
        }
    }
}