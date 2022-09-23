# spring-boot-demo-springdoc

> 此 demo 主要演示了 Spring Boot 如何通过 Springdoc 集成 swagger

## 1.开发步骤

### 1.1.添加依赖

> 3.0.0-M5 依赖的 servlet 为 jakarta，目前 release 的 Springdoc 依赖的是 javax.servlet-api，需要同时使用最新的 2.0.0-M5

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

  <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>${springdoc.version}</version>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.编写测试接口

```java
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户管理")
public class UserController {
    @GetMapping
    @Operation(summary = "条件查询（DONE）", description = "备注")
    @Parameters({
        @Parameter(name = "username", description = "用户名", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class), required = true)
    })
    public Response<User> getByUserName(String username) {
        log.info("多个参数用  @Parameters");
        return Response.ofSuccess(new User(1, username, "JAVA"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "主键查询（DONE）", description = "备注")
    @Parameters({
        @Parameter(name = "id", description = "用户编号", in = ParameterIn.PATH, schema = @Schema(implementation = Integer.class), required = true)
    })
    public Response<User> get(@PathVariable Integer id) {
        log.info("单个参数用  @Parameter");
        return Response.ofSuccess(new User(id, "u1", "p1"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户（DONE）", description = "备注")
    @Parameter(name = "id", description = "用户编号", in = ParameterIn.PATH, schema = @Schema(implementation = Integer.class), required = true)
    public void delete(@PathVariable Integer id) {
        log.info("单个参数用 Parameter");
    }

    @PostMapping
    @Operation(summary = "添加用户（DONE）")
    public User post(@RequestBody User user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @Parameter");
        return user;
    }

    @PostMapping("/multipar")
    @Operation(summary = "添加用户（DONE）")
    public List<User> multipar(@RequestBody List<User> user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @Parameter");
        return user;
    }

    @PostMapping("/array")
    @Operation(summary = "添加用户（DONE）")
    public User[] array(@RequestBody User[] user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @Parameter");
        return user;
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改用户（DONE）")
    public void put(@PathVariable Long id, @RequestBody User user) {
        log.info("如果你不想写 @Parameter 那么 swagger 也会使用默认的参数名作为描述信息 ");
    }

    @PostMapping("/{id}/file")
    @Operation(summary = "文件上传（DONE）")
    public String file(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        log.info(file.getContentType());
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        return file.getOriginalFilename();
    }
}

```

### 1.3.编写返回对象

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User", title = "用户实体", description = "User Entity")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 5057954049311281252L;
    /**
     * 主键id
     */
    @Schema(title = "主键id", required = true)
    private Integer id;
    /**
     * 用户名
     */
    @Schema(title = "用户名", required = true)
    private String name;
    /**
     * 工作岗位
     */
    @Schema(title = "工作岗位", required = true)
    private String job;
}
```

### 1.4.配置 Springdoc

- **SpringdocAutoConfiguration**

```java
@Configuration(proxyBeanMethods = false)
@OpenAPIDefinition(info = @Info(title = "spring-boot-demo-apidoc-swagger", version = "1.0.0-SNAPSHOT", description = "这是一个简单的 Swagger API 演示", contact = @Contact(name = "Yangkai.Shen", url = "https://xkcoding.com", email = "237497819@qq.com")),
  externalDocs = @ExternalDocumentation(description = "springdoc官方文档", url = "https://springdoc.org/"),
  servers = @Server(url = "http://localhost:8080/demo")
)
public class SpringdocAutoConfiguration implements WebMvcConfigurer {

}
```

- **application.yml**

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
springdoc:
  swagger-ui:
    path: /swagger-ui.html
  packages-to-scan: com.xkcoding.swagger.controller
```

## 2.测试

启动项目，访问地址：http://localhost:8080/demo/swagger-ui/index.html

## 3.参考

- [Springdoc 官方文档](https://springdoc.org/)
- [Springfox 迁移到 Springdoc 步骤](https://springdoc.org/#migrating-from-springfox)
