# spring-boot-demo-exceptionhandler

依赖[spring-boot-demo-parent](../spring-boot-demo-parent)、`spring-boot-starter-thymeleaf`（关于 thymeleaf 的会在后面的模板引擎专门有demo）

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-exceptionhandler</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<name>spring-boot-demo-exceptionhandler</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-exceptionhandler</finalName>
	</build>

</project>
```

### application.yml

```yaml
server:
  port: 8080
  context-path: /demo
spring:
  thymeleaf:
    mode: HTML5
    encoding: UTF-8
    content-type: text/html
    cache: false
```

### DemoExceptionHandler.java

```java
/**
 * 自定义异常统一处理
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler.handler
 * @description： 自定义异常统一处理
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午1:37
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@ControllerAdvice
@Slf4j
public class DemoExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";

	/**
	 * 统一 json 异常处理
	 *
	 * @param exception DemoJsonException
	 * @return 统一返回 json 格式
	 */
	@ExceptionHandler(value = DemoJsonException.class)
	@ResponseBody
	public R jsonErrorHandler(DemoJsonException exception) {
		log.error("【DemoJsonException】:{}", exception.getMessage());
		return R.error(exception);
	}

	/**
	 * 统一 页面 异常处理
	 *
	 * @param exception DemoPageException
	 * @return 统一跳转到异常页面
	 */
	@ExceptionHandler(value = DemoPageException.class)
	public ModelAndView pageErrorHandler(DemoPageException exception) {
		log.error("【DemoPageException】:{}", exception.getMessage());
		ModelAndView view = new ModelAndView();
		view.addObject("message", exception.getMessage());
		view.setViewName(DEFAULT_ERROR_VIEW);
		return view;
	}
}
```

### R.java

```java
/**
 * 统一返回的 json 对象
 *
 * @package: com.xkcoding.springbootdemoexceptionhandler
 * @description： 统一返回的 json 对象
 * @author: yangkai.shen
 * @date: Created in 2017/11/24 下午1:42
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
	private Integer code;
	private String message;
	private T data;

	public R(Status status) {
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public static R success(Integer code, String message, Object data) {
		return new R(code, message, data);
	}

	public static R success() {
		return new R(Status.OK);
	}

	public static R success(String message) {
		return success(message, null);
	}

	public static R success(String message, Object data) {
		return success(Code.SUCCESS.getCode(), message, data);
	}

	public static R error(Integer code, String message, Object data) {
		return new R(code, message, data);
	}

	public static R error(Integer code, String message) {
		return error(code, message, null);
	}

	public static R error(DemoJsonException exception) {
		return error(exception.getCode(), exception.getMessage());
	}
}
```

### error.html (在目录 `resources/templates` 下)

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head lang="en">
	<meta charset="UTF-8"/>
	<title>统一页面异常处理</title>
</head>
<body>
<h1>统一页面异常处理</h1>
<div th:text="${message}"></div>
</body>
</html>
```

### 其余代码

详情请参见本demo。