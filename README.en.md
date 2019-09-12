<h1 align="center"><a href="https://github.com/xkcoding" target="_blank">Spring Boot Demo</a></h1>
<p align="center">
  <a href="https://travis-ci.com/xkcoding/spring-boot-demo"><img alt="Travis-CI" src="https://travis-ci.com/xkcoding/spring-boot-demo.svg?branch=master"/></a>
  <a href="https://www.codacy.com/app/xkcoding/spring-boot-demo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=xkcoding/spring-boot-demo&amp;utm_campaign=Badge_Grade"><img alt="Codacy" src="https://api.codacy.com/project/badge/Grade/1f2e3d437b174bfc943dae1600332ec1"/></a>
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

`spring boot demo` is a project for learning and practicing `spring boot`, including `59` demos, and `49` of them have been done.

This project has integrated actuator (`monitoring`), admin (`visual monitoring`), logback (`log`), aopLog (`recording web request logs through AOP`), global exception handling (`json level and page level` ), freemarker (`template engine`), thymeleaf (`template engine`), Beetl (`template engine`), Enjoy (`template engine`), JdbcTemplate (`general JDBC operate database`), JPA (`powerful ORM framework `), mybatis (`powerful ORM framework`), Generic Mapper (`mybatis quick operation `), PageHelper (`powerful mybatis pagination plugin`), mybatis-plus (`mybatis quick operation`), BeetlSQL (`powerful ORM framework `), upload (`local file upload and qiniu cloud file upload`), redis (`cache`), ehcache (`cache`), email (`send various types of mail`), task (`basic scheduled tasks`), quartz (`dynamic management scheduled tasks`), xxl-job (`distributed scheduled tasks`), swagger (`API interface management and tests`), security (`RBAC-based Dynamic Rights Authentication`), SpringSession (`session sharing`), Zookeeper (`implement distributed locks by AOP`), RabbitMQ (`message queue`), Kafka (`message queue`), websocket (` server pushes the monitoring server status to front end `), socket.io (`chat room`), ureport2 (`Chinese-style report`), packaged into a `war` file, integrates ElasticSearch (`basic operations and advanced queries`), Async ( `asynchronous tasks`), integrated Dubbo (`with official starter`), MongoDB (`document database`), neo4j (`graph database`), docker (`container`), `JPA Multi-Datasource`, `Mybatis Multi-Datasource`, `code generator`', GrayLog (`log collection`), JustAuth (`third-party login`), LDAP(`CURD`), `Dynamically add/switch datasources`, Standalone RateLimiting(`AOP + Guava RateLimiter`).

