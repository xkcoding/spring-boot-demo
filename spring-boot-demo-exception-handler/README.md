# spring-boot-demo-exception-handler

> 此 demo 演示了如何在Spring Boot中进行统一的异常处理，包括了两种方式的处理：第一种对常见API形式的接口进行异常处理，统一封装返回格式；第二种是对模板页面请求的异常处理，统一处理错误页面；第三种是针对同一接口对接浏览器和移动端的情况，可以自适应输出模板或者json格式。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-exception-handler</artifactId>
	<version>1.0.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-exception-handler</name>
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
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
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
	</dependencies>

	<build>
		<finalName>spring-boot-demo-exception-handler</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

## ApiResponse.java

> 统一的API格式返回封装，里面涉及到的 `BaseException` 和`Status` 这两个类，具体代码见 demo。

```java
/**
 * <p>
 * 通用的 API 接口封装
 * </p>
 *
 * @package: com.xkcoding.exception.handler.model
 * @description: 通用的 API 接口封装
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 8:57 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class ApiResponse {
	/**
	 * 状态码
	 */
	private Integer code;

	/**
	 * 返回内容
	 */
	private String message;

	/**
	 * 返回数据
	 */
	private Object data;

	/**
	 * 无参构造函数
	 */
	private ApiResponse() {

	}

	/**
	 * 全参构造函数
	 *
	 * @param code    状态码
	 * @param message 返回内容
	 * @param data    返回数据
	 */
	private ApiResponse(Integer code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 构造一个自定义的API返回
	 *
	 * @param code    状态码
	 * @param message 返回内容
	 * @param data    返回数据
	 * @return ApiResponse
	 */
	public static ApiResponse of(Integer code, String message, Object data) {
		return new ApiResponse(code, message, data);
	}

	/**
	 * 构造一个成功且带数据的API返回
	 *
	 * @param data 返回数据
	 * @return ApiResponse
	 */
	public static ApiResponse ofSuccess(Object data) {
		return ofStatus(Status.OK, data);
	}

	/**
	 * 构造一个成功且自定义消息的API返回
	 *
	 * @param message 返回内容
	 * @return ApiResponse
	 */
	public static ApiResponse ofMessage(String message) {
		return of(Status.OK.getCode(), message, null);
	}

	/**
	 * 构造一个有状态的API返回
	 *
	 * @param status 状态 {@link Status}
	 * @return ApiResponse
	 */
	public static ApiResponse ofStatus(Status status) {
		return ofStatus(status, null);
	}

	/**
	 * 构造一个有状态且带数据的API返回
	 *
	 * @param status 状态 {@link Status}
	 * @param data   返回数据
	 * @return ApiResponse
	 */
	public static ApiResponse ofStatus(Status status, Object data) {
		return of(status.getCode(), status.getMessage(), data);
	}

	/**
	 * 构造一个异常且带数据的API返回
	 *
	 * @param t    异常
	 * @param data 返回数据
	 * @param <T>  {@link BaseException} 的子类
	 * @return ApiResponse
	 */
	public static <T extends BaseException> ApiResponse ofException(T t, Object data) {
		return of(t.getCode(), t.getMessage(), data);
	}

	/**
	 * 构造一个异常且带数据的API返回
	 *
	 * @param t   异常
	 * @param <T> {@link BaseException} 的子类
	 * @return ApiResponse
	 */
	public static <T extends BaseException> ApiResponse ofException(T t) {
		return ofException(t, null);
	}

   /**
     * 重写toString
     *
     * @return jackson序列化
     */
    @SneakyThrows
    @Override
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
```

## DemoExceptionHandler.java

