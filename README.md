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

## Description of the Ansible Playbook and Its Tasks

File: ansible/setup_test_server.yml

Purpose:

Configure the test server environment required for PHP application deployment.

### Main Tasks:
1. Update package manager
- name: Update APT cache
  apt:
    update_cache: yes

2. Install required packages
- name: Install utilities
  apt:
    name: ['php', 'php-mysql', 'apache2']
    state: present

3. Ensure Apache is running
- name: Enable and start apache
  service:
    name: apache2
    state: started
    enabled: yes

4. Deploy index.php or configuration file (optional)
- name: Place example index.php
  copy:
    src: ./files/index.php
    dest: /var/www/html/

5. Set correct permissions
- name: Set permissions
  file:
    path: /var/www/html
    mode: '0755'


This playbook ensures the test server becomes fully prepared for deployment.

## Jenkins Pipelines

- **php_build_and_test_pipeline.groovy**: clones PHP repo, installs dependencies, runs PHPUnit tests, and reports results.
- **ansible_setup_pipeline.groovy**: clones ansible repo and runs playbook to configure test server.
- **php_deploy_pipeline.groovy**: clones PHP repo and deploys files to test server via Ansible.


## Theoretical Questions
1. What are the advantages of using Ansible for server configuration?

- Agentless architecture
No software required on the managed servers—only SSH.

- Idempotency
Running the same playbook multiple times produces the same server state.

- Human-readable YAML syntax
Easy to learn and maintain.

- Reusable roles and tasks
Saves time when configuring multiple servers.

- Supports large infrastructure
Can configure hundreds of servers in parallel.

- Integration with CI/CD tools
Works perfectly with Jenkins pipelines.

2. What other Ansible modules exist for configuration management?

Some commonly used modules:

2.1. System Management
- apt, yum, dnf → package management
- service → start/stop services
- user, group → manage users

2.2. File and Permissions
- copy
- template
- file
- unarchive

2.3. Networking
- ufw
- iptables
- firewalld

2.4. Cloud Providers
- aws_* modules
- azure_rm_*
- gcp_compute

2.5 Database
- mysql_db
- postgresql_db
- mongodb_user


3. What problems did you encounter when creating the Ansible playbook and how did you solve them?
- Problem 1: SSH authentication failed

Cause: Wrong private key path or missing permissions.
Solution: Added correct SSH key in Jenkins and used correct --private-key path.

- Problem 2: Ansible could not ping the test server

Cause: Missing Python inside Docker test server.
Solution: Installed Python via Ansible or built the container with it included.

- Problem 3: Permissions issues in /var/www/html

Cause: Apache user owned the directory.
Solution: Used Ansible file module to set permissions.

- Problem 4: Composer not installed on Jenkins

Cause: Jenkins agent did not have composer.
Solution: Installed Composer inside pipeline or used docker agent with PHP image.

- Problem 5: Jenkins pipeline failed due to missing tools

Cause: Jenkins container runs as non-root user.
Solution: Installed necessary tools using a special Jenkins Docker agent or pre-built environment.
