pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git url: 'https://github.com/BozianAnaMaria/Automation-Scripting-lab05.git', branch: 'main'
            }
        }
        stage('Install dependencies') {
            steps {
                sh 'composer install --no-interaction'
            }
        }
        stage('Run tests') {
            steps {
                sh './vendor/bin/phpunit -c php-project/phpunit.xml --log-junit results.xml'
            }
            post {
                always {
                    junit 'results.xml'
                }
            }
        }
    }
}
