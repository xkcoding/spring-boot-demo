# spring-boot-demo-multi-datasource-jpa

> 此 demo 主要演示 Spring Boot 如何集成 JPA 的多数据源。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-multi-datasource-jpa</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-multi-datasource-jpa</name>
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
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-multi-datasource-jpa</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## PrimaryDataSourceConfig.java

> 主数据源配置

```java
/**
 * <p>
 * JPA多数据源配置 - 主数据源
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-17 15:58
 */
@Configuration
public class PrimaryDataSourceConfig {

    /**
     * 扫描spring.datasource.primary开头的配置信息
     *
     * @return 数据源配置信息
     */
    @Primary
    @Bean(name = "primaryDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 获取主库数据源对象
     *
     * @param dataSourceProperties 注入名为primaryDataSourceProperties的bean
     * @return 数据源对象
     */
    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource dataSource(@Qualifier("primaryDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为primaryDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Primary
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
```

## SecondDataSourceConfig.java

> 从数据源配置

```java
/**
 * <p>
 * JPA多数据源配置 - 次数据源
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-17 15:58
 */
@Configuration
public class SecondDataSourceConfig {

    /**
     * 扫描spring.datasource.second开头的配置信息
     *
     * @return 数据源配置信息
     */
    @Bean(name = "secondDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 获取主库数据源对象
     *
     * @param dataSourceProperties 注入名为secondDataSourceProperties的bean
     * @return 数据源对象
     */
    @Bean(name = "secondDataSource")
    public DataSource dataSource(@Qualifier("secondDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为secondDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Bean(name = "secondJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
```

## PrimaryJpaConfig.java

> 主 JPA 配置

```java
/**
 * <p>
 * JPA多数据源配置 - 主 JPA 配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-17 16:54
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        // repository包名
        basePackages = PrimaryJpaConfig.REPOSITORY_PACKAGE,
        // 实体管理bean名称
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        // 事务管理bean名称
        transactionManagerRef = "primaryTransactionManager")
public class PrimaryJpaConfig {
    static final String REPOSITORY_PACKAGE = "com.xkcoding.multi.datasource.jpa.repository.primary";
    private static final String ENTITY_PACKAGE = "com.xkcoding.multi.datasource.jpa.entity.primary";


    /**
     * 扫描spring.jpa.primary开头的配置信息
     *
     * @return jpa配置信息
     */
    @Primary
    @Bean(name = "primaryJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.primary")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * 获取主库实体管理工厂对象
     *
     * @param primaryDataSource 注入名为primaryDataSource的数据源
     * @param jpaProperties     注入名为primaryJpaProperties的jpa配置信息
     * @param builder           注入EntityManagerFactoryBuilder
     * @return 实体管理工厂对象
     */
    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource, @Qualifier("primaryJpaProperties") JpaProperties jpaProperties, EntityManagerFactoryBuilder builder) {
        return builder
                // 设置数据源
                .dataSource(primaryDataSource)
                // 设置jpa配置
                .properties(jpaProperties.getProperties())
                // 设置实体包名
                .packages(ENTITY_PACKAGE)
                // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .persistenceUnit("primaryPersistenceUnit").build();
    }

    /**
     * 获取实体管理对象
     *
     * @param factory 注入名为primaryEntityManagerFactory的bean
     * @return 实体管理对象
     */
    @Primary
    @Bean(name = "primaryEntityManager")
    public EntityManager entityManager(@Qualifier("primaryEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取主库事务管理对象
     *
     * @param factory 注入名为primaryEntityManagerFactory的bean
     * @return 事务管理对象
     */
    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("primaryEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

}
```

## SecondJpaConfig.java

> 从 JPA 配置

```java
/**
 * <p>
 * JPA多数据源配置 - 次 JPA 配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-01-17 16:54
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        // repository包名
        basePackages = SecondJpaConfig.REPOSITORY_PACKAGE,
        // 实体管理bean名称
        entityManagerFactoryRef = "secondEntityManagerFactory",
        // 事务管理bean名称
        transactionManagerRef = "secondTransactionManager")
public class SecondJpaConfig {
    static final String REPOSITORY_PACKAGE = "com.xkcoding.multi.datasource.jpa.repository.second";
    private static final String ENTITY_PACKAGE = "com.xkcoding.multi.datasource.jpa.entity.second";


    /**
     * 扫描spring.jpa.second开头的配置信息
     *
     * @return jpa配置信息
     */
    @Bean(name = "secondJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.second")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * 获取主库实体管理工厂对象
     *
     * @param secondDataSource 注入名为secondDataSource的数据源
     * @param jpaProperties    注入名为secondJpaProperties的jpa配置信息
     * @param builder          注入EntityManagerFactoryBuilder
     * @return 实体管理工厂对象
     */
    @Bean(name = "secondEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("secondDataSource") DataSource secondDataSource, @Qualifier("secondJpaProperties") JpaProperties jpaProperties, EntityManagerFactoryBuilder builder) {
        return builder
                // 设置数据源
                .dataSource(secondDataSource)
                // 设置jpa配置
                .properties(jpaProperties.getProperties())
                // 设置实体包名
                .packages(ENTITY_PACKAGE)
                // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .persistenceUnit("secondPersistenceUnit").build();
    }

    /**
     * 获取实体管理对象
     *
     * @param factory 注入名为secondEntityManagerFactory的bean
     * @return 实体管理对象
     */
    @Bean(name = "secondEntityManager")
    public EntityManager entityManager(@Qualifier("secondEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取主库事务管理对象
     *
     * @param factory 注入名为secondEntityManagerFactory的bean
     * @return 事务管理对象
     */
    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("secondEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

}
```

