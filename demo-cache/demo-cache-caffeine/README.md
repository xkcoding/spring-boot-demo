# spring-boot-demo-cache-caffeine

> 此 demo 主要演示了 Spring Boot 如何集成 caffeine 使用缓存。

## 1.开发步骤

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>demo-cache-api</artifactId>
    <version>1.0.0-SNAPSHOT</version>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
  </dependency>

  <dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
    <version>${caffeine.version}</version>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.修改配置文件

```yaml
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=500,expireAfterAccess=600s
    cache-names:
      - user
logging:
  level:
    com.xkcoding: debug
```

### 1.3.开启缓存自动装配

```java
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CaffeineCacheAutoConfiguration {

}
```

### 1.5.模拟数据服务

> 为了减少重复代码，该部分我将其抽取实现在 demo-cache-api 模块中

## 2.测试

```java
@Slf4j
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 获取两次，查看日志验证缓存
     */
    @Test
    public void getTwice() {
        // 模拟查询id为1的用户
        User user1 = userService.get(1L);
        log.debug("【user1】= {}", user1);

        // 再次查询
        User user2 = userService.get(1L);
        log.debug("【user2】= {}", user2);
        // 查看日志，只打印一次日志，证明缓存生效
    }

    /**
     * 先存，再查询，查看日志验证缓存
     */
    @Test
    public void getAfterSave() {
        userService.saveOrUpdate(new User(4L, "user4"));

        User user = userService.get(4L);
        log.debug("【user】= {}", user);
        // 查看日志，只打印保存用户的日志，查询是未触发查询日志，因此缓存生效
    }

    /**
     * 测试删除，查看caffeine是否存在缓存数据
     */
    @Test
    public void deleteUser() {
        // 查询一次，使caffeine中存在缓存数据
        userService.get(1L);
        // 删除，查看caffeine是否存在缓存数据
        userService.delete(1L);
    }
}
```

## 3.参考

- [Caffeine 官网](https://github.com/ben-manes/caffeine)
- [Spring Boot 官方文档之 Caffeine 集成](https://docs.spring.io/spring-boot/docs/3.0.0-M4/reference/htmlsingle/#io.caching.provider.caffeine)

