# spring-boot-demo-template-beetl

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent) 、`beetl-framework-starter`

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-template-beetl</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-template-beetl</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<dependencies>
		<dependency>
			<groupId>com.ibeetl</groupId>
			<artifactId>beetl-framework-starter</artifactId>
			<version>1.1.46.RELEASE</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-template-beetl</finalName>
	</build>

</project>
```

## HomeController.java

```java
/**
 * <p>
 * 首页 Controller
 * </p>
 *
 * @package: com.xkcoding.springbootdemotemplatebeetl.controller
 * @description： 首页 Controller
 * @author: yangkai.shen
 * @date: Created in 2018/4/26 下午4:36
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Controller
public class HomeController {

	@GetMapping({"", "/", "/index"})
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("/index.btl");
		User admin = new User(0, "admin", "phone0",true);
		List<User> userList = Lists.newArrayList(new User(1, "user1", "phone1",false), new User(2, "user2", "phone2",true), new User(3, "user3", "phone3",true),new User(4, "user4", "phone4",false));

		view.addObject("admin",admin);
		view.addObject("userList",userList);
		return view;
	}
}
```

## index.btl

```html
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
	      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Beetl demo</title>
</head>
<body>
this is index.btl to show some beetl demos!<br>

<!--demo01-->
<%
/* demo01 */
var a = 1;
var b = 2;
var result = a + b;
%>
1+2=${result}<br>

<!--demo02-->
<%
/* demo02 */
print(111);
%>

<!--demo03-->
欢迎登录，${admin.admin?"管理员":"员工"} ${admin.name}<br>

<!--demo04-->
<table>
	<thead>
	<tr>
		<th>序号</th>
		<th>编号</th>
		<th>姓名</th>
		<th>手机</th>
		<th>职位</th>
		<th>注册日期</th>
	</tr>
	</thead>
	<tbody>
	<%for(u in userList){%>
	<tr>
		<td>${uLP.index}</td>
		<td>${u.id}</td>
		<td>${u.name}</td>
		<td>${u.tel}</td>
		<td>${u.admin?"管理员":"员工"}</td>
		<%if(uLP.even){%>
			<td><%var today = date();%> ${today,dateFormat="yyyy-MM-dd HH:mm:ss"}</td>
		<%}else{%>
			<td><%var today = date();%> ${today,dateFormat="yyyy-MM-dd"}</td>
		<%}%>
	</tr>
	<%}%>
	</tbody>
</table>

</body>
</html>
```

## Beetl 语法

请查看 Beetl 官网：

http://ibeetl.com/guide/#beetl