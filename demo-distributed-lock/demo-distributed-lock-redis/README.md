## spring-boot-demo-distributed-lock-redis

> 此 demo 主要演示了 Spring Boot 如何基于 RedisTemplate 实现一个分布式锁

## 1.开发步骤

在 `demo-distributed-lock-api` 模块中，已经实现了基于 AOP 的分布式锁注解拦截、简单的扣减库存案例，因此本模块只需要实现以下两个接口即可。
- `com.xkcoding.distributed.lock.api.DistributedLock`
- `com.xkcoding.distributed.lock.api.DistributedLockClient`

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>demo-distributed-lock-api</artifactId>
    <version>1.0.0-SNAPSHOT</version>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.代码实现

#### 1.2.1.RedisDistributedLock

> 基于 RedisTemplate 实现分布式锁，主要是通过 Lua 脚本保证原子性操作、通过 hash 数据结构实现可重入性

```java
public class RedisDistributedLock extends DistributedLock {
  private final StringRedisTemplate redisTemplate;
  /**
   * 锁的唯一标识，格式：主机标识(UUID)+线程编号
   * 防误删
   */
  private final String uniqueId;

  protected RedisDistributedLock(StringRedisTemplate redisTemplate, String uniqueIdPrefix, String lockKey, long lockTime,
                                 TimeUnit timeUnit) {
    super(lockKey, lockTime, timeUnit);
    this.redisTemplate = redisTemplate;
    this.uniqueId = uniqueIdPrefix + ":" + Thread.currentThread().getId();
  }

  @Override
  public void lock() {
    // 加锁失败，自旋阻塞
    while (!tryLock()) {
      try {
        TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public boolean tryLock() {
    try {
      return tryLock(lockTime, timeUnit);
    } catch (InterruptedException e) {
      return false;
    }
  }

  @Override
  public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
    String script = "if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 " +
      "then " +
      "   redis.call('hincrby', KEYS[1], ARGV[1], 1) " +
      "   redis.call('expire', KEYS[1], ARGV[2]) " +
      "   return 1 " +
      "else " +
      "   return 0 " +
      "end";

    long expire = unit.toSeconds(time);
    Boolean getLock = Optional.ofNullable(
        redisTemplate.execute(
          new DefaultRedisScript<>(script, Boolean.class),
          Collections.singletonList(lockKey),
          uniqueId, String.valueOf(expire)))
      .orElse(Boolean.FALSE);
    // 如果获得锁，开启自动续期
    if (getLock) {
      renewLockTime(time, unit);
    }
    return getLock;
  }

  private void renewLockTime(long time, TimeUnit unit) {
    String script = "if redis.call('hexists', KEYS[1], ARGV[1]) == 1 " +
      "then " +
      "   return redis.call('expire', KEYS[1], ARGV[2]) " +
      "else " +
      "   return 0 " +
      "end";

    long expire = unit.toSeconds(time);
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        Boolean renewed = Optional.ofNullable(
          redisTemplate.execute(
            new DefaultRedisScript<>(script, Boolean.class),
            Collections.singletonList(lockKey),
            uniqueId, String.valueOf(expire))
        ).orElse(Boolean.FALSE);
        // 续期成功，代表未被解锁，则需要进行下一次续期
        if (renewed) {
          renewLockTime(time, unit);
        }
      }
    }, expire * 1000 / 3);
  }

  @Override
  public void unlock() {
    String script = "if redis.call('hexists', KEYS[1], ARGV[1]) == 0 " +
      "then " +
      "   return nil " +
      "elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 " +
      "then " +
      "   return redis.call('del', KEYS[1]) " +
      "else " +
      "   redis.call('expire', KEYS[1], ARGV[2]) " +
      "   return 0 " +
      "end";

    // 如果解锁，发现是重入的，需要重新续期
    long expire = timeUnit.toSeconds(lockTime);
    Long flag = this.redisTemplate.execute(
      new DefaultRedisScript<>(script, Long.class),
      Collections.singletonList(lockKey),
      uniqueId, String.valueOf(expire)
    );
    if (flag == null) {
      throw new IllegalMonitorStateException("this lock doesn't belong to you!");
    }
  }

}
```

#### 1.2.2.RedisDistributedLockClient

> 获取一把 RedisTemplate 分布式锁，这里需要注意集群部署下，通过 uuid 标识当前应用唯一 id

```java
public class RedisDistributedLockClient implements DistributedLockClient {
  private final StringRedisTemplate redisTemplate;

  /**
   * 唯一标识前缀，用于集群环境下的主机标识，会在 Bean 初始化到 Spring 容器的时候设置
   */
  private final String uniqueIdPrefix;

  public RedisDistributedLockClient(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
    uniqueIdPrefix = UUID.randomUUID().toString();
  }


  /**
   * 获取一把锁
   *
   * @param lockKey  锁的标识
   * @param lockTime 锁的时间
   * @param timeUnit 锁的时间单位
   * @return 锁
   */
  @Override
  public DistributedLock getLock(String lockKey, long lockTime, TimeUnit timeUnit) {
    return new RedisDistributedLock(redisTemplate, uniqueIdPrefix, lockKey, lockTime, timeUnit);
  }
}
```

#### 1.2.3.自动装配

> 替换 `demo-distributed-lock-api` 中的默认实现

```java
@Configuration(proxyBeanMethods = false)
public class RedisDistributedLockAutoConfiguration {

  @Bean
  public RedisDistributedLockClient distributedLockClient(StringRedisTemplate redisTemplate) {
    return new RedisDistributedLockClient(redisTemplate);
  }

}
```

## 2.测试

### 2.1.环境搭建

主要是 mysql 及 redis 环境的搭建，这里我提供了 docker-compose 文件，方便同学们一键启动测试环境

```bash
$ cd demo-distributed-lock/demo-distributed-lock-redis
$ docker compose -f docker-compose.env.yml up
```

### 2.2.测试流程

这里我通过 Apache Bench 进行模拟并发场景，我也构建了一个压测镜像 `xkcoding/ab:alpine-3.16.2` ，方便同学们进行快速测试

> 注意：每次启动项目，都会在重置库存，你也可以手动调用 `/demo/stock/reset` 接口重置

#### 2.2.1.测试无分布式锁下高并发请求是否会发生超卖

1. 把 `RedisDistributedLockAutoConfiguration` 类全部注释掉，这将不会装配 Redisson 分布式锁
2. 启动 `RedisDistributedLockApplication`
3. 模拟 5000 请求数 100 并发的压测环境 `docker run -it --rm xkcoding/ab:alpine-3.16.2 ab -n 5000 -c 100 http://${替换成你电脑的内网IP}:8080/demo/stock/reduce`
4. 等待压测结束，前往数据库查看库存是否从 5000 减为 0

#### 2.2.2.测试 Redisson 分布式锁

1. 把 `RedisDistributedLockAutoConfiguration` 类的注释解开，此时 Spring Boot 会自动装配我们的 Redisson 分布式锁
2. 再次启动 `RedisDistributedLockApplication`
3. 再次模拟 5000 请求数 100 并发的压测环境 `docker run -it --rm xkcoding/ab:alpine-3.16.2 ab -n 5000 -c 100 http://${替换成你电脑的内网IP}:8080/demo/stock/reduce`
4. 等待压测结束，前往数据库查看库存是否从 5000 减为 0

## 3.参考

- [Spring Data Redis 官方文档之调用 lua 脚本](https://docs.spring.io/spring-data/redis/docs/3.0.0-M5/reference/html/#scripting)
- [lua 脚本语法](https://www.runoob.com/lua/lua-tutorial.html)
