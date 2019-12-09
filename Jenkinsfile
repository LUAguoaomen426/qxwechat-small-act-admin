pipeline {
  agent any
  stages {
    stage('deploy dev') {
      parallel {
        stage('deploy dev') {
          steps {
            sh 'mvn clean package'
            echo '开发环境已部署完成！'
          }
        }
        stage('deploy test') {
          steps {
            echo 'zzzzz'
          }
        }
      }
    }
    stage('确定') {
      steps {
        input 'Does the staging environment look ok?'
      }
    }
  }
  tools {
    maven 'maven3.3.9'
  }
}