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
                sh 'cd php-project && composer install --no-interaction'
            }
        }
        stage('Run tests') {
            steps {
                sh 'cd php-project && ./vendor/bin/phpunit -c phpunit.xml --log-junit results.xml'
            }
            post {
                always {
                    junit 'php-project/results.xml'
                }
            }
        }
    }
}
