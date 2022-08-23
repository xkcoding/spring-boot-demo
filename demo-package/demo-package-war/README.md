## spring-boot-demo-war

> 本 demo 主要演示了如何将 Spring Boot 项目打包成传统的 war 包程序。

### 1.开发步骤
#### 1.1.修改启动类

```java
@SpringBootApplication
public class WarApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WarApplication.class, args);
    }

    /**
     * 若需要打成 war 包，则需要写一个类继承 {@link SpringBootServletInitializer} 并重写 {@link SpringBootServletInitializer#configure(SpringApplicationBuilder)}
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WarApplication.class);
    }
}
```

#### 1.2.修改 pom.xml

```xml
<!-- 若需要打成 war 包，则需要将打包方式改成 war -->
<packaging>war</packaging>

<dependencies>
  <!-- 若需要打成 war 包，则需要将 tomcat 引入，scope 设置为 provided -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

### 2.参考

- Spring Boot 官方文档：https://docs.spring.io/spring-boot/docs/3.0.0-M4/reference/htmlsingle/#howto.traditional-deployment.war
