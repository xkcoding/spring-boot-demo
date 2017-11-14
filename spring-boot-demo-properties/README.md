# spring-boot-demo-properties

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)

### application.yml

```yml
server:
  port: 8080
  context-path: /demo
application:
  name: spring-boot-demo-properties
  version: 0.0.1-SNAPSHOT
  author:
    name: xkcoding
    website: xkcoding.com
    qq: 237497819
    phone-number: 18601224166
```

### 读取配置文件的两种方式

#### ApplicationConfig.java（第一种方式）

```java
@Component
@Data
public class ApplicationConfig {
	@Value("${application.name}")
	private String name;
	@Value("${application.version}")
	private String version;
}
```

#### AuthorConfig.java（第二种方式）

```java
@Data
@ConfigurationProperties(prefix = "application.author")
@Component
public class AuthorConfig {
	private String name;
	private String website;
	private String qq;
	private String phoneNumber;
}
```