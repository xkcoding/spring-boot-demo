# spring-boot-demo-swagger-beauty

> 此 demo 主要演示如何集成第三方的 swagger 来替换原生的 swagger，美化文档样式。本 demo 使用 [swagger-spring-boot-starter](https://github.com/battcn/swagger-spring-boot) 集成。
>
> 启动项目，访问地址：http://localhost:8080/demo/swagger-ui.html#/
>
> 用户名：xkcoding
>
> 密码：123456

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-swagger-beauty</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-swagger-beauty</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.xkcoding</groupId>
        <artifactId>spring-boot-demo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <battcn.swagger.version>2.1.2-RELEASE</battcn.swagger.version>
    </properties>

    <dependencies>
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
            <groupId>com.battcn</groupId>
            <artifactId>swagger-spring-boot-starter</artifactId>
            <version>${battcn.swagger.version}</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-swagger-beauty</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
spring:
  swagger:
    enabled: true
    title: spring-boot-demo
    description: 这是一个简单的 Swagger API 演示
    version: 1.0.0-SNAPSHOT
    contact:
      name: Yangkai.Shen
      email: 237497819@qq.com
      url: http://xkcoding.com
    # swagger扫描的基础包，默认：全扫描
    # base-package:
    # 需要处理的基础URL规则，默认：/**
    # base-path:
    # 需要排除的URL规则，默认：空
    # exclude-path:
    security:
      # 是否启用 swagger 登录验证
      filter-plugin: true
      username: xkcoding
      password: 123456
    global-response-messages:
      GET[0]:
        code: 400
        message: Bad Request，一般为请求参数不对
      GET[1]:
        code: 404
        message: NOT FOUND，一般为请求路径不对
      GET[2]:
        code: 500
        message: ERROR，一般为程序内部错误
      POST[0]:
        code: 400
        message: Bad Request，一般为请求参数不对
      POST[1]:
        code: 404
        message: NOT FOUND，一般为请求路径不对
      POST[2]:
        code: 500
        message: ERROR，一般为程序内部错误
```

## ApiResponse.java

```java
/**
 * <p>
 * 通用API接口返回
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-28 14:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用PI接口返回", description = "Common Api Response")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -8987146499044811408L;
    /**
     * 通用返回状态
     */
    @ApiModelProperty(value = "通用返回状态", required = true)
    private Integer code;
    /**
     * 通用返回信息
     */
    @ApiModelProperty(value = "通用返回信息", required = true)
    private String message;
    /**
     * 通用返回数据
     */
    @ApiModelProperty(value = "通用返回数据", required = true)
    private T data;
}
```

## User.java

```java
/**
 * <p>
 * 用户实体
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-28 14:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体", description = "User Entity")
public class User implements Serializable {
    private static final long serialVersionUID = 5057954049311281252L;
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", required = true)
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String name;
    /**
     * 工作岗位
     */
    @ApiModelProperty(value = "工作岗位", required = true)
    private String job;
}
```

## UserController.java

```java
/**
 * <p>
 * User Controller
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-28 14:25
 */
@RestController
@RequestMapping("/user")
@Api(tags = "1.0.0-SNAPSHOT", description = "用户管理", value = "用户管理")
@Slf4j
public class UserController {
    @GetMapping
    @ApiOperation(value = "条件查询（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", dataType = DataType.STRING, paramType = ParamType.QUERY, defaultValue = "xxx")})
    public ApiResponse<User> getByUserName(String username) {
        log.info("多个参数用  @ApiImplicitParams");
        return ApiResponse.<User>builder().code(200).message("操作成功").data(new User(1, username, "JAVA")).build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户编号", dataType = DataType.INT, paramType = ParamType.PATH)})
    public ApiResponse<User> get(@PathVariable Integer id) {
        log.info("单个参数用  @ApiImplicitParam");
        return ApiResponse.<User>builder().code(200).message("操作成功").data(new User(id, "u1", "p1")).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）", notes = "备注")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = DataType.INT, paramType = ParamType.PATH)
    public void delete(@PathVariable Integer id) {
        log.info("单个参数用 ApiImplicitParam");
    }

    @PostMapping
    @ApiOperation(value = "添加用户（DONE）")
    public User post(@RequestBody User user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    @PostMapping("/multipar")
    @ApiOperation(value = "添加用户（DONE）")
    public List<User> multipar(@RequestBody List<User> user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");

        return user;
    }

    @PostMapping("/array")
    @ApiOperation(value = "添加用户（DONE）")
    public User[] array(@RequestBody User[] user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户（DONE）")
    public void put(@PathVariable Long id, @RequestBody User user) {
        log.info("如果你不想写 @ApiImplicitParam 那么 swagger 也会使用默认的参数名作为描述信息 ");
    }

    @PostMapping("/{id}/file")
    @ApiOperation(value = "文件上传（DONE）")
    public String file(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        log.info(file.getContentType());
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        return file.getOriginalFilename();
    }
}
```

## 参考

- https://github.com/battcn/swagger-spring-boot/blob/master/README.md
- 几款比较好看的swagger-ui，具体使用方法参见各个依赖的官方文档：
  - [battcn](https://github.com/battcn) 的  [swagger-spring-boot-starter](https://github.com/battcn/swagger-spring-boot) 文档：https://github.com/battcn/swagger-spring-boot/blob/master/README.md
  - [ swagger-ui-layer](https://gitee.com/caspar-chen/Swagger-UI-layer) 文档：https://gitee.com/caspar-chen/Swagger-UI-layer#%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8
  - [swagger-bootstrap-ui](https://gitee.com/xiaoym/swagger-bootstrap-ui) 文档：https://gitee.com/xiaoym/swagger-bootstrap-ui#%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E
  - [swagger-ui-themes](https://github.com/ostranme/swagger-ui-themes) 文档：https://github.com/ostranme/swagger-ui-themes#getting-started
