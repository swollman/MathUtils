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

        stage('Extract Test Stats') {
            steps {
                script {
                    def xml = sh(
                        script: "cat target/surefire-reports/*.xml | grep testsuite | head -1",
                        returnStdout: true
                    ).trim()

                    env.TESTS_TOTAL   = sh(script: "echo '${xml}' | sed -n 's/.*tests=\"\\([0-9]*\\)\".*/\\1/p'", returnStdout: true).trim()
                    env.TESTS_FAILED  = sh(script: "echo '${xml}' | sed -n 's/.*failures=\"\\([0-9]*\\)\".*/\\1/p'", returnStdout: true).trim()
                    env.TESTS_ERRORS  = sh(script: "echo '${xml}' | sed -n 's/.*errors=\"\\([0-9]*\\)\".*/\\1/p'", returnStdout: true).trim()
                    env.TESTS_SKIPPED = sh(script: "echo '${xml}' | sed -n 's/.*skipped=\"\\([0-9]*\\)\".*/\\1/p'", returnStdout: true).trim()
                }
            }
        }

        stage('Extract Coverage %') {
            steps {
                script {
                    def line = sh(
                        script: "grep -m1 'INSTRUCTION' target/site/jacoco/jacoco.xml",
                        returnStdout: true
                    ).trim()

                    def missed  = sh(script: "echo '${line}' | sed -n 's/.*missed=\"\\([0-9]*\\)\".*/\\1/p'", returnStdout: true).trim() as Integer
                    def covered = sh(script: "echo '${line}' | sed -n 's/.*covered=\"\\([0-9]*\\)\".*/\\1/p'", returnStdout: true).trim() as Integer

                    def percent = (covered * 100.0) / (missed + covered)
                    env.COVERAGE = String.format('%.2f', percent)
                }
            }
        }

        stage('Archive Artifact') {
            steps {
                echo "Archiving JAR artifact..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            slackSend(
                channel: '#jenkins',
                message: """✔ SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}
Tests: ${env.TESTS_TOTAL} total, ${env.TESTS_FAILED} failed, ${env.TESTS_ERRORS} errors, ${env.TESTS_SKIPPED} skipped
Coverage: ${env.COVERAGE}%
${env.BUILD_URL}"""
            )
        }
        failure {
            slackSend(
                channel: '#jenkins',
                message: """❌ FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}
Tests: ${env.TESTS_TOTAL} total, ${env.TESTS_FAILED} failed, ${env.TESTS_ERRORS} errors, ${env.TESTS_SKIPPED} skipped
Coverage: ${env.COVERAGE}%
${env.BUILD_URL}"""
            )
        }
    }
}
