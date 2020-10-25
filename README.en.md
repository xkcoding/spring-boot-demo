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

`spring boot demo` is a project for learning and practicing `spring boot`, including `66` demos, and `54` of them have been done.

This project has integrated actuator (`monitoring`), admin (`visual monitoring`), logback (`log`), aopLog (`recording web request logs through AOP`), global exception handling (`json level and page level` ), freemarker (`template engine`), thymeleaf (`template engine`), Beetl (`template engine`), Enjoy (`template engine`), JdbcTemplate (`general JDBC operate database`), JPA (`powerful ORM framework `), mybatis (`powerful ORM framework`), Generic Mapper (`mybatis quick operation `), PageHelper (`powerful mybatis pagination plugin`), mybatis-plus (`mybatis quick operation`), BeetlSQL (`powerful ORM framework `), upload (`local file upload and qiniu cloud file upload`), redis (`cache`), ehcache (`cache`), email (`send various types of mail`), task (`basic scheduled tasks`), quartz (`dynamic management scheduled tasks`), xxl-job (`distributed scheduled tasks`), swagger (`API interface management and tests`), security (`RBAC-based Dynamic Rights Authentication`), SpringSession (`session sharing`), Zookeeper (`implement distributed locks by AOP`), RabbitMQ (`message queue`), Kafka (`message queue`), websocket (` server pushes the monitoring server status to front end `), socket.io (`chat room`), ureport2 (`Chinese-style report`), packaged into a `war` file, integrate ElasticSearch (`basic operations and advanced queries`), Async ( `asynchronous tasks`), integrated Dubbo (`with official starter`), MongoDB (`document database`), neo4j (`graph database`), docker (`container`), `JPA Multi-Datasource`, `Mybatis Multi-Datasource`, `code generator`', GrayLog (`log collection`), JustAuth (`third-party login`), LDAP(`CURD`), `Dynamically add/switch datasources`, Standalone RateLimiting(`AOP + Guava RateLimiter`), Distributed Ratelimiting(`AOP + Redis + Lua`), ElasticSearch 7.x(`use official Rest High Level Client`), HTTPS, Flyway(`initialize databases`).

