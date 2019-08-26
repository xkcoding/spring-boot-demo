# spring-boot-demo-ldap

> 此 demo 主要演示了 Spring Boot 如何集成 `spring-boot-starter-data-ldap` 完成对 Ldap 的基本 CURD操作, 并给出以登录为实战的 API 示例

## docker openldap 安装步骤

> 参考： https://github.com/osixia/docker-openldap
1. 下载镜像： `docker pull osixia/openldap:1.2.5`

2. 运行容器： `docker run -p 389:389 -p 636:636 --name my-openldap --detach osixia/openldap:1.2.5`

3. 添加管理员： `docker exec my-openldap ldapsearch -x -H ldap://localhost -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w admin`

4. 停止容器：`docker stop my-openldap`

5. 启动容器：`docker start my-openldap`


## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <artifactId>spring-boot-demo-ldap</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>spring-boot-demo-ldap</name>
  <description>Demo project for Spring Boot</description>

  <parent>
    <artifactId>spring-boot-demo</artifactId>
    <groupId>com.xkcoding</groupId>
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
      <artifactId>spring-boot-starter-data-ldap</artifactId>
    </dependency>

    <!-- test -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>

    <!-- lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
      <scope>provided</scope>
    </dependency>
  </dependencies>

</project>
```

## application.yml

```yaml
spring:
  ldap:
    urls: ldap://localhost:389
    base: dc=example,dc=org
    username: cn=admin,dc=example,dc=org
    password: admin
```

## Person.java

> 实体类
> @Entry 注解 映射ldap对象关系 
```java
/**
 * People
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 0:51
 */
@Data
@Entry(
    base = "ou=people",
    objectClasses = {"posixAccount", "inetOrgPerson", "top"}
)
public class Person implements Serializable {

    private static final long serialVersionUID = -7946768337975852352L;

    @Id
    private Name id;

    private String uidNumber;

    private String gidNumber;

    /**
     * 用户名
     */
    @DnAttribute(value = "uid", index = 1)
    private String uid;

    /**
     * 姓名
     */
    @Attribute(name = "cn")
    private String personName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 名字
     */
    private String givenName;

    /**
     * 姓氏
     */
    @Attribute(name = "sn")
    private String surname;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 职位
     */
    private String title;

    /**
     * 根目录
     */
    private String homeDirectory;

    /**
     * loginShell
     */
    private String loginShell;
}
```

## PersonRepository.java
> person 数据持久层
```java
/**
 * PersonRepository
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:02
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Name> {

    /**
     * 根据用户名查找
     *
     * @param uid 用户名
     * @return com.xkcoding.ldap.entity.Person
     */
    Person findByUid(String uid);
}
```

## PersonService.java
> 数据操作服务
```java
/**
 * PersonService
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:05
 */
public interface PersonService {

    /**
     * 登录
     *
     * @param request {@link LoginRequest}
     * @return {@link Result}
     */
    Result login(LoginRequest request);

    /**
     * 查询全部
     *
     * @return {@link Result}
     */
    Result listAllPerson();

    /**
     * 保存
     *
     * @param person {@link Person}
     */
    void save(Person person);

    /**
     * 删除
     *
     * @param person {@link Person}
     */
    void delete(Person person);

}
```

## PersonServiceImpl.java 
> person数据操作服务具体逻辑实现类

```java
/**
 * PersonServiceImpl
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:05
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    /**
     * 登录
     *
     * @param request {@link LoginRequest}
     * @return {@link Result}
     */
    @Override
    public Result login(LoginRequest request) {
        log.info("IN LDAP auth");

        Person user = personRepository.findByUid(request.getUsername());

        try {
            if (ObjectUtils.isEmpty(user)) {
                throw new ServiceException("用户名或密码错误，请重新尝试");
            } else {
                user.setUserPassword(LdapUtils.asciiToString(user.getUserPassword()));
                if (!LdapUtils.verify(user.getUserPassword(), request.getPassword())) {
                    throw new ServiceException("用户名或密码错误，请重新尝试");
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        log.info("user info:{}", user);
        return Result.success(user);
    }

    /**
     * 查询全部
     *
     * @return {@link Result}
     */
    @Override
    public Result listAllPerson() {
        Iterable<Person> personList = personRepository.findAll();
        personList.forEach(person -> person.setUserPassword(LdapUtils.asciiToString(person.getUserPassword())));
        return Result.success(personList);
    }

    /**
     * 保存
     *
     * @param person {@link Person}
     */
    @Override
    public void save(Person person) {
        Person p = personRepository.save(person);
        log.info("用户{}保存成功", p.getUid());
    }

    /**
     * 删除
     *
     * @param person {@link Person}
     */
    @Override
    public void delete(Person person) {
        personRepository.delete(person);
        log.info("删除用户{}成功", person.getUid());
    }
    
}
```

## LdapDemoApplicationTests.java
> 测试
```java
/**
 * LdapDemoApplicationTest
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LdapDemoApplicationTests {

    @Resource
    private PersonService personService;

    @Test
    public void contextLoads() {
    }

    /**
     * 测试查询单个
     */
    @Test
    public void loginTest() {
        LoginRequest loginRequest = LoginRequest.builder().username("wangwu").password("123456").build();
        Result login = personService.login(loginRequest);
        System.out.println(login);
    }

    /**
     * 测试查询列表
     */
    @Test
    public void listAllPersonTest() {
        Result result = personService.listAllPerson();
        System.out.println(result);
    }

    /**
     * 测试保存
     */
    @Test
    public void saveTest() {
        Person person = new Person();

        person.setUid("zhaosi");

        person.setSurname("赵");
        person.setGivenName("四");
        person.setUserPassword("123456");

        // required field
        person.setPersonName("赵四");
        person.setUidNumber("666");
        person.setGidNumber("666");
        person.setHomeDirectory("/home/zhaosi");
        person.setLoginShell("/bin/bash");

        personService.save(person);
    }

    /**
     * 测试删除
     */
    @Test
    public void deleteTest() {
        Person person = new Person();
        person.setUid("zhaosi");

        personService.delete(person);
    }

}
```

## 其余代码参见本 demo

## 参考

spring-data-ldap 官方文档: https://docs.spring.io/spring-data/ldap/docs/2.1.10.RELEASE/reference/html/
