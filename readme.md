# Spring Boot Demo

依赖版本：

```xml
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>1.5.8.RELEASE</version>
	<relativePath/> <!-- lookup parent from repository -->
</parent>
```

### spring-boot-demo-parent

所有的**Module**均依赖：[**spring-boot-demo-parent**](https://github.com/xkcoding/spring-boot-demo/tree/master/spring-boot-demo-parent)

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
		<module>../spring-boot-demo-logback</module>
		<module>../spring-boot-demo-mybatis</module>
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
			<groupId>com.xiaoleilu</groupId>
			<artifactId>hutool-all</artifactId>
			<version>3.1.2</version>
		</dependency>
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>20.0</version>
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

### 官方提供的starter

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

