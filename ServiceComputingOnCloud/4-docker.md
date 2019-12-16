---
title: docker 实践
---

## docker 实践
### [返回目录](../ServiceComputingOnCloud-Catalog)

## 1. 准备 docker 环境
- [ubuntu docker 安装参考](https://www.runoob.com/docker/ubuntu-docker-install.html)
- [使用国内镜像库以加速](https://my.oschina.net/u/3703365/blog/1810028)

## 2. 运行第一个容器
```
guojj@guojj-VirtualBox:~$ docker run hello-world

Hello from Docker!
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps:
1. The Docker client contacted the Docker daemon.
2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64)
3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.
4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.

To try something more ambitious, you can run an Ubuntu container with:
$ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
https://hub.docker.com/

For more examples and ideas, visit:
https://docs.docker.com/get-started/

```

## 3. docker 基本操作
- 拉取 ubuntu 镜像并运行
    ```
    guojj@guojj-VirtualBox:~$ docker pull ubuntu
    Using default tag: latest
    latest: Pulling from library/ubuntu
    7ddbc47eeb70: Pull complete 
    c1bbdc448b72: Pull complete 
    8c3b70e39044: Pull complete 
    45d437916d57: Pull complete 
    Digest: sha256:6e9f67fa63b0323e9a1e587fd71c561ba48a034504fb804fd26fd8800039835d
    Status: Downloaded newer image for ubuntu:latest
    docker.io/library/ubuntu:latest

    guojj@guojj-VirtualBox:~$ docker run -it ubuntu bash
    root@489fcbac35d5:/# 
    ```

- 显示本地镜像库内容
    ```
    guojj@guojj-VirtualBox:~$ docker images
    REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
    ubuntu              latest              775349758637        6 weeks ago         64.2MB
    hello-world         latest              fce289e99eb9        11 months ago       1.84kB
    ```

- 获得帮助
    ```
    guojj@guojj-VirtualBox:~$ docker --help

    Usage:	docker [OPTIONS] COMMAND

    A self-sufficient runtime for containers

    Options:
        ...

    Management Commands:
        ...

    Commands:
        ...
    ```

- 显示运行中的容器
    ```
    guojj@guojj-VirtualBox:~$ docker ps
    CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
    ```

- 显示所有容器（包含已终止）
    ```
    guojj@guojj-VirtualBox:~$ docker ps -a
    CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS                       PORTS               NAMES
    489fcbac35d5        ubuntu              "bash"              4 minutes ago       Exited (130) 3 minutes ago                       hopeful_pare
    a6261e738b63        hello-world         "/hello"            2 hours ago         Exited (0) 2 hours ago                           serene_kare
    1fe664abbe17        hello-world         "/hello"            2 hours ago         Exited (0) 2 hours ago                           nostalgic_leakey
    32f13bf1dc50        hello-world         "/hello"            2 hours ago         Exited (0) 2 hours ago                           keen_noyce
    e80d312cb8e1        hello-world         "/hello"            2 hours ago         Exited (0) 2 hours ago                           brave_pascal
    ```

- 继续运行原容器并进入，docker restart 后的参数是对应容器的 NAMES
    ```
    guojj@guojj-VirtualBox:~$ docker restart hopeful_pare
    hopeful_pare
    guojj@guojj-VirtualBox:~$ docker ps
    CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
    489fcbac35d5        ubuntu              "bash"              11 minutes ago      Up 13 seconds                           hopeful_pare
    guojj@guojj-VirtualBox:~$ docker attach hopeful_pare
    root@489fcbac35d5:/# 
    ```

## 4. MySQL 与容器化
#### 4.1 拉取 MySQL
- 拉取 MySQL 镜像
    ```
    guojj@guojj-VirtualBox:~$ docker pull mysql:5.7
    5.7: Pulling from library/mysql
    d599a449871e: Pull complete 
    f287049d3170: Pull complete 
    08947732a1b0: Pull complete 
    96f3056887f2: Pull complete 
    871f7f65f017: Pull complete 
    1dd50c4b99cb: Pull complete 
    5bcbdf508448: Pull complete 
    02a97db830bd: Pull complete 
    c09912a99bce: Pull complete 
    08a981fc6a89: Pull complete 
    818a84239152: Pull complete 
    Digest: sha256:5779c71a4730da36f013a23a437b5831198e68e634575f487d37a0639470e3a8
    Status: Downloaded newer image for mysql:5.7
    docker.io/library/mysql:5.7
    ```

- 练习：构建 docker 镜像并运行
    ```
    guojj@guojj-VirtualBox:~$ mkdir mydock
    guojj@guojj-VirtualBox:~$ cd mydock
    guojj@guojj-VirtualBox:~/mydock$ vim dockerfile
    guojj@guojj-VirtualBox:~/mydock$ docker build . -t hello
    Sending build context to Docker daemon  2.048kB
    Step 1/3 : FROM ubuntu
    ---> 775349758637
    Step 2/3 : ENTRYPOINT ["top", "-b"]
    ---> Running in c270f8f30e0e
    Removing intermediate container c270f8f30e0e
    ---> 7f3872821fc1
    Step 3/3 : CMD ["-c"]
    ---> Running in af03e730b9d9
    Removing intermediate container af03e730b9d9
    ---> e65f77b9112a
    Successfully built e65f77b9112a
    Successfully tagged hello:latest
    ```
    其中 dockerfile 输入的内容为
    ```
    FROM ubuntu
    ENTRYPOINT ["top", "-b"]
    CMD ["-c"]
    ```

#### 4.2 使用 MYSQL 容器
- 启动服务器
    ```
    guojj@guojj-VirtualBox:/etc/apt$ sudo docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7
    bd49264cd63b3bbbcf29dba81a3dda04528feda50ba0f16517d2ad751e1a1f76
    guojj@guojj-VirtualBox:/etc/apt$ docker ps
    CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                               NAMES
    bd49264cd63b        mysql:5.7           "docker-entrypoint.s…"   22 seconds ago      Up 20 seconds       0.0.0.0:3306->3306/tcp, 33060/tcp   mysql
    ```

- 启动 MySQL 客户端
    ```
    guojj@guojj-VirtualBox:/etc/apt$ sudo docker run -it --net host mysql:5.7 "sh"
    # mysql -h127.0.0.1 -P3306 -uroot -proot
    mysql: [Warning] Using a password on the command line interface can be insecure.
    Welcome to the MySQL monitor.  Commands end with ; or \g.
    Your MySQL connection id is 2
    Server version: 5.7.28 MySQL Community Server (GPL)

    Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.

    Oracle is a registered trademark of Oracle Corporation and/or its
    affiliates. Other names may be trademarks of their respective
    owners.

    Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

    mysql> 
    ```

- 数据库文件
    ```
    guojj@guojj-VirtualBox:~$ docker exec -it mysql bash
    root@bd49264cd63b:/# ls /var/lib/mysql 
    auto.cnf	 client-key.pem  ibdata1	     private_key.pem  sys
    ca-key.pem	 ib_buffer_pool  ibtmp1		     public_key.pem
    ca.pem		 ib_logfile0	 mysql		     server-cert.pem
    client-cert.pem  ib_logfile1	 performance_schema  server-key.pem
    ```

- dockerfile 的 VOLUME /var/lib/mysql 的含义
    ```
    guojj@guojj-VirtualBox:~$ docker container prune -f
    Deleted Containers:
    ...
    Total reclaimed space: 28B
    guojj@guojj-VirtualBox:~$ docker volume prune -f
    Deleted Volumes:
    ...
    Total reclaimed space: 413.8MB
    guojj@guojj-VirtualBox:~$ docker volume ls
    DRIVER              VOLUME NAME
    local               34b01cf97ee1d1538570d7f14d2bd29ed25a40daa4d03086b85bde436ea4ae22
    ```

#### 4.3 创建卷并挂载
```
guojj@guojj-VirtualBox:~$ docker rm $(docker ps -a -q) -f -v
bd49264cd63b
guojj@guojj-VirtualBox:~$ docker volume create mydb 
mydb
guojj@guojj-VirtualBox:~$ docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -v mydb:/var/lib/mysql -d mysql:5.7
b4d53d626e6f8ca9e826aa0189486d71f0198934db2447dfa235d26017c8b574
guojj@guojj-VirtualBox:~$ docker volume ls
DRIVER              VOLUME NAME
local               15161f03dee2a55cbb0de941e6b2c97dd9dd0a9102eeec00ae9f49bd26c99d2f
local               mydb
```

#### 4.4 启动客户端容器连接服务器
若显示 3306 端口已被占用，可尝试先 ``sudo service mysql stop ``
```
guojj@guojj-VirtualBox:~$ docker run --name myclient --link mysql:mysql -it mysql:5.7 bash
root@716d6f7ef833:/# env
...
root@716d6f7ef833:/# mysql -hmysql -P3306 -uroot -proot
mysql: [Warning] Using a password on the command line interface can be insecure.
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 2
Server version: 5.7.28 MySQL Community Server (GPL)

Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> 
```

#### 4.5 docker compose 与多容器应用自动化部署
- 编写 stack.yml 并启动服务
    ```
    guojj@guojj-VirtualBox:~$ mkdir comptest && cd comptest
    guojj@guojj-VirtualBox:~/comptest$ vim stack.yml
    guojj@guojj-VirtualBox:~/comptest$ docker-compose -f stack.yml up
    Creating network "comptest_default" with the default driver
    Creating comptest_db_1_20dfc8408d00      ... done
    Creating comptest_adminer_1_db9b65d0e160 ... done
    Attaching to comptest_adminer_1_63661434adf9, comptest_db_1_82b03f762f7f
    adminer_1_63661434adf9 | [Mon Dec 16 12:08:56 2019] PHP 7.4.0 Development Server (http://[::]:8080) started
    db_1_82b03f762f7f | 2019-12-16 12:08:57+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.28-1debian9 started.
    db_1_82b03f762f7f | 2019-12-16 12:08:57+00:00 [Note] [Entrypoint]: Switching to dedicated user 'mysql'
    db_1_82b03f762f7f | 2019-12-16 12:08:57+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 5.7.28-1debian9 started.
    db_1_82b03f762f7f | 2019-12-16 12:08:57+00:00 [Note] [Entrypoint]: Initializing database files
    ...
    ```
    其中 stack.yml 输入为
    ```
    version: '3.1'
        services:
            db:
                image: mysql:5.7
                command: --default-authentication-plugin=mysql_native_password
                restart: always
                environment: 
                    MYSQL_ROOT_PASSWORD: example 
            adminer: 
                image: adminer 
                restart: always 
                ports: 
                  - 8080:808
    ```

## 5. docker 网络
- 管理容器网络
    ```
    guojj@guojj-VirtualBox:~/comptest$ docker network ls
    NETWORK ID          NAME                DRIVER              SCOPE
    ebd29e0ed21c        bridge              bridge              local
    4729deda7dda        comptest_default    bridge              local
    a7b1183f0850        host                host                local
    39bfea1cdb06        none                null                local
    ```
- 制备支持 ifconfig 和 ping 命令的 ubuntu 容器
    ```
    guojj@guojj-VirtualBox:~/comptest$ docker run --name unet -it --rm ubuntu bash
    root@4940f22280fd:/# apt-get update
    ...
    root@4940f22280fd:/# apt-get install net-tools
    ...
    root@4940f22280fd:/# apt-get install iputils-ping -y
    ...
    root@4940f22280fd:/# ifconfig
    eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
            inet 172.17.0.2  netmask 255.255.0.0  broadcast 172.17.255.255
            ether 02:42:ac:11:00:02  txqueuelen 0  (Ethernet)
            RX packets 3732  bytes 18709528 (18.7 MB)
            RX errors 0  dropped 0  overruns 0  frame 0
            TX packets 3692  bytes 210633 (210.6 KB)
            TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

    lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
            inet 127.0.0.1  netmask 255.0.0.0
            loop  txqueuelen 1000  (Local Loopback)
            RX packets 0  bytes 0 (0.0 B)
            RX errors 0  dropped 0  overruns 0  frame 0
            TX packets 0  bytes 0 (0.0 B)
            TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

    root@4940f22280fd:/# ping wwww.baidu.com
    PING ps_other.a.shifen.com (39.156.66.10) 56(84) bytes of data.
    64 bytes from 39.156.66.10 (39.156.66.10): icmp_seq=1 ttl=46 time=39.8 ms
    64 bytes from 39.156.66.10 (39.156.66.10): icmp_seq=2 ttl=46 time=40.2 ms
    64 bytes from 39.156.66.10 (39.156.66.10): icmp_seq=3 ttl=46 time=40.2 ms
    ^C
    --- ps_other.a.shifen.com ping statistics ---
    3 packets transmitted, 3 received, 0% packet loss, time 2010ms
    rtt min/avg/max/mdev = 39.864/40.107/40.230/0.237 ms
    root@4940f22280fd:/# 
    ```

- 启动另一个命令窗口，由容器制作镜像（注意不要终止上一步正在运行的容器）
    ```
    guojj@guojj-VirtualBox:~$ docker commit unet ubuntu:net
    ```

- 创建自定义网络
    ```
    guojj@guojj-VirtualBox:~$ docker network create mynet
    7eb16b58de49b6f5d6f7266c9f582746d70012c31a7936e972a2cdf1da37140d
    ```
- 新建窗口创建 u1 容器网络
    ```
    guojj@guojj-VirtualBox:~$ docker run --name u1 -it -p 8888:80 --net mynet --rm ubuntu:net bash 
    root@26d19cbff64c:/# 
    ```

- 新建窗口创建 u2 容器网络
    ```
    guojj@guojj-VirtualBox:~$  docker run --name u2 --net mynet -it --rm ubuntu:net bash 
    root@7b2378aeb797:/# 
    ```

- 容器网络操作
    ```
    guojj@guojj-VirtualBox:~$ docker network connect bridge u1          #将 u1 连接到 bridge 网络
    guojj@guojj-VirtualBox:~$ docker network disconnect mynet u1        #将 u1 与 mynet 网络断开
    ```
---

[返回目录](..//ServiceComputingOnCloud-Catalog)