## spring-boot-demo-distributed-lock-zookeeper

> 此 demo 主要演示了 Spring Boot 如何基于 Zookeeper 原生客户端实现一个分布式锁，支持可重入

## 1.开发步骤

在 `demo-distributed-lock-api` 模块中，已经实现了基于 AOP 的分布式锁注解拦截、简单的扣减库存案例，因此本模块只需要实现以下两个接口即可。
- `com.xkcoding.distributed.lock.api.DistributedLock`
- `com.xkcoding.distributed.lock.api.DistributedLockClient`

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>common-tools</artifactId>
  </dependency>

  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>demo-distributed-lock-api</artifactId>
    <version>1.0.0-SNAPSHOT</version>
  </dependency>

  <dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>${zookeeper.version}</version>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.代码实现

#### 1.2.1.ZookeeperDistributedLock

> 基于 Zookeeper 实现分布式锁，主要是通过创建并监听临时有序节点 + ThreadLocal 数据结构实现可重入性

```java
public class ZookeeperDistributedLock extends DistributedLock {

  private final ZooKeeper zooKeeper;

  private final String lockRootPath;

  private final String lockNodePath;

  private final ThreadLocal<Integer> LOCK_TIMES = new ThreadLocal<>();

  protected ZookeeperDistributedLock(ZkClient client, String lockKey, long lockTime, TimeUnit timeUnit) {
    super(lockKey, lockTime, timeUnit);
    this.lockRootPath = client.getLockRootPath();
    this.zooKeeper = client.getZooKeeper();
    try {
      // 直接创建临时顺序节点
      this.lockNodePath = this.zooKeeper.create(client.getLockRootPath() + "/" + lockKey + "-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL_SEQUENTIAL);
    } catch (KeeperException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void lock() {
    // 如果是锁重入，则次数加 1，然后直接返回
    Integer lockTimes = LOCK_TIMES.get();
    if (lockTimes != null && lockTimes > 0L) {
      LOCK_TIMES.set(lockTimes + 1);
      return;
    }

    try {
      // 当前节点是第一个临时节点，如果是，直接获得锁
      String previousLockNodePath = getPreviousNodePath(lockNodePath);
      if (StrUtil.isBlank(previousLockNodePath)) {
        LOCK_TIMES.set(1);
        return;
      } else {
        // 如果不是第一个临时节点，则给它的前一个临时节点添加监听删除事件（即锁释放）
        CountDownLatch wait = new CountDownLatch(1);
        Stat stat = this.zooKeeper.exists(lockRootPath + "/" + previousLockNodePath, new Watcher() {
          @Override
          public void process(WatchedEvent event) {
            if (Event.EventType.NodeDeleted == event.getType()) {
              // 节点删除，通知获得锁，避免自旋判断影响性能
              wait.countDown();
            }
          }
        });
        // 未被删除，则需要阻塞
        if (stat != null) {
          wait.await();
        }
        // 获得锁，则重入次数设置为 1
        LOCK_TIMES.set(1);
      }
    } catch (Exception e) {
      try {
        TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
      // 重新尝试获取锁
      lock();
    }

  }

  @Override
  public boolean tryLock() {
    throw new UnsupportedOperationException("ZookeeperDistributedLock`s tryLock method is unsupported");
  }

  @Override
  public boolean tryLock(long time, @NotNull TimeUnit unit) {
    throw new UnsupportedOperationException("ZookeeperDistributedLock`s tryLock method is unsupported");
  }

  @Override
  public void unlock() {
    // -1 代表不使用乐观锁删除，类似 delete --force
    Integer lockTimes = LOCK_TIMES.get();
    if (lockTimes != null && lockTimes > 0) {
      // 计算剩下的重入次数
      int leftLockTime = lockTimes - 1;
      LOCK_TIMES.set(leftLockTime);
      // 剩下为 0 的时候，代表完全解锁，需要删除临时节点
      if (leftLockTime == 0) {
        try {
          this.zooKeeper.delete(lockNodePath, -1);
          LOCK_TIMES.remove();
        } catch (InterruptedException | KeeperException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  /**
   * 根据当前节点，获取前一个节点
   *
   * @param currentNodePath 当前节点路径
   * @return 前一个节点
   */
  private String getPreviousNodePath(String currentNodePath) {
    try {
      // 截取当前临时节点编号
      long currentNodeSeq = Long.parseLong(StrUtil.subAfter(currentNodePath, "-", true));
      // 获取锁根路径下的所有临时节点
      List<String> nodes = this.zooKeeper.getChildren(lockRootPath, false);

      if (CollUtil.isEmpty(nodes)) {
        return null;
      }

      // 用于标记遍历所有子节点时，离当前最近的一次编号
      long previousNodeSeq = 0L;
      String previousNodePath = null;
      for (String nodePath : nodes) {
        // 获取每个临时节点编号
        long nodeSeq = Long.parseLong(StrUtil.subAfter(nodePath, "-", true));
        if (nodeSeq < currentNodeSeq && nodeSeq > previousNodeSeq) {
          previousNodeSeq = nodeSeq;
          previousNodePath = nodePath;
        }
      }

      return previousNodePath;
    } catch (KeeperException | InterruptedException e) {
      throw new RuntimeException(e);
    }

  }
}
```

#### 1.2.2.ZkClient

> 配置 Zookeeper 客户端连接

```java
@Slf4j
public class ZkClient {
    /**
     * 连接地址
     */
    @Getter
    private final String connectServer;

    /**
     * 节点根路径，默认是 /locks
     */
    @Getter
    private final String lockRootPath;

    @Getter
    private ZooKeeper zooKeeper;

    private static final String DEFAULT_ROOT_PATH = "/locks";

    public ZkClient(String connectServer) {
        this.connectServer = connectServer;
        this.lockRootPath = DEFAULT_ROOT_PATH;
    }

    public ZkClient(String connectServer, String lockRootPath) {
        this.connectServer = connectServer;
        this.lockRootPath = lockRootPath;
    }

    public void init() {
        try {
            this.zooKeeper = new ZooKeeper(connectServer, 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState() && Event.EventType.None == watchedEvent.getType()) {
                        log.info("===========> zookeeper connected <===========");
                    }
                }
            });
            // 判断根节点是否存在，不存在则创建
            if (this.zooKeeper.exists(lockRootPath, false) == null) {
                this.zooKeeper.create(lockRootPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | KeeperException | InterruptedException e) {
            log.error("===========> zookeeper connect error <===========");
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        if (this.zooKeeper != null) {
            try {
                zooKeeper.close();
                log.info("===========> zookeeper disconnected <===========");
            } catch (InterruptedException e) {
                log.error("===========> zookeeper disconnect error <===========");
                throw new RuntimeException(e);
            }
        }
    }
}
```

#### 1.2.3.ZookeeperDistributedLockClient

> 获取一把 Zookeeper 分布式锁

```java
@RequiredArgsConstructor
public class ZookeeperDistributedLockClient implements DistributedLockClient {
  private final ZkClient zkClient;

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
    return new ZookeeperDistributedLock(zkClient, lockKey, lockTime, timeUnit);
  }
}
```

#### 1.2.4.自动装配

> 替换 `demo-distributed-lock-api` 中的默认实现

```java
@Configuration(proxyBeanMethods = false)
public class ZookeeperDistributedLockAutoConfiguration {
  @Bean(initMethod = "init", destroyMethod = "destroy")
  public ZkClient zkClient() {
    return new ZkClient("127.0.0.1:2181");
  }

  @Bean
  public ZookeeperDistributedLockClient distributedLockClient(ZkClient zkClient) {
    return new ZookeeperDistributedLockClient(zkClient);
  }
}
```

## 2.测试

### 2.1.环境搭建

主要是 mysql 及 zookeeper 环境的搭建，这里我提供了 docker-compose 文件，方便同学们一键启动测试环境

```bash
$ cd demo-distributed-lock/demo-distributed-lock-zookeeper
$ docker compose -f docker-compose.env.yml up
```

### 2.2.测试流程

这里我通过 Apache Bench 进行模拟并发场景，我也构建了一个压测镜像 `xkcoding/ab:alpine-3.16.2` ，方便同学们进行快速测试

> 注意：每次启动项目，都会在重置库存，你也可以手动调用 `/demo/stock/reset` 接口重置

#### 2.2.1.测试无分布式锁下高并发请求是否会发生超卖

1. 把 `ZookeeperDistributedLockAutoConfiguration` 类全部注释掉，这将不会装配 Zookeeper 分布式锁
2. 启动 `ZookeeperDistributedLockApplication`
3. 模拟 5000 请求数 100 并发的压测环境 `docker run -it --rm xkcoding/ab:alpine-3.16.2 ab -n 5000 -c 100 http://${替换成你电脑的内网IP}:8080/demo/stock/reduce`
4. 等待压测结束，前往数据库查看库存是否从 5000 减为 0

#### 2.2.2.测试 Zookeeper 分布式锁

1. 把 `ZookeeperDistributedLockAutoConfiguration` 类的注释解开，此时 Spring Boot 会自动装配我们的 Zookeeper 分布式锁
2. 再次启动 `ZookeeperDistributedLockApplication`
3. 再次模拟 5000 请求数 100 并发的压测环境 `docker run -it --rm xkcoding/ab:alpine-3.16.2 ab -n 5000 -c 100 http://${替换成你电脑的内网IP}:8080/demo/stock/reduce`
4. 等待压测结束，前往数据库查看库存是否从 5000 减为 0

## 3.参考

- [Zookeeper官方 API 文档](https://zookeeper.apache.org/doc/r3.8.0/zookeeperProgrammers.html)
