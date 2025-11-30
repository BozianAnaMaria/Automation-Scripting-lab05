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
                // Using ansible ad-hoc copy or playbook
                sh "ansible -i ansible/hosts.ini testserver -m copy -a 'src=/var/jenkins_home/workspace/ yourdest=/var/www/html/' --private-key ./ssh-keys/id_rsa"
            }
        }
    }
}
