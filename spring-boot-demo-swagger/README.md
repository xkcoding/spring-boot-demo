# spring-boot-demo-swagger

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)、[spring-boot-starter-swagger](https://github.com/SpringForAll/spring-boot-starter-swagger) (由大佬[翟永超](http://blog.didispace.com/)开源)

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-swagger</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-swagger</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<properties>
		<swagger.version>1.5.1.RELEASE</swagger.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>com.spring4all</groupId>
			<artifactId>spring-boot-starter-swagger</artifactId>
			<version>${swagger.version}</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-swagger</finalName>
	</build>

</project>
```

### application.yml

```yml
server:
  port: 8080
  context-path: /demo
swagger:
  # 是否启用swagger，默认：true
  enabled: true
  # 标题
  title: swagger-demo API 管理
  # 描述
  description: 这里是 swagger-demo API 管理的描述信息
  # 版本
  version: 0.0.1-SNAPSHOT
  # 许可证
  license: MIT License
  # 许可证URL
  licenseUrl: https://github.com/xkcoding/spring-boot-demo/blob/master/LICENSE
  # 许可证URL
  termsOfServiceUrl: https://github.com/xkcoding/spring-boot-demo/blob/master/LICENSE
  contact:
      # 维护人
      name: xkcoding
      # 维护人URL
      url: http://xkcoding.com
      # 维护人URL
      email: 237497819@qq.com
  # swagger扫描的基础包，默认：全扫描
  base-package: com.xkcoding
  # 需要处理的基础URL规则，默认：/**
  base-path: /**
  # 需要排除的URL规则，默认：空
  exclude-path: /error
# swagger.host=文档的host信息，默认：空
# swagger.globalOperationParameters[0].name=参数名
# swagger.globalOperationParameters[0].description=描述信息
# swagger.globalOperationParameters[0].modelRef=指定参数类型
# swagger.globalOperationParameters[0].parameterType=指定参数存放位置,可选header,query,path,body.form
# swagger.globalOperationParameters[0].required=指定参数是否必传，true,false
```

### SpringBootDemoSwaggerApplication.java

```java
@SpringBootApplication
@EnableSwagger2Doc // 启用 Swagger
public class SpringBootDemoSwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoSwaggerApplication.class, args);
	}
}
```

### User.java

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户基本信息")
public class User {
	private Long id;

	@ApiModelProperty("姓名")
	@Size(max = 20)
	private String name;

	@Max(value = 30, message = "年龄小于30岁才可以！")
	@Min(value = 18, message = "你必须成年才可以！")
	private Integer age;

	@Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "邮箱格式错误！")
	private String email;

	@Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "请输入正确的手机格式！")
	private String phone;
}
```

### UserController.java

```java
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

	@ApiOperation("新增用户")
	@PostMapping({"", "/"})
	public User insert(@RequestBody @Valid User user) {
		return user;
	}

	@ApiIgnore
	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable Long id) {
		return "已删除用户 --> " + id;
	}

	@ApiOperation("修改用户详情")
	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody @Valid User user) {
		user.setId(id);
		return user;
	}

	@ApiOperation("用户详情")
	@GetMapping("/{id}")
	public User findById(@PathVariable Long id) {
		return new User(id, "xkcoding" + id, 21, StrUtil.format("xkcoding{}@163.com", id), StrUtil.fill("186", id.toString().charAt(0), 11, false));
	}

	@ApiOperation("用户列表")
	@GetMapping({"", "/"})
	public List<User> index(@ApiParam("第几页") @RequestParam(defaultValue = "1") Integer pageNum, @ApiParam("每页的条目数") @RequestParam(defaultValue = "20") Integer pageSize) {
		List<User> users = Lists.newArrayList();
		users.add(new User(0L, "xkcoding0", 18, "xkcoding0@163.com", "18600000000"));
		users.add(new User(1L, "xkcoding1", 19, "xkcoding1@163.com", "18611111111"));
		return users;
	}

}
```

### 启动项目，查看 API 接口信息

http://localhost:8080/demo/swagger-ui.html

### 附上大佬(翟永超)博客关于 swagger 的一些文章

http://blog.didispace.com/tags/Swagger/