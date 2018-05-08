# spring-boot-demo-task-schedule

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)

## TaskConfig.java

```java
/**
 * <p>
 * 定时任务配置，配置线程池，使用不同线程执行任务，提升效率
 * </p>
 *
 * @package: com.xkcoding.springbootdemotaskschedule.config
 * @description： 定时任务配置，配置线程池，使用不同线程执行任务，提升效率
 * @author: yangkai.shen
 * @date: Created in 2018/5/8 上午10:01
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.xkcoding.springbootdemotaskschedule.task")
public class TaskConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}

	@Bean
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

}
```

## TaskJob.java

```java
/**
 * <p>
 * 定时任务
 * </p>
 *
 * @package: com.xkcoding.springbootdemotaskschedule.task
 * @description： 定时任务
 * @author: yangkai.shen
 * @date: Created in 2018/5/8 上午10:05
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class TaskJob {

	/**
	 * 每隔 10s 执行一次
	 */
	@Scheduled(cron = "0/10 * * * * ?")
	public void job1(){
		log.info("【job1】开始执行：{}",DateUtil.formatDateTime(new Date()));
	}

	/**
	 * 间隔 2s 执行
	 */
	@Scheduled(fixedRate = 2000)
	public void job2(){
		log.info("【job2】开始执行：{}",DateUtil.formatDateTime(new Date()));
	}

	/**
	 * 延迟 5s 后间隔 4s 执行
	 */
	@Scheduled(fixedDelay = 4000,initialDelay = 5000)
	public void job3(){
		log.info("【job3】开始执行：{}",DateUtil.formatDateTime(new Date()));
	}
}
```

