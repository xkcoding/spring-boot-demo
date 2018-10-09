# spring-boot-demo-template-freemarker

> 本 demo 主要演示了 Spring Boot 项目如何集成 freemarker 模板引擎

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.xkcoding</groupId>
	<artifactId>spring-boot-template-freemarker</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-template-freemarker</name>
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
		<hutool.version>4.1.15</hutool.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-freemarker</artifactId>
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

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
		</dependency>

		<dependency>
			<groupId>cn.hutool</groupId>
			<artifactId>hutool-all</artifactId>
			<version>${hutool.version}</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-template-freemarker</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## IndexController.java

```java
/**
 * <p>
 * 主页
 * </p>
 *
 * @package: com.xkcoding.template.freemarker.controller
 * @description: 主页
 * @author: yangkai.shen
 * @date: Created in 2018/10/9 3:07 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller
@Slf4j
public class IndexController {

	@GetMapping(value = {"", "/"})
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		User user = (User) request.getSession().getAttribute("user");
		if (ObjectUtil.isNull(user)) {
			mv.setViewName("redirect:/user/login");
		} else {
			mv.setViewName("index");
			mv.addObject(user);
		}

		return mv;
	}
}
```

## UserController.java

```java
/**
 * <p>
 * 用户页面
 * </p>
 *
 * @package: com.xkcoding.template.freemarker.controller
 * @description: 用户页面
 * @author: yangkai.shen
 * @date: Created in 2018/10/9 3:11 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
	@PostMapping("/login")
	public ModelAndView login(User user, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		mv.addObject(user);
		mv.setViewName("redirect:/");

		request.getSession().setAttribute("user", user);
		return mv;
	}

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
}
```

## index.ftl

```jsp
<!doctype html>
<html lang="en">
<#include "./common/head.ftl">
<body>
<div id="app" style="margin: 20px 20%">
	欢迎登录，${user.name}！
</div>
</body>
</html>
```

## login.ftl

```jsp
<!doctype html>
<html lang="en">
<#include "./common/head.ftl">
<body>
<div id="app" style="margin: 20px 20%">
	<form action="/demo/user/login" method="post">
		用户名<input type="text" name="name" placeholder="用户名"/>
		密码<input type="password" name="password" placeholder="密码"/>
		<input type="submit" value="登录">
	</form>
</div>
</body>
</html>
```

