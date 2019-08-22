# spring-boot-demo-admin

> 本 demo 主要演示了 Spring Boot 如何集成 Admin 管控台，监控管理 Spring Boot 应用，分别为 admin 服务端和 admin 客户端，两个模块。

## 运行步骤

1. 进入 `spring-boot-demo-admin-server` 服务端，启动管控台服务端程序
2. 进入 `spring-boot-demo-admin-client` 客户端，启动客户端程序，注册到服务端
3. 观察服务端里，客户端程序的运行状态等信息

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <parent>
    <artifactId>spring-boot-demo</artifactId>
    <groupId>com.xkcoding</groupId>
    <version>1.0.0-SNAPSHOT</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <artifactId>spring-boot-demo-admin</artifactId>
  <packaging>pom</packaging>

  <properties>
    <spring-boot-admin.version>2.1.0</spring-boot-admin.version>
  </properties>

  <modules>
    <module>spring-boot-demo-admin-client</module>
    <module>spring-boot-demo-admin-server</module>
  </modules>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-dependencies</artifactId>
        <version>${spring-boot-admin.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>

</project>
```

