pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git url: 'https://github.com/BozianAnaMaria/Automation-Scripting-lab05.git', branch: 'main'
            }
        }
        stage('Deploy to test server') {
            steps {
                // Copy PHP project files to test server
                sh 'ansible -i ansible/hosts.ini testserver -m copy -a "src=./php-project/ dest=/var/www/html/ recursive=yes" --private-key ./ssh-keys/id_rsa'
            }
        }
    }
}
