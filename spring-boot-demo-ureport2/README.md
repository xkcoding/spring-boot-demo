# spring-boot-demo-ureport2

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)、[ureport2](https://github.com/youseries/ureport) （ureport2 可以轻松实现复杂的中国式报表，功能十分强大）

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-ureport2</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<name>spring-boot-demo-ureport2</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<properties>
		<ureport2.version>2.2</ureport2.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>com.bstek.ureport</groupId>
			<artifactId>ureport2-console</artifactId>
			<version>${ureport2.version}</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-ureport2</finalName>
	</build>

</project>
```

### UReportConfiguration.java

```java
@Configuration
@ImportResource("classpath:ureport-context.xml")
public class UReportConfiguration {

	@Bean
	public ServletRegistrationBean initUReport() {
		return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
	}
}
```

### ureport-context.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

	<import resource="classpath:ureport-console-context.xml"/>

	<bean id="propertyConfigurer" parent="ureport.props">
		<property name="location">
			<value>classpath:ureport-config.properties</value>
		</property>
	</bean>
</beans>
```

### ureport-config.properties

```properties
# 配置模板存放的位置
ureport.fileStoreDir=/Users/yangkai.shen/Documents/code/back-end/spring-boot-demo/spring-boot-demo-ureport2/src/main/resources/ureportfiles
```

### resources目录

```
resources
├── application.yml···························spring boot 配置文件
├── export····································运行 demo 示例后，导出的文件
│   ├── ureport.docx··························导出的 word 文件
│   ├── ureport.pdf···························导出的 PDF 文件
│   └── ureport.xlsx··························导出的 Excel 文件
├── ureport-config.properties·················ureport2 自定义的一些配置信息，比如模板存放路径
├── ureport-context.xml·······················ureport2 的配置文件
└── ureportfiles······························用于存放 ureport2 的模板文件
    └── ureport-demo.ureport.xml··············demo 示例的模板文件
```

