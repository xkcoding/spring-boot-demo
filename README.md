# Spring Boot Demo

spring boot demo 是一个用来学习 spring boot 的项目，已经集成 actuator(监控)、admin(可视化监控)、logback(日志)、aopLog(通过 AOP 记录 web 请求日志)、统一异常处理( json 级别和页面级别)、freemarker(模板引擎)、thymeleaf(模板引擎)、JPA(ORM 框架)、mybatis(ORM 框架)、redis-cache(缓存)、swagger(API 接口管理测试)、ureport2(中国式报表)、打包成 war 文件、集成 ElasticSearch（采用原生操作 ES 的方式）、集成 Dubbo（采用非官方的 starter），后续会集成activemq,email,shiro,websocket,quartz,netty等模块。

依赖的 Spring Boot 版本：

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>1.5.8.RELEASE</version>
	<relativePath/> <!-- lookup parent from repository -->
</parent>
```

### 开发环境

- **JDK1.8 +**
- **Maven 3.5 +**
- **IntelliJ IDEA ULTIMATE 2017.2 +**
- **mysql 5.7 +** (*尽量5.7版本以上，因为5.7版本加了一些新特性，不向下兼容。本demo里会尽量避免这种不兼容的地方，但还是建议尽量保证5.7版本以上*)

### 开发计划

[TODO](./TODO.md)

### spring-boot-demo-parent

除了 **Dubbo** 的 demo 以外，所有的 **Module** 均依赖：[**spring-boot-demo-parent**](./spring-boot-demo-parent)

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.xkcoding</groupId>
	<artifactId>spring-boot-demo-parent</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>

	<name>spring-boot-demo-parent</name>
	<description>The parent of spring-boot-demo</description>

	<modules>
		<module>../spring-boot-demo-helloworld</module>
		<module>../spring-boot-demo-properties</module>
		<module>../spring-boot-demo-actuator</module>
		<module>../spring-boot-demo-admin</module>
		<module>../spring-boot-demo-logback</module>
		<module>../spring-boot-demo-aoplog</module>
		<module>../spring-boot-demo-exceptionhandler</module>
		<module>../spring-boot-demo-template-freemarker</module>
		<module>../spring-boot-demo-template-thymeleaf</module>
		<module>../spring-boot-demo-orm-jpa</module>
		<module>../spring-boot-demo-orm-mybatis</module>
		<module>../spring-boot-demo-cache-redis</module>
		<module>../spring-boot-demo-swagger</module>
		<module>../spring-boot-demo-rabc-shiro-mybatis</module>
		<module>../spring-boot-demo-ureport2</module>
		<module>../spring-boot-demo-war</module>
		<module>../spring-boot-demo-util</module>
		<module>../spring-boot-demo-elasticsearch</module>
	</modules>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.8.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<hutool.version>3.2.0</hutool.version>
		<commons.lang3.version>3.5</commons.lang3.version>
		<commons.collections.version>3.2.2</commons.collections.version>
		<commons.codec.version>1.10</commons.codec.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<!--DB-->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
		<!--工具类-->
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
			<version>${commons.lang3.version}</version>
		</dependency>
		<dependency>
			<groupId>commons-collections</groupId>
			<artifactId>commons-collections</artifactId>
			<version>${commons.collections.version}</version>
		</dependency>
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>${commons.codec.version}</version>
		</dependency>
		<dependency>
			<groupId>com.xiaoleilu</groupId>
			<artifactId>hutool-all</artifactId>
			<version>${hutool.version}</version>
		</dependency>
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>23.0</version>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
		</dependency>
	</dependencies>

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

### 各 Module 介绍

| Module 名称                                                  | Module 介绍                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [spring-boot-demo-helloworld](./spring-boot-demo-helloworld) | spring-boot 的一个 helloworld                                |
| [spring-boot-demo-properties](./spring-boot-demo-properties) | spring-boot 读取配置文件中的内容                             |
| [spring-boot-demo-actuator](./spring-boot-demo-actuator)     | spring-boot 集成 spring-boot-starter-actuator 用于监控 spring-boot 的启动和运行状态 |
| [spring-boot-demo-admin](./spring-boot-demo-admin)           | spring-boot 集成 spring-boot-admin 来可视化的监控 spring-boot 程序的运行状态，可以与 actuator 互相搭配使用 |
| [spring-boot-demo-logback](./spring-boot-demo-logback)       | spring-boot 集成 logback 日志                                |
| [spring-boot-demo-aoplog](./spring-boot-demo-aoplog)         | spring-boot 使用 AOP 切面的方式记录 web 请求日志             |
| [spring-boot-demo-exceptionhandler](./spring-boot-demo-exceptionhandler) | spring-boot 统一异常处理，包括2种，一种返回统一的 json 格式，第二种统一跳转到异常页面 |
| [spring-boot-demo-template-freemarker](./spring-boot-demo-template-freemarker) | spring-boot 集成 freemarker 模板引擎                         |
| [spring-boot-demo-template-thymeleaf](./spring-boot-demo-template-thymeleaf) | spring-boot 集成 thymeleaf 模板引擎                          |
| [spring-boot-demo-orm-jpa](./spring-boot-demo-orm-jpa)       | spring-boot 集成 spring-boot-starter-data-jpa 操作数据库     |
| [spring-boot-demo-orm-mybatis](./spring-boot-demo-orm-mybatis) | spring-boot 集成 [mybatis-spring-boot-starter](https://github.com/mybatis/spring-boot-starter)、[mybatis-spring-boot-starter](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter) |
| [spring-boot-demo-cache-redis](./spring-boot-demo-cache-redis) | spring-boot 使用 Redis 做缓存                                |
| [spring-boot-demo-swagger](./spring-boot-demo-swagger)       | spring-boot 集成 [spring-boot-starter-swagger](https://github.com/SpringForAll/spring-boot-starter-swagger) (由大佬[翟永超](http://blog.didispace.com/)开源)用于统一管理、测试 API 接口 |
| [spring-boot-demo-ureport2](./spring-boot-demo-ureport2)     | spring-boot 集成 [ureport2](https://github.com/youseries/ureport) 实现自定义报表（ureport2可以轻松实现复杂的中国式报表，功能十分强大） |
| [spring-boot-demo-war](./spring-boot-demo-war)               | spring-boot 打成 war 包的配置                                |
| [spring-boot-demo-elasticsearch](./spring-boot-demo-elasticsearch) | spring-boot 集成 ElasticSearch（采用原生操作 ES 的方式）     |
| [spring-boot-demo-dubbo-parent](./spring-boot-demo-dubbo-parent) | spring-boot 集成 Dubbo                                       |

# 官方提供的 starter 介绍

| Name                                   | Description                              |
| :------------------------------------- | :--------------------------------------- |
| spring-boot-starter                    | The core Spring Boot starter, including auto-configuration support, logging and YAML. |
| spring-boot-starter-actuator           | Production ready features to help you monitor and manage your application. |
| spring-boot-starter-amqp               | are neat                                 |
| spring-boot-starter-aop                | Support for aspect-oriented programming including spring-aop and AspectJ. |
| spring-boot-starter-artemis            | Support for “Java Message Service API” via Apache Artemis. |
| spring-boot-starter-batch              | Support for “Spring Batch” including HSQLDB database. |
| spring-boot-starter-cache              | Support for Spring’s Cache abstraction.  |
| spring-boot-starter-cloud-connectors   | Support for “Spring Cloud Connectors” which simplifies connecting to services in cloud platforms like Cloud Foundry and Heroku. |
| spring-boot-starter-data-elasticsearch | Support for the Elasticsearch search and analytics engine including spring-data-elasticsearch. |
| spring-boot-starter-data-gemfire       | Support for the GemFire distributed data store including spring-data-gemfire. |
| spring-boot-starter-data-jpa           | Support for the “Java Persistence API” including spring-data-jpa, spring-orm and Hibernate. |
| spring-boot-starter-data-mongodb       | Support for the MongoDB NoSQL Database, including spring-data-mongodb. |
| spring-boot-starter-data-rest          | Support for exposing Spring Data repositories over REST via spring-data-rest-webmvc. |
| spring-boot-starter-data-solr          | Support for the Apache Solr search platform, including spring-data-solr. |
| spring-boot-starter-freemarker         | Support for the FreeMarker templating engine. |
| spring-boot-starter-groovy-templates   | Support for the Groovy templating engine. |
| spring-boot-starter-hateoas            | Support for HATEOAS-based RESTful services via spring-hateoas. |
| spring-boot-starter-hornetq            | Support for “Java Message Service API” via HornetQ. |
| spring-boot-starter-integration        | Support for common spring-integration modules. |
| spring-boot-starter-jdbc               | Support for JDBC databases.              |
| spring-boot-starter-jersey             | Support for the Jersey RESTful Web Services framework. |
| spring-boot-starter-jta-atomikos       | Support for JTA distributed transactions via Atomikos. |
| spring-boot-starter-jta-bitronix       | Support for JTA distributed transactions via Bitronix. |
| spring-boot-starter-mail               | Support for javax.mail.                  |
| spring-boot-starter-mobile             | Support for spring-mobile.               |
| spring-boot-starter-mustache           | Support for the Mustache templating engine. |
| spring-boot-starter-redis              | Support for the REDIS key-value data store, including spring-redis. |
| spring-boot-starter-security           | Support for spring-security.             |
| spring-boot-starter-social-facebook    | Support for spring-social-facebook.      |
| spring-boot-starter-social-linkedin    | Support for spring-social-linkedin.      |
| spring-boot-starter-social-twitter     | Support for spring-social-twitter.       |
| spring-boot-starter-test               | Support for common test dependencies, including JUnit, Hamcrest and Mockito along with the spring-test module. |
| spring-boot-starter-thymeleaf          | Support for the Thymeleaf templating engine, including integration with Spring. |
| spring-boot-starter-velocity           | Support for the Velocity templating engine. |
| spring-boot-starter-web                | Support for full-stack web development, including Tomcat and spring-webmvc. |
| spring-boot-starter-websocket          | Support for WebSocket development.       |
| spring-boot-starter-ws                 | Support for Spring Web Services.         |

# License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2017 Yangkai.Shen