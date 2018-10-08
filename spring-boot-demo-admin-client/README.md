# spring-boot-demo-admin-client

> 本 demo 主要演示了普通项目如何集成 Spring Boot Admin，并把自己的运行状态交给 Spring Boot Admin 进行展现。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.xkcoding</groupId>
	<artifactId>spring-boot-demo-admin-client</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-admin-client</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.5.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-boot-admin.version>2.0.3</spring-boot-admin.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-starter-client</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>de.codecentric</groupId>
				<artifactId>spring-boot-admin-dependencies</artifactId>
				<version>${spring-boot-admin.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<finalName>spring-boot-demo-admin-client</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
spring:
  application:
    # Spring Boot Admin展示的客户端项目名，不设置，会使用自动生成的随机id
    name: spring-boot-demo-admin-client
  boot:
    admin:
      client:
        # Spring Boot Admin 服务端地址
        url: "http://localhost:8000/admin"
        instance:
          metadata:
            # 客户端端点信息的安全认证信息
            user.name: ${spring.security.user.name}
            user.password: ${spring.security.user.password}
  security:
    user:
      name: xkcoding
      password: 123456
management:
  endpoint:
    health:
      # 端点健康情况，默认值"never"，设置为"always"可以显示硬盘使用情况和线程情况
      show-details: always
  endpoints:
    web:
      exposure:
        # 设置端点暴露的哪些内容，默认["health","info"]，设置"*"代表暴露所有可访问的端点
        include: "*"
```

