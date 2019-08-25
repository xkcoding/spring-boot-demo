# spring-boot-demo-ldap

> 此 demo 主要演示了 Spring Boot 如何集成 `spring-boot-starter-data-ldap` 完成对 Ldap 的基本CURD操作, 并给出以登录为实战的api 示例

## docker openldap 安装步骤

> 参考： https://github.com/osixia/docker-openldap
1. 下载镜像： `docker pull osixia/openldap:1.2.5`

2. 运行容器： `docker run -p 389:389 -p 636:636 --name my-openldap --detach osixia/openldap:1.2.5`

3. 添加管理员： `docker exec my-openldap ldapsearch -x -H ldap://localhost -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w admin`

4. 停止容器：`docker stop my-openldap`

5. 启动容器：`docker start my-openldap`


## pom.xml

```
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

## Person.java

> 实体类
> @Entry 注解 映射ldap对象关系 
```
package com.xkcoding.ldap.entity;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.io.Serializable;

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
```
package com.xkcoding.ldap.repository;

import com.xkcoding.ldap.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.naming.Name;

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

    /**
     * 查询全部
     * @return
     */
    @Override
    Iterable<Person> findAll();

    /**
     * 保存
     * @param s
     * @param <S>
     * @return
     */
    @Override
    <S extends Person> S save(S s);

    /**
     * 删除
     * @param person
     */
    @Override
    void delete(Person person);
}

```

## PersonService.java
> 数据操作服务
```
package com.xkcoding.ldap.service;

import com.xkcoding.ldap.entity.Person;
import com.xkcoding.ldap.entity.Result;
import com.xkcoding.ldap.request.LoginRequest;

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
     * @param request com.xkcoding.ldap.request.LoginRequest
     * @return com.xkcoding.ldap.entity.Result
     */
    Result login(LoginRequest request);

    /**
     * 查询全部
     * @return com.xkcoding.ldap.entity.Result
     */
    Result listAllPerson();

    /**
     * 保存
     * @param person com.xkcoding.ldap.entity.Person
     */
    void save(Person person);

    /**
     * 删除
     * @param person com.xkcoding.ldap.entity.Person
     */
    void delete(Person person);
}

```

## PersonServiceImpl.java 
> person数据操作服务具体逻辑实现类

```
package com.xkcoding.ldap.service.impl;

import com.xkcoding.ldap.entity.Person;
import com.xkcoding.ldap.entity.Result;
import com.xkcoding.ldap.exception.ServiceException;
import com.xkcoding.ldap.repository.PersonRepository;
import com.xkcoding.ldap.request.LoginRequest;
import com.xkcoding.ldap.service.PersonService;
import com.xkcoding.ldap.util.LdapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * PersonServiceImpl
 *
 * @author fxbin
 * @version v1.0
 * @since 2019/8/26 1:05
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonRepository personRepository;

    @Override
    public Result login(LoginRequest request) {

        log.info("IN LDAP auth");

        Person user = personRepository.findByUid(request.getUsername());

        try {
            if(ObjectUtils.isEmpty(user)) {
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

    @Override
    public Result listAllPerson() {
        Iterable<Person> personList = personRepository.findAll();
        personList.forEach(person -> {
            person.setUserPassword(LdapUtils.asciiToString(person.getUserPassword()));
        });
        return Result.success(personList);
    }

    @Override
    public void save(Person person) {
        Person p = personRepository.save(person);
        log.info("用户{}保存成功", p.getUid());
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
        log.info("删除用户{}成功", person.getUid());
    }
}

```

## LdapDemoApplicationTests.java
> 测试
```
package com.xkcoding.ldap;

import com.xkcoding.ldap.entity.Person;
import com.xkcoding.ldap.entity.Result;
import com.xkcoding.ldap.request.LoginRequest;
import com.xkcoding.ldap.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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

    @Test
    public void loginTest() {
        LoginRequest loginRequest = LoginRequest.builder().username("wangwu").password("123456").build();
        Result login = personService.login(loginRequest);
        System.out.println(login);
    }

    @Test
    public void listAllPersonTest() {
        Result result = personService.listAllPerson();
        System.out.println(result);
    }

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


    @Test
    public void deleteTest() {
        Person person = new Person();
        person.setUid("zhaosi");

        personService.delete(person);
    }
}
```

## 参考
spring-data-ldap 官方文档: https://docs.spring.io/spring-data/ldap/docs/2.1.10.RELEASE/reference/html/
