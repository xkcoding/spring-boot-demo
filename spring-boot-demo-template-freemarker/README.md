# spring-boot-demo-template-freemarker

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent) 、`spring-boot-starter-freemarker`

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-template-freemarker</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<name>spring-boot-demo-template-freemarker</name>
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
			<artifactId>spring-boot-starter-freemarker</artifactId>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-template-freemarker</finalName>
	</build>

</project>
```

### SpringBootDemoTemplateFreemarkerApplication.java

```java
@Controller
@SpringBootApplication
public class SpringBootDemoTemplateFreemarkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoTemplateFreemarkerApplication.class, args);
	}

	@GetMapping({"","/"})
	public ModelAndView home(){
		return new ModelAndView("index");
	}
}
```

### UserController.java

```java
/**
 * 用户 Controller
 *
 * @package: com.xkcoding.springbootdemotemplatefreemarker.controller
 * @description： 用户 Controller
 * @author: yangkai.shen
 * @date: Created in 2017/11/26 下午6:47
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
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
		mv.setViewName("home");

		request.getSession().setAttribute("user", user);
		return mv;
	}

	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			mv.setViewName("redirect:/");
		} else {
			mv.setViewName("home");
		}

		return mv;
	}
}
```

### home.ftl

```html
<!doctype html>
<html lang="en">
<#include "./common/header.ftl">
<body>
<div id="app" style="margin: 20px 20%">
	欢迎登录，${user.name}！
</div>
</body>
</html>
```

### index.ftl

```html
<!doctype html>
<html lang="en">
<#include "./common/header.ftl">
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

### Freemarker 语法

待补充。。。