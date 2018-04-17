# spring-boot-demo-dubbo-api

Spring Boot 与 Dubbo 整合抽取的服务接口

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-dubbo-api</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-dubbo-api</name>
	<description>Spring Boot 与 Dubbo 整合抽取的服务接口</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-dubbo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../pom.xml</relativePath>
	</parent>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

### HelloService.java

```java
package com.xkcoding.springbootdemodubboapi.service;

/**
 * <p>
 * HelloService
 * </p>
 *
 * @package: com.xkcoding.springbootdemodubboapi.service
 * @description： HelloService
 * @author: yangkai.shen
 * @date: Created in 2018/4/17 下午5:13
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface HelloService {
	String hello(String name);
}
```

