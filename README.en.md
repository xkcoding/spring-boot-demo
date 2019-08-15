<h1 align="center"><a href="https://github.com/xkcoding" target="_blank">Spring Boot Demo</a></h1>
<p align="center">
  <a href="https://travis-ci.com/xkcoding/spring-boot-demo"><img alt="Travis-CI" src="https://travis-ci.com/xkcoding/spring-boot-demo.svg?branch=master"/></a>
  <a href="https://xkcoding.com"><img alt="author" src="https://img.shields.io/badge/author-Yangkai.Shen-blue.svg"/></a>
  <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8.0_162-orange.svg"/></a>
  <a href="https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/html/"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.1.0.RELEASE-brightgreen.svg"/></a>
  <a href="https://github.com/xkcoding/spring-boot-demo/blob/master/LICENSE"><img alt="LICENSE" src="https://img.shields.io/github/license/xkcoding/spring-boot-demo.svg"/></a>  
</p>

<p align="center">
  <a href="https://github.com/xkcoding/spring-boot-demo/stargazers"><img alt="star" src="https://img.shields.io/github/stars/xkcoding/spring-boot-demo.svg?label=Stars&style=social"/></a>
  <a href="https://github.com/xkcoding/spring-boot-demo/network/members"><img alt="star" src="https://img.shields.io/github/forks/xkcoding/spring-boot-demo.svg?label=Fork&style=social"/></a>
  <a href="https://github.com/xkcoding/spring-boot-demo/watchers"><img alt="star" src="https://img.shields.io/github/watchers/xkcoding/spring-boot-demo.svg?label=Watch&style=social"/></a>
</p>

<p align="center">
  <span>English | <a href="./README.md">中文</a></span>
</p>

## Introduction

`spring boot demo `  is a project for learning and practice `spring boot`, with a total of `57` integrated demos, and `47` have been completed.

The project has successfully integrated actuator (`monitoring`), admin (`visual monitoring`), logback (`log`), aopLog (`recording web request logs through AOP`), global exception handling (`json level and page level` ), freemarker (`template engine`), thymeleaf (`template engine`), Beetl (`template engine`), Enjoy (`template engine`), JdbcTemplate (`general JDBC operate database`), JPA (`powerful ORM framework `), mybatis (`powerful ORM framework`), Generic Mapper (`mybatis quick operation `), PageHelper (`powerful mybatis pagination plugin`), mybatis-plus (`mybatis quick operation`), BeetlSQL (`powerful ORM framework `), upload (`local file upload and qiniu cloud file upload`), redis (`cache`), ehcache (`cache`), email (`send various types of mail`), task (`basic scheduled tasks`), quartz (`dynamic management scheduled tasks`), xxl-job (`distributed scheduled tasks`), swagger (`API interface management and tests`), security (`RBAC-based Dynamic Rights Authentication`), SpringSession (`session sharing`), Zookeeper (`implement distributed locks by AOP`), RabbitMQ (`message queue`), Kafka (`message queue`), websocket (` server pushes the monitoring server status to front end `), socket.io (`chat room`), ureport2 (`Chinese-style report`), packaged into a `war` file, integrates ElasticSearch (`basic operations and advanced queries`), Async ( `asynchronous tasks`), integrated Dubbo (`with official starter`), MongoDB (`document database`), neo4j (`graph database`), docker (`container`), `JPA Multi-Datasource`, `Mybatis Multi-Datasource`, `code generator`', GrayLog (`log collection`), JustAuth (`third-party login`).

