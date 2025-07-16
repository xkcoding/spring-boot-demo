# spring-boot-demo-cache-ehcache

> 此 demo 主要演示了 Spring Boot 如何集成 Caffeine 使用缓存，通过springboot提供的开箱即用模式配置

## 引入必要缓存依赖

```xml
<!--引入spring boot 缓存包-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
  <!--引入caffeine缓存依赖替代默认ConcurrentHashMap缓存-->
<dependency>
<groupId>com.github.ben-manes.caffeine</groupId>
<artifactId>caffeine</artifactId>
</dependency>
```

## 通过注解开启缓存

```java

@SpringBootApplication
@EnableCaching
public class SpringBootDemoCacheEhcacheApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoCacheEhcacheApplication.class, args);
  }
}
```

## 必要配置：配置缓存名称，缓存策略

```yaml
spring:
  cache:
    #设置缓存类型为caffeine
    type: caffeine
    #在配置中自定义 1-N项缓存空间
    cache-names: user-caffeine-cache1, user-caffeine-cache2
    caffeine:
      # 这表示 user-caffeine-cache1 和 user-caffeine-cache2 都会使用相同的 Caffeine 配置：
      # 最大缓存条目数为 3, 缓存条目在 5s 无访问后过期
      spec: maximumSize=3,expireAfterAccess=5s
```

## 可选配置：配置缓存名称，缓存策略
```yaml
logging:
  level:
    #启用 Spring 缓存抽象类的日志输出，包括缓存的命中、失效、存储等操作。
    org.springframework.cache: TRACE
```

## 参考

- [Caffeine](https://github.com/ben-manes/caffeine)
- [Spring Boot Caching 官方文档v2.1.0.RELEASE](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-caching-provider-caffeine)


