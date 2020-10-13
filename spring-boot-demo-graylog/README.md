# spring-boot-demo-graylog

> 此 demo 主要演示了 Spring Boot 项目如何接入 GrayLog 进行日志管理。

## 注意

作者在编写此 demo 时，`graylog` 采用 `docker-compose` 启动，其中 `graylog` 依赖的 `mongodb` 以及 `elasticsearch` 都同步启动，生产环境建议使用外部存储。

## 1. 环境准备

**编写 `graylog` 的 `docker-compose` 启动文件**

> 如果本地没有 `mongo:3` 和 `elasticsearch-oss:6.6.1` 的镜像，会比较耗时间

```yaml
version: '2'
services:
  # MongoDB: https://hub.docker.com/_/mongo/
  mongodb:
    image: mongo:3
  # Elasticsearch: https://www.elastic.co/guide/en/elasticsearch/reference/6.6/docker.html
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch-oss:6.6.1
    environment:
      - http.host=0.0.0.0
      - transport.host=localhost
      - network.host=0.0.0.0
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    mem_limit: 1g
  # Graylog: https://hub.docker.com/r/graylog/graylog/
  graylog:
    image: graylog/graylog:3.0
    environment:
      # 加密盐值，不设置，graylog会启动失败
      # 该字段最少需要16个字符
      - GRAYLOG_PASSWORD_SECRET=somepasswordpepper
      # 设置用户名
      - GRAYLOG_ROOT_USERNAME=admin
      # 设置密码，此为密码进过SHA256加密后的字符串
      # 加密方式，执行 echo -n "Enter Password: " && head -1 </dev/stdin | tr -d '\n' | sha256sum | cut -d" " -f1
      - GRAYLOG_ROOT_PASSWORD_SHA2=8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918
      - GRAYLOG_HTTP_EXTERNAL_URI=http://127.0.0.1:9000/
      # 设置时区
      - GRAYLOG_ROOT_TIMEZONE=Asia/Shanghai
    links:
      - mongodb:mongo
      - elasticsearch
    depends_on:
      - mongodb
      - elasticsearch
    ports:
      # Graylog web interface and REST API
      - 9000:9000
      # Syslog TCP
      - 1514:1514
      # Syslog UDP
      - 1514:1514/udp
      # GELF TCP
      - 12201:12201
      # GELF UDP
      - 12201:12201/udp
```

## 2. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <artifactId>spring-boot-demo-graylog</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>spring-boot-demo-graylog</name>
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
      <version>2.0.0</version>
    </dependency>
  </dependencies>

  <build>
    <finalName>spring-boot-demo-graylog</finalName>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

</project>
```

## 3. application.yml

```yaml
spring:
  application:
    name: graylog
```

## 4. logback-spring.xml

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
    <graylogHost>localhost</graylogHost>
    <graylogPort>12201</graylogPort>
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
    <appender-ref ref="GELF" />
  </root>

  <logger name="net.sf.ehcache" level="INFO"/>
  <logger name="druid.sql" level="INFO"/>


  <!-- MyBatis log configure -->
  <logger name="com.apache.ibatis" level="INFO"/>
  <logger name="org.mybatis.spring" level="DEBUG"/>
  <logger name="java.sql.Connection" level="DEBUG"/>
  <logger name="java.sql.Statement" level="DEBUG"/>
  <logger name="java.sql.PreparedStatement" level="DEBUG"/>

  <!-- 减少部分debug日志 -->
  <logger name="druid.sql" level="INFO"/>
  <logger name="org.apache.shiro" level="INFO"/>
  <logger name="org.mybatis.spring" level="INFO"/>
  <logger name="org.springframework" level="INFO"/>
  <logger name="org.springframework.context" level="WARN"/>
  <logger name="org.springframework.beans" level="WARN"/>
  <logger name="com.baomidou.mybatisplus" level="INFO"/>
  <logger name="org.apache.ibatis.io" level="INFO"/>
  <logger name="org.apache.velocity" level="INFO"/>
  <logger name="org.eclipse.jetty" level="INFO"/>
  <logger name="io.undertow" level="INFO"/>
  <logger name="org.xnio.nio" level="INFO"/>
  <logger name="org.thymeleaf" level="INFO"/>
  <logger name="springfox.documentation" level="INFO"/>
  <logger name="org.hibernate.validator" level="INFO"/>
  <logger name="com.netflix.loadbalancer" level="INFO"/>
  <logger name="com.netflix.hystrix" level="INFO"/>
  <logger name="com.netflix.zuul" level="INFO"/>
  <logger name="de.codecentric" level="INFO"/>
  <!-- cache INFO -->
  <logger name="net.sf.ehcache" level="INFO"/>
  <logger name="org.springframework.cache" level="INFO"/>
  <!-- cloud -->
  <logger name="org.apache.http" level="INFO"/>
  <logger name="com.netflix.discovery" level="INFO"/>
  <logger name="com.netflix.eureka" level="INFO"/>
  <!-- 业务日志 -->
  <Logger name="com.xkcoding" level="DEBUG"/>

</configuration>
```

## 5. 配置 graylog 控制台，接收日志来源

1. 登录 `graylog`，打开浏览器访问：http://localhost:9000

   输入 `docker-compose.yml` 里配置的 `用户名/密码` 信息

   ![登录graylog](http://static.xkcoding.com/spring-boot-demo/graylog/063124.jpg)

2. 设置来源信息

   ![设置Inputs](http://static.xkcoding.com/spring-boot-demo/graylog/063125.jpg)

   ![image-20190423164748993](http://static.xkcoding.com/spring-boot-demo/graylog/063121-1.jpg)

   ![image-20190423164932488](http://static.xkcoding.com/spring-boot-demo/graylog/063121.jpg)

   ![image-20190423165120586](http://static.xkcoding.com/spring-boot-demo/graylog/063122.jpg)

## 6. 启动 Spring Boot 项目

启动成功后，返回graylog页面查看日志信息

![image-20190423165936711](http://static.xkcoding.com/spring-boot-demo/graylog/063123.jpg)

## 参考

- graylog 官方下载地址：https://www.graylog.org/downloads#open-source

- graylog 官方docker镜像：https://hub.docker.com/r/graylog/graylog/

- graylog 镜像启动方式：http://docs.graylog.org/en/stable/pages/installation/docker.html

- graylog 启动参数配置：http://docs.graylog.org/en/stable/pages/configuration/server.conf.html

  注意，启动参数需要加 `GRAYLOG_` 前缀

- 日志收集依赖：https://github.com/osiegmar/logback-gelf