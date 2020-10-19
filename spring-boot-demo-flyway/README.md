# spring-boot-demo-flyway

> 本 demo 演示了 Spring Boot 如何使用 Flyway 去初始化项目数据库，同时支持数据库脚本的版本控制。

## 1. 添加依赖

- Flyway 依赖

```xml
<!-- 添加 flyway 的依赖 -->
<dependency>
  <groupId>org.flywaydb</groupId>
  <artifactId>flyway-core</artifactId>
</dependency>
```

- 初始化表结构，需要操作数据库，因此引入数据库驱动以及数据源依赖（这里用 spring-boot-starter-data-jdbc）

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>

<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <scope>runtime</scope>
</dependency>
```

## 2. Flyway 知识补充

1. Flyway 默认会去读取 `classpath:db/migration`，可以通过 `spring.flyway.locations` 去指定自定义路径，多个路径使用半角英文逗号分隔，内部资源使用 `classpath:`，外部资源使用 `file:`

2. 如果项目初期没有数据库文件，但是又引用了 Flyway，那么在项目启动的时候，Flyway 会去检查是否存在 SQL 文件，此时你需要将这个检查关闭，`spring.flyway.check-location = false`

3. Flyway 会在项目初次启动的时候创建一张名为 `flyway_schema_history` 的表，在这张表里记录数据库脚本执行的历史记录，当然，你可以通过 `spring.flyway.table` 去修改这个值

4. Flyway 执行的 SQL 脚本必须遵循一种命名规则，`V<VERSION>__<NAME>.sql` 首先是 `V` ，然后是版本号，如果版本号有多个数字，使用`_`分隔，比如`1_0`、`1_1`，版本号的后面是 2 个下划线，最后是 SQL 脚本的名称。

   **这里需要注意：V 开头的只会执行一次，下次项目启动不会执行，也不可以修改原始文件，否则项目启动会报错，如果需要对 V 开头的脚本做修改，需要清空`flyway_schema_history`表，如果有个 SQL 脚本需要在每次启动的时候都执行，那么将 V 改为 `R` 开头即可**

5. Flyway 默认情况下会去清空原始库，再重新执行 SQL 脚本，这在生产环境下是不可取的，因此需要将这个配置关闭，`spring.flyway.clean-disabled = true`

## 3. application.yml 配置

> 贴出我的 application.yml 配置

```yaml
spring:
  flyway:
    enabled: true
    # 迁移前校验 SQL 文件是否存在问题
    validate-on-migrate: true
    # 生产环境一定要关闭
    clean-disabled: true
    # 校验路径下是否存在 SQL 文件
    check-location: false
    # 最开始已经存在表结构，且不存在 flyway_schema_history 表时，需要设置为 true
    baseline-on-migrate: true
    # 基础版本 0
    baseline-version: 0
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/flyway-test?useSSL=false
    username: root
    password: root
    type: com.zaxxer.hikari.HikariDataSource
```

## 4. 测试

### 4.1. 测试 1.0 版本的 SQL 脚本

创建 `V1_0__INIT.sql` 

```mysql
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '加密后的密码',
  `salt` varchar(32) NOT NULL COMMENT '加密使用的盐',
  `email` varchar(32) NOT NULL COMMENT '邮箱',
  `phone_number` varchar(15) NOT NULL COMMENT '手机号码',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态，-1：逻辑删除，0：禁用，1：启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone_number` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='1.0-用户表';
```

启动项目，可以看到日志输出：

```java
2020-03-05 10:48:37.799  INFO 3351 --- [           main] o.f.c.internal.license.VersionPrinter    : Flyway Community Edition 5.2.1 by Boxfuse
2020-03-05 10:48:37.802  INFO 3351 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-03-05 10:48:37.971  INFO 3351 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2020-03-05 10:48:37.974  INFO 3351 --- [           main] o.f.c.internal.database.DatabaseFactory  : Database: jdbc:mysql://127.0.0.1:3306/flyway-test (MySQL 5.7)
2020-03-05 10:48:38.039  INFO 3351 --- [           main] o.f.core.internal.command.DbValidate     : Successfully validated 1 migration (execution time 00:00.015s)
2020-03-05 10:48:38.083  INFO 3351 --- [           main] o.f.c.i.s.JdbcTableSchemaHistory         : Creating Schema History table: `flyway-test`.`flyway_schema_history`
2020-03-05 10:48:38.143  INFO 3351 --- [           main] o.f.core.internal.command.DbMigrate      : Current version of schema `flyway-test`: << Empty Schema >>
2020-03-05 10:48:38.144  INFO 3351 --- [           main] o.f.core.internal.command.DbMigrate      : Migrating schema `flyway-test` to version 1.0 - INIT
2020-03-05 10:48:38.156  WARN 3351 --- [           main] o.f.c.i.s.DefaultSqlScriptExecutor       : DB: Unknown table 'flyway-test.t_user' (SQL State: 42S02 - Error Code: 1051)
2020-03-05 10:48:38.183  INFO 3351 --- [           main] o.f.core.internal.command.DbMigrate      : Successfully applied 1 migration to schema `flyway-test` (execution time 00:00.100s)
```

检查数据库，发现创建了 2 张表，一张是 Flyway 依赖的历史表，另一张就是我们的 `t_user` 表

<img src="http://static.xkcoding.com/spring-boot-demo/flyway/062903.jpg" alt="image-20200305105632047" style="zoom:50%;" />

查看下 `flyway-schema-history` 表

<img src="http://static.xkcoding.com/spring-boot-demo/flyway/062901.jpg" alt="image-20200305110147176" style="zoom:50%;" />

### 4.2. 测试 1.1 版本的 SQL 脚本

创建 `V1_1__ALTER.sql` 

```mysql
ALTER TABLE t_user COMMENT = '用户 v1.1';
```

启动项目，可以看到日志输出：

```java
2020-03-05 10:59:02.279  INFO 3536 --- [           main] o.f.c.internal.license.VersionPrinter    : Flyway Community Edition 5.2.1 by Boxfuse
2020-03-05 10:59:02.282  INFO 3536 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2020-03-05 10:59:02.442  INFO 3536 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2020-03-05 10:59:02.445  INFO 3536 --- [           main] o.f.c.internal.database.DatabaseFactory  : Database: jdbc:mysql://127.0.0.1:3306/flyway-test (MySQL 5.7)
2020-03-05 10:59:02.530  INFO 3536 --- [           main] o.f.core.internal.command.DbValidate     : Successfully validated 2 migrations (execution time 00:00.018s)
2020-03-05 10:59:02.538  INFO 3536 --- [           main] o.f.core.internal.command.DbMigrate      : Current version of schema `flyway-test`: 1.0
2020-03-05 10:59:02.538  INFO 3536 --- [           main] o.f.core.internal.command.DbMigrate      : Migrating schema `flyway-test` to version 1.1 - ALTER
2020-03-05 10:59:02.564  INFO 3536 --- [           main] o.f.core.internal.command.DbMigrate      : Successfully applied 1 migration to schema `flyway-test` (execution time 00:00.029s)
```

检查数据库，可以发现 `t_user` 表的注释已经更新

<img src="http://static.xkcoding.com/spring-boot-demo/flyway/062851.jpg" alt="image-20200305105958181" style="zoom:50%;" />

查看下 `flyway-schema-history` 表

<img src="http://static.xkcoding.com/spring-boot-demo/flyway/062908.jpg" alt="image-20200305110057768" style="zoom:50%;" />

## 参考

1. [Spring Boot 官方文档 - Migration 章节](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup)
2. [Flyway 官方文档](https://flywaydb.org/documentation/)