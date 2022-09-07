# spring-boot-demo-helloworld

> 本 demo 演示如何使用 Spring Boot 写一个hello world

## 1.开发步骤

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>common-tools</artifactId>
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
</dependencies>
```

### 1.2.启动类 HelloworldApplication.java

```java
@SpringBootApplication
@RestController
public class HelloworldApplication {

  public static void main(String[] args) {
    SpringApplication.run(HelloworldApplication.class, args);
  }

  /**
   * Hello，World
   *
   * @param who 参数，非必须
   * @return Hello, ${who}
   */
  @GetMapping("/hello")
  public String sayHello(@RequestParam(required = false, name = "who") String who) {
    if (StrUtil.isBlank(who)) {
      who = "World";
    }
    return StrUtil.format("Hello, {}!", who);
  }
}
```

### 1.3.配置文件 application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
```
## 2.测试

启动 `HelloworldApplication` 打开任意浏览器：
- 输入 `http://localhost:8080/demo/hello` ，输出 `Hello, World!`；
- 输入 `http://localhost:8080/demo/hello?who=xkcoding` ，输出 `Hello, xkcoding!`。
