pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        IMAGE_NAME = "evento"
        CONTAINER_NAME = "evento-app"
    }

    stages {
        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $IMAGE_NAME ."
            }
        }

        stage('Stop Old Container') {
            steps {
                sh "docker rm -f $CONTAINER_NAME || true"
            }
        }

        stage('Run New Container') {
            steps {
                sh "docker run -d -p 8080:8900 --name $CONTAINER_NAME $IMAGE_NAME"
            }
        }
    }
}
