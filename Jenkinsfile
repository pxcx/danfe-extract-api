pipeline {
  agent any
  environment {
    DOCKERHUB_CREDS = credentials('docker-hub-creds');
    SSL_CERT_FILE = credentials('ssl-cert-file');
    SSL_CERT_PATH = credentials('ssl-cert-path');
    SSL_CERT_PASS = credentials('ssl-cert-pass');
    SSL_CERT_NAME = credentials('ssl-cert-name');
  }
  stages {
    stage('Prepare') {
      steps {
        git branch: 'main', changelog: false, credentialsId: 'pxcx-github-creds', url: 'https://github.com/pxcx/danfe-extract-api.git'
        sh '''
            rm -rf target/
            cp ${SSL_CERT_PATH} src/main/resources/${SSL_CERT_FILE}
            echo "server.ssl.key-store-type=PKCS12" >> src/main/resources/application.properties
            echo "server.ssl.key-store=classpath:${SSL_CERT_FILE}" >> src/main/resources/application.properties
            echo "server.ssl.key-store-password=${SSL_CERT_PASS}" >> src/main/resources/application.properties
            echo "server.ssl.key-alias=${SSL_CERT_NAME}" >> src/main/resources/application.properties
        '''
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