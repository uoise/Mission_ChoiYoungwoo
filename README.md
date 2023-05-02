# Deploy on Centos
1. yum update
    ```shell
    yum install epel-release -y
   
    yum update -y
    ```
   
2. install `java-17-openjdk`
   ```shell
    sudo yum install java-17-openjdk -y
   ```
   
3. install Docker 
   ```shell
   # docker cleanup
    yum remove docker \
    docker-client \
    docker-client-latest \
    docker-common \
    docker-latest \
    docker-latest-logrotate \
    docker-logrotate \
    docker-engine

    # Set up the repository
    yum install -y yum-utils
    
    yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
    
    # Install Docker Engine
    yum install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    
    systemctl start docker
   
    systemctl enable docker
   ```
   
4. install and run mariaDB on Docker
    ```shell
    docker run \
    --name mariadb_1 \
    -d \
    --restart unless-stopped \
    -e MARIADB_ROOT_PASSWORD={YOUR_PASSWORD} \
    -e TZ=Asia/Seoul \
    -p 3306:3306 \
    -v /docker_projects/mariadb_1/conf.d:/etc/mysql/conf.d \
    -v /docker_projects/mariadb_1/mysql:/var/lib/mysql \
    -v /docker_projects/mariadb_1/run/mysqld:/run/mysqld/ \
    mariadb:latest
   
   # After installation
   docker exec -it mariadb_1 /usr/bin/mariadb-secure-installation
   # Switch to unix_socket authentication [Y/n] : n
   ```
   
    - setting database
    ```sql
    CREATE DATABASE gram__prod;
   ```
   
5. clone this project
    ```shell
    rm -rf /docker_projects/gram/project
   
    mkdir -p /docker_projects/gram/project
   
    cd /docker_projects/gram/project
   
    git clone https://github.com/uoise/Mission_ChoiYoungwoo .
   ```

6. set Oauth Secret
    - Need OAuth Login for Google, Naver, Kakao, Facebook, Instagram
    ```shell
   # default template at src/main/resouces/application-secret.yml.default
   # set your api-key, password
   vim src/main/resouces/application-secret.yml
   ```
   
7. generate `keystore.p12`
    ```shell
    # set password only
    keytool -genkey -alias bns-ssl -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
    ```
   
8. build
    ```shell
   # gradle build
    cd /docker_projects/gram/project
    
    chmod 744 gradlew
   
   ./gradlew clean build
   # form tests failure is ok
   
   # Docker build
   docker build -t gramgram . 
   ```
   
9. Docker run
    ```shell
   docker run \
    --name=gramgram_1 \
    -p 443:8080 \
    --rm \
    -d \
    gramgram
   ```
   
## Troubleshooting
- keystore generate fail
  - `keytool error: java.lang.Exception: Key pair not generated, alias <bns-ssl> already exists`
  - keystore already exists, delete existing keystore.
- disable firewalls
  ```shell
  systemctl status firewalld
  systemctl stop firewalld
  ```
