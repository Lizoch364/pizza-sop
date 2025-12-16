pipeline {
    agent any

    tools {
        maven 'maven-3.9.9'
    }

    environment {
        JAVA_HOME = tool 'jdk-17'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                sh 'which java || true'
                sh 'java -version'
                sh 'javac -version'
                sh 'echo "JAVA_HOME=$JAVA_HOME"'
                sh 'mvn -v'
                checkout scm
                sh 'git submodule init || true'
                sh 'git submodule update --recursive --remote || true'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean install -DskipTests'
            }
        }

        stage('Docker Compose Build') {
                    steps {
                        script {
                            sh 'docker --version'

                            sh 'docker compose --version || docker compose version'

                            sh 'docker compose -f docker-compose.yml down --remove-orphans || true'

                            sh 'docker compose -f docker-compose.yml build --no-cache'


                                    sh 'pwd'
                                    sh 'ls -la'
                                    sh '''
                                    if [ ! -e prometheus.yml ]; then
                                        echo "ERROR: prometheus.yml does not exist!"
                                        exit 1
                                    fi
                                    if [ -d prometheus.yml ]; then
                                        echo "ERROR: prometheus.yml is a directory, not a file!"
                                        exit 1
                                    fi
                                    echo "prometheus.yml exists and is a file"
                                    '''


                            sh 'docker compose -f docker-compose.yml up -d'
                        }
                    }
        }
    }
}