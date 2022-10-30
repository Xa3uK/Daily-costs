pipeline {
    agent any;

    stages {
        stage("Build") {
            steps{
            echo 'Building...'
            sh """
            mvn clean install -Dskiptests=true
            """
            }
        }
        stage("Test") {
            steps{
            echo 'Testing...'
            sh """
            mvn test -Dsurefire.userFile=false
            """
            }
        }
    }
}