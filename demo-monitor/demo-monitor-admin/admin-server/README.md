## spring-boot-demo-admin-server

> 本 demo 主要演示了如何搭建一个 Spring Boot Admin 的服务端项目，可视化展示自己客户端项目的运行状态。

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
    <artifactId>spring-boot-admin-starter-server</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

#### 1.2.启动类配置

```java
@EnableAdminServer
@SpringBootApplication
public class AdminServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AdminServerApplication.class, args);
  }
}
```

#### 1.3.修改配置文件

```yaml
server:
  port: 8000
spring:
  application:
    name: spring-boot-demo-admin-server
  boot:
    admin:
      instance-auth:
        enabled: true
        default-user-name: xkcoding
        default-password: 123456
      ui:
        external-views:
          - label: "xkcoding 🚀"
            url: https://xkcoding.com
            order: 2000

```

### 2.测试

1. 启动 `AdminServerApplication`，然后打开 `http://localhost:8000`
2. 再启动 `AdminClientApplication`，等待启动成功之后，观察是否成功注册上服务端
