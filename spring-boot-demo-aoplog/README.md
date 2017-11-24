# spring-boot-demo-aoplog

依赖[spring-boot-demo-parent](../spring-boot-demo-parent)、`spring-boot-starter-aop`

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-aoplog</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<name>spring-boot-demo-aoplog</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<properties>
		<useragent.version>1.20</useragent.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
		<!--UserAgent工具类-->
		<dependency>
			<groupId>eu.bitwalker</groupId>
			<artifactId>UserAgentUtils</artifactId>
			<version>${useragent.version}</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-aoplog</finalName>
	</build>

</project>
```

### AopLog.java

```java
/**
 * aop 切面记录请求日志
 *
 * @package: com.xkcoding.springbootdemoaoplog.aspectj
 * @description：aop 切面记录请求日志
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 上午9:43
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Aspect
@Component
@Slf4j
public class AopLog {
	private static final String START_TIME = "start-request";

	@Pointcut("execution(public * com.xkcoding.springbootdemoaoplog.controller.*Controller.*(..))")
	public void log() {
	}

	@Before("log()")
	public void beforeLog(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		log.info("【请求 URL】：{}", request.getRequestURL());
		log.info("【请求 IP】：{}", request.getRemoteAddr());
		log.info("【请求类名】：{}，【请求方法名】：{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
		Map parameterMap = request.getParameterMap();
		log.info("【请求参数】：{}，", JsonMapper.obj2Str(parameterMap));
		Long start = System.currentTimeMillis();
		request.setAttribute(START_TIME, start);
	}

	@Around("log()")
	public Object arroundLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = proceedingJoinPoint.proceed();
		log.info("【返回值】：{}", JsonMapper.obj2Str(result));
		return result;
	}

	@AfterReturning("log()")
	public void afterReturning(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Long start = (Long) request.getAttribute(START_TIME);
		Long end = System.currentTimeMillis();
		log.info("【请求耗时】：{}毫秒", end - start);
		String header = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		log.info("【浏览器类型】：{}，【操作系统】：{}，【原始User-Agent】：{}", userAgent.getBrowser().toString(), userAgent.getOperatingSystem().toString(), header);
	}
}
```

### JsonMapper.java

```java
/**
 * Json 转化工具类
 *
 * @package: com.xkcoding.springbootdemoaoplog.util
 * @description：Json 转化工具类
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 上午9:36
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Slf4j
public class JsonMapper {
	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 对象转 json 字符串
	 *
	 * @param src 元对象
	 * @param <T> 类型
	 * @return json 字符串
	 */
	public static <T> String obj2Str(T src) {
		if (src == null) {
			return null;
		}
		try {
			return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
		} catch (IOException e) {
			log.error("【JSON 转换：对象 --> 字符串】，异常堆栈：{}", e);
			return null;
		}
	}

	/**
	 * json 字符串转化为对象
	 *
	 * @param src           源 json 字符串
	 * @param typeReference 转化后的类型
	 * @param <T>           类型
	 * @return 返回转化后的对象
	 */
	public static <T> T str2Obj(String src, TypeReference<T> typeReference) {
		if (src == null || typeReference == null) {
			return null;
		}
		try {
			return (T) (typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, typeReference));
		} catch (Exception e) {
			log.error("【JSON 转换：字符串 --> 对象】，异常堆栈：{}", e);
			return null;
		}
	}
}
```

