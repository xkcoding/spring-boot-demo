# spring-boot-demo-logback

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-logback</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-logback</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<build>
		<finalName>spring-boot-demo-logback</finalName>
	</build>

</project>
```

### SpringBootDemoLogbackApplication.java

```java
@SpringBootApplication
@Slf4j
public class SpringBootDemoLogbackApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootDemoLogbackApplication.class, args);
		int length = context.getBeanDefinitionNames().length;
		log.trace("Spring boot启动初始化了 {} 个 Bean", length);
		log.debug("Spring boot启动初始化了 {} 个 Bean", length);
		log.info("Spring boot启动初始化了 {} 个 Bean", length);
		log.warn("Spring boot启动初始化了 {} 个 Bean", length);
		log.error("Spring boot启动初始化了 {} 个 Bean", length);
		try {
			int i = 0;
			int j = 1 / i;
		} catch (Exception e) {
			log.error("【SpringBootDemoLogbackApplication】启动异常：", e);
		}
	}
}
```

### application.yml

```yaml
server:
  port: 8080
  context-path: /demo
spring:
  application:
    name: logback-demo
```

### logback-spring.xml

```xml
<?xml version="1.0" encoding="utf-8" ?>
<configuration>
	<conversionRule conversionWord="clr"
	                converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
	<conversionRule conversionWord="wex"
	                converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter"/>
	<conversionRule conversionWord="wEx"
	                converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter"/>

	<appender name="consoleLog" class="ch.qos.logback.core.ConsoleAppender">
		<layout class="ch.qos.logback.classic.PatternLayout">
			<pattern>【xkcoding】%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}
			</pattern>
		</layout>
	</appender>

	<appender name="fileLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<!--滚动策略-->
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!--输出路径-->
			<fileNamePattern>${user.dir}/logs/log/logback-demo.%d.log</fileNamePattern>
		</rollingPolicy>
		<encoder>
			<pattern>【xkcoding】%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{36} - %msg%n</pattern>
		</encoder>
	</appender>

	<appender name="fileErrorLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<filter class="ch.qos.logback.classic.filter.LevelFilter">
			<level>ERROR</level>
			<onMatch>ACCEPT</onMatch>
			<onMismatch>DENY</onMismatch>
		</filter>
		<!--滚动策略-->
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!--输出路径-->
			<fileNamePattern>${user.dir}/logs/error/logback-demo.%d.error</fileNamePattern>
		</rollingPolicy>
		<encoder>
			<pattern>【xkcoding】%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{36} - %msg%n</pattern>
		</encoder>
	</appender>

	<root level="info">
		<appender-ref ref="consoleLog"/>
		<appender-ref ref="fileLog"/>
		<appender-ref ref="fileErrorLog"/>
	</root>
</configuration>
```