# spring-boot-demo-task

> 此 demo 主要演示了 Spring Boot 如何通过 Spring Task 快速实现定时任务。

## 1.开发步骤

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>common-tools</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

### 1.2.SpringTask的自动装配

> 此处等同于在配置文件配置
>
> ```properties
> spring.task.scheduling.pool.size=20
> spring.task.scheduling.thread-name-prefix=Job-Thread-
> ```

```java
@EnableScheduling
@Configuration
public class TaskAutoConfiguration implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    /**
     * 这里等同于配置文件配置
     * {@code spring.task.scheduling.pool.size=20} - Maximum allowed number of threads.
     * {@code spring.task.scheduling.thread-name-prefix=Job-Thread- } - Prefix to use for the names of newly created threads.
     * {@link org.springframework.boot.autoconfigure.task.TaskSchedulingProperties}
     */
    @Bean
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(20, new NamedThreadFactory("Job-Thread-", false));
    }
}
```

### 1.3.配置文件

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
# 下面的配置等同于 TaskConfig
#spring:
#  task:
#    scheduling:
#      pool:
#        size: 20
#      thread-name-prefix: Job-Thread-
```

### 1.4.创建定时任务

```java
@Slf4j
@Component
public class MockSpringTask {

    /**
     * 按照标准时间来算，每隔 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {
        log.info("【job1】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，间隔 2s 执行
     * 固定间隔时间
     */
    @Scheduled(fixedRate = 2000)
    public void job2() {
        log.info("【job2】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }

    /**
     * 从启动时间开始，延迟 5s 后间隔 4s 执行
     * 固定等待时间
     */
    @Scheduled(fixedDelay = 4000, initialDelay = 5000)
    public void job3() {
        log.info("【job3】开始执行：{}", DateUtil.formatDateTime(new Date()));
    }
}
```

## 2.测试

启动 `SpringTaskApplication` 观察控制台日志

## 3.参考

- [Spring Boot 官方文档之定时任务](https://docs.spring.io/spring-boot/docs/3.0.0-M4/reference/htmlsingle/#features.task-execution-and-scheduling)

