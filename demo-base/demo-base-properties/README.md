## spring-boot-demo-properties

> 本 demo 演示如何获取配置文件的自定义配置，以及如何多环境下的配置文件信息的获取

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
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>

  <!--
 在 META-INF/additional-spring-configuration-metadata.json 中配置
 可以去除 application.yml 中自定义配置的红线警告，并且为自定义配置添加 hint 提醒
  -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>

</dependencies>
```

#### 1.2.配置类

> 分别使用两种方式获取配置

```java
@Data
@Component
public class ApplicationProperty {
  @Value("${application.name}")
  private String name;
  @Value("${application.version}")
  private String version;
}

@Data
@ConfigurationProperties(prefix = "developer")
@Component
public class DeveloperProperty {
  private String name;
  private String website;
  private String email;
}
```

#### 1.3.测试入口

```java
@RestController
public class PropertyController {
  private final ApplicationProperty applicationProperty;
  private final DeveloperProperty developerProperty;

  @Autowired
  public PropertyController(ApplicationProperty applicationProperty, DeveloperProperty developerProperty) {
    this.applicationProperty = applicationProperty;
    this.developerProperty = developerProperty;
  }

  @GetMapping("/property")
  public Dict index() {
    return Dict.create().set("applicationProperty", applicationProperty).set("developerProperty", developerProperty);
  }
}
```

#### 1.4.配置文件
**application.yml**
```yml
server:
  port: 8080
  servlet:
    context-path: /demo
spring:
  profiles:
    active: prod
```
**application-dev.yml**
```yml
application:
  name: dev环境 @artifactId@
  version: dev环境 @version@
developer:
  name: dev环境 xkcoding
  website: dev环境 https://xkcoding.com
  email: dev环境 237497819@qq.com
```
**application-prod.yml**
```yml
application:
  name: prod环境 @artifactId@
  version: prod环境 @version@
developer:
  name: prod环境 xkcoding
  website: prod环境 https://xkcoding.com
  email: prod环境 237497819@qq.com
```

#### 1.5.其他

编写配置提示，`additional-spring-configuration-metadata.json`

参考： src/main/resources/META-INF/additional-spring-configuration-metadata.json

### 2.测试

1. 启动 `PropertiesApplication` ；
2. 打开任意浏览器，输入 `http://localhost:8080/demo/property` ，检查输出结果；
3. 将 `application.yml` 配置文件中的 `spring.profiles.active` 参数从 `prod` 修改为 `dev` ，重新启动，观察输出结果变化。
