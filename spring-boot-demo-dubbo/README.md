# spring-boot-demo-dubbo

> 此 demo 主要演示了 Spring Boot 如何集成 Dubbo，demo 分了3个module，分别为公共模块 `spring-boot-demo-dubbo-common`、服务提供方`spring-boot-demo-dubbo-provider`、服务调用方`spring-boot-demo-dubbo-consumer`

## 注意

本例注册中心使用的是 zookeeper，作者编写本demo时，采用docker方式运行 zookeeper

1. 下载镜像：`docker pull wurstmeister/zookeeper`

2. 运行容器：`docker run -d -p 2181:2181 -p 2888:2888 -p 2222:22 -p 3888:3888 --name zk wurstmeister/zookeeper`

3. 停止容器：`docker stop zk`

4. 启动容器：`docker start zk`

## 运行步骤

1. 进入服务提供方 `spring-boot-demo-dubbo-provider` 目录，运行 `SpringBootDemoDubboProviderApplication.java`
2. 进入服务调用方 `spring-boot-demo-dubbo-consumer` 目录，运行 `SpringBootDemoDubboConsumerApplication.java`
3. 打开浏览器输入 http://localhost:8080/demo/sayHello ，观察浏览器输出，以及服务提供方和服务调用方的控制台输出日志情况

## 参考

1. dubbo 官网：http://dubbo.apache.org/zh-cn/
2. [超详细，新手都能看懂 ！使用SpringBoot+Dubbo 搭建一个简单的分布式服务](https://segmentfault.com/a/1190000017178722#articleHeader20)

