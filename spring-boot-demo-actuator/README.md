# spring-boot-demo-actuator

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)、spring-boot-starter-actuator

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-actuator</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-actuator</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-actuator</finalName>
	</build>

</project>
```

### application.yml

```yml
server:
  port: 8080
  context-path: /demo
# actuator 配置
management:
  security:
    # 关闭 actuator 的身份验证
    enabled: false
  # 配置 actuator 的访问路径前缀
  context-path: /sys
  # actuator暴露接口使用的端口，为了和api接口使用的端口进行分离
  port: 1111
```

### actuator 提供的端点信息

| 端点          | 描述                                       | HTTP 方法 |
| ----------- | ---------------------------------------- | ------- |
| autoconfig  | 显示自动配置的信息                                | GET     |
| beans       | 显示应用程序上下文所有的Spring bean                  | GET     |
| configprops | 显示所有 `@ConfigurationProperties` 的配置属性列表  | GET     |
| dump        | 显示线程活动的快照                                | GET     |
| env         | 显示应用的环境变量                                | GET     |
| health      | 显示应用程序的健康指标，这些值由HealthIndicator的实现类提供。常见取值：`UP` / `DOWN` / `UNKNOWN` / `OUT_OF_SERVICE` | GET     |
| info        | 显示应用的信息，可使用 `info.*` 属性自定义info端点公开的数据    | GET     |
| mappings    | 显示所有的URL路径                               | GET     |
| metrics     | 显示应用的度量标准信息                              | GET     |
| shutdown    | 关闭应用（默认情况下不启用，如需启用，需设置`endpoints.shutdown.enabled=true`） | POST    |
| trace       | 显示跟踪信息（默认情况下为最近100个HTTP请求）               | GET     |

### actuator 的访问权限

#### 方法一：(本 demo 中使用的是这种)

```yaml
management:
  security:
    enabled: false
```

#### 方法二：

pom.xml 中添加以下 `spring-boot-starter-security` 依赖：

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

并在 `application.yml` 文件中设置访问的密码

```yaml
security:
  basic:
    enabled: true
  user:
    name: user
    password: 123
```

如果不设置，则用户名是 user，密码是一个随机值，在启动时会在控制台打印。