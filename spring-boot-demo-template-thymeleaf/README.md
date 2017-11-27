# spring-boot-demo-template-thymeleaf
依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)、`spring-boot-starter-thymeleaf`

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-template-thymeleaf</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-template-thymeleaf</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-template-thymeleaf</finalName>
	</build>

</project>
```

### application.yml

```yaml
server:
  port: 8080
  context-path: /demo
spring:
  thymeleaf:
    mode: HTML5
    encoding: UTF-8
    content-type: text/html
    cache: false
```

### SpringBootDemoTemplateThymeleafApplication.java

```java
@SpringBootApplication
@Controller
public class SpringBootDemoTemplateThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoTemplateThymeleafApplication.class, args);
	}

	@GetMapping({"", "/"})
	public ModelAndView index(@RequestParam(defaultValue = "thymeleaf") String name) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", name);
		mv.setViewName("index");
		return mv;
	}
}
```

### index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="UTF-8"/>
	<title>spring-boot-demo-template-thymeleaf</title>
</head>
<body>
<span th:text="'Hello,'+${name}+'!'"></span>
</body>
</html>
```

### 测试

浏览器分别输入：

http://localhost:8080/demo → Hello,thymeleaf!

http://localhost:8080/demo?name=xkcoding → Hello,xkcoding!

### Thymeleaf 语法

待补充。。。