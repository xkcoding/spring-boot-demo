# spring-boot-demo-jpa

依赖 [spring-boot-demo-helloworld](../spring-boot-demo-parent)、`spring-boot-starter-data-jpa`、`druid-spring-boot-starter`

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-jpa</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-jpa</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent</relativePath>
	</parent>

	<properties>
		<druid.starter.version>1.1.5</druid.starter.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-starter</artifactId>
			<version>${druid.starter.version}</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-jpa</finalName>
	</build>

</project>
```

### application.yml

```yml
server:
  port: 8080
  context-path: /demo
spring:
  datasource:
    continue-on-error: true
    druid:
      url: jdbc:mysql://localhost:3306/spring-boot-demo?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false
      username: root
      password: root
      driver-class-name: com.mysql.jdbc.Driver
      # 连接池配置
      # 初始化大小，最小，最大
      initialSize: 5
      minIdle: 5
      maxActive: 20
      # 配置获取连接等待超时的时间
      maxWait: 60000
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      timeBetweenEvictionRunsMillis: 60000
      # 配置一个连接在池中最小生存的时间，单位是毫秒
      minEvictableIdleTimeMillis: 300000
      validationQuery: SELECT 1 FROM DUAL
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      # 打开PSCache，并且指定每个连接上PSCache的大小
      poolPreparedStatements: true
      maxPoolPreparedStatementPerConnectionSize: 20
      # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
      filters: stat,wall,log4j
      # 监控配置
      web-stat-filter:
        enabled: true
        url-pattern: /*
        exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
      stat-view-servlet:
        enabled: true
        url-pattern: /sys/druid/*
        reset-enable: fasle
        login-username: xkcoding
        login-password: 123456
      filter:
        stat:
          log-slow-sql: true
          slow-sql-millis: 5000
          merge-sql: true
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

### JpaUser.java

```java
@Entity
@Data
@DynamicUpdate
public class JpaUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "create_time")
	private Date createTime;
}
```

### JpaUserRepository.java

```java
public interface JpaUserRepository extends JpaRepository<JpaUser, Long> {

	/**
	 * 自定义一个查询，HQL，根据姓名查询
	 *
	 * @param name 名称
	 * @return JpaUser
	 */
	@Query("from JpaUser u where u.name like :name")
	JpaUser findJpaUser(@Param("name") String name);

	JpaUser findJpaUserByName(String name);

	List<JpaUser> findJpaUsersByIdIn(List<Long> ids);

}
```

### 其余代码

详情请参见本demo。