# spring-boot-demo-properties

> 本 demo 演示如何获取配置文件的自定义配置，以及如何多环境下的配置文件信息的获取

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-properties</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-properties</name>
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

		<!--
		在 META-INF/additional-spring-configuration-metadata.json 中配置
		可以去除 application.yml 中自定义配置的红线警告，并且为自定义配置添加 hint 提醒
		 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

		<dependency>
			<groupId>cn.hutool</groupId>
			<artifactId>hutool-all</artifactId>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-properties</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## ApplicationProperty.java

```java
/**
 * <p>
 * 项目配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-09-29 10:50
 */
@Data
@Component
public class ApplicationProperty {
	@Value("${application.name}")
	private String name;
	@Value("${application.version}")
	private String version;
}
```

## DeveloperProperty.java

```java
/**
 * <p>
 * 开发人员配置信息
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-09-29 10:51
 */
@Data
@ConfigurationProperties(prefix = "developer")
@Component
public class DeveloperProperty {
	private String name;
	private String website;
	private String qq;
	private String phoneNumber;
}
```

## PropertyController.java

```java
/**
 * <p>
 * 测试Controller
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-09-29 10:49
 */
@RestController
public class PropertyController {
	private final ApplicationProperty applicationProperty;
	private final DeveloperProperty developerProperty;

	@Autowired
	public PropertyController(ApplicationProperty applicationProperty, DeveloperProperty developerProperty) {
		this.applicationProperty = applicationProperty;
		this.developerProperty = developerProperty;
	}

	@GetMapping("/property")
	public Dict index() {
		return Dict.create().set("applicationProperty", applicationProperty).set("developerProperty", developerProperty);
	}
}
```

## additional-spring-configuration-metadata.json

> 位置： src/main/resources/META-INF/additional-spring-configuration-metadata.json

```json
{
	"properties": [
		{
			"name": "application.name",
			"description": "Default value is artifactId in pom.xml.",
			"type": "java.lang.String"
		},
		{
			"name": "application.version",
			"description": "Default value is version in pom.xml.",
			"type": "java.lang.String"
		},
		{
			"name": "developer.name",
			"description": "The Developer Name.",
			"type": "java.lang.String"
		},
		{
			"name": "developer.website",
			"description": "The Developer Website.",
			"type": "java.lang.String"
		},
		{
			"name": "developer.qq",
			"description": "The Developer QQ Number.",
			"type": "java.lang.String"
		},
		{
			"name": "developer.phone-number",
			"description": "The Developer Phone Number.",
			"type": "java.lang.String"
		}
	]
}
```

