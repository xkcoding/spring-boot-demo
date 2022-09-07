# spring-boot-demo-exception-handler

> 此 demo 演示了如何在Spring Boot中进行统一的异常处理，包括了两种方式的处理：第一种对常见API形式的接口进行异常处理，统一封装返回格式；第二种是对模板页面请求的异常处理，统一处理错误页面。

## 1.开发步骤

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>common-tools</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <!--  模板引擎，用于错误页面的展示  -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
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
```

### 1.2.构造JSON异常和页面跳转异常

```java
// JSON 异常
@Getter
public class JsonException extends CommonBizException {

    public JsonException(IStatus status) {
        super(status);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}

// 页面异常
@Getter
public class PageException extends CommonBizException {

  public PageException(IStatus status) {
    super(status);
  }

  public PageException(Integer code, String message) {
    super(code, message);
  }
}
```

### 1.3.异常拦截

```java
@Slf4j
@ControllerAdvice
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
  public Response<Void> jsonErrorHandler(JsonException exception) {
    log.error("【JsonException】:{}", exception.getMessage());
    return Response.ofError(exception);
  }

  /**
   * 统一 页面 异常处理
   *
   * @param exception PageException
   * @return 统一跳转到异常页面
   */
  @ExceptionHandler(value = PageException.class)
  public ModelAndView pageErrorHandler(PageException exception) {
    log.error("【PageException】:{}", exception.getMessage());
    ModelAndView view = new ModelAndView();
    view.addObject("message", exception.getMessage());
    view.setViewName(DEFAULT_ERROR_VIEW);
    return view;
  }
}
```

### 1.4.编写统一错误页面

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
<div th:text="${message}"></div>
</body>
</html>
```

## 2.测试

### 2.1.编写测试路由代码模拟异常

```java
@Controller
public class TestController {

    @GetMapping("/json")
    @ResponseBody
    public Response<Void> jsonException() {
        throw new JsonException(CommonStatus.SERVER_ERROR);
    }

    @GetMapping("/page")
    public ModelAndView pageException() {
        throw new PageException(CommonStatus.SERVER_ERROR);
    }
}
```

启动 `ExceptionHandlerApplication`
- 测试API形式的异常拦截返回，浏览器进入 `http://localhost:8080/demo/json` ，同时观察控制台日志输出
- 测试统一错误页面的异常拦截返回，浏览器进入 `http://localhost:8080/demo/page` ，同时观察控制台日志输出
