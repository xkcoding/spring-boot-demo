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

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-dubbo</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <modules>
        <module>spring-boot-demo-dubbo-common</module>
        <module>spring-boot-demo-dubbo-provider</module>
        <module>spring-boot-demo-dubbo-consumer</module>
    </modules>
    <packaging>pom</packaging>

    <name>spring-boot-demo-dubbo</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.xkcoding</groupId>
        <artifactId>spring-boot-demo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <dubbo.starter.version>2.0.0</dubbo.starter.version>
        <zkclient.version>0.10</zkclient.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## 参考

1. dubbo 官网：http://dubbo.apache.org/zh-cn/
2. [超详细，新手都能看懂 ！使用SpringBoot+Dubbo 搭建一个简单的分布式服务](https://segmentfault.com/a/1190000017178722#articleHeader20)

