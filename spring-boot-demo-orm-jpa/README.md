# spring-boot-demo-orm-jpa
> 此 demo 主要演示了 Spring Boot 如何使用 JPA 操作数据库。

## pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-orm-jpa</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-orm-jpa</name>
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
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
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
        <finalName>spring-boot-demo-orm-jpa</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```
##  JpaConfig.java
```java
/**
 * <p>
 * JPA配置类
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.config
 * @description: JPA配置类
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 11:05
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.xkcoding.orm.jpa.repository", transactionManagerRef = "jpaTransactionManager")
public class JpaConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter japVendor = new HibernateJpaVendorAdapter();
        japVendor.setGenerateDdl(false);
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(japVendor);
        entityManagerFactory.setPackagesToScan("com.xkcoding.orm.jpa.entity");
        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
```
## User.java
```java
/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.entity
 * @description: 用户实体类
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 14:06
 * @copyright: Copyright (c)
 * @version: V1.0
 * @modified: yangkai.shen
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orm_user")
@ToString(callSuper = true)
public class User extends AbstractAuditModel {
    /**
     * 用户名
     */
    private String name;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 加密使用的盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;
}
```
## AbstractAuditModel.java
```java
/**
 * <p>
 * 实体通用父类
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.entity.base
 * @description: 实体通用父类
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 14:01
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractAuditModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    @CreatedDate
    private Date createTime;

    /**
     * 上次更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_time", nullable = false)
    @LastModifiedDate
    private Date lastUpdateTime;
}
```
## UserDao.java
```java
/**
 * <p>
 * User Dao
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.repository
 * @description: User Dao
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 14:07
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

}
```
## application.yml
```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
spring:
  datasource:
    jdbc-url: jdbc:mysql://127.0.0.1:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
    initialization-mode: always
    continue-on-error: true
    schema:
    - "classpath:db/schema.sql"
    data:
    - "classpath:db/data.sql"
    hikari:
      minimum-idle: 5
      connection-test-query: SELECT 1 FROM DUAL
      maximum-pool-size: 20
      auto-commit: true
      idle-timeout: 30000
      pool-name: SpringBootDemoHikariCP
      max-lifetime: 60000
      connection-timeout: 30000
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: validate
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
## UserDaoTest.java
```java
/**
 * <p>
 * jpa 测试类
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.repository
 * @description: jpa 测试类
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 14:09
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class UserDaoTest extends SpringBootDemoOrmJpaApplicationTests {
    @Autowired
    private UserDao userDao;

    /**
     * 测试保存
     */
    @Test
    public void testSave() {
        String salt = IdUtil.fastSimpleUUID();
        User testSave3 = User.builder().name("testSave3").password(SecureUtil.md5("123456" + salt)).salt(salt).email("testSave3@xkcoding.com").phoneNumber("17300000003").status(1).lastLoginTime(new DateTime()).build();
        userDao.save(testSave3);

        Assert.assertNotNull(testSave3.getId());
        Optional<User> byId = userDao.findById(testSave3.getId());
        Assert.assertTrue(byId.isPresent());
        log.debug("【byId】= {}", byId.get());
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        long count = userDao.count();
        userDao.deleteById(1L);
        long left = userDao.count();
        Assert.assertEquals(count - 1, left);
    }

    /**
     * 测试修改
     */
    @Test
    public void testUpdate() {
        userDao.findById(1L).ifPresent(user -> {
            user.setName("JPA修改名字");
            userDao.save(user);
        });
        Assert.assertEquals("JPA修改名字", userDao.findById(1L).get().getName());
    }

    /**
     * 测试查询单个
     */
    @Test
    public void testQueryOne() {
        Optional<User> byId = userDao.findById(1L);
        Assert.assertTrue(byId.isPresent());
        log.debug("【byId】= {}", byId.get());
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testQueryAll() {
        List<User> users = userDao.findAll();
        Assert.assertNotEquals(0, users.size());
        log.debug("【users】= {}", users);
    }

    /**
     * 测试分页排序查询
     */
    @Test
    public void testQueryPage() {
        // 初始化数据
        initData();
        // JPA分页的时候起始页是页码减1
        Integer currentPage = 0;
        Integer pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        Page<User> userPage = userDao.findAll(pageRequest);

        Assert.assertEquals(5, userPage.getSize());
        Assert.assertEquals(userDao.count(), userPage.getTotalElements());
        log.debug("【id】= {}", userPage.getContent().stream().map(User::getId).collect(Collectors.toList()));
    }

    /**
     * 初始化10条数据
     */
    private void initData() {
        List<User> userList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            String salt = IdUtil.fastSimpleUUID();
            int index = 3 + i;
            User user = User.builder().name("testSave" + index).password(SecureUtil.md5("123456" + salt)).salt(salt).email("testSave" + index + "@xkcoding.com").phoneNumber("1730000000" + index).status(1).lastLoginTime(new DateTime()).build();
            userList.add(user);
        }
        userDao.saveAll(userList);
    }

}
```

## 参考

Spring Data JPA 官方文档：https://docs.spring.io/spring-data/jpa/docs/current/reference/html/ 