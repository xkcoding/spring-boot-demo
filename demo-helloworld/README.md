# spring-boot-demo-helloworld

## Runing spring boot demo helloworld

```sh
 $ mvn spring-boot:run
```
##
> 本 demo 演示如何使用 Spring Boot 写一个hello world

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-helloworld</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-helloworld</name>
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

### SpringBootDemoHelloworldApplication.java

```java
/**
 * <p>
 * SpringBoot启动类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-09-28 14:49
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

**@SpringBootApplication**

`@SpringBootApplication` 标志着这是一个 Spring Boot 入门文件。在本例中以`SpringBootDemoHelloworldApplication` 类作为配置类来运行 Spring Boot 项目。

**@RestController**

`@RestController` 是创建 Restful 控制器的一个方便注释。在 Spring Boot 中 `@RestController` 的作用等同于 `@Controller` + `@ResponseBody`。它将响应转化为 `JSON` 或 `XML` ，与视图层不兼容，因此方法不能返回视图。

* `@Controller`：`@Controller` 用于标记在一个类上，使用它标记的类就是一个 Spring MVC 的 Controller 对象，分发处理器会扫描使用该注解的类的方法，并检测该方法是否使用了 `@RequestMapping` 注解。
* `@ResponseBody`：将 `@Controller` 的方法返回值通过适当的转换器转换为指定的格式之后，写入到 response 对象的 body 区，通常用来返回 JSON 数据或者是 XML 数据。此注解之后不会再走视图处理器，而是直接将数据写入到输入流中。

**@GetMapping**

`@GetMapping` 用于处理请求方法的*GET*类型。注解提供了路径信息，在本例中，它告诉Spring，任何带有 `/hello` 路径的 HTTP 请求都应该被映射到 `sayHello` 方法。

**@RequestParam** 

`@RequestParam` 指定 HTTP 参数与方法参数的映射关系。

* `required`：该参数是默认是true，表示请求中一定要传入对应的参数，否则会报404错误。如果设置为false时，当请求中没有此参数，将会默认为null。而对于基本数据类型的变量，则必须有值。如果允许空值，则接口中变量需要使用包装类来声明。
* `name`：请求中传入参数的名称。

### application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
```

`server.port`：使用 8080 端口启动应用。

` server.servlet.context-path`：应用的上下文路径，也可以称为项目路径，是构成url地址的一部分。不配置时，默认为 `/` ，如：localhost:8080/xxxx 。