```java
/**
 * <p>
 * 统一异常处理
 * </p>
 *
 * @package: com.xkcoding.exception.handler.handler
 * @description: 统一异常处理
 * @author: yangkai.shen
 * @date: Created in 2018/10/2 9:26 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class DemoExceptionHandler {
	private static final String DEFAULT_ERROR_VIEW = "error";

	/**
	 * 统一 json 异常处理
	 *
	 * @param exception JsonException
	 * @return 统一返回 json 格式
	 */
	@ExceptionHandler(value = JsonException.class)
	@ResponseBody
	public ApiResponse jsonErrorHandler(JsonException exception) {
		log.error("【JsonException】:{}", exception.getMessage());
		return ApiResponse.ofException(exception);
	}

	/**
	 * 统一 页面 异常处理
	 *
	 * @param exception PageException
	 * @return 统一跳转到异常页面
	 */
	@ExceptionHandler(value = PageException.class)
	public ModelAndView pageErrorHandler(PageException exception) {
		log.error("【DemoPageException】:{}", exception.getMessage());
		ModelAndView view = new ModelAndView();
		view.addObject("message", exception.getMessage());
		view.setViewName(DEFAULT_ERROR_VIEW);
		return view;
	}

    /**
     * 定义状态码和模板路径对应关系
     */
    private final Map<Integer, String> errorPageMap = new HashMap<>();

    @PostConstruct
    public void init() {
        errorPageMap.put(404, "/4xx");
        errorPageMap.put(500, "/5xx");
    }

    /**
     * 自适应返回 异常处理
     *
     * @param exception BizException
     * @param request   request
     * @param response  response
     * @throws IOException
     * @throws ServletException
     */
    @ExceptionHandler(value = {BizException.class})
    public void autoHandler(BizException exception, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.error("【BizException】: {}", exception.getMessage());
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));

        boolean showJson = (userAgent.isMobile() && exception.isJson()) || (!userAgent.isMobile() && !exception.isView());

        if (showJson) {
            write(exception, response);
        } else {
            write(exception, request, response);
        }

    }

    /**
     * 输出json到客户端
     *
     * @param exception {@link BaseException}的子类
     * @param response  response
     * @throws IOException
     */
    private void write(BaseException exception, HttpServletResponse response) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(ApiResponse.ofException(exception, exception.getData()).toString());
        writer.flush();
        writer.close();
    }

    /**
     * 输出视图到客户端
     *
     * @param exception {@link BaseException}的子类
     * @param response  response
     * @throws IOException
     * @throws ServletException
     */
    private void write(BaseException exception, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        exception.getAttributes().forEach(request::setAttribute);
        request.getRequestDispatcher(getErrorPage(exception.getCode())).forward(request, response);
    }

    /**
     * 根据抛出状态码定位到视图路径，若未匹配到，则使用统一异常页面
     *
     * @param code 状态码
     * @return 视图路径
     */
    private String getErrorPage(Integer code) {
        return errorPageMap.getOrDefault(code, "/error");
    }

}
```

## 4xx.html

> 位于 `src/main/resources/template` 目录下

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head lang="en">
	<meta charset="UTF-8"/>
	<title>4xx 统一页面异常处理</title>
</head>
<body>
<h1>404 统一页面异常处理</h1>
<div th:text="'默认值: ' + ${default}"></div>
<div th:text="'提示信息: ' + ${message}"></div>
</body>
</html>
```

## 5xx.html

> 位于 `src/main/resources/template` 目录下

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head lang="en">
	<meta charset="UTF-8"/>
	<title>5xx 统一页面异常处理</title>
</head>
<body>
<h1>500 统一页面异常处理</h1>
<div th:text="'提示信息: ' + ${message}"></div>
</body>
</html>
```

## error.html

> 位于 `src/main/resources/template` 目录下

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head lang="en">
	<meta charset="UTF-8"/>
	<title>统一页面异常处理</title>
</head>
<body>
<h1>统一页面异常处理</h1>
<div th:text="'提示信息: ' + ${message}"></div>
</body>
</html>
```

## 针对同一接口自适应输出的说明

### 1. 定义数据传输方式

#### IDataType.java

```java
/**
 * 定义顶级数据传输方式为json或视图
 *
 * @author FYT
 * @since 2020/4/7
 */
public interface IDataType {

    /**
     * 控制移动端返回格式是否为json
     *
     * @return true / false
     */
    default boolean isJson() {
        return true;
    }

    /**
     * 控制非移动端返回格式是否为视图
     *
     * @return true / false
     */
    default boolean isView() {
        return true;
    }

}
```

### 2. BaseException实现接口

由于`IDataType`接口有默认实现，所以BaseException在实现接口之后，各子类异常可根据各自业务重写，来达到灵活返回。

### 3. 自定义异常

#### BizException.java

```java
/**
 * 自定义业务异常
 *
 * @author FYT
 * @since 2020/4/7
 */
public class BizException extends BaseException {

    public BizException(Status status) {
        super(status);
    }

    public BizException(Status status, Dict attributes) {
        super(status, attributes);
    }

    public BizException(Integer code, String message) {
        super(code, message);
    }

    public BizException(Integer code, String message, Object data) {
        super(code, message, data);
    }

    /**
     * 移动端使用json
     *
     * @return json
     */
    @Override
    public boolean isJson() {
        return true;
    }

    /**
     * 浏览器使用 视图
     *
     * @return 视图
     */
    @Override
    public boolean isView() {
        return true;
    }
}
```

### 4. 错误页面路由

#### ErrorPageController.java

```java
/**
 * 错误页面路由
 *
 * @author FYT
 * @since 2020/4/7
 */
@Controller
public class ErrorPageController {

    @GetMapping(value = "/4xx")
    public String resourcesNotFound(Model model) {
        model.addAttribute("default", "4xx 页面默认值");
        return "4xx";
    }

    @GetMapping(value = "/5xx")
    public String internalError() {
        return "5xx";
    }

}
```

自适应返回视图采用的是`request.getRequestDispatcher().forward(request, response);`。个性化的数据可直接在业务controller方法中直接使用`request.setAttribute()`进行赋值。`BaseException`中也提供了`Dict`字段。当然你也可以在`ErrorPageController`中直接赋默认值。
