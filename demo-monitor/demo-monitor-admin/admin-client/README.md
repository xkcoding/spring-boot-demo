## spring-boot-demo-admin-client

> 本 demo 主要演示了普通项目如何集成 Spring Boot Admin，并把自己的运行状态交给 Spring Boot Admin 进行展现。

### 1.开发步骤

#### 1.1.增加依赖

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
  </dependency>

  <!--  In order to secure the endpoints -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

#### 1.2.增加测试路由以及日志支持

```java
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        int range = new Random().nextInt(1, 11);
        for (int i = 0; i < range; i++) {
            log.info("#TestController#test: info log total: {}, now is {}", range, (i + 1));
        }
        return "Just for testing";
    }

}
```

#### 1.3.修改配置文件

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo

spring:
  application:
    # Spring Boot Admin展示的客户端项目名，不设置，会使用自动生成的随机id
    name: spring-boot-demo-admin-client
  security:
    user:
      name: xkcoding
      password: 123456
  boot:
    admin:
      client:
        # Spring Boot Admin 服务端地址
        url: "http://localhost:8000/"
        username: ${spring.security.user.name}
        password: ${spring.security.user.password}
        instance:
          service-host-type: ip
          metadata:
            # 客户端端点信息的安全认证信息
            user.name: ${spring.security.user.name}
            user.password: ${spring.security.user.password}
management:
  endpoint:
    health:
      # 端点健康情况，默认值"never"，设置为"always"可以显示硬盘使用情况和线程情况
      show-details: always
  endpoints:
    web:
      exposure:
        # 设置端点暴露的哪些内容，默认["health","info"]，设置"*"代表暴露所有可访问的端点
        include: "*"
  info:
    env:
      enabled: true
# 增加日志配置，为了展示 Spring Boot Admin 的观察在线日志的能力
logging:
  file:
    name: logs/${spring.application.name}.log
  pattern:
    file: "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(%5p) %clr(${PID}){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx"
```

### 2.测试

1. 启动 `AdminServerApplication` 之后，再启动 `AdminClientApplication`，然后输入 `http://localhost:8080/demo` ，观察 client 程序是否启动成功
2. 输入 `http://localhost:8080/demo/test` ，观察 admin-server 是否可以在线查看日志
