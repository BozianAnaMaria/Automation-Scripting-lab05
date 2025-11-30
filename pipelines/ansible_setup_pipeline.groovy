pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git url: 'https://github.com/BozianAnaMaria/Automation-Scripting-lab05.git', branch: 'main'
            }
        }
        stage('Run Ansible Playbook') {
            steps {
                sh 'ansible-playbook -i ansible/hosts.ini ansible/setup_test_server.yml --private-key ./ssh-keys/id_rsa'
            }
        }
    }
}
