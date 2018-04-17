# spring-boot-demo-dubbo-provider

Spring Boot 与 Dubbo 整合服务的提供方

依赖  [spring-boot-demo-dubbo-api](../spring-boot-demo-dubbo-api/pom.xml)

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-dubbo-provider</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-dubbo-provider</name>
	<description>Spring Boot 与 Dubbo 整合服务的提供方</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-dubbo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../pom.xml</relativePath>
	</parent>
	
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
	</dependencies>

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

### application.yml

```yaml
server:
  port: 8082
spring:
  dubbo:
    application:
      name: dubbo-provider
    base-package: com.xkcoding.springbootdemodubboprovider.service  # dubbo服务发布者所在的包
    registry:
      address: 127.0.0.1                   # zookeeper注册中心的地址
      port: 2181                           # zookeeper注册中心的端口
    protocol:
      name: dubbo
      serialization: hessian2
    provider:
      retries: 0
```

### HelloServiceImpl.java

代码中的 `@Service` 使用的是 `Dubbo` 的注解，而不是 `Spring` 的注解

```java
package com.xkcoding.springbootdemodubboprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xkcoding.springbootdemodubboapi.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * HelloServiceImpl
 * </p>
 *
 * @package: com.xkcoding.springbootdemodubboprovider.service.impl
 * @description： HelloServiceImpl
 * @author: yangkai.shen
 * @date: Created in 2018/4/17 下午5:24
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
@Slf4j
public class HelloServiceImpl implements HelloService {
	@Value("${server.port}")
	private Integer port;

	@Override
	public String hello(String name) {
		log.info("Hello, {}, from port: {}",name, port);
		return "Hello, " + name + ", from port: " + port;
	}
}
```

