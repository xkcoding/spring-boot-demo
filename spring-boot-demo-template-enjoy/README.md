# spring-boot-demo-template-enjoy

> 本 demo 主要演示了 Spring Boot 项目如何集成 enjoy 模板引擎。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-template-enjoy</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-template-enjoy</name>
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
		<enjoy.version>3.5</enjoy.version>
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
			<groupId>com.jfinal</groupId>
			<artifactId>enjoy</artifactId>
			<version>${enjoy.version}</version>
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
		<finalName>spring-boot-demo-template-enjoy</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## EnjoyConfig.java

```java
/**
 * <p>
 * Enjoy 模板配置类
 * </p>
 *
 * @package: com.xkcoding.template.enjoy.config
 * @description: Enjoy 模板配置类
 * @author: yangkai.shen
 * @date: Created in 2018/10/11 2:06 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
public class EnjoyConfig {
   @Bean(name = "jfinalViewResolver")
   public JFinalViewResolver getJFinalViewResolver() {
      JFinalViewResolver jfr = new JFinalViewResolver();
      // setDevMode 配置放在最前面
      jfr.setDevMode(true);
      // 使用 ClassPathSourceFactory 从 class path 与 jar 包中加载模板文件
      jfr.setSourceFactory(new ClassPathSourceFactory());
      // 在使用 ClassPathSourceFactory 时要使用 setBaseTemplatePath
      // 代替 jfr.setPrefix("/view/")
      JFinalViewResolver.engine.setBaseTemplatePath("/templates/");

      jfr.setSessionInView(true);
      jfr.setSuffix(".html");
      jfr.setContentType("text/html;charset=UTF-8");
      jfr.setOrder(0);
      return jfr;
   }
}
```

## IndexController.java

```java
/**
 * <p>
 * 主页
 * </p>
 *
 * @package: com.xkcoding.template.enjoy.controller
 * @description: 主页
 * @author: yangkai.shen
 * @date: Created in 2018/10/11 2:22 PM
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
			mv.setViewName("page/index");
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
 * @package: com.xkcoding.template.enjoy.controller
 * @description: 用户页面
 * @author: yangkai.shen
 * @date: Created in 2018/10/11 2:24 PM
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
		return new ModelAndView("page/login");
	}
}
```

## index.html

```jsp
<!doctype html>
<html lang="en">
#include("/common/head.html")
<body>
<div id="app" style="margin: 20px 20%">
	欢迎登录，#(user.name)！
</div>
</body>
</html>
```

## login.html

```jsp
<!doctype html>
<html lang="en">
#include("/common/head.html")
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

## application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
```

## Enjoy 语法糖学习文档

http://www.jfinal.com/doc/6-1



