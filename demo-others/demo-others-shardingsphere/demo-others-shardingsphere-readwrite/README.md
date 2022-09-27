# spring-boot-demo-shardingsphere-readwrite

> 本 demo 主要演示了如何集成 `shardingsphere` 实现读写分离操作，ORM 层使用了`Mybatis-Plus`简化开发，童鞋们可以按照自己的喜好替换为 JPA、通用Mapper、JdbcTemplate甚至原生的JDBC都可以。

## 1.开发步骤

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>common-tools</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
  </dependency>

  <dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
    <version>${shardingsphere.version}</version>
  </dependency>

  <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>${mybatis-plus.version}</version>
  </dependency>

  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.添加读写分离配置

```yaml
spring:
  shardingsphere:
    mode:
      type: Memory
    # 数据源配置
    datasource:
      names: master,slave1,slave2
      master:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/spring-boot-demo
        username: root
        password: root
      slave1:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3307/spring-boot-demo
        username: root
        password: root
      slave2:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3308/spring-boot-demo
        username: root
        password: root

    rules:
      # 读写分离规则配置
      readwrite-splitting:
        data-sources:
          demo-datasource:
            type: Static
            props:
              write-data-source-name: master
              read-data-source-names: slave1,slave2
              load-balancer-name: test_round_robin
        # 负载均衡算法配置
        load-balancers:
          test_round_robin:
            type: ROUND_ROBIN

    # 打印 SQL
    props:
      sql-show: true

```

### 1.3.实体类和 ORM 操作

```java
@Data
@TableName("t_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

}

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
```

## 2.测试

### 2.1.测试环境搭建

主要是 MySQL 的主从同步环境搭建，这里我提供了 docker-compose 文件，方便同学们一键启动测试环境

```bash
$ cd demo-others/demo-others-shardingsphere/demo-others-shardingsphere-readwrite/env
$ docker compose -f docker-compose.env.yml up -d
```

此时会启动 3 台 MySQL 服务：

```
master → 3306
slave1 → 3307
slave2 → 3308
```

接下来我们需要进入 master 服务的命令行，做一些基础配置。

```bash
$ docker compose -f docker-compose.env.yml exec -it master /bin/bash
```

接下来在 master 服务创建专门用于主从同步的用户（*测试场景下，也可以使用 root 用户，为了模拟真实场景，这里创建新用户用于主从同步*）

```bash
mysql -uroot -proot
```

进入 MySQL 的交互命令行之后，执行创建用户的 SQL：

```mysql
mysql> create user 'demo4slave'@'%';
Query OK, 0 rows affected (0.00 sec)

mysql> alter user 'demo4slave'@'%' identified with mysql_native_password by 'demo4slave';
Query OK, 0 rows affected (0.01 sec)

mysql> grant replication slave on *.* to 'demo4slave'@'%';
Query OK, 0 rows affected (0.00 sec)

mysql> flush privileges;
Query OK, 0 rows affected (0.00 sec)
```

创建完用户之后，在 master 服务中查询主从同步环境下主服务器 binlog 文件的写入状态

> 注意：
>
> 1. 这个状态很重要，从服务器就是根据这个文件的具体位置开始进行同步操作的，后续配置从服务器的时候需要用到！
> 2. 执行完之后，就不要再操作 master 服务了，防止 binlog 文件的写入状态发生变化。

```mysql
mysql> show master status;
+---------------+----------+--------------+------------------+-------------------+
| File          | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+---------------+----------+--------------+------------------+-------------------+
| binlog.000002 |     1066 |              |                  |                   |
+---------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```

master 服务操作截图如下：

<img src="https://static.xkcoding.com/spring-boot-demo/demo-others/demo-others-shardingsphere/2022-09-27-050351.png" alt="CleanShot 2022-09-27 at 13.02.42@2x" style="zoom:50%;" />

接下来配置从服务器，同样，我们先需要进入从服务的命令行，做一些基础配置。

```bash
$ docker compose -f docker-compose.env.yml exec -it slave1 /bin/bash
```

然后进入 MySQL 的交互命令行

```bash
mysql -uroot -proot
```

接着配置主从关系，并开启主从同步

> 注意：
>
> 1. MASTER_HOST 这里是因为我们用的是 docker-compose 的方式，所以可以直接使用服务名作为 host 访问
> 2. MASTER_LOG_FILE 和 MASTER_LOG_POS 是上面从主服务查询出来的

```mysql
mysql> change master to MASTER_HOST='master',MASTER_PORT=3306,MASTER_USER='demo4slave',MASTER_PASSWORD='demo4slave',MASTER_LOG_FILE='binlog.000002',MASTER_LOG_POS=1066;
Query OK, 0 rows affected, 9 warnings (0.01 sec)

mysql> start slave;
Query OK, 0 rows affected, 1 warning (0.01 sec)
```

查看主从同步状态

