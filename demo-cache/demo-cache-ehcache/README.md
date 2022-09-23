# spring-boot-demo-cache-ehcache

> 此 demo 主要演示了 Spring Boot 如何集成 ehcache3 使用缓存。

## 1.开发步骤

### 1.1.添加依赖

> 注意：Spring Boot 3 依赖 jakarta 的包，但是默认的 ehcache 依赖的是 javax 的，所以需要再引入依赖的时候设置 `<classifier>jakarta</classifier>`

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
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>

  <dependency>
    <groupId>javax.cache</groupId>
    <artifactId>cache-api</artifactId>
  </dependency>

  <dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>${ehcache.version}</version>
    <classifier>jakarta</classifier>
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
    type: jcache
    jcache:
      config: classpath:ehcache3.xml
logging:
  level:
    com.xkcoding: debug
```

### 1.3.配置 ehcache

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<eh:config
  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'
  xmlns:eh='http://www.ehcache.org/v3'
  xsi:schemaLocation="http://www.ehcache.org/v3 https://www.ehcache.org/schema/ehcache-core-3.3.xsd">
  <!--指定缓存目录-->
  <eh:persistence directory="${java.io.tmpdir}/cache-data"/>

  <!--缓存模板-->
  <eh:cache-template name="default">
    <eh:expiry>
      <eh:ttl unit="seconds">600</eh:ttl>
    </eh:expiry>
    <eh:resources>
      <!--堆内内存可以放2000个条目，超出部分堆外100MB-->
      <eh:heap unit="entries">2000</eh:heap>
      <eh:offheap unit="MB">100</eh:offheap>
    </eh:resources>
  </eh:cache-template>

  <!--实际的缓存区间，继承了default缓存模板,user完全使用模板默认-->
  <eh:cache alias="user" uses-template="default"/>

</eh:config>
```

### 1.4.开启缓存自动装配

```java
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class EhcacheCacheAutoConfiguration {

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
     * 测试删除，查看ehcache是否存在缓存数据
     */
    @Test
    public void deleteUser() {
        // 查询一次，使ehcache中存在缓存数据
        userService.get(1L);
        // 删除，查看ehcache是否存在缓存数据
        userService.delete(1L);
    }
}
```

## 3.参考

- [Ehcache 官网](http://www.ehcache.org/documentation/)
- [Spring Boot 官方文档之 JCache 集成](https://docs.spring.io/spring-boot/docs/3.0.0-M5/reference/htmlsingle/#io.caching.provider.jcache)

