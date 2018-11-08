# spring-boot-demo-orm-mybatis-mapper-page

> 此 demo 演示了 Spring Boot 如何集成通用Mapper插件和分页助手插件，简化Mybatis开发，带给你难以置信的开发体验。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-orm-mybatis-mapper-page</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-orm-mybatis-mapper-page</name>
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
        <mybatis.mapper.version>2.0.4</mybatis.mapper.version>
        <mybatis.pagehelper.version>1.2.9</mybatis.pagehelper.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <!-- 通用Mapper -->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>${mybatis.mapper.version}</version>
        </dependency>

        <!-- 分页助手 -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>${mybatis.pagehelper.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
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
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-orm-mybatis-mapper-page</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## SpringBootDemoOrmMybatisApplication.java

```java
/**
 * <p>
 * 启动器
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.MapperAndPage
 * @description: 启动器
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 13:43
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.xkcoding.orm.mybatis.MapperAndPage.mapper"}) // 注意：这里的 MapperScan 是 tk.mybatis.spring.annotation.MapperScan 这个包下的
public class SpringBootDemoOrmMybatisMapperPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoOrmMybatisMapperPageApplication.class, args);
    }
}
```

## application.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8
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
logging:
  level:
    com.xkcoding: debug
    com.xkcoding.orm.mybatis.MapperAndPage.mapper: trace
mybatis:
  configuration:
    # 下划线转驼峰
    map-underscore-to-camel-case: true
  mapper-locations: classpath:mappers/*.xml
  type-aliases-package: com.xkcoding.orm.mybatis.MapperAndPage.entity
mapper:
  mappers:
  - tk.mybatis.mapper.common.Mapper
  not-empty: true
  style: camelhump
  wrap-keyword: "`{0}`"
  safe-delete: true
  safe-update: true
  identity: MYSQL
pagehelper:
  auto-dialect: true
  helper-dialect: mysql
  reasonable: true
  params: count=countSql
```

## UserMapper.java

```java
/**
 * <p>
 * UserMapper
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.MapperAndPage.mapper
 * @description: UserMapper
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 14:15
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
// 注意：这里的Mapper是tk.mybatis.mapper.common.Mapper包下的
public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
}
```

## UserMapperTest.java

```java
/**
 * <p>
 * UserMapper 测试
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.MapperAndPage.mapper
 * @description: UserMapper 测试
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 14:25
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class UserMapperTest extends SpringBootDemoOrmMybatisMapperPageApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试通用Mapper - 保存
     */
    @Test
    public void testInsert() {
        String salt = IdUtil.fastSimpleUUID();
        User testSave3 = User.builder().name("testSave3").password(SecureUtil.md5("123456" + salt)).salt(salt).email("testSave3@xkcoding.com").phoneNumber("17300000003").status(1).lastLoginTime(new DateTime()).createTime(new DateTime()).lastUpdateTime(new DateTime()).build();
        userMapper.insertUseGeneratedKeys(testSave3);
        Assert.assertNotNull(testSave3.getId());
        log.debug("【测试主键回写#testSave3.getId()】= {}", testSave3.getId());
    }

    /**
     * 测试通用Mapper - 批量保存
     */
    @Test
    public void testInsertList() {
        List<User> userList = Lists.newArrayList();
        for (int i = 4; i < 14; i++) {
            String salt = IdUtil.fastSimpleUUID();
            User user = User.builder().name("testSave" + i).password(SecureUtil.md5("123456" + salt)).salt(salt).email("testSave" + i + "@xkcoding.com").phoneNumber("1730000000" + i).status(1).lastLoginTime(new DateTime()).createTime(new DateTime()).lastUpdateTime(new DateTime()).build();
            userList.add(user);
        }
        int i = userMapper.insertList(userList);
        Assert.assertEquals(userList.size(), i);
        List<Long> ids = userList.stream().map(User::getId).collect(Collectors.toList());
        log.debug("【测试主键回写#userList.ids】= {}", ids);
    }

    /**
     * 测试通用Mapper - 删除
     */
    @Test
    public void testDelete() {
        Long primaryKey = 1L;
        int i = userMapper.deleteByPrimaryKey(primaryKey);
        Assert.assertEquals(1, i);
        User user = userMapper.selectByPrimaryKey(primaryKey);
        Assert.assertNull(user);
    }

    /**
     * 测试通用Mapper - 更新
     */
    @Test
    public void testUpdate() {
        Long primaryKey = 1L;
        User user = userMapper.selectByPrimaryKey(primaryKey);
        user.setName("通用Mapper名字更新");
        int i = userMapper.updateByPrimaryKeySelective(user);
        Assert.assertEquals(1, i);
        User update = userMapper.selectByPrimaryKey(primaryKey);
        Assert.assertNotNull(update);
        Assert.assertEquals("通用Mapper名字更新", update.getName());
        log.debug("【update】= {}", update);
    }

    /**
     * 测试通用Mapper - 查询
     */
    @Test
    public void testQueryAll() {
        List<User> users = userMapper.selectAll();
        Assert.assertTrue(CollUtil.isNotEmpty(users));
        log.debug("【users】= {}", users);
    }

    /**
     * 测试分页助手 - 分页排序查询
     */
    @Test
    public void testQueryByPageAndSort() {
        initData();
        int currentPage = 1;
        int pageSize = 5;
        String orderBy = "id desc";
        int count = userMapper.selectCount(null);
        PageHelper.startPage(currentPage, pageSize, orderBy);
        List<User> users = userMapper.selectAll();
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        Assert.assertEquals(5, userPageInfo.getSize());
        Assert.assertEquals(count, userPageInfo.getTotal());
        log.debug("【userPageInfo】= {}", userPageInfo);
    }

    /**
     * 测试通用Mapper - 条件查询
     */
    @Test
    public void testQueryByCondition() {
        initData();
        Example example = new Example(User.class);
        // 过滤
        example.createCriteria().andLike("name", "%Save1%").orEqualTo("phoneNumber", "17300000001");
        // 排序
        example.setOrderByClause("id desc");
        int count = userMapper.selectCountByExample(example);
        // 分页
        PageHelper.startPage(1, 3);
        // 查询
        List<User> userList = userMapper.selectByExample(example);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        Assert.assertEquals(3, userPageInfo.getSize());
        Assert.assertEquals(count, userPageInfo.getTotal());
        log.debug("【userPageInfo】= {}", userPageInfo);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        testInsertList();
    }

}
```

## 参考

- 通用Mapper官方文档：https://github.com/abel533/Mapper/wiki/1.integration
- pagehelper 官方文档：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md