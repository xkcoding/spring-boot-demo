# spring-boot-demo-mq-rabbitmq



## 注意

作者编写本demo时，RabbitMQ 版本使用 `3.7.7-management`，使用 docker 运行，下面是所有步骤：

1. 下载镜像：`docker pull rabbitmq:3.7.7-management`

2. 运行容器：`docker run -d -p 5671:5617 -p 5672:5672 -p 4369:4369 -p 15671:15671 -p 15672:15672 -p 25672:25672 --name rabbit-3.7.7 rabbitmq:3.7.7-management`

3. 进入容器：`docker exec -it rabbit-3.7.7 /bin/bash`

4. 给容器安装 下载工具 wget：`apt-get install -y wget`

5. 下载插件包，因为我们的 `RabbitMQ` 版本为 `3.7.7` 所以我们安装 `3.7.x` 版本的延迟队列插件

   ```bash
   root@f72ac937f2be:/plugins# wget https://dl.bintray.com/rabbitmq/community-plugins/3.7.x/rabbitmq_delayed_message_exchange/rabbitmq_delayed_message_exchange-20171201-3.7.x.zip
   ```

6. 给容器安装 解压工具 unzip：`apt-get install -y unzip`

7. 解压插件包

   ```bash
   root@f72ac937f2be:/plugins# unzip rabbitmq_delayed_message_exchange-20171201-3.7.x.zip
   Archive:  rabbitmq_delayed_message_exchange-20171201-3.7.x.zip
     inflating: rabbitmq_delayed_message_exchange-20171201-3.7.x.ez
   ```

8. 启动延迟队列插件

   ```yaml
   root@f72ac937f2be:/plugins# rabbitmq-plugins enable rabbitmq_delayed_message_exchange
   The following plugins have been configured:
     rabbitmq_delayed_message_exchange
     rabbitmq_management
     rabbitmq_management_agent
     rabbitmq_web_dispatch
   Applying plugin configuration to rabbit@f72ac937f2be...
   The following plugins have been enabled:
     rabbitmq_delayed_message_exchange
   
   started 1 plugins.
   ```

9. 退出容器：`exit`

10. 停止容器：`docker stop rabbitmq:3.7.7-management`

11. 启动容器：`docker start rabbitmq:3.7.7-management`

