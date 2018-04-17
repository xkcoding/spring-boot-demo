# spring-boot-demo-dubbo-parent

依赖 [非官方的spring-boot-starter-dubbo](https://gitee.com/reger/spring-boot-starter-dubbo)

更新 springboot 版本为 2.0.1.RELEASE

本身是个父依赖，包含了3个 module

| 名称                                                         | 作用                                    |
| :----------------------------------------------------------- | :-------------------------------------- |
| [spring-boot-demo-dubbo-api](./spring-boot-demo-dubbo-api/pom.xml) | Spring Boot 与 Dubbo 整合抽取的服务接口 |
| [spring-boot-demo-dubbo-provider](./spring-boot-demo-dubbo-provider/pom.xml) | Spring Boot 与 Dubbo 整合服务的提供方   |
| [spring-boot-demo-dubbo-consumer](./spring-boot-demo-dubbo-consumer/pom.xml) | Spring Boot 与 Dubbo 整合服务的消费方   |

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.xkcoding</groupId>
	<artifactId>spring-boot-demo-dubbo-parent</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>

	<name>spring-boot-demo-dubbo-parent</name>
	<description>Spring Boot 与 Dubbo 整合的父依赖</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.1.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<modules>
		<module>spring-boot-demo-dubbo-api</module>
		<module>spring-boot-demo-dubbo-provider</module>
		<module>spring-boot-demo-dubbo-consumer</module>
	</modules>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-boot-starter-dubbo.version>1.1.1</spring-boot-starter-dubbo.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>com.gitee.reger</groupId>
			<artifactId>spring-boot-starter-dubbo</artifactId>
			<version>${spring-boot-starter-dubbo.version}</version>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.16.20</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		
		<dependency>
			<groupId>com.xkcoding</groupId>
			<artifactId>spring-boot-demo-dubbo-api</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

### 步骤

1. 启动本地的 `Zookeeper` 服务，端口号为 **2181**

   > 这里 `Zookeeper` 可以直接使用单机版，如果需要配置集群版的，可以参考 http://xkcoding.com/2017/11/01/zookeeper-cluster.html

2. 启动两个 `spring-boot-demo-dubbo-provider` 程序，端口号分别使用 **8082**、**8083**

3. 启动 `spring-boot-demo-dubbo-consumer` 程序，端口号为 **9090**

4. 浏览器输入 http://localhost:9090/greet?name=test 

   可以看到，**8082**和**8083**端口随机打印

   > Hello, test, from port: 8082

   或

   > Hello, test, from port: 8083

5. 本地启动 `dubbo-admin` 可以查看服务提供方和消费方，也可以配置负载均衡策略等等

   > 关于 `dubbo-admin` 的安装和配置等，可以参考 http://xkcoding.com/2017/11/08/dubbo-admin-install.html