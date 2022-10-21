pipeline {
  agent any
  environment {
    DOCKERHUB_CREDS = Credentials('docker-hub-creds');
  }
  stages {
    stage('Prepare') {
      steps {
        git branch: 'main', changelog: false, credentialsId: 'pxcx-github-creds', url: 'https://github.com/pxcx/danfe-extract-api.git'
        sh '''
            ls -a
            rm -rf target/
        '''
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('App Build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage('Container Build') {
      steps {
        sh '''
            docker login -u ${DOCKERHUB_CREDS_USR} -p ${DOCKERHUB_CREDS_PSW}
            docker build . -t pxcx/danfe-extract-api
            docker push pxcx/danfe-extract-api
        '''
      }
    }
  }
}