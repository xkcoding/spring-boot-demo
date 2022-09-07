# spring-boot-demo-distributed-lock-curator

> 此 demo 主要演示了 Spring Boot 如何基于 Curator 实现一个分布式锁

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
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-framework</artifactId>
    <version>${curator.version}</version>
  </dependency>
  <dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>${curator.version}</version>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.代码实现

#### 1.2.1.CuratorDistributedLock

> 基于 Curator 实现分布式锁

```java
public class CuratorDistributedLock extends DistributedLock {
  private final CuratorFramework curatorFramework;
  private final InterProcessMutex mutex;
  private static final String ROOT_PATH = "/locks";

  protected CuratorDistributedLock(CuratorFramework curatorFramework, String lockKey, long lockTime, TimeUnit timeUnit) {
    super(lockKey, lockTime, timeUnit);
    this.curatorFramework = curatorFramework;
    mutex = new InterProcessMutex(curatorFramework, ROOT_PATH + "/" + lockKey);
  }

  @Override
  public void lock() {
    try {
      mutex.acquire();
    } catch (Exception e) {
      throw new RuntimeException(e);
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
    try {
      return mutex.acquire(time, unit);
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void unlock() {
    try {
      mutex.release();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
```

#### 1.2.2.CuratorDistributedLockClient

> 获取一把 Curator 分布式锁

```java
@RequiredArgsConstructor
public class CuratorDistributedLockClient implements DistributedLockClient {
  private final CuratorFramework curatorFramework;

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
    return new CuratorDistributedLock(curatorFramework, lockKey, lockTime, timeUnit);
  }
}
```

#### 1.2.3.自动装配

> 替换 `demo-distributed-lock-api` 中的默认实现

```java
@Slf4j
@Configuration(proxyBeanMethods = false)
public class CuratorDistributedLockAutoConfiguration {
  @Bean
  public CuratorFramework curatorFramework() {
    // 指数重试
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    // 创建 Curator
    CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
    // 启动 Curator
    client.start();
    log.info("===========> curator connected <===========");
    return client;
  }

  @Bean
  public CuratorDistributedLockClient distributedLockClient(CuratorFramework curatorFramework) {
    return new CuratorDistributedLockClient(curatorFramework);
  }
}
```

## 2.测试

### 2.1.环境搭建

主要是 mysql 及 zookeeper 环境的搭建，这里我提供了 docker-compose 文件，方便同学们一键启动测试环境

```bash
$ cd demo-distributed-lock/demo-distributed-lock-curator
$ docker compose -f docker-compose.env.yml up
```

### 2.2.测试流程

这里我通过 Apache Bench 进行模拟并发场景，我也构建了一个压测镜像 `xkcoding/ab:alpine-3.16.2` ，方便同学们进行快速测试

> 注意：每次启动项目，都会在重置库存，你也可以手动调用 `/demo/stock/reset` 接口重置

#### 2.2.1.测试无分布式锁下高并发请求是否会发生超卖

1. 把 `CuratorDistributedLockAutoConfiguration` 类全部注释掉，这将不会装配 Curator 分布式锁
2. 启动 `CuratorDistributedLockApplication`
3. 模拟 5000 请求数 100 并发的压测环境 `docker run -it --rm xkcoding/ab:alpine-3.16.2 ab -n 5000 -c 100 http://${替换成你电脑的内网IP}:8080/demo/stock/reduce`
4. 等待压测结束，前往数据库查看库存是否从 5000 减为 0

#### 2.2.2.测试 Curator 分布式锁

1. 把 `CuratorDistributedLockAutoConfiguration` 类的注释解开，此时 Spring Boot 会自动装配我们的 Curator 分布式锁
2. 再次启动 `CuratorDistributedLockApplication`
3. 再次模拟 5000 请求数 100 并发的压测环境 `docker run -it --rm xkcoding/ab:alpine-3.16.2 ab -n 5000 -c 100 http://${替换成你电脑的内网IP}:8080/demo/stock/reduce`
4. 等待压测结束，前往数据库查看库存是否从 5000 减为 0

## 3.参考

- [Curator](https://curator.apache.org/)
