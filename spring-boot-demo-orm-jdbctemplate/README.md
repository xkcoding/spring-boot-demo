# spring-boot-demo-orm-jdbctemplate
依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)、spring-boot-starter-jdbc

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-orm-jdbctemplate</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-orm-jdbctemplate</name>
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
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-starter</artifactId>
			<version>1.1.9</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-orm-jdbctemplate</finalName>
	</build>

</project>
```

## application.yml

```yaml
server:
  port: 8080
  context-path: /demo
spring:
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
```

## schema.sql

```mysql
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `jdbctemplate_user`;
CREATE TABLE `jdbctemplate_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `jdbctemplate_user` VALUES (1, 'klay', '13799008800', '2017-11-13 16:04:39');
INSERT INTO `jdbctemplate_user` VALUES (2, 'Tome', '18988991234', '2017-11-13 16:13:28');
```

## UserServiceImpl.java

```java
/**
 * <p>
 * UserServiceImpl
 * </p>
 *
 * @package: com.xkcoding.springbootdemoormjdbctemplate.service.impl
 * @description： UserServiceImpl
 * @author: yangkai.shen
 * @date: Created in 2018/5/11 下午3:27
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Integer save(User user) {
		String sql = "INSERT INTO jdbctemplate_user (name,tel,create_time) VALUES(?,?,?)";
		return jdbcTemplate.update(sql, user.getName(), user.getTel(), user.getCreateTime());
	}

	@Override
	public Integer update(User user) {
		String sql = "UPDATE jdbctemplate_user SET name = ?,tel = ? where id = ?";
		return jdbcTemplate.update(sql, user.getName(), user.getTel(), user.getId());
	}

	@Override
	public Integer delete(User user) {
		String sql = "DELETE FROM jdbctemplate_user where id = ?";
		return jdbcTemplate.update(sql, user.getId());
	}

	@Override
	public User findById(Integer id) {
		String sql = "SELECT * FROM jdbctemplate_user where id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
	}

	@Override
	public User findByName(String name) {
		String sql = "SELECT * FROM jdbctemplate_user where name = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		return jdbcTemplate.queryForObject(sql, new Object[]{name}, rowMapper);
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM jdbctemplate_user";
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
		List<User> ret = Lists.newArrayList();
		maps.forEach(map -> ret.add(BeanUtil.fillBeanWithMap(map, new User(), true, false)));
		return ret;
	}

	@Override
	public List<User> findUserByPage(Integer pageNum, Integer pageSize) {
		String sql = "SELECT * FROM jdbctemplate_user LIMIT ?,?";
		Integer offset = (pageNum - 1) * pageSize;
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{offset, pageSize});
		List<User> ret = Lists.newArrayList();
		maps.forEach(map -> ret.add(BeanUtil.fillBeanWithMap(map, new User(), true, false)));
		return ret;
	}
}
```

## 其余代码

详情请参见本demo。