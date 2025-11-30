# Automation-Scripting-lab05

## Project Description

This project automates building, testing, configuring, and deploying a PHP project using Jenkins pipelines, Docker containers, SSH authentication, and Ansible playbooks.

## Jenkins Controller Setup

- Used official Jenkins LTS Docker image.
- Opened ports 8080 and 50000.
- Mounted Docker socket for Docker commands.
- Installed plugins: Docker, Docker Pipeline, GitHub Integration, SSH Agent.

## SSH Agent Setup

- Created Dockerfile.ssh_agent with PHP CLI and SSH client.
- Generated SSH keys for Jenkins integration.
- Configured volumes to share SSH keys with Jenkins and other services.

## Ansible Agent Setup

- Created Dockerfile.ansible_agent based on Ubuntu.
- Installed Ansible and dependencies.
- Generated SSH keys for Jenkins to connect and for connecting to test server.
- Configured Ansible inventory and SSH keys.

## Test Server Setup

- Created Dockerfile.test_server based on Ubuntu.
- Installed Apache, PHP, OpenSSH.
- Created ansible user with SSH key access.
- Configured SSH for key-based authentication.

## Ansible Playbook

- Installs and configures Apache2, PHP, and required extensions.
- Configures Apache virtual host for the PHP project.
- Ensures Apache is enabled and running.

## Jenkins Pipelines

- **php_build_and_test_pipeline.groovy**: clones PHP repo, installs dependencies, runs PHPUnit tests, and reports results.
- **ansible_setup_pipeline.groovy**: clones ansible repo and runs playbook to configure test server.
- **php_deploy_pipeline.groovy**: clones PHP repo and deploys files to test server via Ansible.

## Advantages of Using Ansible for Server Configuration

- Agentless: connects over SSH without installing additional software.
- Idempotent: safe to run multiple times with predictable outcomes.
- Declarative language makes configuration readable and maintainable.
- Wide module ecosystem for managing packages, services, files, users, etc.
- Easily integrates with CI/CD pipelines like Jenkins.

## Other Ansible Modules for Configuration Management

- `user` - manage user accounts
- `package` - install/remove software packages
- `service` - manage services
- `copy` - copy files
- `template` - manage config files with templates
- `git` - deploy code from repositories
- `firewalld` - manage firewall settings
- `docker_container` - manage Docker containers
- `mysql_db` - manage MySQL databases

## Problems Encountered and Solutions

- SSH authentication issues solved by careful key management and permissions.
- Networking between Docker containers required careful port mappings and dependency ordering.
- Ensuring Ansible ran with proper privileges to install software using `become: yes`.
- Debugging the playbook by enabling verbose Ansible output and iterative testing.
