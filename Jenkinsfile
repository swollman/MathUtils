pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Java Tests') {
            steps {
                dir('java') {
                    sh 'mvn clean test'
                }
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'java/target/surefire-reports/*.xml'
                }
            }
        }
    }
}