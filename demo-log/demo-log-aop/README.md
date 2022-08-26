## spring-boot-demo-log-aop

> 此 demo 主要是演示如何使用 aop 切面对请求进行日志记录，并且记录 UserAgent 信息。

### 1.开发步骤

#### 1.1.添加依赖

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

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
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

  <!-- 解析 UserAgent 信息 -->
  <dependency>
    <groupId>eu.bitwalker</groupId>
    <artifactId>UserAgentUtils</artifactId>
    <version>1.21</version>
  </dependency>
</dependencies>
```

#### 1.2.添加切面拦截

```java
@Slf4j
@Aspect
@Component
public class AopLog {
  /**
   * 切入点
   */
  @Pointcut("execution(public * com.xkcoding.log.aop.controller.*Controller.*(..))")
  public void log() {

  }

  /**
   * 环绕操作
   *
   * @param point 切入点
   * @return 原方法返回值
   * @throws Throwable 异常信息
   */
  @Around("log()")
  public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
    // 开始打印请求日志
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

    // 打印请求相关参数
    long startTime = System.currentTimeMillis();
    Object result = point.proceed();
    String header = request.getHeader("User-Agent");
    UserAgent userAgent = UserAgent.parseUserAgentString(header);

    LogData logData = LogData.builder()
      .threadId(Long.toString(Thread.currentThread().getId()))
      .threadName(Thread.currentThread().getName())
      .ip(getIp(request))
      .url(request.getRequestURL().toString())
      .classMethod(String.format("%s.%s", point.getSignature().getDeclaringTypeName(),
        point.getSignature().getName()))
      .httpMethod(request.getMethod())
      .requestParams(getNameAndValue(point))
      .result(result)
      .timeCost(System.currentTimeMillis() - startTime)
      .userAgent(header)
      .browser(userAgent.getBrowser().toString())
      .os(userAgent.getOperatingSystem().toString()).build();

    log.info("Request Log Info : {}", JSONUtil.toJsonStr(logData));

    return result;
  }

  /**
   * 获取方法参数名和参数值
   */
  private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
    final Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    final String[] names = methodSignature.getParameterNames();
    final Object[] args = joinPoint.getArgs();

    if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
      return Collections.emptyMap();
    }
    if (names.length != args.length) {
      log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
      return Collections.emptyMap();
    }
    Map<String, Object> map = Maps.newHashMap();
    for (int i = 0; i < names.length; i++) {
      map.put(names[i], args[i]);
    }
    return map;
  }

  private static final String UNKNOWN = "unknown";

  /**
   * 获取ip地址
   */
  public static String getIp(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    String comma = ",";
    String localhost = "127.0.0.1";
    if (ip.contains(comma)) {
      ip = ip.split(",")[0];
    }
    if (localhost.equals(ip)) {
      // 获取本机真正的ip地址
      try {
        ip = InetAddress.getLocalHost().getHostAddress();
      } catch (UnknownHostException e) {
        log.error(e.getMessage(), e);
      }
    }
    return ip;
  }

}
```

### 2.测试

#### 2.1.添加测试接口
```java
@Slf4j
@RestController
public class TestController {

  /**
   * 测试方法
   *
   * @param who 测试参数
   * @return {@link Dict}
   */
  @GetMapping("/test")
  public Dict test(String who) {
    return Dict.create().set("who", StrUtil.isBlank(who) ? "me" : who);
  }

  /**
   * 测试post json方法
   *
   * @param map 请求的json参数
   * @return {@link Dict}
   */
  @PostMapping("/testJson")
  public Dict testJson(@RequestBody Map<String, Object> map) {

    final String jsonStr = JSONUtil.toJsonStr(map);
    log.info(jsonStr);
    return Dict.create().set("json", map);
  }
}
```

启动 `LogAopApplication`，使用 postman 分别测试
- GET请求

method: GET

url: `http://127.0.0.1:8080/test?who=xkcoding`

- POST请求

method: POST

url: `http://127.0.0.1:8080/testJson`

header: `ContentType:application/json`

data: `{"id":"xxxx","content":"test"}`
