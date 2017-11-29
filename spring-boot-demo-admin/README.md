# spring-boot-demo-admin

依赖[spring-boot-demo-parent](../spring-boot-demo-parent)、服务端依赖 `spring-boot-demo-admin` 和 `spring-boot-admin-server-ui`、客户端依赖 `spring-boot-admin-starter-client`

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-admin</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-admin</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<properties>
		<admin.server.version>1.5.2</admin.server.version>
		<admin.server.ui.version>1.5.2</admin.server.ui.version>
		<admin.client.version>1.5.2</admin.client.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-server</artifactId>
			<version>${admin.server.version}</version>
		</dependency>
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-server-ui</artifactId>
			<version>${admin.server.ui.version}</version>
		</dependency>
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-starter-client</artifactId>
			<version>${admin.client.version}</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-admin</finalName>
	</build>

</project>
```

### application.yml

```yaml
server:
  port: 8080
  context-path: /demo
spring:
  application:
      # 可视化管控台展示的监控项目名，不设置，会使用自动生成的名字
      name: Spring Boot Admin
  boot:
    admin:
      # 可视化管控台界面的 context-path
      context-path: /spa
      url: http://localhost:${server.port}/${server.context-path}
  jackson:
    serialization: true
# 去除权限校验
endpoints:
  sensitive: false
```

### SpringBootDemoAdminApplication.java

```java
@SpringBootApplication
@EnableAdminServer // 开启管控台
@RestController
public class SpringBootDemoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoAdminApplication.class, args);
	}

	@GetMapping("/")
	public Map<String, Object> index() {
		ConcurrentMap<String, Object> ret = Maps.newConcurrentMap();
		ret.put("msg", "Hello Spring Boot Admin");
		return ret;
	}
}
```

### 访问

http://localhost:8080/demo/spa 即可查看管控台主页，点击项目的 `Detail` 即可查看详细信息，但是比起 `actuator` 提供的端点监控，看起来确实美观不少，但是都各有优缺点。