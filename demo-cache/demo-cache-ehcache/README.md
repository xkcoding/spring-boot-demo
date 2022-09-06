# spring-boot-demo-cache-ehcache

> 此 demo 主要演示了 Spring Boot 如何集成 ehcache 使用缓存。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-cache-ehcache</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-cache-ehcache</name>
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
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>

        <dependency>
            <groupId>net.sf.ehcache</groupId>
            <artifactId>ehcache</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-cache-ehcache</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## SpringBootDemoCacheEhcacheApplication.java

```java
/**
 * <p>
 * 启动类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-16 17:02
 */
@SpringBootApplication
@EnableCaching
public class SpringBootDemoCacheEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoCacheEhcacheApplication.class, args);
    }
}
```

## application.yml

```yaml
spring:
  cache:
    type: ehcache
    ehcache:
      config: classpath:ehcache.xml
logging:
  level:
    com.xkcoding: debug
```

## ehcache.xml

```xml
<!-- ehcache配置 -->
<ehcache
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
        updateCheck="false">
    <!--缓存路径，用户目录下的base_ehcache目录-->
    <diskStore path="user.home/base_ehcache"/>

    <defaultCache
            maxElementsInMemory="20000"
            eternal="false"
            timeToIdleSeconds="120"
            timeToLiveSeconds="120"
            overflowToDisk="true"
            maxElementsOnDisk="10000000"
            diskPersistent="false"
            diskExpiryThreadIntervalSeconds="120"
            memoryStoreEvictionPolicy="LRU"/>

    <!--
    缓存文件名：user，同样的可以配置多个缓存
    maxElementsInMemory：内存中最多存储
    eternal：外部存储
    overflowToDisk：超出缓存到磁盘
    diskPersistent：磁盘持久化
    timeToLiveSeconds：缓存时间
    diskExpiryThreadIntervalSeconds：磁盘过期时间
    -->
    <cache name="user"
           maxElementsInMemory="20000"
           eternal="true"
           overflowToDisk="true"
           diskPersistent="false"
           timeToLiveSeconds="0"
           diskExpiryThreadIntervalSeconds="120"/>

</ehcache>
```

## UserServiceImpl.java

```java
/**
 * <p>
 * UserService
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-16 16:54
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    /**
     * 模拟数据库
     */
    private static final Map<Long, User> DATABASES = Maps.newConcurrentMap();

    /**
     * 初始化数据
     */
    static {
        DATABASES.put(1L, new User(1L, "user1"));
        DATABASES.put(2L, new User(2L, "user2"));
        DATABASES.put(3L, new User(3L, "user3"));
    }

    /**
     * 保存或修改用户
     *
     * @param user 用户对象
     * @return 操作结果
     */
    @CachePut(value = "user", key = "#user.id")
    @Override
    public User saveOrUpdate(User user) {
        DATABASES.put(user.getId(), user);
        log.info("保存用户【user】= {}", user);
        return user;
    }

    /**
     * 获取用户
     *
     * @param id key值
     * @return 返回结果
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User get(Long id) {
        // 我们假设从数据库读取
        log.info("查询用户【id】= {}", id);
        return DATABASES.get(id);
    }

    /**
     * 删除
     *
     * @param id key值
     */
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Long id) {
        DATABASES.remove(id);
        log.info("删除用户【id】= {}", id);
    }
}
```

## UserServiceTest.java

```java
/**
 * <p>
 * ehcache缓存测试
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-16 16:58
 */
@Slf4j
public class UserServiceTest extends SpringBootDemoCacheEhcacheApplicationTests {

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
     * 测试删除，查看redis是否存在缓存数据
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

## 参考

- Ehcache 官网：http://www.ehcache.org/documentation/
- Spring Boot 官方文档：https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-caching-provider-ehcache2
- 博客：https://juejin.im/post/5b308de9518825748b56ae1d
