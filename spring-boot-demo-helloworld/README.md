# spring-boot-demo-helloworld

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-helloworld</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-helloworld</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<build>
		<finalName>spring-boot-demo-helloworld</finalName>
	</build>

</project>
```

### SpringBootDemoHelloworldApplication.java

```java
@RestController
@SpringBootApplication
@Configuration
public class SpringBootDemoHelloworldApplication {
	@Value("${spring.boot.demo.helloworld.data.version}")
	private String version;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoHelloworldApplication.class, args);
	}

	@GetMapping("/hello")
	public Map sayHello(@Value("${author}") String author, @Value("${exclusions}") String exclusions, @Value("${connectionProperties}") String connectionProperties) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("ret", true);
		result.put("msg", StrUtil.format("hello,world,{}", author));
		Map<String, Object> data = Maps.newHashMap();
		data.put("version", version);
		data.put("exclusions", exclusions.split(","));
		Map<String, Object> connectionProperty = Maps.newHashMap();
		for (String connection : connectionProperties.split(";")) {
			String[] conn = connection.split("=");
			connectionProperty.put(conn[0], conn[1]);
		}
		data.put("connectionProperties", connectionProperty);
		result.put("data", data);
		return result;
	}
}
```

### application.yml

```yaml
server:
  port: 8080
  context-path: /demo
spring:
  boot:
    demo:
      helloworld:
        data:
          version: 1.0.0
author: xkcoding
exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
```

