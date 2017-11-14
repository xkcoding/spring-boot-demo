# spring-boot-demo-mybatis

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)、`mybatis-spring-boot-starter`、`druid-spring-boot-starter`、`mapper-spring-boot-starter`(通用Mapper)、`pagehelper-spring-boot-starter`(分页插件PageHelper)

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-mybatis</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<name>spring-boot-demo-mybatis</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<properties>
		<mybatis.starter.version>1.3.1</mybatis.starter.version>
		<druid.starter.version>1.1.5</druid.starter.version>
		<mapper.version>1.1.1</mapper.version>
		<pagehelper.version>1.1.0</pagehelper.version>
	</properties>

	<dependencies>
		<!--mybatis-->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>${mybatis.starter.version}</version>
		</dependency>
		<!--druid-->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-starter</artifactId>
			<version>${druid.starter.version}</version>
		</dependency>
		<!--通用 Mapper-->
		<dependency>
			<groupId>tk.mybatis</groupId>
			<artifactId>mapper-spring-boot-starter</artifactId>
			<version>${mapper.version}</version>
		</dependency>
		<!--PageHelper-->
		<dependency>
			<groupId>com.github.pagehelper</groupId>
			<artifactId>pagehelper-spring-boot-starter</artifactId>
			<version>${pagehelper.version}</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-mybatis</finalName>
	</build>

</project>
```

### application.yml

```yml
server:
  port: 8080
  context-path: /demo
spring:
  # json 转化移除 null 字段
  # jackson:
  #   default-property-inclusion: non_null
  datasource:
    # 启动时自动运行的 SQL 文件
    schema: classpath:init-sql/schema.sql
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
# mybatis 配置
mybatis:
  type-aliases-package: com.xkcoding.springbootdemomybatis.model
  mapper-locations: classpath:mapper/*.xml
  # 配置项：开启下划线到驼峰的自动转换. 作用：将数据库字段根据驼峰规则自动注入到对象属性
  configuration:
    map-underscore-to-camel-case: true
# 通用 Mapper 配置
mapper:
  not-empty: false
  identity: MYSQL
  mappers: com.xkcoding.springbootdemomybatis.util.MyMapper
# PageHelper 配置
pagehelper:
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
  params: count=countSql
```

### schema.sql

```mysql
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `boot_user`
-- ----------------------------
DROP TABLE IF EXISTS `mybatis_user`;
CREATE TABLE `mybatis_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of boot_user
-- ----------------------------
INSERT INTO `mybatis_user` VALUES (1, 'klay', '13799008800', '2017-11-13 16:04:39');
INSERT INTO `mybatis_user` VALUES (2, 'Tome', '18988991234', '2017-11-13 16:13:28');
```

### SpringBootDemoMybatisApplication.java

```java
@SpringBootApplication
@MapperScan(basePackages = {"com.xkcoding.springbootdemomybatis.mapper"})
public class SpringBootDemoMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoMybatisApplication.class, args);
	}
}
```

### MybatisUser.java

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "mybatis_user")
public class MybatisUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "tel")
	private String tel;

	@Column(name = "create_time")
	private Date createTime;
}
```

### MyMapper.java

```java
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
```

### MybatisUserMapper.java

```java
@Component
public interface MybatisUserMapper extends MyMapper<MybatisUser> {
   MybatisUser findByName(@Param("name") String name);
}
```

### MybatisUserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xkcoding.springbootdemomybatis.mapper.MybatisUserMapper">
	<resultMap id="mybatisUserMap" type="com.xkcoding.springbootdemomybatis.model.MybatisUser">
		<id property="id" column="id"/>

		<result property="name" column="name"/>
		<result property="tel" column="tel"/>
		<result property="createTime" column="create_time" jdbcType="TIMESTAMP"/>
	</resultMap>

	<select id="findByName" resultMap="mybatisUserMap">
		SELECT * FROM mybatis_user WHERE name LIKE #{name}
	</select>

</mapper>
```

### 其余代码

详情请参见本demo。