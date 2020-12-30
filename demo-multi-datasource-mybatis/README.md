# spring-boot-demo-multi-datasource-mybatis

> 此 demo 主要演示了 Spring Boot 如何集成 Mybatis 的多数据源。可以自己基于AOP实现多数据源，这里基于 Mybatis-Plus 提供的一个优雅的开源的解决方案来实现。

## 准备工作

准备两个数据源，分别执行如下建表语句

```mysql
DROP TABLE IF EXISTS `multi_user`;
CREATE TABLE `multi_user`(
  `id` bigint(64) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `age` int(30) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci;
```

## 导入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-multi-datasource-mybatis</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-multi-datasource-mybatis</name>
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
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version>2.5.0</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.7.1</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-multi-datasource-mybatis</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## 准备实体类

`User.java`

> 1. @Data / @NoArgsConstructor / @AllArgsConstructor / @Builder 都是 lombok 注解
> 2. @TableName("multi_user") 是 Mybatis-Plus 注解，主要是当实体类名字和表名不满足 **驼峰和下划线互转** 的格式时，用于表示数据库表名
> 3. @TableId(type = IdType.ID_WORKER) 是 Mybatis-Plus 注解，主要是指定主键类型，这里我使用的是 Mybatis-Plus 基于 twitter 提供的 雪花算法

```java
/**
 * <p>
 * User实体类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-21 14:19
 */
@Data
@TableName("multi_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -1923859222295750467L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;
}
```

## 数据访问层

`UserMapper.java`

> 不需要建对应的xml，只需要继承 BaseMapper 就拥有了大部分单表操作的方法了。

```java
/**
 * <p>
 * 数据访问层
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-21 14:28
 */
public interface UserMapper extends BaseMapper<User> {
}
```

## 数据服务层

### 接口

`UserService.java`

```java
/**
 * <p>
 * 数据服务层
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-21 14:31
 */
public interface UserService extends IService<User> {

    /**
     * 添加 User
     *
     * @param user 用户
     */
    void addUser(User user);
}
```

### 实现

`UserServiceImpl.java`

> 1. @DS: 注解在类上或方法上来切换数据源，方法上的@DS优先级大于类上的@DS
> 2. baseMapper: mapper 对象，即`UserMapper`，可获得CRUD功能
> 3. 默认走从库: `@DS(value = "slave")`在类上，默认走从库，除非在方法在添加`@DS(value = "master")`才走主库

```java
/**
 * <p>
 * 数据服务层 实现
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-21 14:37
 */
@Service
@DS("slave")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 类上 {@code @DS("slave")} 代表默认从库，在方法上写 {@code @DS("master")} 代表默认主库
     *
     * @param user 用户
     */
    @DS("master")
    @Override
    public void addUser(User user) {
        baseMapper.insert(user);
    }
}
```

## 启动类

`SpringBootDemoMultiDatasourceMybatisApplication.java`

> 启动类上方需要使用@MapperScan扫描 mapper 类所在的包

```java
/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-21 14:19
 */
@SpringBootApplication
@MapperScan(basePackages = "com.xkcoding.multi.datasource.mybatis.mapper")
public class SpringBootDemoMultiDatasourceMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoMultiDatasourceMybatisApplication.class, args);
    }

}
```

## 配置文件

`application.yml`

```yaml
spring:
  datasource:
    dynamic:
      datasource:
        master:
          username: root
          password: JiangKai@Password123
          url: jdbc:mysql://193.112.94.161:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8
          driver-class-name: com.mysql.cj.jdbc.Driver
        slave:
          username: root
          password: JiangKai@Password123
          url: jdbc:mysql://193.112.94.161:3306/spring-boot-demo-2?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8
          driver-class-name: com.mysql.cj.jdbc.Driver
      mp-enabled: true
logging:
  level:
    com.xkcoding.multi.datasource.mybatis: debug
```

## 测试类

```java
/**
 * <p>
 * 测试主从数据源
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-21 14:45
 */
@Slf4j
public class UserServiceImplTest extends SpringBootDemoMultiDatasourceMybatisApplicationTests {
    @Autowired
    private UserService userService;

    /**
     * 主从库添加
     */
    @Test
    public void addUser() {
        User userMaster = User.builder().name("主库添加").age(20).build();
        userService.addUser(userMaster);

        User userSlave = User.builder().name("从库添加").age(20).build();
        userService.save(userSlave);
    }

    /**
     * 从库查询
     */
    @Test
    public void testListUser() {
        List<User> list = userService.list(new QueryWrapper<>());
        log.info("【list】= {}", JSONUtil.toJsonStr(list));
    }
}
```

### 测试结果

主从数据源加载成功

```java
2019-01-21 14:55:41.096  INFO 7239 --- [           main] com.zaxxer.hikari.HikariDataSource       : master - Starting...
2019-01-21 14:55:41.307  INFO 7239 --- [           main] com.zaxxer.hikari.HikariDataSource       : master - Start completed.
2019-01-21 14:55:41.308  INFO 7239 --- [           main] com.zaxxer.hikari.HikariDataSource       : slave - Starting...
2019-01-21 14:55:41.312  INFO 7239 --- [           main] com.zaxxer.hikari.HikariDataSource       : slave - Start completed.
2019-01-21 14:55:41.312  INFO 7239 --- [           main] c.b.d.d.DynamicRoutingDataSource         : 初始共加载 2 个数据源
2019-01-21 14:55:41.313  INFO 7239 --- [           main] c.b.d.d.DynamicRoutingDataSource         : 动态数据源-加载 slave 成功
2019-01-21 14:55:41.313  INFO 7239 --- [           main] c.b.d.d.DynamicRoutingDataSource         : 动态数据源-加载 master 成功
2019-01-21 14:55:41.313  INFO 7239 --- [           main] c.b.d.d.DynamicRoutingDataSource         : 当前的默认数据源是单数据源，数据源名为 master
 _ _   |_  _ _|_. ___ _ |    _
| | |\/|_)(_| | |_\  |_)||_|_\
     /               |
                        3.0.7.1
```

**主**库 **建议** 只执行 **INSERT** **UPDATE** **DELETE** 操作

![image-20190121153211509](http://static.xkcoding.com/spring-boot-demo/multi-datasource/mybatis/063506.jpg)

**从**库 **建议** 只执行 **SELECT** 操作

![image-20190121152825859](http://static.xkcoding.com/spring-boot-demo/multi-datasource/mybatis/063505.jpg)

> 生产环境需要搭建 **主从复制**

## 参考

1. Mybatis-Plus 多数据源文档：https://mybatis.plus/guide/dynamic-datasource.html
2. Mybatis-Plus 多数据源集成官方 demo：https://gitee.com/baomidou/dynamic-datasource-spring-boot-starter/tree/master/samples
