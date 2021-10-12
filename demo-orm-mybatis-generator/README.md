# spring-boot-demo-orm-mybatis-generator

> 此 demo 演示了 Spring Boot 如何与原生的 mybatis 整合，通过mybatis-generator插件，将DB表映射为Java实体类，并自动生成对应Mapper文件。使用生成的Example类和
> Criteria 通过Java代码组合查询条件或更新条件，免去编写SQl过程。

1. IDEA下点击右👉边栏 Maven
2. demo-orm-mybatis-generator
3. Plugins
4. mybatis-generator
5. mybatis-generator:generate
6. 在entity、mapper包下面就会生成对应的代码

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <artifactId>demo-orm-mybatis-generator</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>demo-orm-mybatis-generator</name>
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
    <mybatis.version>2.2.0</mybatis.version>
    <mybatis.generator.version>1.4.0</mybatis.generator.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>${mybatis.version}</version>
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
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <finalName>demo-orm-mybatis-generator</finalName>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>${mybatis.generator.version}</version>
        <configuration>
          <configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
          <overwrite>true</overwrite>
          <verbose>true</verbose>
        </configuration>
      </plugin>
    </plugins>
  </build>

</project>
```

## generatorConfig.xml

**需要修改对应的数据库驱动路径、连接信息和表信息**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
  <!-- 数据库驱动:选择你的本地硬盘上面的数据库驱动包-->
  <classPathEntry
          location="/Users/qlh/.m2/repository/mysql/mysql-connector-java/5.1.47/mysql-connector-java-5.1.47.jar"/>
  <context id="DB2Tables" targetRuntime="MyBatis3">
    <!--生成的实体类添加toString-->
    <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>
    <!--生成的实体类添加hashCode-->
    <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin"/>

    <commentGenerator>
      <property name="suppressDate" value="true"/>
      <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
      <property name="suppressAllComments" value="true"/>
    </commentGenerator>

    <!--数据库链接URL，用户名、密码 -->
    <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                    connectionURL="jdbc:mysql://127.0.0.1:3306/spring-boot-demo?useUnicode=true&amp;characterEncoding=UTF-8&amp;useSSL=false&amp;autoReconnect=true&amp;failOverReadOnly=false&amp;serverTimezone=GMT%2B8"
                    userId="root"
                    password="root">
    </jdbcConnection>

    <javaTypeResolver>
      <property name="forceBigDecimals" value="false"/>
    </javaTypeResolver>

    <!-- 生成模型的包名和位置-->
    <javaModelGenerator targetPackage="com.xkcoding.orm.mybatis.generator.entity" targetProject="src/main/java">
      <property name="enableSubPackages" value="true"/>
      <property name="trimStrings" value="true"/>
    </javaModelGenerator>

    <!-- 生成映射文件的包名和位置-->
    <sqlMapGenerator targetPackage="mappers" targetProject="src/main/resources">
      <property name="enableSubPackages" value="true"/>
    </sqlMapGenerator>

    <!-- 生成DAO的包名和位置-->
    <javaClientGenerator type="XMLMAPPER" targetPackage="com.xkcoding.orm.mybatis.generator.mapper"
                         targetProject="src/main/java">
      <property name="enableSubPackages" value="true"/>
    </javaClientGenerator>

    <!-- 要生成的表 tableName是数据库中的表名或视图名 domainObjectName是实体类名-->
    <table tableName="orm_user"
           domainObjectName="User"
           enableCountByExample="true"
           enableUpdateByExample="true"
           enableDeleteByExample="true"
           enableSelectByExample="true"
           selectByExampleQueryId="true">
    </table>
  </context>
</generatorConfiguration>
```

## SpringBootDemoOrmMybatisGeneratorApplication.java

```java
/**
 * <p>
 * 启动类
 * </p>
 *
 * @author qilihui
 * @date 2021/10/11 9:50 下午
 */
@MapperScan(basePackages = {"com.xkcoding.orm.mybatis.generator.mapper"})
@SpringBootApplication
public class SpringBootDemoOrmMybatisGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoOrmMybatisGeneratorApplication.class, args);
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
    com.xkcoding.orm.mybatis.mapper: trace
mybatis:
  configuration:
    # 下划线转驼峰
    map-underscore-to-camel-case: true
  mapper-locations: classpath:mappers/*.xml
  type-aliases-package: com.xkcoding.orm.mybatis.entity
```

## UserRepo.java

```java
/**
 * @author qilihui
 * @date 2021/10/11 9:50 下午
 */
@Repository
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserRepo {
    private final UserMapper userMapper;

    public User selectByName(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> userList = userMapper.selectByExample(example);
        if (CollectionUtil.isNotEmpty(userList)) {
            return userList.get(0);
        }
        return null;
    }

    public int insert(User user) {
        return userMapper.insertSelective(user);
    }

    public int updateByNameSelective(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(user.getName());
        return userMapper.updateByExampleSelective(user, example);
    }
}
```

## UserRepoTest.java

```java
/**
 * @author qilihui
 * @date 2021/10/12 11:09 上午
 */
@Slf4j
public class UserRepoTest extends SpringBootDemoOrmMybatisGeneratorApplicationTests {
    @Autowired
    private UserRepo userRepo;

    /**
     * 根据name查询测试
     */
    @Test
    public void selectByName() {
        User user = userRepo.selectByName("user_1");
        Assert.assertTrue(Objects.nonNull(user));
        log.debug("【user】= {}", user);
    }

    /**
     * insert 测试
     */
    @Test
    public void insert() {
        User user = new User();
        user.setName("user_3");
        user.setPassword("6c6bf02c8d5d3d128f34b1700cb1e32c");
        user.setSalt("fcbdd0e8a9404a5585ea4e01d0e4d7a0");
        user.setEmail("user3@xkcoding.com");
        user.setPhoneNumber("17300000003");
        user.setStatus(1);
        userRepo.insert(user);

        User select = userRepo.selectByName("user_3");
        Assert.assertTrue(Objects.nonNull(select));
        log.debug("【user】= {}", select);
    }

    /**
     * update 测试
     */
    @Test
    public void updateByNameSelective() {
        User user = new User();
        user.setName("user_2");
        user.setStatus(2);
        int update = userRepo.updateByNameSelective(user);
        Assert.assertEquals(update, 1);

        User select = userRepo.selectByName("user_2");
        Assert.assertTrue(Objects.nonNull(select));
        log.debug("【user】= {}", select);
    }
}
```

## 参考

- Mybatis官方文档：http://www.mybatis.org/mybatis-3/zh/index.html

- Mybatis官方脚手架文档：http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

- Mybatis整合Spring Boot官方demo：https://github.com/mybatis/spring-boot-starter/tree/master/mybatis-spring-boot-samples
