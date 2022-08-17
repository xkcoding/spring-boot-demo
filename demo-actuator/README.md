## spring-boot-demo-actuator

> 本 demo 主要演示了如何在 Spring Boot 中通过 actuator 检查项目运行情况

### 1.开发步骤

#### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>common-tools</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
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

#### 1.2.配置文件 application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
# 若要访问端点信息，需要配置用户名和密码
spring:
  security:
    user:
      name: xkcoding
      password: 123456
management:
  # 端点信息接口使用的端口，为了和主系统接口使用的端口进行分离
  server:
    port: 8090
    base-path: /sys
  # 端点健康情况，默认值"never"，设置为"always"可以显示硬盘使用情况和线程情况
  endpoint:
    health:
      show-details: always
  # 设置端点暴露的哪些内容，默认["health","info"]，设置"*"代表暴露所有可访问的端点
  endpoints:
    web:
      exposure:
        include: '*'
```

### 2.测试

将项目运行起来之后，会在**控制台**里查看所有可以访问的端口信息
1. 打开浏览器，访问：http://localhost:8090/sys/actuator/mappings ，输入用户名(xkcoding)密码(123456)即可看到所有的mapping信息
2. 访问：http://localhost:8090/sys/actuator/beans ，输入用户名(xkcoding)密码(123456)即可看到所有 Spring 管理的Bean
3. 其余可访问的路径，参见文档

### 3.参考

- actuator文档：https://docs.spring.io/spring-boot/docs/3.0.0-M4/reference/htmlsingle/#actuator
- 具体可以访问哪些路径，参考: https://docs.spring.io/spring-boot/docs/3.0.0-M4/reference/htmlsingle/#actuator.endpoints
