# spring-boot-demo-graylog

> 此 demo 主要演示了 Spring Boot 项目如何接入 GrayLog 进行日志管理。

## 1.环境准备

主要是 graylog 环境的搭建，因为 graylog 依赖 mongo 和 elasticsearch，所以这里我提供了 docker-compose 文件，方便同学们一键启动测试环境

> 第一次运行，下载镜像会比较慢，依赖本地的网络环境

```bash
$ cd demo-log/demo-log-graylog
$ docker compose -f docker-compose.env.yml up
```

## 2.开发步骤

### 2.1.添加依赖

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
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>

  <!-- 提供logback传输日志到graylog的依赖 -->
  <dependency>
    <groupId>de.siegmar</groupId>
    <artifactId>logback-gelf</artifactId>
    <version>${logback.gelf.version}</version>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 2.2.添加 logback 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
  ~ Copyright 2019 Yangkai.Shen
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  -->
<configuration scan="true" scanPeriod="60 seconds">

  <!-- 彩色日志依赖的渲染类 -->
  <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
  <conversionRule conversionWord="wex"
                  converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter"/>
  <conversionRule conversionWord="wEx"
                  converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter"/>
  <!-- 彩色日志格式 -->
  <property name="CONSOLE_LOG_PATTERN"
            value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{50}){cyan} %clr(:){faint} %file:%line - %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
  <!-- graylog全日志格式 -->
  <property name="GRAY_LOG_FULL_PATTERN"
            value="%n%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%logger{50}] %file:%line%n%-5level: %msg%n"/>
  <!-- graylog简化日志格式 -->
  <property name="GRAY_LOG_SHORT_PATTERN"
            value="%m%nopex"/>

  <property name="GRAY_LOG_HOST" value="127.0.0.1"/>
  <property name="GRAY_LOG_PORT" value="12201"/>

  <!-- 获取服务名 -->
  <springProperty scope="context" name="APP_NAME" source="spring.application.name"/>

  <!-- 控制台输出 -->
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
      <pattern>${CONSOLE_LOG_PATTERN}</pattern>
      <charset>utf8</charset>
    </encoder>
  </appender>

  <!-- graylog 日志收集 -->
  <appender name="GELF" class="de.siegmar.logbackgelf.GelfUdpAppender">
    <graylogHost>${GRAY_LOG_HOST}</graylogHost>
    <graylogPort>${GRAY_LOG_PORT}</graylogPort>
    <maxChunkSize>508</maxChunkSize>
    <useCompression>true</useCompression>
    <encoder class="de.siegmar.logbackgelf.GelfEncoder">
      <includeRawMessage>true</includeRawMessage>
      <includeMarker>true</includeMarker>
      <includeMdcData>true</includeMdcData>
      <includeCallerData>false</includeCallerData>
      <includeRootCauseData>false</includeRootCauseData>
      <includeLevelName>true</includeLevelName>
      <shortPatternLayout class="ch.qos.logback.classic.PatternLayout">
        <pattern>${GRAY_LOG_SHORT_PATTERN}</pattern>
      </shortPatternLayout>
      <fullPatternLayout class="ch.qos.logback.classic.PatternLayout">
        <pattern>${GRAY_LOG_FULL_PATTERN}</pattern>
      </fullPatternLayout>
      <staticField>app_name:${APP_NAME}</staticField>
      <staticField>os_arch:${os.arch}</staticField>
      <staticField>os_name:${os.name}</staticField>
      <staticField>os_version:${os.version}</staticField>
    </encoder>
  </appender>

  <!-- 日志输出级别 -->
  <root level="INFO">
    <appender-ref ref="STDOUT"/>
    <appender-ref ref="GELF"/>
  </root>

</configuration>
```

### 2.3.修改配置文件

```yaml
spring:
  application:
    name: spring-boot-demo-log-graylog
```

## 3.测试

### 3.1.模拟日志输出

```java
@Slf4j
@EnableScheduling
@SpringBootApplication
public class GraylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraylogApplication.class, args);
    }

    /**
     * 定时模拟输出日志
     */
    @Scheduled(initialDelay = 5000, fixedRate = 10000)
    public void testLog() {
        log.info("[测试日志]log test, name: {}", RandomUtil.randomString(RandomUtil.randomInt(4, 8)));
        int seed = RandomUtil.randomInt(0, 20);
        if (seed < 5) {
            log.error("[测试日志]log error, {}", seed);
        }
    }

}
```

### 3.2.配置 graylog 控制台，接收日志来源

登录 `graylog`，打开浏览器访问：http://localhost:9000 输入配置的 `用户名/密码` 信息（默认为admin/admin）

![登录graylog](http://static.xkcoding.com/spring-boot-demo/graylog/063124.jpg)



设置日志来源信息

![设置Inputs](http://static.xkcoding.com/spring-boot-demo/graylog/063125.jpg)

![image-20190423164748993](http://static.xkcoding.com/spring-boot-demo/graylog/063121-1.jpg)

![image-20190423164932488](http://static.xkcoding.com/spring-boot-demo/graylog/063121.jpg)

![image-20190423165120586](http://static.xkcoding.com/spring-boot-demo/graylog/063122.jpg)

### 3.3.启动 Spring Boot 项目

启动成功后，返回graylog页面查看日志信息

![GrayLog 首页](https://static.xkcoding.com/spring-boot-demo/demo-log/demo-log-graylog/2022-09-06-075044.png)

## 4.参考

- [Graylog 官方文档](https://docs.graylog.org/)
- [Graylog 官方文档之docker启动](https://docs.graylog.org/docs/docker)
- [Graylog 镜像地址](https://hub.docker.com/r/graylog/graylog)
- [logback gelf log appender 官方文档](https://github.com/osiegmar/logback-gelf)
