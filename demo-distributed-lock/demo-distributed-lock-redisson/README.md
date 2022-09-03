## spring-boot-demo-distributed-lock-redisson

> 此 demo 主要演示了 Spring Boot 如何基于 Redisson 实现一个分布式锁

## 1.开发步骤

在 `demo-distributed-lock-api` 模块中，已经实现了基于 AOP 的分布式锁注解拦截、简单的扣减库存案例，因此本模块只需要实现 `com.xkcoding.distributed.lock.api.DistributedLock` 和 `com.xkcoding.distributed.lock.api.DistributedLockClient` 两个接口即可。

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>demo-distributed-lock-api</artifactId>
    <version>1.0.0-SNAPSHOT</version>
  </dependency>

  <dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>${redisson.version}</version>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.代码实现

#### 1.2.1.RedissonDistributedLock

> 基于 Redisson 实现分布式锁

```java
public class RedissonDistributedLock extends DistributedLock {
    private final RedissonClient redissonClient;

    protected RedissonDistributedLock(RedissonClient redissonClient, String lockKey, long lockTime, TimeUnit timeUnit) {
        super(lockKey, lockTime, timeUnit);
        this.redissonClient = redissonClient;
    }

    @Override
    public void lock() {
        redissonClient.getLock(lockKey).lock();
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
        return redissonClient.getLock(lockKey).tryLock(lockTime, timeUnit);
    }

    @Override
    public void unlock() {
        redissonClient.getLock(lockKey).unlock();
    }
}
```

#### 1.2.2.RedissonDistributedLockClient

> 获取一把 Redisson 分布式锁

```java
@RequiredArgsConstructor
public class RedissonDistributedLockClient implements DistributedLockClient {
    private final RedissonClient redissonClient;

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
        return new RedissonDistributedLock(redissonClient, lockKey, lockTime, timeUnit);
    }
}
```

#### 1.2.3.自动装配

> 替换 `demo-distributed-lock-api` 中的默认实现

```java
@Configuration(proxyBeanMethods = false)
public class RedissonDistributedLockAutoConfiguration {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

    @Bean
    public RedissonDistributedLockClient distributedLockClient(RedissonClient redissonClient) {
        return new RedissonDistributedLockClient(redissonClient);
    }
}
```

## 2.测试

### 2.1.环境搭建

主要是 mysql 及 redis 环境的搭建，这里我提供了 docker-compose 文件，方便同学们一键启动测试环境

```bash
$ cd demo-distributed-lock/demo-distributed-lock-redisson
$ docker compose -f docker-compose.env.yml up
```

### 2.2.测试流程

这里我通过 Apache Bench 进行模拟并发场景，我也构建了一个压测镜像 `xkcoding/ab:alpine-3.16.2` ，方便同学们进行快速测试

#### 2.2.1.测试无分布式锁下高并发请求是否会发生超卖

1. 把 `RedissonDistributedLockAutoConfiguration` 类全部注释掉，这将不会装配 Redisson 分布式锁
2. 启动 `RedissonDistributedLockApplication`
3. 模拟 5000 请求数 100 并发的压测环境 `docker run -it --rm xkcoding/ab:alpine-3.16.2 ab -n 5000 -c 100 http://${替换成你电脑的内网IP}:8080/demo/stock/reduce`
4. 等待压测结束，前往数据库查看库存是否从 5000 减为 0

#### 2.2.2.测试 Redisson 分布式锁

1. 把 `RedissonDistributedLockAutoConfiguration` 类的注释解开，此时 Spring Boot 会自动装配我们的 Redisson 分布式锁
2. 再次启动 `RedissonDistributedLockApplication`
3. 再次模拟 5000 请求数 100 并发的压测环境 `docker run -it --rm xkcoding/ab:alpine-3.16.2 ab -n 5000 -c 100 http://${替换成你电脑的内网IP}:8080/demo/stock/reduce`
4. 等待压测结束，前往数据库查看库存是否从 5000 减为 0

## 3.参考

- [Redisson](https://github.com/redisson/redisson/wiki)
