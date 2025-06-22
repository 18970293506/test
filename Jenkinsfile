pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.8.6'
    }

    environment {
        // 镜像名称和标签
        IMAGE_NAME = "spring-mvc-app"
        IMAGE_TAG = "${env.BUILD_NUMBER}"

        // Docker Hub 凭据 (可选)
        DOCKER_CREDENTIALS = credentials('docker-hub-creds')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                url: 'https://github.com/18970293506/test.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // 停止并删除旧容器
                    sh 'docker stop spring-app || true'
                    sh 'docker rm spring-app || true'

                    // 运行新容器
                    docker.image("${IMAGE_NAME}:${IMAGE_TAG}").run(
                        "--name spring-app -p 8080:8080 -d"
                    )
                }
            }
        }

        // 可选：推送到 Docker Hub
        stage('Push to Docker Hub') {
            when {
                branch 'main'
            }
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-creds') {
                        docker.image("${IMAGE_NAME}:${IMAGE_TAG}").push()
                        docker.image("${IMAGE_NAME}:latest").push()
                    }
                }
            }
        }
    }

    post {
        always {
            // 清理工作空间
            deleteDir()
        }
        success {
            slackSend channel: '#deployments',
                      message: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER} deployed"
        }
        failure {
            slackSend channel: '#deployments',
                      message: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }
    }
}