## application.yml

```yaml
spring:
  datasource:
    primary:
      url: jdbc:mysql://127.0.0.1:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8
      username: root
      password: root
      driver-class-name: com.mysql.cj.jdbc.Driver
      type: com.zaxxer.hikari.HikariDataSource
      hikari:
        minimum-idle: 5
        connection-test-query: SELECT 1 FROM DUAL
        maximum-pool-size: 20
        auto-commit: true
        idle-timeout: 30000
        pool-name: PrimaryHikariCP
        max-lifetime: 60000
        connection-timeout: 30000
    second:
      url: jdbc:mysql://127.0.0.1:3306/spring-boot-demo-2?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8
      username: root
      password: root
      driver-class-name: com.mysql.cj.jdbc.Driver
      type: com.zaxxer.hikari.HikariDataSource
      hikari:
        minimum-idle: 5
        connection-test-query: SELECT 1 FROM DUAL
        maximum-pool-size: 20
        auto-commit: true
        idle-timeout: 30000
        pool-name: SecondHikariCP
        max-lifetime: 60000
        connection-timeout: 30000
  jpa:
    primary:
      show-sql: true
      generate-ddl: true
      hibernate:
        ddl-auto: update
      properties:
        hibernate:
          dialect: org.hibernate.dialect.MySQL57InnoDBDialect
      open-in-view: true
    second:
      show-sql: true
      generate-ddl: true
      hibernate:
        ddl-auto: update
      properties:
        hibernate:
          dialect: org.hibernate.dialect.MySQL57InnoDBDialect
      open-in-view: true
logging:
  level:
    com.xkcoding: debug
    org.hibernate.SQL: debug
    org.hibernate.type: trace
```

## SpringBootDemoMultiDatasourceJpaApplicationTests.java

```java
package com.xkcoding.multi.datasource.jpa;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import com.xkcoding.multi.datasource.jpa.entity.primary.PrimaryMultiTable;
import com.xkcoding.multi.datasource.jpa.entity.second.SecondMultiTable;
import com.xkcoding.multi.datasource.jpa.repository.primary.PrimaryMultiTableRepository;
import com.xkcoding.multi.datasource.jpa.repository.second.SecondMultiTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootDemoMultiDatasourceJpaApplicationTests {
    @Autowired
    private PrimaryMultiTableRepository primaryRepo;
    @Autowired
    private SecondMultiTableRepository secondRepo;
    @Autowired
    private Snowflake snowflake;

    @Test
    public void testInsert() {
        PrimaryMultiTable primary = new PrimaryMultiTable(snowflake.nextId(),"测试名称-1");
        primaryRepo.save(primary);

        SecondMultiTable second = new SecondMultiTable();
        BeanUtil.copyProperties(primary, second);
        secondRepo.save(second);
    }

    @Test
    public void testUpdate() {
        primaryRepo.findAll().forEach(primary -> {
            primary.setName("修改后的"+primary.getName());
            primaryRepo.save(primary);

            SecondMultiTable second = new SecondMultiTable();
            BeanUtil.copyProperties(primary, second);
            secondRepo.save(second);
        });
    }

    @Test
    public void testDelete() {
        primaryRepo.deleteAll();

        secondRepo.deleteAll();
    }

    @Test
    public void testSelect() {
        List<PrimaryMultiTable> primary = primaryRepo.findAll();
        log.info("【primary】= {}", primary);

        List<SecondMultiTable> second = secondRepo.findAll();
        log.info("【second】= {}", second);
    }

}
```

## 目录结构

```
.
├── README.md
├── pom.xml
├── spring-boot-demo-multi-datasource-jpa.iml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.xkcoding.multi.datasource.jpa
│   │   │       ├── SpringBootDemoMultiDatasourceJpaApplication.java
│   │   │       ├── config
│   │   │       │   ├── PrimaryDataSourceConfig.java
│   │   │       │   ├── PrimaryJpaConfig.java
│   │   │       │   ├── SecondDataSourceConfig.java
│   │   │       │   ├── SecondJpaConfig.java
│   │   │       │   └── SnowflakeConfig.java
│   │   │       ├── entity
│   │   │       │   ├── primary
│   │   │       │   │   └── PrimaryMultiTable.java
│   │   │       │   └── second
│   │   │       │       └── SecondMultiTable.java
│   │   │       └── repository
│   │   │               ├── primary
│   │   │               │   └── PrimaryMultiTableRepository.java
│   │   │               └── second
│   │   │                   └── SecondMultiTableRepository.java
│   │   └── resources
│   │       └── application.yml
│   └── test
│       └── java
│           └── com.xkcoding.multi.datasource.jpa
│               └── SpringBootDemoMultiDatasourceJpaApplicationTests.java
└── target
```

## 参考

1. https://www.jianshu.com/p/34730e595a8c
2. https://blog.csdn.net/anxpp/article/details/52274120
