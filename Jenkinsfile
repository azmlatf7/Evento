pipeline {
    agent any

    environment {
        IMAGE_NAME = "evento"
        CONTAINER_NAME = "evento-app"
    }

    stages {

        stage('Build JAR') {
            steps {
                sh '''
				apk add --no-cache openjdk17 maven
				mvn clean package -DskipTests
				'''
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
