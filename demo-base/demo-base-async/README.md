# spring-boot-demo-async

> 此 demo 主要演示了 Spring Boot 如何使用原生提供的异步任务支持，实现异步执行任务。

## 1.开发步骤

### 1.1.添加依赖

```xml
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
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.增加异步线程池的配置

> 注意：如果不配置的话，会使用默认的线程池配置

```yaml
spring:
  task:
    execution:
      pool:
        # 最大线程数
        max-size: 16
        # 核心线程数
        core-size: 16
        # 存活时间
        keep-alive: 10s
        # 队列大小
        queue-capacity: 100
        # 是否允许核心线程超时
        allow-core-thread-timeout: true
      # 线程名称前缀
      thread-name-prefix: async-task-
```

### 1.3.同步、异步任务模拟

> 1. 异步方法的返回值，需要指定为：`java.util.concurrent.Future`,`org.springframework.util.concurrent.ListenableFuture`,`java.util.concurrent.CompletableFuture`
> 2. 方法上标记 `@Async`

```java
@Component
@Slf4j
public class MockTaskFactory {

  /**
   * 模拟5秒的异步任务
   */
  @Async
  public CompletableFuture<Boolean> asyncTask1() throws InterruptedException {
    doTask("asyncTask1", 5);
    return CompletableFuture.completedFuture(Boolean.TRUE);
  }

  /**
   * 模拟2秒的异步任务
   */
  @Async
  public CompletableFuture<Boolean> asyncTask2() throws InterruptedException {
    doTask("asyncTask2", 2);
    return CompletableFuture.completedFuture(Boolean.TRUE);
  }

  /**
   * 模拟3秒的异步任务
   */
  @Async
  public CompletableFuture<Boolean> asyncTask3() throws InterruptedException {
    doTask("asyncTask3", 3);
    return CompletableFuture.completedFuture(Boolean.TRUE);
  }

  /**
   * 模拟5秒的同步任务
   */
  public void task1() throws InterruptedException {
    doTask("task1", 5);
  }

  /**
   * 模拟2秒的同步任务
   */
  public void task2() throws InterruptedException {
    doTask("task2", 2);
  }

  /**
   * 模拟3秒的同步任务
   */
  public void task3() throws InterruptedException {
    doTask("task3", 3);
  }

  private void doTask(String taskName, Integer time) throws InterruptedException {
    log.info("{}开始执行，当前线程名称【{}】", taskName, Thread.currentThread().getName());
    TimeUnit.SECONDS.sleep(time);
    log.info("{}执行成功，当前线程名称【{}】", taskName, Thread.currentThread().getName());
  }
}
```

### 1.4.在启动类上增加注解 `@EnableAsync`

```java
@EnableAsync
@SpringBootApplication
public class AsyncApplication {

  public static void main(String[] args) {
    SpringApplication.run(AsyncApplication.class, args);
  }

}
```

## 2.测试

### 2.1.编写测试代码

```java
@Slf4j
@SpringBootTest
@DisplayName("异步化测试")
public class MockTaskFactoryTest {
  @Autowired
  private MockTaskFactory task;

  /**
   * 测试异步任务
   */
  @Test
  @DisplayName("异步任务")
  public void asyncTaskTest() throws InterruptedException, ExecutionException {
    long start = System.currentTimeMillis();
    CompletableFuture<Boolean> asyncResult1 = task.asyncTask1();
    CompletableFuture<Boolean> asyncResult2 = task.asyncTask2();
    CompletableFuture<Boolean> asyncResult3 = task.asyncTask3();

    CompletableFuture<Void> allResult = CompletableFuture.allOf(asyncResult1, asyncResult2, asyncResult3);
    // 调用 get() 阻塞主线程
    allResult.get();
    long end = System.currentTimeMillis();

    log.info("异步任务全部执行结束，总耗时：{} 毫秒", (end - start));
  }

  /**
   * 测试同步任务
   */
  @Test
  @DisplayName("同步任务")
  public void taskTest() throws InterruptedException {
    long start = System.currentTimeMillis();
    task.task1();
    task.task2();
    task.task3();
    long end = System.currentTimeMillis();

    log.info("同步任务全部执行结束，总耗时：{} 毫秒", (end - start));
  }
}
```

### 2.2运行结果

运行 `MockTaskFactoryTest`，查看日志

- 异步任务

```bash
INFO 11574 --- [   async-task-1] com.xkcoding.async.task.MockTaskFactory  : asyncTask1开始执行，当前线程名称【async-task-1】
INFO 11574 --- [   async-task-2] com.xkcoding.async.task.MockTaskFactory  : asyncTask2开始执行，当前线程名称【async-task-2】
INFO 11574 --- [   async-task-3] com.xkcoding.async.task.MockTaskFactory  : asyncTask3开始执行，当前线程名称【async-task-3】
INFO 11574 --- [   async-task-2] com.xkcoding.async.task.MockTaskFactory  : asyncTask2执行成功，当前线程名称【async-task-2】
INFO 11574 --- [   async-task-3] com.xkcoding.async.task.MockTaskFactory  : asyncTask3执行成功，当前线程名称【async-task-3】
INFO 11574 --- [   async-task-1] com.xkcoding.async.task.MockTaskFactory  : asyncTask1执行成功，当前线程名称【async-task-1】
INFO 11574 --- [           main] c.x.async.task.MockTaskFactoryTest       : 异步任务全部执行结束，总耗时：5017 毫秒
```

- 同步任务

```bash
INFO 11574 --- [           main] com.xkcoding.async.task.MockTaskFactory  : task1开始执行，当前线程名称【main】
INFO 11574 --- [           main] com.xkcoding.async.task.MockTaskFactory  : task1执行成功，当前线程名称【main】
INFO 11574 --- [           main] com.xkcoding.async.task.MockTaskFactory  : task2开始执行，当前线程名称【main】
INFO 11574 --- [           main] com.xkcoding.async.task.MockTaskFactory  : task2执行成功，当前线程名称【main】
INFO 11574 --- [           main] com.xkcoding.async.task.MockTaskFactory  : task3开始执行，当前线程名称【main】
INFO 11574 --- [           main] com.xkcoding.async.task.MockTaskFactory  : task3执行成功，当前线程名称【main】
INFO 11574 --- [           main] c.x.async.task.MockTaskFactoryTest       : 同步任务全部执行结束，总耗时：10032 毫秒
```

## 3.参考

- [Spring Boot 官方文档之异步任务线程池的配置](https://docs.spring.io/spring-boot/docs/3.0.0-M4/reference/htmlsingle/#features.task-execution-and-scheduling)
