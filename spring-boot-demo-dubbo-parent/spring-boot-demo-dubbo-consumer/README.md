# spring-boot-demo-dubbo-consumer

Spring Boot 与 Dubbo 整合服务的消费方

依赖 [spring-boot-demo-dubbo-api](../spring-boot-demo-dubbo-api/pom.xml)

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-dubbo-consumer</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-dubbo-consumer</name>
	<description>Spring Boot 与 Dubbo 整合服务的消费方</description>
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
  port: 9090
spring:
  dubbo:
    application:
      name: dubbo-consumer
    base-package: com.xkcoding.springbootdemodubboconsumer.service  # dubbo服务调用者所在的包
    registry:
      address: 127.0.0.1                   # zookeeper注册中心的地址
      port: 2181                           # zookeeper注册中心的端口
    consumer:
      timeout: 1000
      check: true                          # 服务启动时检查被调用服务是否可用
      retries: 2
```

### GreetController.java

```java
package com.xkcoding.springbootdemodubboconsumer.controller;

import com.xkcoding.springbootdemodubboconsumer.service.GreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * GreetController
 * </p>
 *
 * @package: com.xkcoding.springbootdemodubboconsumer.controller
 * @description： GreetController
 * @author: yangkai.shen
 * @date: Created in 2018/4/17 下午5:29
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/greet")
public class GreetController {
	@Autowired
	private GreetService greetService;

	@GetMapping("")
	public String hello(@RequestParam String name) {
		return greetService.greeting(name);
	}
}
```

### GreetService.java

```java
package com.xkcoding.springbootdemodubboconsumer.service;

/**
 * <p>
 * GreetService
 * </p>
 *
 * @package: com.xkcoding.springbootdemodubboconsumer.service
 * @description： GreetService
 * @author: yangkai.shen
 * @date: Created in 2018/4/17 下午5:30
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface GreetService {
	String greeting(String name);
}
```

### GreetServiceImpl.java

代码中的 `@Service` 使用的是 `Spring` 的注解，而不是 `Dubbo` 的注解

代码中的 `@Inject` 可以替换成 `Dubbo` 提供的 `@Reference`，效果相同

```java
package com.xkcoding.springbootdemodubboconsumer.service.impl;

import com.reger.dubbo.annotation.Inject;
import com.xkcoding.springbootdemodubboapi.service.HelloService;
import com.xkcoding.springbootdemodubboconsumer.service.GreetService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * GreetServiceImpl
 * </p>
 *
 * @package: com.xkcoding.springbootdemodubboconsumer.service.impl
 * @description： GreetServiceImpl
 * @author: yangkai.shen
 * @date: Created in 2018/4/17 下午5:31
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class GreetServiceImpl implements GreetService {
	@Inject
	private HelloService helloService;

	@Override
	public String greeting(String name) {
		return helloService.hello(name);
	}
}
```

