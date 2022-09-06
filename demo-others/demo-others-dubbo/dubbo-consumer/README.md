# spring-boot-demo-dubbo-consumer

> 此 module 主要是服务调用方的示例

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>spring-boot-demo-dubbo</artifactId>
        <groupId>com.xkcoding</groupId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-dubbo-consumer</artifactId>

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

        <dependency>
            <groupId>com.alibaba.spring.boot</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>${dubbo.starter.version}</version>
        </dependency>

        <dependency>
            <groupId>${project.groupId}</groupId>
            <artifactId>spring-boot-demo-dubbo-common</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>com.101tec</groupId>
            <artifactId>zkclient</artifactId>
            <version>${zkclient.version}</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-dubbo-consumer</finalName>
    </build>

</project>
```

## application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo

spring:
  dubbo:
    application:
      name: spring-boot-demo-dubbo-consumer
      registry: zookeeper://127.0.0.1:2181
```

## SpringBootDemoDubboConsumerApplication.java

```java
/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-25 16:49
 */
@SpringBootApplication
@EnableDubboConfiguration
public class SpringBootDemoDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoDubboConsumerApplication.class, args);
    }
}
```

## HelloController.java

```java
/**
 * <p>
 * Hello服务API
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-25 17:22
 */
@RestController
@Slf4j
public class HelloController {
    @Reference
    private HelloService helloService;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam(defaultValue = "xkcoding") String name) {
        log.info("i'm ready to call someone......");
        return helloService.sayHello(name);
    }
}
```
