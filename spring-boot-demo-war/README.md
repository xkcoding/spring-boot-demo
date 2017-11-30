# spring-boot-demo-war

依赖[spring-boot-demo-parent](../spring-boot-demo-parent)、`spring-boot-starter-tomcat`

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-war</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<!-- 打成 war 包修改的第 ① 处 -->
	<packaging>war</packaging>

	<name>spring-boot-demo-war</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<dependencies>
		<!-- 打成 war 包修改的第 ② 处 -->
		<!-- 若需要打成 war 包，则需要将 tomcat 引入，scope 设置为 provided -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-war</finalName>
	</build>

</project>
```

### ServletInitializer.java

```java
/**
 * 新建一个 ServletInitializer 类，继承 SpringBootServletInitializer
 * <p>打成 war 包修改的第 ③ 处</p>
 * @package: com.xkcoding.springbootdemowar
 * @description： 新建一个 ServletInitializer 类，继承 SpringBootServletInitializer
 * @author: yangkai.shen
 * @date: Created in 2017/11/30 下午1:26
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootDemoWarApplication.class);
	}

}
```