# spring-boot-demo-task-xxl-job

> 此 demo 主要演示了 Spring Boot 如何集成 XXL-JOB 实现分布式定时任务，并提供绕过 `xxl-job-admin` 对定时任务的管理的方法，包括定时任务列表，触发器列表，新增定时任务，删除定时任务，停止定时任务，启动定时任务，修改定时任务，手动触发定时任务。

## 1. xxl-job-admin调度中心

> https://github.com/xuxueli/xxl-job.git

克隆 调度中心代码

```bash
$ git clone https://github.com/xuxueli/xxl-job.git
```

### 1.1. 创建调度中心的表结构

数据库脚本地址：`/xxl-job/doc/db/tables_xxl_job.sql`

### 1.2. 修改 application.properties

```properties
server.port=18080

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/xxl_job?Unicode=true&characterEncoding=UTF-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
```

### 1.3. 修改日志配置文件 logback.xml

```xml
<property name="log.path" value="logs/xxl-job/xxl-job-admin.log"/>
```

### 1.4. 启动调度中心

Run `XxlJobAdminApplication`

默认用户名密码：admin/admin

![image-20190808105554414](https://static.xkcoding.com/spring-boot-demo/2019-08-08-025555.png)

![image-20190808105628852](https://static.xkcoding.com/spring-boot-demo/2019-08-08-025629.png)

## 2. 编写执行器项目

### 2.1. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <artifactId>spring-boot-demo-task-xxl-job</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>spring-boot-demo-task-xxl-job</name>
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
    <xxl-job.version>2.1.0</xxl-job.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-configuration-processor</artifactId>
      <optional>true</optional>
    </dependency>

    <!-- xxl-job-core -->
    <dependency>
      <groupId>com.xuxueli</groupId>
      <artifactId>xxl-job-core</artifactId>
      <version>${xxl-job.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>cn.hutool</groupId>
      <artifactId>hutool-all</artifactId>
    </dependency>

    <dependency>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
  </dependencies>

  <build>
    <finalName>spring-boot-demo-task-xxl-job</finalName>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

</project>
```

### 2.2. 编写 配置类 XxlJobProps.java

```java
/**
 * <p>
 * xxl-job 配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-07 10:25
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProps {
    /**
     * 调度中心配置
     */
    private XxlJobAdminProps admin;

    /**
     * 执行器配置
     */
    private XxlJobExecutorProps executor;

    /**
     * 与调度中心交互的accessToken
     */
    private String accessToken;

    @Data
    public static class XxlJobAdminProps {
        /**
         * 调度中心地址
         */
        private String address;
    }

    @Data
    public static class XxlJobExecutorProps {
        /**
         * 执行器名称
         */
        private String appName;

        /**
         * 执行器 IP
         */
        private String ip;

        /**
         * 执行器端口
         */
        private int port;

        /**
         * 执行器日志
         */
        private String logPath;

        /**
         * 执行器日志保留天数，-1
         */
        private int logRetentionDays;
    }
}
```

### 2.3. 编写配置文件 application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
xxl:
  job:
    # 执行器通讯TOKEN [选填]：非空时启用；
    access-token:
    admin:
      # 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
      address: http://localhost:18080/xxl-job-admin
    executor:
      # 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
      app-name: spring-boot-demo-task-xxl-job-executor
      # 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
      ip:
      # 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
      port: 9999
      # 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
      log-path: logs/spring-boot-demo-task-xxl-job/task-log
      # 执行器日志保存天数 [选填] ：值大于3时生效，启用执行器Log文件定期清理功能，否则不生效；
      log-retention-days: -1
```

### 2.4. 编写自动装配类 XxlConfig.java

```java
/**
 * <p>
 * xxl-job 自动装配
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-07 10:20
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(XxlJobProps.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class XxlJobConfig {
    private final XxlJobProps xxlJobProps;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProps.getAdmin().getAddress());
        xxlJobSpringExecutor.setAccessToken(xxlJobProps.getAccessToken());
        xxlJobSpringExecutor.setAppName(xxlJobProps.getExecutor().getAppName());
        xxlJobSpringExecutor.setIp(xxlJobProps.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(xxlJobProps.getExecutor().getPort());
        xxlJobSpringExecutor.setLogPath(xxlJobProps.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProps.getExecutor().getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

}
```

### 2.5. 编写具体的定时逻辑 DemoTask.java

```java
/**
 * <p>
 * 测试定时任务
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-07 10:15
 */
@Slf4j
@Component
@JobHandler("demoTask")
public class DemoTask extends IJobHandler {

    /**
     * execute handler, invoked when executor receives a scheduling request
     *
     * @param param 定时任务参数
     * @return 执行状态
     * @throws Exception 任务异常
     */
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        // 可以动态获取传递过来的参数，根据参数不同，当前调度的任务不同
        log.info("【param】= {}", param);
        XxlJobLogger.log("demo task run at : {}", DateUtil.now());
        return RandomUtil.randomInt(1, 11) % 2 == 0 ? SUCCESS : FAIL;
    }
}
```

### 2.6. 启动执行器

Run `SpringBootDemoTaskXxlJobApplication`

## 3. 配置定时任务

### 3.1. 将启动的执行器添加到调度中心

执行器管理 - 新增执行器

![image-20190808105910203](https://static.xkcoding.com/spring-boot-demo/2019-08-08-025910.png)

### 3.2. 添加定时任务

任务管理 - 新增 - 保存

![image-20190808110127956](https://static.xkcoding.com/spring-boot-demo/2019-08-08-030128.png)

### 3.3. 启停定时任务

任务列表的操作列，拥有以下操作：执行、启动/停止、日志、编辑、删除

执行：单次触发任务，不影响定时逻辑

启动：启动定时任务

停止：停止定时任务

日志：查看当前任务执行日志

编辑：更新定时任务

删除：删除定时任务

## 4. 使用API添加定时任务

> 实际场景中，如果添加定时任务都需要手动在 xxl-job-admin 去操作，这样可能比较麻烦，用户更希望在自己的页面，添加定时任务参数、定时调度表达式，然后通过 API 的方式添加定时任务

### 4.1. 改造xxl-job-admin

#### 4.1.1. 修改 JobGroupController.java

```java
...
// 添加执行器列表
@RequestMapping("/list")
@ResponseBody
// 去除权限校验
@PermissionLimit(limit = false)
public ReturnT<List<XxlJobGroup>> list(){
		return  new ReturnT<>(xxlJobGroupDao.findAll());
}
...
```

#### 4.1.2. 修改 JobInfoController.java

```java
// 分别在 pageList、add、update、remove、pause、start、triggerJob 方法上添加注解，去除权限校验
@PermissionLimit(limit = false)
```

### 4.2. 改造 执行器项目

#### 4.2.1. 添加手动触发类

```java
/**
 * <p>
 * 手动操作 xxl-job
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-07 14:58
 */
@Slf4j
@RestController
@RequestMapping("/xxl-job")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ManualOperateController {
    private final static String baseUri = "http://127.0.0.1:18080/xxl-job-admin";
    private final static String JOB_INFO_URI = "/jobinfo";
    private final static String JOB_GROUP_URI = "/jobgroup";

    /**
     * 任务组列表，xxl-job叫做触发器列表
     */
    @GetMapping("/group")
    public String xxlJobGroup() {
        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_GROUP_URI + "/list").execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 分页任务列表
     *
     * @param page 当前页，第一页 -> 0
     * @param size 每页条数，默认10
     * @return 分页任务列表
     */
    @GetMapping("/list")
    public String xxlJobList(Integer page, Integer size) {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("start", page != null ? page : 0);
        jobInfo.put("length", size != null ? size : 10);
        jobInfo.put("jobGroup", 2);
        jobInfo.put("triggerStatus", -1);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/pageList").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动保存任务
     */
    @GetMapping("/add")
    public String xxlJobAdd() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("jobGroup", 2);
        jobInfo.put("jobCron", "0 0/1 * * * ? *");
        jobInfo.put("jobDesc", "手动添加的任务");
        jobInfo.put("author", "admin");
        jobInfo.put("executorRouteStrategy", "ROUND");
        jobInfo.put("executorHandler", "demoTask");
        jobInfo.put("executorParam", "手动添加的任务的参数");
        jobInfo.put("executorBlockStrategy", ExecutorBlockStrategyEnum.SERIAL_EXECUTION);
        jobInfo.put("glueType", GlueTypeEnum.BEAN);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/add").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动触发一次任务
     */
    @GetMapping("/trigger")
    public String xxlJobTrigger() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("id", 4);
        jobInfo.put("executorParam", JSONUtil.toJsonStr(jobInfo));

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/trigger").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动删除任务
     */
    @GetMapping("/remove")
    public String xxlJobRemove() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("id", 4);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/remove").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动停止任务
     */
    @GetMapping("/stop")
    public String xxlJobStop() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("id", 4);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/stop").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

    /**
     * 测试手动启动任务
     */
    @GetMapping("/start")
    public String xxlJobStart() {
        Map<String, Object> jobInfo = Maps.newHashMap();
        jobInfo.put("id", 4);

        HttpResponse execute = HttpUtil.createGet(baseUri + JOB_INFO_URI + "/start").form(jobInfo).execute();
        log.info("【execute】= {}", execute);
        return execute.body();
    }

}
```

> 后端其余代码请 clone 本项目，查看具体代码

## 参考

- [《分布式任务调度平台xxl-job》](http://www.xuxueli.com/xxl-job/#/)

