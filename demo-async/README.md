# spring-boot-demo-async

> 此 demo 主要演示了 Spring Boot 如何使用原生提供的异步任务支持，实现异步执行任务。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-async</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-async</name>
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
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-async</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## application.yml

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

## SpringBootDemoAsyncApplication.java

```java
/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-29 10:28
 */
@EnableAsync
@SpringBootApplication
public class SpringBootDemoAsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoAsyncApplication.class, args);
    }

}
```

## TaskFactory.java

```java
/**
 * <p>
 * 任务工厂
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-29 10:37
 */
@Component
@Slf4j
public class TaskFactory {

    /**
     * 模拟5秒的异步任务
     */
    @Async
    public Future<Boolean> asyncTask1() throws InterruptedException {
        doTask("asyncTask1", 5);
        return new AsyncResult<>(Boolean.TRUE);
    }

    /**
     * 模拟2秒的异步任务
     */
    @Async
    public Future<Boolean> asyncTask2() throws InterruptedException {
        doTask("asyncTask2", 2);
        return new AsyncResult<>(Boolean.TRUE);
    }

    /**
     * 模拟3秒的异步任务
     */
    @Async
    public Future<Boolean> asyncTask3() throws InterruptedException {
        doTask("asyncTask3", 3);
        return new AsyncResult<>(Boolean.TRUE);
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

## TaskFactoryTest.java

```java
/**
 * <p>
 * 测试任务
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-29 10:49
 */
@Slf4j
public class TaskFactoryTest extends SpringBootDemoAsyncApplicationTests {
    @Autowired
    private TaskFactory task;

    /**
     * 测试异步任务
     */
    @Test
    public void asyncTaskTest() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        Future<Boolean> asyncTask1 = task.asyncTask1();
        Future<Boolean> asyncTask2 = task.asyncTask2();
        Future<Boolean> asyncTask3 = task.asyncTask3();

        // 调用 get() 阻塞主线程
        asyncTask1.get();
        asyncTask2.get();
        asyncTask3.get();
        long end = System.currentTimeMillis();

        log.info("异步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }

    /**
     * 测试同步任务
     */
    @Test
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

## 运行结果

### 异步任务

```bash
2018-12-29 10:57:28.511  INFO 3134 --- [   async-task-3] com.xkcoding.async.task.TaskFactory      : asyncTask3开始执行，当前线程名称【async-task-3】
2018-12-29 10:57:28.511  INFO 3134 --- [   async-task-1] com.xkcoding.async.task.TaskFactory      : asyncTask1开始执行，当前线程名称【async-task-1】
2018-12-29 10:57:28.511  INFO 3134 --- [   async-task-2] com.xkcoding.async.task.TaskFactory      : asyncTask2开始执行，当前线程名称【async-task-2】
2018-12-29 10:57:30.514  INFO 3134 --- [   async-task-2] com.xkcoding.async.task.TaskFactory      : asyncTask2执行成功，当前线程名称【async-task-2】
2018-12-29 10:57:31.516  INFO 3134 --- [   async-task-3] com.xkcoding.async.task.TaskFactory      : asyncTask3执行成功，当前线程名称【async-task-3】
2018-12-29 10:57:33.517  INFO 3134 --- [   async-task-1] com.xkcoding.async.task.TaskFactory      : asyncTask1执行成功，当前线程名称【async-task-1】
2018-12-29 10:57:33.517  INFO 3134 --- [           main] com.xkcoding.async.task.TaskFactoryTest  : 异步任务全部执行结束，总耗时：5015 毫秒
```

### 同步任务

```bash
2018-12-29 10:55:49.830  INFO 3079 --- [           main] com.xkcoding.async.task.TaskFactory      : task1开始执行，当前线程名称【main】
2018-12-29 10:55:54.834  INFO 3079 --- [           main] com.xkcoding.async.task.TaskFactory      : task1执行成功，当前线程名称【main】
2018-12-29 10:55:54.835  INFO 3079 --- [           main] com.xkcoding.async.task.TaskFactory      : task2开始执行，当前线程名称【main】
2018-12-29 10:55:56.839  INFO 3079 --- [           main] com.xkcoding.async.task.TaskFactory      : task2执行成功，当前线程名称【main】
2018-12-29 10:55:56.839  INFO 3079 --- [           main] com.xkcoding.async.task.TaskFactory      : task3开始执行，当前线程名称【main】
2018-12-29 10:55:59.843  INFO 3079 --- [           main] com.xkcoding.async.task.TaskFactory      : task3执行成功，当前线程名称【main】
2018-12-29 10:55:59.843  INFO 3079 --- [           main] com.xkcoding.async.task.TaskFactoryTest  : 同步任务全部执行结束，总耗时：10023 毫秒
```

## 参考

- Spring Boot 异步任务线程池的配置 参考官方文档：https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-task-execution-scheduling
