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

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Print Test Summary') {
            steps {
                echo "===== TEST SUMMARY ====="
                sh '''
                    for f in $(find target/surefire-reports -name "*.txt"); do
                        echo "---- $f ----"
                        cat "$f"
                        echo ""
                    done
                '''
            }
        }

        stage('Test Reports') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }
}