> If you have demos to contribute or needs to meet, it is very welcome to submit a [issue](https://github.com/xkcoding/spring-boot-demo/issues/new) and I will add it to my [TODO](./TODO.en.md) list.

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

## Stargazers over time

[![Stargazers over time](https://starchart.cc/xkcoding/spring-boot-demo.svg)](https://starchart.cc/xkcoding/spring-boot-demo)

## Appendix

### Recommended  Open source

- `JustAuth`：The most comprehensive open source library for third-party logins in history，https://github.com/justauth/JustAuth
- `Mica`：Spring Boot microservices efficient development toolset，https://github.com/lets-mica/mica
- `awesome-collector`：https://github.com/P-P-X/awesome-collector
- `SpringBlade`：Complete micro-service online solution (required for enterprise development)，https://github.com/chillzhuang/SpringBlade
- `Pig`：The universe's strongest micro-service certification authorized scaffolding (architect necessary)，https://github.com/pigxcloud/pig

### TODO

View the [TODO](./TODO.en.md) file

### Introduction of each Module

| Module Name                                                  | Module Description                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [demo-helloworld](./demo-helloworld) | a helloworld demo.                                           |
| [demo-properties](./demo-properties) | a demo to read the contents of configuration file.           |
| [demo-actuator](./demo-actuator)     | a demo to integrate spring-boot-starter-actuator for monitoring the starting status and the running status of application. |
| [demo-admin-client](./demo-admin/admin-client) | a client demo to  integrate spring-boot-admin for visually monitoring the running status of application, it can be used with spring-boot-starter-actuator. |
| [demo-admin-server](./demo-admin/admin-server) | a server demo to  integrate spring-boot-admin for visually monitoring the running status of the spring-boot program, it can be used with spring-boot-starter-actuator. |
| [demo-logback](./demo-logback)       | a demo to integrate the logback for logging.                 |
| [demo-log-aop](./demo-log-aop)       | a demo to record web request logs using AOP aspect.          |
| [demo-exception-handler](./demo-exception-handler) | a demo to demonstrate global exception handling, including 2 types, the first one returns  json data, and the second one jumps to error page. |
| [demo-template-freemarker](./demo-template-freemarker) | a demo to integrate Freemarker template engine.              |
| [demo-template-thymeleaf](./demo-template-thymeleaf) | a demo to integrate Thymeleaf template engine.               |
| [demo-template-beetl](./demo-template-beetl) | a demo to integrate Beetl template engine.                   |
| [demo-template-enjoy](./demo-template-enjoy) | a demo to integrate Enjoy template engine.                   |
| [demo-orm-jdbctemplate](./demo-orm-jdbctemplate) | a demo to integrate the Jdbc Template for operating database and easily encapsulate the generic Dao layer. |
| [demo-orm-jpa](./demo-orm-jpa)       | a demo to integrate spring-boot-starter-data-jpa for operating database. |
| [demo-orm-mybatis](./demo-orm-mybatis) | a demo to integrate native mybatis by using [mybatis-spring-boot-starter](https://github.com/mybatis/spring-boot-starter) dependency. |
| [demo-orm-mybatis-mapper-page](./demo-orm-mybatis-mapper-page) | a demo to integrate [Mapper](https://github.com/abel533/Mapper) and [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) by using [mapper-spring-boot-starter](https://github.com/abel533/Mapper/tree/master/spring-boot-starter) and [pagehelper-spring-boot-starter](https://github.com/pagehelper/pagehelper-spring-boot) dependencies. |
| [demo-orm-mybatis-plus](./demo-orm-mybatis-plus) | a demo to integrate [mybatis-plus](https://mybatis.plus/en/) by using [mybatis-plus-boot-starter](http://mp.baomidou.com/) dependency, integrate BaseMapper / BaseService / ActiveRecord to operate database. |
| [demo-orm-beetlsql](./demo-orm-beetlsql) | a demo to integrate [beetl-sql](http://ibeetl.com/guide/#beetlsql) by using [beetl-framework-starter](http://ibeetl.com/guide/#beetlsql) dependency. |
| [demo-upload](./demo-upload)         | a file upload demo, including local file upload and qiniu cloud file upload. |
| [demo-cache-redis](./demo-cache-redis) | a demo to integrate redis, operate data in redis, and use redis to cache data. |
| [demo-cache-ehcache](./demo-cache-ehcache) | a demo to integrate ehcache, and use ehcache to cache data.  |
| [demo-email](./demo-email)           | a demo to integrate email, including sending simple text email, HTML email (including template HTML email), attachment email, and static resource email. |
| [demo-task](./demo-task)             | a demo to show easy to use scheduled task.                   |
| [demo-task-quartz](./demo-task-quartz) | a demo to integrate quartz for managing scheduled tasks, including adding new scheduled tasks, deleting scheduled tasks, suspending scheduled tasks, restoring scheduled tasks, modifying scheduled task startup times, and timing task list queries, and `providing front-end pages`. |
| [demo-task-xxl-job](./demo-task-xxl-job) | a demo to integrate [xxl-job](http://www.xuxueli.com/xxl-job/en/#/) for distributed scheduled tasks and provide methods to manage scheduled tasks bypass `xxl-job-admin`, including scheduled task lists, trigger lists, new scheduled tasks, deleted scheduled tasks, stopped scheduled tasks, and started scheduled tasks. Modify the scheduled task and manually trigger the scheduled task. |
| [demo-swagger](./demo-swagger)       | a demo to integrate native `swagger` to manage and test API interfaces. |
| [demo-swagger-beauty](./demo-swagger-beauty) | a demo to integrate third part of swagger dependency [swagger-bootstrap-ui](https://github.com/xiaoymin/Swagger-Bootstrap-UI) to beautify document style and manage and test API interfaces. |
| [demo-rbac-security](./demo-rbac-security) | a demo to integrate spring security implement privilege management based on RBAC privilege model, supports custom filtering request, dynamic privilege authentication, uses JWT security authentication, supports online population statistics, manually kicks out users, etc. |
| [demo-rbac-shiro](./demo-rbac-shiro) | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate shiro for authentication management. |
| [demo-session](./demo-session)       | a demo to integrate Spring Session to implement Session sharing, restart program Session does not expire. |
| [demo-oauth](./demo-oauth)           | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to implement the oauth server and  to implement oauth2 protocol such as the authorization code, access token. |
| [demo-social](./demo-social)         | a demo to integrate third-party login by using `justauth-spring-boot-starter` dependency to achieve QQ login, GitHub login, WeChat login, Google login, Microsoft login, Xiaomi login, enterprise WeChat login. |
| [demo-zookeeper](./demo-zookeeper)   | a demo to integrate Zookeeper and AOP to implement distributed lock. |
| [demo-mq-rabbitmq](./demo-mq-rabbitmq) | a demo to integrate RabbitMQ implementation for message delivery and reception based on direct queue mode, fanout mode, topic mode, delay queue. |
| [demo-mq-rocketmq](./demo-mq-rocketmq) | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate RocketMQ implementation for message delivery and reception. |
| [demo-mq-kafka](./demo-mq-kafka)     | a demo to integrate Kafka implementation for message delivery and reception. |
| [demo-websocket](./demo-websocket)   | a demo to integrate websocket, the backend actively pushes the server running status to front end. |
| [demo-websocket-socketio](./demo-websocket-socketio) | a demo to integrate websocket by using `netty-socketio`, implement a simple chat room. |
| [demo-ureport2](./demo-ureport2)     | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate [ureport2](https://github.com/youseries/ureport) to implement complex, customized Chinese-style reports. |
| [demo-uflo](./demo-uflo)             | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate [uflo](https://github.com/youseries/uflo)(process engine like Activiti and Flowable) to quickly implement a lightweight process engine. |
| [demo-urule](./demo-urule)           | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate [urule](https://github.com/youseries/urule)(rule engine like drools) fast implementation rule engine. |
| [demo-activiti](./demo-activiti)     | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate Activiti 7 process engine. |
| [demo-async](./demo-async)           | asynchronous execution of tasks by using natively provided asynchronous task support. |
| [demo-war](./demo-war)               | packaged into a war format configuration                     |
| [demo-elasticsearch](./demo-elasticsearch) | a demo to integrate ElasticSearch by using `spring-boot-starter-data-elasticsearch` to implement advanced techniques for using ElasticSearch, including creating indexes, configuring mappings, deleting indexes, adding and deleting basic operations, complex queries, advanced queries, aggregate queries, etc. |
| [demo-dubbo](./demo-dubbo)           | a demo to integrate Dubbo, common module `spring-boot-demo-dubbo-common`, service provider `spring-boot-demo-dubbo-provider`, service consumer `spring-boot-demo-dubbo-consumer`. |
| [demo-mongodb](./demo-mongodb)       | a demo to integrate MongoDB and use the official starter to CRUD. |
| [demo-neo4j](./demo-neo4j)           | a demo to integrate Neo4j graph database to implement a campus character relationship network. |
| [demo-docker](./demo-docker)         | docker container.                                            |
| [demo-multi-datasource-jpa](./demo-multi-datasource-jpa) | a demo to implement JPA multi-datasource.                    |
| [demo-multi-datasource-mybatis](./demo-multi-datasource-mybatis) | a demo to implement Mybatis multi-datasource by using an open source solution from Mybatis-Plus. |
| [demo-sharding-jdbc](./demo-sharding-jdbc) | a demo to use `sharding-jdbc` to implement sub-database and sub-tables, while ORM uses Mybatis-Plus. |
| [demo-tio](./demo-tio)               | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate t-io(a network programming framework like netty). |
| demo-grpc            | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate Google grpc, need to be configure tls/ssl, see [ISSUE#5](https://github.com/xkcoding/spring-boot-demo/issues/5). |
| [demo-codegen](./demo-codegen)       | a demo to integrate velocity template engine to implement code generator, improve development efficiency. |
| [demo-graylog](./demo-graylog)       | a demo to integrate graylog for unified log collection.      |
| demo-sso                                         | <span style="color:pink;">NOT FINISHED YET!</span> <br />a demo to integrate Single Sign On, see [ISSUE#12](https://github.com/xkcoding/spring-boot-demo/issues/12). |
| [demo-ldap](./demo-ldap)             | a demo to integrate LDAP to use `spring-boot-starter-data-ldap` to implement  CURD operations and give the login demo, see [ISSUE#23](https://github.com/xkcoding/spring-boot-demo/issues/23), thanks [@fxbin](https://github.com/fxbin). |
| [demo-dynamic-datasource](./demo-dynamic-datasource) | a demo to add datasource dynamically, switch datasource dynamically. |
| [demo-ratelimit-guava](./demo-ratelimit-guava) | a demo to use use Guava RateLimiter to protect API  by standalone rate limiting. |
| [demo-ratelimit-redis](./demo-ratelimit-redis) | a demo to use Redis and Lua script implementation to protect API by distributed rate limiting. |
| [demo-https](./demo-https)           | a demo to integrate HTTPS.                                   |
| [demo-elasticsearch-rest-high-level-client](./demo-elasticsearch-rest-high-level-client) | a demo to integrate ElasticSearch 7.x version by using official Rest High Level Client to operate ES data. |
| [demo-flyway](./demo-flyway)              | a demo to integrate Flyway to initialize tables and data in database, Flyway also support the sql script version control. |

### Thanks

- <a href="https://www.jetbrains.com/?from=spring-boot-demo"><img src="http://static.xkcoding.com/spring-boot-demo/064312.jpg" width="100px" alt="jetbrains">**Thanks JetBrains Offer Open Source Free License**</a>
-  [Thanks MyBatisCodeHelper-Pro(The Best Code Generator Plugin) Offer Permanent Activation Code](https://gejun123456.github.io/MyBatisCodeHelper-Pro/#/?id=mybatiscodehelper-pro)

### License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2018 Yangkai.Shen
