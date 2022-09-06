# spring-boot-demo-dubbo-common

> 此 module 主要是用于公共部分，主要存放工具类，实体，以及服务提供方/调用方的接口定义

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>spring-boot-demo-dubbo</artifactId>
        <groupId>com.xkcoding</groupId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-dubbo-common</artifactId>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <build>
        <finalName>spring-boot-demo-dubbo-common</finalName>
    </build>

</project>
```

## HelloService.java

```java
/**
 * <p>
 * Hello服务接口
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-25 16:56
 */
public interface HelloService {
    /**
     * 问好
     *
     * @param name 姓名
     * @return 问好
     */
    String sayHello(String name);
}
```