```mysql
mysql> show slave status\G
*************************** 1. row ***************************
               Slave_IO_State: Waiting for source to send event
                  Master_Host: master
                  Master_User: demo4slave
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: binlog.000002
          Read_Master_Log_Pos: 1066
               Relay_Log_File: 74a825687f44-relay-bin.000002
                Relay_Log_Pos: 323
        Relay_Master_Log_File: binlog.000002
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
              Replicate_Do_DB:
          Replicate_Ignore_DB:
           Replicate_Do_Table:
       Replicate_Ignore_Table:
      Replicate_Wild_Do_Table:
  Replicate_Wild_Ignore_Table:
                   Last_Errno: 0
                   Last_Error:
                 Skip_Counter: 0
          Exec_Master_Log_Pos: 1066
              Relay_Log_Space: 540
              Until_Condition: None
               Until_Log_File:
                Until_Log_Pos: 0
           Master_SSL_Allowed: No
           Master_SSL_CA_File:
           Master_SSL_CA_Path:
              Master_SSL_Cert:
            Master_SSL_Cipher:
               Master_SSL_Key:
        Seconds_Behind_Master: 0
Master_SSL_Verify_Server_Cert: No
                Last_IO_Errno: 0
                Last_IO_Error:
               Last_SQL_Errno: 0
               Last_SQL_Error:
  Replicate_Ignore_Server_Ids:
             Master_Server_Id: 3306
                  Master_UUID: 724e47c4-3e21-11ed-98ad-0242c0a81003
             Master_Info_File: mysql.slave_master_info
                    SQL_Delay: 0
          SQL_Remaining_Delay: NULL
      Slave_SQL_Running_State: Replica has read all relay log; waiting for more updates
           Master_Retry_Count: 86400
                  Master_Bind:
      Last_IO_Error_Timestamp:
     Last_SQL_Error_Timestamp:
               Master_SSL_Crl:
           Master_SSL_Crlpath:
           Retrieved_Gtid_Set:
            Executed_Gtid_Set:
                Auto_Position: 0
         Replicate_Rewrite_DB:
                 Channel_Name:
           Master_TLS_Version:
       Master_public_key_path:
        Get_master_public_key: 0
            Network_Namespace:
1 row in set, 1 warning (0.00 sec)
```

查看状态发现，`Slave_IO_Running` 和 `Slave_SQL_Running` 都是 YES 的时候，就表示主从同步配置已经生效了。

slave 服务操作截图如下：

<img src="https://static.xkcoding.com/spring-boot-demo/demo-others/demo-others-shardingsphere/2022-09-27-CleanShot%202022-09-27%20at%2013.05.53%402x.png" alt="CleanShot 2022-09-27 at 13.05.53@2x" style="zoom:50%;" />

最后将另一台从服务器同样配置即可完成主从同步的环境搭建。

接下来我们在通过工具连接主服务器，创建表并新增数据，在从服务器中查看表和数据是否已经正常同步

```mysql
USE `spring-boot-demo`;
CREATE TABLE `t_user`(
	id BIGINT AUTO_INCREMENT,
	username VARCHAR(30),
	PRIMARY KEY (id)
);

INSERT t_user(username) VALUES ('xkcoding');
INSERT t_user(username) VALUES ('spring-boot-demo-test');
```

### 2.2.测试读写分离

```java
@SpringBootTest
public class ReadWriteTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername(RandomUtil.randomString(5));
        userMapper.insert(user);
    }

    @Test
    public void testSelect() {
        // 测试负载均衡
        for (int i = 0; i < 6; i++) {
            List<User> users = userMapper.selectList(null);
            Optional.ofNullable(users).ifPresent(x -> x.forEach(System.out::println));
        }
    }

    /**
     * 开启事务之后，读写操作均在 master 上
     */
    @Test
    @Transactional
    public void testTransactional() {
        User user = new User();
        user.setUsername(RandomUtil.randomString(5));
        userMapper.insert(user);

        List<User> users = userMapper.selectList(null);
        Optional.ofNullable(users).ifPresent(x -> x.forEach(System.out::println));
    }

}
```

- 运行 `ReadWriteTests#testInsert()` 方法，查看日志输出，可以发现`写操作`路由到主服务器。
- 运行 `ReadWriteTests#testSelect()` 方法，查看日志输出，可以发现 `读操作` 路由到从服务器，同时实现了负载均衡的策略。
- 运行 `ReadWriteTests#testTransactional()` 方法，查看日志输出，可以发现，开启事务之后，读写操作，均只在主服务器执行。

## 3.参考

- [shardingsphere 官方文档](https://shardingsphere.apache.org/index_zh.html)
- [shardingsphere 官方文档之数据源配置](https://shardingsphere.apache.org/document/5.1.1/cn/user-manual/shardingsphere-jdbc/spring-boot-starter/data-source/)
- [shardingsphere 官方文档之读写分离配置](https://shardingsphere.apache.org/document/5.1.1/cn/user-manual/shardingsphere-jdbc/spring-boot-starter/rules/readwrite-splitting/)
- [shardingsphere 官方文档之负载均衡算法配置](https://shardingsphere.apache.org/document/5.1.1/cn/user-manual/shardingsphere-jdbc/builtin-algorithm/load-balance/)
- [MySQL 官方文档之 replication](https://dev.mysql.com/doc/refman/8.0/en/replication.html)