> If you have demos to contribute or needs to meet, it is very welcome to submit a [issue](https://github.com/xkcoding/spring-boot-demo/issues/new) and I will add it to my [TODO](./TODO.en.md) list.

## Sponsers

<a href="https://www.jetbrains.com/?from=spring-boot-demo"><img src="assets/jetbrains.png" width="100px" alt="jetbrains"></a>

## Branch Introduction

- branch master: Based on Spring Boot version `2.1.0.RELEASE`. Every module's parent dependency is the pom.xml at root directory in convenience of managing common dependencies and learning spring boot.
- branch v-1.5.x: Based on Spring Boot version `1.5.8.RELEASE`. Every module's parent dependency is spring-boot-demo-parent. But since the feedback shows that it is not much friendly to many new learners, this branch will not be mantained any more. All of the demos will be moved to branch master. Everyone could still study at this branch but it's suggested to study at branch master while Spring Boot has much new content over version `2.x`.

## Environment

- **JDK 1.8 +**
- **Maven 3.5 +**
- **IntelliJ IDEA ULTIMATE 2018.2 +** (*Note: Please use IDEA and make sure plugin `lombok` installed.*)
- **Mysql 5.7 +** (*Please use version 5.7 or higher because mysql has some new features and is not backward compatible at version 5.7. Althought this project will try to avoid this incompatibility*)

## Getting Started

> Note: If you has been forked this project, need to sync the project's code, please see: https://xkcoding.com/2018/09/18/how-to-update-the-fork-project.html

1. `git clone https://github.com/xkcoding/spring-boot-demo.git`
2. Open the cloned project in IDEA
3. Import the `pom.xml` file from the root directory using `Maven Projects` panel
4. If you can not find `Maven Projects` panel, try to tick `View -> Tool Buttons` on and the `Maven Projects` panel will appear on the right side of IDEA.
5. Find each Application class to run each module.
6. **`Note: Each demo has a detailed README file. Remember to check it before running the demo~`**
7. **`Note: In some condition you have to execute sql to prepare data before running demo, don't forget it~`**

## TODO

View the [TODO](./TODO.en.md) file

## Introduction of each Module

| Module Name                                                  | Module Description                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [spring-boot-demo-helloworld](./spring-boot-demo-helloworld) | a helloworld demo.                                           |
| [spring-boot-demo-properties](./spring-boot-demo-properties) | a demo to read the contents of configuration file.           |
| [spring-boot-demo-actuator](./spring-boot-demo-actuator)     | a demo to integrate spring-boot-starter-actuator for monitoring the starting status and the running status of application. |
| [spring-boot-demo-admin-client](./spring-boot-demo-admin/spring-boot-demo-admin-client) | a client demo to  integrate spring-boot-admin for visually monitoring the running status of application, it can be used with spring-boot-starter-actuator. |
| [spring-boot-demo-admin-server](./spring-boot-demo-admin/spring-boot-demo-admin-server) | a server demo to  integrate spring-boot-admin for visually monitoring the running status of the spring-boot program, it can be used with spring-boot-starter-actuator. |
| [spring-boot-demo-logback](./spring-boot-demo-logback)       | a demo to integrate the logback for logging.                 |
| [spring-boot-demo-log-aop](./spring-boot-demo-log-aop)       | a demo to record web request logs using AOP aspect.          |
| [spring-boot-demo-exception-handler](./spring-boot-demo-exception-handler) | a demo to demonstrate global exception handling, including 2 types, the first one returns  json data, and the second one jumps to error page. |
| [spring-boot-demo-template-freemarker](./spring-boot-demo-template-freemarker) | a demo to integrate Freemarker template engine.              |
| [spring-boot-demo-template-thymeleaf](./spring-boot-demo-template-thymeleaf) | a demo to integrate Thymeleaf template engine.               |
| [spring-boot-demo-template-beetl](./spring-boot-demo-template-beetl) | a demo to integrate Beetl template engine.                   |
| [spring-boot-demo-template-enjoy](./spring-boot-demo-template-enjoy) | a demo to integrate Enjoy template engine.                   |
| [spring-boot-demo-orm-jdbctemplate](./spring-boot-demo-orm-jdbctemplate) | a demo to integrate the Jdbc Template for operating database and easily encapsulate the generic Dao layer. |
| [spring-boot-demo-orm-jpa](./spring-boot-demo-orm-jpa)       | a demo to integrate spring-boot-starter-data-jpa for operating database. |
| [spring-boot-demo-orm-mybatis](./spring-boot-demo-orm-mybatis) | a demo to integrate native mybatis by using [mybatis-spring-boot-starter](https://github.com/mybatis/spring-boot-starter) dependency. |
| [spring-boot-demo-orm-mybatis-mapper-page](./spring-boot-demo-orm-mybatis-mapper-page) | a demo to integrate [Mapper](https://github.com/abel533/Mapper) and [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) by using [mapper-spring-boot-starter](https://github.com/abel533/Mapper/tree/master/spring-boot-starter) and [pagehelper-spring-boot-starter](https://github.com/pagehelper/pagehelper-spring-boot) dependencies. |
| [spring-boot-demo-orm-mybatis-plus](./spring-boot-demo-orm-mybatis-plus) | a demo to integrate [mybatis-plus](https://mybatis.plus/en/) by using [mybatis-plus-boot-starter](http://mp.baomidou.com/) dependency. |
| [spring-boot-demo-orm-beetlsql](./spring-boot-demo-orm-beetlsql) | a demo to integrate [beetl-sql](http://ibeetl.com/guide/#beetlsql) by using [beetl-framework-starter](http://ibeetl.com/guide/#beetlsql) dependency. |
| [spring-boot-demo-upload](./spring-boot-demo-upload)         | a file upload demo, including local file upload and qiniu cloud file upload. |
| [spring-boot-demo-cache-redis](./spring-boot-demo-cache-redis) | a demo to integrate redis, operate data in redis, and use redis to cache data. |
| [spring-boot-demo-cache-ehcache](./spring-boot-demo-cache-ehcache) | a demo to integrate ehcache, and use ehcache to cache data.  |
| [spring-boot-demo-email](./spring-boot-demo-email)           | a demo to integrate email, including sending simple text email, HTML email (including template HTML email), attachment email, and static resource email. |
| [spring-boot-demo-task](./spring-boot-demo-task)             | a demo to show easy to use scheduled task.                   |
| [spring-boot-demo-task-quartz](./spring-boot-demo-task-quartz) | a demo to integrate quartz for managing scheduled tasks, including adding new scheduled tasks, deleting scheduled tasks, suspending scheduled tasks, restoring scheduled tasks, modifying scheduled task startup times, and timing task list queries, and `providing front-end pages`. |
| [spring-boot-demo-task-xxl-job](./spring-boot-demo-task-xxl-job) | a demo to integrate [xxl-job](http://www.xuxueli.com/xxl-job/en/#/) for distributed scheduled tasks and provide methods to manage scheduled tasks bypass `xxl-job-admin`, including scheduled task lists, trigger lists, new scheduled tasks, deleted scheduled tasks, stopped scheduled tasks, and started scheduled tasks. Modify the scheduled task and manually trigger the scheduled task. |
| [spring-boot-demo-swagger](./spring-boot-demo-swagger)       | a demo to integrate native `swagger` to manage and test API interfaces. |
| [spring-boot-demo-swagger-beauty](./spring-boot-demo-swagger-beauty) | a demo to integrate third part of swagger dependency [swagger-bootstrap-ui](https://github.com/xiaoymin/Swagger-Bootstrap-UI) to beautify document style and manage and test API interfaces. |
| [spring-boot-demo-rbac-security](./spring-boot-demo-rbac-security) | a demo to integrate spring security implement privilege management based on RBAC privilege model, supports custom filtering request, dynamic privilege authentication, uses JWT security authentication, supports online population statistics, manually kicks out users, etc. |
| [spring-boot-demo-rbac-shiro](./spring-boot-demo-rbac-shiro) | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate shiro for authentication management. |
| [spring-boot-demo-session](./spring-boot-demo-session)       | a demo to integrate Spring Session to implement Session sharing, restart program Session does not expire. |
| [spring-boot-demo-oauth](./spring-boot-demo-oauth)           | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to implement the oauth server and  to implement oauth2 protocol such as the authorization code, access token. |
| [spring-boot-demo-social](./spring-boot-demo-social)         | a demo to integrate third-party login by using `justauth-spring-boot-starter` dependency to achieve QQ login, GitHub login, WeChat login, Google login, Microsoft login, Xiaomi login, enterprise WeChat login. |
| [spring-boot-demo-zookeeper](./spring-boot-demo-zookeeper)   | a demo to integrate Zookeeper and AOP to implement distributed lock. |
| [spring-boot-demo-mq-rabbitmq](./spring-boot-demo-mq-rabbitmq) | a demo to integrate RabbitMQ implementation for message delivery and reception based on direct queue mode, fanout mode, topic mode, delay queue. |
| [spring-boot-demo-mq-rocketmq](./spring-boot-demo-mq-rocketmq) | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate RocketMQ implementation for message delivery and reception. |
| [spring-boot-demo-mq-kafka](./spring-boot-demo-mq-kafka)     | a demo to integrate Kafka implementation for message delivery and reception. |
| [spring-boot-demo-websocket](./spring-boot-demo-websocket)   | a demo to integrate websocket, the backend actively pushes the server running status to front end. |
| [spring-boot-demo-websocket-socketio](./spring-boot-demo-websocket-socketio) | a demo to integrate websocket by using `netty-socketio`, implement a simple chat room. |
| [spring-boot-demo-ureport2](./spring-boot-demo-ureport2)     | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate [ureport2](https://github.com/youseries/ureport) to implement complex, customized Chinese-style reports. |
| [spring-boot-demo-uflo](./spring-boot-demo-uflo)             | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate [uflo](https://github.com/youseries/uflo)(process engine like Activiti and Flowable) to quickly implement a lightweight process engine. |
| [spring-boot-demo-urule](./spring-boot-demo-urule)           | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate [urule](https://github.com/youseries/urule)(rule engine like drools) fast implementation rule engine. |
| [spring-boot-demo-activiti](./spring-boot-demo-activiti)     | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate Activiti 7 process engine. |
| [spring-boot-demo-async](./spring-boot-demo-async)           | asynchronous execution of tasks by using natively provided asynchronous task support. |
| [spring-boot-demo-war](./spring-boot-demo-war)               | packaged into a war format configuration                     |
| [spring-boot-demo-elasticsearch](./spring-boot-demo-elasticsearch) | a demo to integrate ElasticSearch by using `spring-boot-starter-data-elasticsearch` to implement advanced techniques for using ElasticSearch, including creating indexes, configuring mappings, deleting indexes, adding and deleting basic operations, complex queries, advanced queries, aggregate queries, etc. |
| [spring-boot-demo-dubbo](./spring-boot-demo-dubbo)           | a demo to integrate Dubbo, common module `spring-boot-demo-dubbo-common`, service provider `spring-boot-demo-dubbo-provider`, service consumer `spring-boot-demo-dubbo-consumer`. |
| [spring-boot-demo-mongodb](./spring-boot-demo-mongodb)       | a demo to integrate MongoDB and use the official starter to CRUD. |
| [spring-boot-demo-neo4j](./spring-boot-demo-neo4j)           | a demo to integrate Neo4j graph database to implement a campus character relationship network. |
| [spring-boot-demo-docker](./spring-boot-demo-docker)         | docker container.                                            |
| [spring-boot-demo-multi-datasource-jpa](./spring-boot-demo-multi-datasource-jpa) | a demo to implement JPA multi-datasource.                    |
| [spring-boot-demo-multi-datasource-mybatis](./spring-boot-demo-multi-datasource-mybatis) | a demo to implement Mybatis multi-datasource by using an open source solution from Mybatis-Plus. |
| [spring-boot-demo-sharding-jdbc](./spring-boot-demo-sharding-jdbc) | a demo to use `sharding-jdbc` to implement sub-database and sub-tables, while ORM uses Mybatis-Plus. |
| [spring-boot-demo-tio](./spring-boot-demo-tio)               | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate t-io(a network programming framework like netty). |
| [spring-boot-demo-grpc](./spring-boot-demo-grpc)             | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate Google grpc, need to be configure tls/ssl, see [ISSUE#5](https://github.com/xkcoding/spring-boot-demo/issues/5). |
| [spring-boot-demo-codegen](./spring-boot-demo-codegen)       | a demo to integrate velocity template engine to implement code generator, improve development efficiency. |
| [spring-boot-demo-graylog](./spring-boot-demo-graylog)       | a demo to integrate graylog for unified log collection.      |
| spring-boot-demo-sso                                         | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate Single Sign On, see [ISSUE#12](https://github.com/xkcoding/spring-boot-demo/issues/12). |
| [spring-boot-demo-ldap](./spring-boot-demo-ldap)             | a demo to integrate LADP to use `spring-boot-starter-data-ldap` to implement  CURD operations and give the login demo, see [ISSUE#23](https://github.com/xkcoding/spring-boot-demo/issues/23), thanks [@fxbin](https://github.com/fxbin). |
| [spring-boot-demo-dynamic-datasource](./spring-boot-demo-dynamic-datasource) | a demo to add datasource dynamically, switch datasource dynamically. |
| [spring-boot-demo-ratelimit-guava](./spring-boot-demo-ratelimit-guava) | a demo to use use Guava RateLimiter to protect API  by standalone rate limiting. |
| spring-boot-demo-ratelimit-redis                             | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to use use Redis Token Bucket to protect API  by cluster rate limiting. |
| spring-boot-demo-https                                       | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate HTTPS. |

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
    <module>spring-boot-demo-admin</module>
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
    <module>spring-boot-demo-ldap</module>
    <module>spring-boot-demo-dynamic-datasource</module>
    <module>spring-boot-demo-ratelimit-guava</module>
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
    <hutool.version>4.6.5</hutool.version>
    <guava.version>28.1-jre</guava.version>
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

### Recommended  Open source

- `JustAuth`：The most comprehensive open source library for third-party logins in history，https://github.com/justauth/JustAuth
- `Mica`：Spring Boot microservices efficient development toolset，https://github.com/lets-mica/mica
- `awesome-collector`：https://github.com/P-P-X/awesome-collector
- `SpringBlade`：Complete micro-service online solution (required for enterprise development)，https://github.com/chillzhuang/SpringBlade
- `Pig`：The universe's strongest micro-service certification authorized scaffolding (architect necessary)，https://github.com/pigxcloud/pig
