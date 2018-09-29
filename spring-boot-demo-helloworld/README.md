# spring-boot-demo-helloworld

> 本 demo 是 Spring Boot 的一个起步demo

## pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.xkcoding</groupId>
	<artifactId>spring-boot-demo-helloworld</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-helloworld</name>
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
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>cn.hutool</groupId>
			<artifactId>hutool-all</artifactId>
			<version>4.1.14</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-helloworld</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## SpringBootDemoHelloworldApplication.java

```java
/**
 * <p>
 * SpringBoot启动类
 * </p>
 *
 * @package: com.xkcoding.helloworld
 * @description: SpringBoot启动类
 * @author: yangkai.shen
 * @date: Created in 2018/9/28 2:49 PM
 * @copyright: Copyright (c)
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
@RestController
public class SpringBootDemoHelloworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoHelloworldApplication.class, args);
	}

	/**
	 * Hello，World
	 *
	 * @param who 参数，非必须
	 * @return Hello, ${who}
	 */
	@GetMapping("/hello")
	public String sayHello(@RequestParam(required = false, name = "who") String who) {
		if (StrUtil.isBlank(who)) {
			who = "World";
		}
		return StrUtil.format("Hello, {}!", who);
	}
}
```

## application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
```

