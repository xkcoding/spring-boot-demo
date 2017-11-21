# spring-boot-demo-cache-redis

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)、`spring-boot-starter-data-redis`

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-cache-redis</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<name>spring-boot-demo-cache-redis</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-cache-redis</finalName>
	</build>

</project>
```

### application.yml

```yaml
server:
  port: 8080
  context-path: /demo
spring:
  redis:
    host: localhost
    port: 6379
```

### SpringBootDemoCacheRedisApplication.java (开启使用缓存注解)

```java
@SpringBootApplication
@EnableCaching
public class SpringBootDemoCacheRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoCacheRedisApplication.class, args);
	}
}
```

### User.java (一定要序列化！)

```java
@Data
public class User implements Serializable {
	private static final long serialVersionUID = 1442134563392432775L;
	private Long id;
	private String name;
	private Date birthDay;
}
```

### UserController.java

```java
@RestController
@RequestMapping("/user")
@CacheConfig(cacheNames = "users") // 整体配置缓存的名称
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping({"", "/"})
	@Cacheable
	public List<User> index() {
		return userService.find();
	}

	@GetMapping("/find/{id}")
	@Cacheable(key = "#id", condition = "#id != null") // #id 以及 condition 里的语法是 SpEL 语法
	public User find(@PathVariable Long id) {
		return userService.find(id);
	}

	@GetMapping("/find")
	@Cacheable(key = "#name", condition = "#name != null")
	public User find(@RequestParam String name) {
		return userService.find(name);
	}

	@GetMapping("/save")
	@CacheEvict(allEntries = true, beforeInvocation = true) //@CacheEvict 清空缓存
	public User save() {
		return userService.save();
	}

	@GetMapping("/update/{id}")
	@CacheEvict(allEntries = true, beforeInvocation = true)
	public User update(@PathVariable Long id) {
		return userService.update(id);
	}

	@GetMapping("/delete/{id}")
	@CacheEvict(allEntries = true, beforeInvocation = true)
	public String delete(@PathVariable Long id) {
		User user = userService.delete(id);
		if (user == null) {
			return "用户不存在";
		} else {
			return user.getName() + "删除成功";
		}
	}
}
```

### 其余 Service、Dao 代码（采用 List 存储，只为了演示启用 cache 后，是否仍会进入对应的 method）

详情请参见本demo。

### 缓存 @Annotation 详解

#### @CacheConfig

用于统一配置缓存的基本信息

#### @Cacheable

将返回的结果缓存，该注解里的参数如下

| 参数        | 作用                                       | 示例                                       |
| --------- | ---------------------------------------- | ---------------------------------------- |
| value     | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个          | 例如：@Cacheable(value=”mycache”) 或者 @Cacheable(value={”cache1”,”cache2”} |
| key       | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合 | 例如：@Cacheable(value=”testcache”,key=”#userName”) |
| condition | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存 | 例如：@Cacheable(value=”testcache”,condition=”#userName.length()>2”) |

#### @CacheEvict

能够根据一定的条件对缓存进行清空，该注解里的参数如下

| 参数               | 作用                                       | 示例                                       |
| ---------------- | ---------------------------------------- | ---------------------------------------- |
| value            | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个          | 例如：@CachEvict(value=”mycache”) 或者 @CachEvict(value={”cache1”,”cache2”} |
| key              | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合 | 例如：@CachEvict(value=”testcache”,key=”#userName”) |
| condition        | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才清空缓存 | 例如：@CachEvict(value=”testcache”,condition=”#userName.length()>2”) |
| allEntries       | 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存 | 例如：@CachEvict(value=”testcache”,allEntries=true) |
| beforeInvocation | 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存 | 例如：@CachEvict(value=”testcache”，beforeInvocation=true) |

#### @CachePut

能够根据方法的请求参数对其结果进行缓存，和 `@Cacheable` 不同的是，它每次都会触发真实方法的调用

| 参数        | 作用                                       | 示例                                       |
| --------- | ---------------------------------------- | ---------------------------------------- |
| value     | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个          | 例如：@Cacheable(value=”mycache”) 或者 @Cacheable(value={”cache1”,”cache2”} |
| key       | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合 | 例如：@Cacheable(value=”testcache”,key=”#userName”) |
| condition | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存 | 例如：@Cacheable(value=”testcache”,condition=”#userName.length()>2”) |