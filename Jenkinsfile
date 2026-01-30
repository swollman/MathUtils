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
                sh 'mvn clean package'
            }
        }

        stage('Print Test Summary') {
            steps {
                echo "===== TEST SUMMARY ====="
                sh '''
                    if [ -d target/surefire-reports ]; then
                        for f in $(find target/surefire-reports -name "*.txt"); do
                            echo "---- $f ----"
                            cat "$f"
                            echo ""
                        done
                    else
                        echo "No Surefire reports found."
                    fi
                '''
            }
        }

        stage('Code Coverage (JaCoCo)') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('Publish Coverage') {
            steps {
                jacoco execPattern: '**/jacoco.exec'
            }
        }

        stage('Test Reports') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Archive Artifact') {
            steps {
                echo "Archiving JAR artifact..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
