# spring-boot-demo-sharding-jdbc

> 本 demo 主要演示了如何集成 `sharding-jdbc` 实现分库分表操作，ORM 层使用了`Mybatis-Plus`简化开发，童鞋们可以按照自己的喜好替换为 JPA、通用Mapper、JdbcTemplate甚至原生的JDBC都可以。
>
> PS：
>
> 1. 目前当当官方提供的starter存在bug，版本号：`3.1.0`，因此本demo采用手动配置。
> 2. 文档真的很垃圾​ :joy:

## 1. 运行方式

1. 在数据库创建2个数据库，分别为：`spring-boot-demo`、`spring-boot-demo-2`
2. 去数据库执行 `sql/schema.sql` ，创建 `6` 张分片表
3. 找到 `DataSourceShardingConfig` 配置类，修改 `数据源` 的相关配置，位于 `dataSourceMap()` 这个方法
4. 找到测试类 `SpringBootDemoShardingJdbcApplicationTests` 进行测试

## 2. 关键代码

### 2.1. `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <artifactId>spring-boot-demo-sharding-jdbc</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>spring-boot-demo-sharding-jdbc</name>
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
      <artifactId>spring-boot-starter</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-boot-starter</artifactId>
      <version>3.1.0</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
    </dependency>

    <dependency>
      <groupId>io.shardingsphere</groupId>
      <artifactId>sharding-jdbc-core</artifactId>
      <version>3.1.0</version>
    </dependency>

    <dependency>
      <groupId>cn.hutool</groupId>
      <artifactId>hutool-all</artifactId>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
  </dependencies>

  <build>
    <finalName>spring-boot-demo-sharding-jdbc</finalName>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

</project>
```

### 2.2. `CustomSnowflakeKeyGenerator.java`

```java
package com.xkcoding.sharding.jdbc.config;

import cn.hutool.core.lang.Snowflake;
import io.shardingsphere.core.keygen.KeyGenerator;

/**
 * <p>
 * 自定义雪花算法，替换 DefaultKeyGenerator，避免DefaultKeyGenerator生成的id大几率是偶数
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-03-26 17:07
 */
public class CustomSnowflakeKeyGenerator implements KeyGenerator {
    private Snowflake snowflake;

    public CustomSnowflakeKeyGenerator(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

    @Override
    public Number generateKey() {
        return snowflake.nextId();
    }
}
```

### 2.3. `DataSourceShardingConfig.java`

```java
/**
 * <p>
 * sharding-jdbc 的数据源配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-03-26 16:47
 */
@Configuration
public class DataSourceShardingConfig {
    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    /**
     * 需要手动配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 设置分库策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        // 设置规则适配的表
        shardingRuleConfig.getBindingTableGroups().add("t_order");
        // 设置分表策略
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRule());
        shardingRuleConfig.setDefaultDataSourceName("ds0");
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());

        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");

        return ShardingDataSourceFactory.createDataSource(dataSourceMap(), shardingRuleConfig, new ConcurrentHashMap<>(16), properties);
    }

    private TableRuleConfiguration orderTableRule() {
        TableRuleConfiguration tableRule = new TableRuleConfiguration();
        // 设置逻辑表名
        tableRule.setLogicTable("t_order");
        // ds${0..1}.t_order_${0..2} 也可以写成 ds$->{0..1}.t_order_$->{0..1}
        tableRule.setActualDataNodes("ds${0..1}.t_order_${0..2}");
        tableRule.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_$->{order_id % 3}"));
        tableRule.setKeyGenerator(customKeyGenerator());
        tableRule.setKeyGeneratorColumnName("order_id");
        return tableRule;
    }

    private Map<String, DataSource> dataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(16);

        // 配置第一个数据源
        HikariDataSource ds0 = new HikariDataSource();
        ds0.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds0.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8");
        ds0.setUsername("root");
        ds0.setPassword("root");

        // 配置第二个数据源
        HikariDataSource ds1 = new HikariDataSource();
        ds1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds1.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/spring-boot-demo-2?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8");
        ds1.setUsername("root");
        ds1.setPassword("root");

        dataSourceMap.put("ds0", ds0);
        dataSourceMap.put("ds1", ds1);
        return dataSourceMap;
    }

    /**
     * 自定义主键生成器
     */
    private KeyGenerator customKeyGenerator() {
        return new CustomSnowflakeKeyGenerator(snowflake);
    }

}
```

### 2.3. `SpringBootDemoShardingJdbcApplicationTests.java`

```java
/**
 * <p>
 * 测试sharding-jdbc分库分表
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-03-26 13:44
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoShardingJdbcApplicationTests {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 测试新增
     */
    @Test
    public void testInsert() {
        for (long i = 1; i < 10; i++) {
            for (long j = 1; j < 20; j++) {
                Order order = Order.builder().userId(i).orderId(j).remark(RandomUtil.randomString(20)).build();
                orderMapper.insert(order);
            }
        }
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        Order update = new Order();
        update.setRemark("修改备注信息");
        orderMapper.update(update, Wrappers.<Order>update().lambda().eq(Order::getOrderId, 2).eq(Order::getUserId, 2));
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        orderMapper.delete(new QueryWrapper<>());
    }

    /**
     * 测试查询
     */
    @Test
    public void testSelect() {
        List<Order> orders = orderMapper.selectList(Wrappers.<Order>query().lambda().in(Order::getOrderId, 1, 2));
        log.info("【orders】= {}", JSONUtil.toJsonStr(orders));
    }

}
```

## 3. 参考

1. `ShardingSphere` 官网：https://shardingsphere.apache.org/index_zh.html (虽然文档确实垃圾，但是还是得参考啊~)
2. `Mybatis-Plus` 语法参考官网：https://mybatis.plus/
