# spring-boot-demo-helloworld

依赖 [spring-boot-demo-helloworld](../spring-boot-demo-parent)

SpringBootDemoHelloworldApplication.java

```java
@RestController
@SpringBootApplication
@Configuration
public class SpringBootDemoHelloworldApplication {
	@Value("${spring.boot.demo.helloworld.data.version}")
	private String version;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoHelloworldApplication.class, args);
	}

	@GetMapping("/hello")
	public Map sayHello(@Value("${author}") String author, @Value("${exclusions}") String exclusions, @Value("${connectionProperties}") String connectionProperties) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("ret", true);
		result.put("msg", StrUtil.format("hello,world,{}", author));
		Map<String, Object> data = Maps.newHashMap();
		data.put("version", version);
		data.put("exclusions", exclusions.split(","));
		Map<String, Object> connectionProperty = Maps.newHashMap();
		for (String connection : connectionProperties.split(";")) {
			String[] conn = connection.split("=");
			connectionProperty.put(conn[0], conn[1]);
		}
		data.put("connectionProperties", connectionProperty);
		result.put("data", data);
		return result;
	}
}
```

application.yml

```yml
server:
  port: 8080
  context-path: /demo
spring:
  boot:
    demo:
      helloworld:
        data:
          version: 1.0.0
author: xkcoding
exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
```

