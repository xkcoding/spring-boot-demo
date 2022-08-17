## spring-boot-demo-admin

> 本 demo 主要演示了 Spring Boot 如何集成 Admin 管控台，监控管理 Spring Boot 应用，分别为 admin 服务端和 admin 客户端，两个模块。

### 1.开发步骤

#### 1.1.pom.xml

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

  <artifactId>demo-admin</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>pom</packaging>

  <properties>
    <java.version>17</java.version>
    <spring-boot-admin.version>3.0.0-M4</spring-boot-admin.version>
  </properties>

  <modules>
    <module>admin-server</module>
    <module>admin-client</module>
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

#### 1.2.admin-server开发

参考 [admin-server 模块](./admin-server/README.md)

#### 1.3.admin-client开发

参考 [admin-client 模块](./admin-client/README.md)

### 2.测试

1. 进入 [admin-server](./admin-server) 服务端，启动管控台服务端程序
2. 进入 [admin-client](./admin-client) 客户端，启动客户端程序，注册到服务端
3. 打开 `http://localhost:8000` 观察 client 是否注册成功，以及实例注册上来的详细信息
4. Spring Boot Admin 支持在线查看日志，但是需要客户端事先配置日志，配置好之后，就可以在线查看日志了

### 3.参考

官方文档：https://codecentric.github.io/spring-boot-admin/3.0.0-M4/#getting-started