> If you still have a demo that you want to integrate, you can also request it in [issue] (https://github.com/xkcoding/spring-boot-demo/issues/new). I will add it to the [TODO](./TODO.en.md) list.✊

## Branches

- master: Based on the Spring Boot version `2.1.0.RELEASE`, the parent of each Module depends on the pom.xml in the root directory, which is mainly used to manage the generic dependencies' version of each Module. It's easier to learn this project.
- v-1.5.x: Based on the Spring Boot version `1.5.8.RELEASE`, each module depends on spring-boot-demo-parent. There are quite a few friends who report that this way is not very friendly to rookies and it is a little difficult to run this project, so ***The branch (v-1.5.x) will stop development and maintenance***, and all the content will be slowly synchronized to the master branch. If this branch is not completed yet in the TODO list, it will be added directly to the master branch. Everyone can still learn in this branch, but still suggest to switch to the master branch in the future. After all, the official has been upgraded to the Spring Boot version `2.x`. 🙂

## Environment

- **JDK 1.8 +**
- **Maven 3.5 +**
- **IntelliJ IDEA ULTIMATE 2018.2 +** (*Note: Be sure to use IDEA and ensure that the `lombok` plugin is installed.*)
- **Mysql 5.7 +** (*Try to be sure to use version 5.7 or higher, because version 5.7 adds some new features and is not backward compatible. This project will try to avoid this incompatibility, but it is recommended to ensure 5.7 or higher.*)

## Getting Started

1. `git clone https://github.com/xkcoding/spring-boot-demo.git`
2. Open the cloned project using IDEA
3. Open the project in IDEA
4. Import the `pom.xml` file from the root of the project using the `Maven Projects` panel
5. If you can not find the `Maven Projects` panel, you can try to check `View -> Tool Buttons` on the top toolbar of IDEA and the `Maven Projects` panel will appear on the right side of IDEA.
6. 找到各个 Module 的 Application 类就可以运行各个 demo 了
7. **`Note: Each demo has a detailed README file. Remember to check it before running the demo~`**
8. **`Note: Before running each demo, some need to initialize the database data beforehand, don't forget it~`**

## Plan

View the [TODO](./TODO.en.md) file

## Introduction of each Module

| Module 名称                                                  | Module 介绍                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [spring-boot-demo-helloworld](./spring-boot-demo-helloworld) | spring-boot 的一个 helloworld                                |
| [spring-boot-demo-properties](./spring-boot-demo-properties) | spring-boot 读取配置文件中的内容                             |
| [spring-boot-demo-actuator](./spring-boot-demo-actuator)     | spring-boot 集成 spring-boot-starter-actuator 用于监控 spring-boot 的启动和运行状态 |
| [spring-boot-demo-admin-client](./spring-boot-demo-admin-client) | spring-boot 集成 spring-boot-admin 来可视化的监控 spring-boot 程序的运行状态，可以与 actuator 互相搭配使用，客户端示例 |
| [spring-boot-demo-admin-server](./spring-boot-demo-admin-server) | spring-boot 集成 spring-boot-admin 来可视化的监控 spring-boot 程序的运行状态，可以与 actuator 互相搭配使用，服务端示例 |
| [spring-boot-demo-logback](./spring-boot-demo-logback)       | spring-boot 集成 logback 日志                                |
| [spring-boot-demo-log-aop](./spring-boot-demo-log-aop)       | spring-boot 使用 AOP 切面的方式记录 web 请求日志             |
| [spring-boot-demo-exception-handler](./spring-boot-demo-exception-handler) | spring-boot 统一异常处理，包括2种，第一种返回统一的 json 格式，第二种统一跳转到异常页面 |
| [spring-boot-demo-template-freemarker](./spring-boot-demo-template-freemarker) | spring-boot 集成 Freemarker 模板引擎                         |
| [spring-boot-demo-template-thymeleaf](./spring-boot-demo-template-thymeleaf) | spring-boot 集成 Thymeleaf 模板引擎                          |
| [spring-boot-demo-template-beetl](./spring-boot-demo-template-beetl) | spring-boot 集成 Beetl 模板引擎                              |
| [spring-boot-demo-template-enjoy](./spring-boot-demo-template-enjoy) | spring-boot 集成 Enjoy 模板引擎                              |
| [spring-boot-demo-orm-jdbctemplate](./spring-boot-demo-orm-jdbctemplate) | spring-boot 集成 Jdbc Template 操作数据库，并简易封装通用 Dao 层 |
| [spring-boot-demo-orm-jpa](./spring-boot-demo-orm-jpa)       | spring-boot 集成 spring-boot-starter-data-jpa 操作数据库     |
| [spring-boot-demo-orm-mybatis](./spring-boot-demo-orm-mybatis) | spring-boot 集成原生mybatis，使用 [mybatis-spring-boot-starter](https://github.com/mybatis/spring-boot-starter) 集成 |
| [spring-boot-demo-orm-mybatis-mapper-page](./spring-boot-demo-orm-mybatis-mapper-page) | spring-boot 集成通用Mapper和PageHelper，使用 [mapper-spring-boot-starter](https://github.com/abel533/Mapper/tree/master/spring-boot-starter) 和 [pagehelper-spring-boot-starter](https://github.com/pagehelper/pagehelper-spring-boot) 集成 |
| [spring-boot-demo-orm-mybatis-plus](./spring-boot-demo-orm-mybatis-plus) | spring-boot 集成 mybatis-plus，使用 [mybatis-plus-boot-starter](http://mp.baomidou.com/) 集成 |
| [spring-boot-demo-orm-beetlsql](./spring-boot-demo-orm-beetlsql) | spring-boot 集成 beetl sql，使用 [beetl-framework-starter](http://ibeetl.com/guide/#beetlsql) 集成 |
| [spring-boot-demo-upload](./spring-boot-demo-upload)         | spring-boot 文件上传示例，包含本地文件上传以及七牛云文件上传 |
| [spring-boot-demo-cache-redis](./spring-boot-demo-cache-redis) | spring-boot 整合 redis，操作redis中的数据，并使用redis缓存数据 |
| [spring-boot-demo-cache-ehcache](./spring-boot-demo-cache-ehcache) | spring-boot 整合 ehcache，使用 ehcache 缓存数据              |
| [spring-boot-demo-email](./spring-boot-demo-email)           | spring-boot 整合 email，包括发送简单文本邮件、HTML邮件（包括模板HTML邮件）、附件邮件、静态资源邮件 |
| [spring-boot-demo-task](./spring-boot-demo-task)             | spring-boot 快速实现定时任务                                 |
| [spring-boot-demo-task-quartz](./spring-boot-demo-task-quartz) | spring-boot 整合 quartz，并实现对定时任务的管理，包括新增定时任务，删除定时任务，暂停定时任务，恢复定时任务，修改定时任务启动时间，以及定时任务列表查询，`提供前端页面` |
| [spring-boot-demo-task-xxl-job](./spring-boot-demo-task-xxl-job) | spring-boot 整合 xxl-job，并提供绕过 `xxl-job-admin` 对定时任务的管理的方法，包括定时任务列表，触发器列表，新增定时任务，删除定时任务，停止定时任务，启动定时任务，修改定时任务，手动触发定时任务 |
| [spring-boot-demo-swagger](./spring-boot-demo-swagger)       | spring-boot 集成原生的 swagger 用于统一管理、测试 API 接口   |
| [spring-boot-demo-swagger-beauty](./spring-boot-demo-swagger-beauty) | spring-boot 集成第三方 swagger 美化API文档样式，用于统一管理、测试 API 接口 |
| [spring-boot-demo-rbac-security](./spring-boot-demo-rbac-security) | spring-boot 集成 spring security 完成基于RBAC权限模型的权限管理，支持自定义过滤请求，动态权限认证，使用 JWT 安全认证，支持在线人数统计，手动踢出用户等操作 |
| [spring-boot-demo-rbac-shiro](./spring-boot-demo-rbac-shiro) | spring-boot 集成 shiro 实现权限管理 <span style="color:pink;">待完成</span> |
| [spring-boot-demo-session](./spring-boot-demo-session)       | spring-boot 集成 Spring Session 实现Session共享、重启程序Session不失效 |
| [spring-boot-demo-oauth](./spring-boot-demo-oauth)           | spring-boot 实现 oauth 服务器功能，实现授权码机制 <span style="color:pink;">待完成</span> |
| [spring-boot-demo-social](./spring-boot-demo-social)         | spring-boot 集成第三方登录，集成 `justauth-spring-boot-starter` 实现QQ登录、GitHub登录、微信登录、谷歌登录、微软登录、小米登录、企业微信登录。 |
| [spring-boot-demo-zookeeper](./spring-boot-demo-zookeeper)   | spring-boot 集成 Zookeeper 结合AOP实现分布式锁               |
| [spring-boot-demo-mq-rabbitmq](./spring-boot-demo-mq-rabbitmq) | spring-boot 集成 RabbitMQ 实现基于直接队列模式、分列模式、主题模式、延迟队列的消息发送和接收 |
| [spring-boot-demo-mq-rocketmq](./spring-boot-demo-mq-rocketmq) | spring-boot 集成 RocketMQ，实现消息的发送和接收 <span style="color:pink;">待完成</span> |
| [spring-boot-demo-mq-kafka](./spring-boot-demo-mq-kafka)     | spring-boot 集成 kafka，实现消息的发送和接收                 |
| [spring-boot-demo-websocket](./spring-boot-demo-websocket)   | spring-boot 集成 websocket，后端主动推送前端服务器运行信息   |
| [spring-boot-demo-websocket-socketio](./spring-boot-demo-websocket-socketio) | spring-boot 使用 netty-socketio 集成 websocket，实现一个简单的聊天室 |
| [spring-boot-demo-ureport2](./spring-boot-demo-ureport2)     | spring-boot 集成 ureport2 实现复杂的自定义的中国式报表 <span style="color:pink;">待完成</span> |
| [spring-boot-demo-uflo](./spring-boot-demo-uflo)             | spring-boot 集成 uflo 快速实现轻量级流程引擎 <span style="color:pink;">待完成</span> |
| [spring-boot-demo-urule](./spring-boot-demo-urule)           | spring-boot 集成 urule 快速实现规则引擎 <span style="color:pink;">待完成</span> |
| [spring-boot-demo-activiti](./spring-boot-demo-activiti)     | spring-boot 集成 activiti 7  流程引擎 <span style="color:pink;">待完成</span> |
| [spring-boot-demo-async](./spring-boot-demo-async)           | spring-boot 使用原生提供的异步任务支持，实现异步执行任务     |
| [spring-boot-demo-war](./spring-boot-demo-war)               | spring-boot 打成 war 包的配置                                |
| [spring-boot-demo-elasticsearch](./spring-boot-demo-elasticsearch) | spring-boot 集成 ElasticSearch，集成 `spring-boot-starter-data-elasticsearch` 完成对 ElasticSearch 的高级使用技巧，包括创建索引、配置映射、删除索引、增删改查基本操作、复杂查询、高级查询、聚合查询等 |
| [spring-boot-demo-dubbo](./spring-boot-demo-dubbo)           | spring-boot 集成 Dubbo，分别为公共模块 `spring-boot-demo-dubbo-common`、服务提供方`spring-boot-demo-dubbo-provider`、服务调用方`spring-boot-demo-dubbo-consumer` |
| [spring-boot-demo-mongodb](./spring-boot-demo-mongodb)       | spring-boot 集成 MongoDB，使用官方的 starter 实现增删改查    |
| [spring-boot-demo-neo4j](./spring-boot-demo-neo4j)           | spring-boot 集成 Neo4j 图数据库，实现一个校园人物关系网的demo |
| [spring-boot-demo-docker](./spring-boot-demo-docker)         | spring-boot 容器化                                           |
| [spring-boot-demo-multi-datasource-jpa](./spring-boot-demo-multi-datasource-jpa) | spring-boot 使用JPA集成多数据源                              |
| [spring-boot-demo-multi-datasource-mybatis](./spring-boot-demo-multi-datasource-mybatis) | spring-boot 使用Mybatis集成多数据源，使用 Mybatis-Plus 提供的开源解决方案实现 |
| [spring-boot-demo-sharding-jdbc](./spring-boot-demo-sharding-jdbc) | spring-boot 使用 `sharding-jdbc` 实现分库分表，同时ORM采用 Mybatis-Plus |
| [spring-boot-demo-tio](./spring-boot-demo-tio)               | spring-boot 集成 tio 网络编程框架 <span style="color:pink;">待完成</span> |
| [spring-boot-demo-grpc](./spring-boot-demo-grpc)             | spring-boot 集成grpc，配置tls/ssl，参见[ISSUE#5](https://github.com/xkcoding/spring-boot-demo/issues/5) <span style="color:pink;">待完成</span> |
| [spring-boot-demo-codegen](./spring-boot-demo-codegen)       | spring-boot 集成 velocity 模板技术实现的代码生成器，简化开发 |
| [spring-boot-demo-graylog](./spring-boot-demo-graylog)       | spring-boot 集成 graylog 实现日志统一收集                    |
| spring-boot-demo-sso                                         | spring-boot 集成 SSO 单点登录，参见 [ISSUE#12](https://github.com/xkcoding/spring-boot-demo/issues/12) <span style="color:pink;">待完成</span> |

## License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2018 Yangkai.Shen

## Stargazers over time

[![Stargazers over time](https://starchart.cc/xkcoding/spring-boot-demo.svg)](https://starchart.cc/xkcoding/spring-boot-demo)

## Appendix

### Pom.xml in the root directory

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.xkcoding</groupId>
  <artifactId>spring-boot-demo</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <modules>
    <module>spring-boot-demo-helloworld</module>
    <module>spring-boot-demo-properties</module>
    <module>spring-boot-demo-actuator</module>
    <module>spring-boot-demo-admin-client</module>
    <module>spring-boot-demo-admin-server</module>
    <module>spring-boot-demo-logback</module>
    <module>spring-boot-demo-log-aop</module>
    <module>spring-boot-demo-exception-handler</module>
    <module>spring-boot-demo-template-freemarker</module>
    <module>spring-boot-demo-template-thymeleaf</module>
    <module>spring-boot-demo-template-beetl</module>
    <module>spring-boot-demo-template-enjoy</module>
    <module>spring-boot-demo-orm-jdbctemplate</module>
    <module>spring-boot-demo-orm-jpa</module>
    <module>spring-boot-demo-orm-mybatis</module>
    <module>spring-boot-demo-orm-mybatis-mapper-page</module>
    <module>spring-boot-demo-orm-mybatis-plus</module>
    <module>spring-boot-demo-orm-beetlsql</module>
    <module>spring-boot-demo-upload</module>
    <module>spring-boot-demo-cache-redis</module>
    <module>spring-boot-demo-cache-ehcache</module>
    <module>spring-boot-demo-email</module>
    <module>spring-boot-demo-task</module>
    <module>spring-boot-demo-task-quartz</module>
    <module>spring-boot-demo-task-xxl-job</module>
    <module>spring-boot-demo-swagger</module>
    <module>spring-boot-demo-swagger-beauty</module>
    <module>spring-boot-demo-rbac-security</module>
    <module>spring-boot-demo-rbac-shiro</module>
    <module>spring-boot-demo-session</module>
    <module>spring-boot-demo-oauth</module>
    <module>spring-boot-demo-social</module>
    <module>spring-boot-demo-zookeeper</module>
    <module>spring-boot-demo-mq-rabbitmq</module>
    <module>spring-boot-demo-mq-rocketmq</module>
    <module>spring-boot-demo-mq-kafka</module>
    <module>spring-boot-demo-websocket</module>
    <module>spring-boot-demo-websocket-socketio</module>
    <module>spring-boot-demo-ureport2</module>
    <module>spring-boot-demo-uflo</module>
    <module>spring-boot-demo-urule</module>
    <module>spring-boot-demo-activiti</module>
    <module>spring-boot-demo-async</module>
    <module>spring-boot-demo-dubbo</module>
    <module>spring-boot-demo-war</module>
    <module>spring-boot-demo-elasticsearch</module>
    <module>spring-boot-demo-mongodb</module>
    <module>spring-boot-demo-neo4j</module>
    <module>spring-boot-demo-docker</module>
    <module>spring-boot-demo-multi-datasource-jpa</module>
    <module>spring-boot-demo-multi-datasource-mybatis</module>
    <module>spring-boot-demo-sharding-jdbc</module>
    <module>spring-boot-demo-tio</module>
    <module>spring-boot-demo-codegen</module>
    <module>spring-boot-demo-graylog</module>
  </modules>
  <packaging>pom</packaging>

  <name>spring-boot-demo</name>
  <url>http://xkcoding.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <spring.boot.version>2.1.0.RELEASE</spring.boot.version>
    <mysql.version>8.0.12</mysql.version>
    <hutool.version>4.5.1</hutool.version>
    <guava.version>27.0.1-jre</guava.version>
    <user.agent.version>1.20</user.agent.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>${spring.boot.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
      </dependency>
      <!-- hutool工具类 -->
      <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>${hutool.version}</version>
      </dependency>
      <!-- guava工具类 -->
      <dependency>
        <groupId>com.google.guava</groupId>
        <artifactId>guava</artifactId>
        <version>${guava.version}</version>
      </dependency>
      <!-- 解析 UserAgent 信息 -->
      <dependency>
        <groupId>eu.bitwalker</groupId>
        <artifactId>UserAgentUtils</artifactId>
        <version>${user.agent.version}</version>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.0.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.7.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.20.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-jar-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
        <plugin>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-maven-plugin</artifactId>
          <version>${spring.boot.version}</version>
          <executions>
            <execution>
              <goals>
                <goal>repackage</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
```

### Official starter introduction

| Name                                   | Description                                                  |
| :------------------------------------- | :----------------------------------------------------------- |
| spring-boot-starter                    | The core Spring Boot starter, including auto-configuration support, logging and YAML. |
| spring-boot-starter-actuator           | Production ready features to help you monitor and manage your application. |
| spring-boot-starter-amqp               | Support for RabbitMQ messages                                |
| spring-boot-starter-aop                | Support for aspect-oriented programming including spring-aop and AspectJ. |
| spring-boot-starter-batch              | Support for “Spring Batch” including HSQLDB database.        |
| spring-boot-starter-cache              | Support for Spring’s Cache abstraction.                      |
| spring-boot-starter-data-elasticsearch | Support for the Elasticsearch search and analytics engine including spring-data-elasticsearch. |
| spring-boot-starter-data-jpa           | Support for the “Java Persistence API” including spring-data-jpa, spring-orm and Hibernate. |
| spring-boot-starter-data-mongodb       | Support for the MongoDB NoSQL Database, including spring-data-mongodb. |
| spring-boot-starter-data-rest          | Support for exposing Spring Data repositories over REST via spring-data-rest-webmvc. |
| spring-boot-starter-data-solr          | Support for the Apache Solr search platform, including spring-data-solr. |
| spring-boot-starter-freemarker         | Support for the FreeMarker templating engine.                |
| spring-boot-starter-groovy-templates   | Support for the Groovy templating engine.                    |
| spring-boot-starter-integration        | Support for common spring-integration modules.               |
| spring-boot-starter-jdbc               | Support for JDBC databases.                                  |
| spring-boot-starter-jersey             | Support for the Jersey RESTful Web Services framework.       |
| spring-boot-starter-jta-atomikos       | Support for JTA distributed transactions via Atomikos.       |
| spring-boot-starter-jta-bitronix       | Support for JTA distributed transactions via Bitronix.       |
| spring-boot-starter-mail               | Support for javax.mail.                                      |
| spring-boot-starter-mustache           | Support for the Mustache templating engine.                  |
| spring-boot-starter-redis              | Support for the REDIS key-value data store, including spring-redis. |
| spring-boot-starter-security           | Support for spring-security.                                 |
| spring-boot-starter-social-facebook    | Support for spring-social-facebook.                          |
| spring-boot-starter-social-linkedin    | Support for spring-social-linkedin.                          |
| spring-boot-starter-social-twitter     | Support for spring-social-twitter.                           |
| spring-boot-starter-test               | Support for common test dependencies, including JUnit, Hamcrest and Mockito along with the spring-test module. |
| spring-boot-starter-thymeleaf          | Support for the Thymeleaf templating engine, including integration with Spring. |
| spring-boot-starter-velocity           | Support for the Velocity templating engine.                  |
| spring-boot-starter-web                | Support for full-stack web development, including Tomcat and spring-webmvc. |
| spring-boot-starter-websocket          | Support for WebSocket development.                           |
| spring-boot-starter-ws                 | Support for Spring Web Services.                             |