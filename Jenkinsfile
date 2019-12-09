pipeline {
  agent any
  stages {
    stage('build'){
        sh 'mvn clean package install'    
    }
    stage('push_dev') { 
        timeout(time: 7, unit: 'DAYS') {
            input message: '是否发布到dev环境？',ok: 'Yes'
        }
        //sh label: '', script: '/shell/deploy_v2.sh uat'
    }
    stage('push_uat') { 
        timeout(time: 7, unit: 'DAYS') {
            input message: '是否发布到uat环境？',ok: 'Yes'
        }
        //sh label: '', script: '/shell/deploy_v2.sh online1'
    }
  }
  tools {
    maven 'maven3.3.9'
  }
}