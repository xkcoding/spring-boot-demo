# spring-boot-demo-upload

> 本 demo 演示了 Spring Boot 如何实现本地文件上传以及如何上传文件至七牛云平台。前端使用 vue 和 iview 实现上传页面。

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
    <groupId>com.qiniu</groupId>
    <artifactId>qiniu-java-sdk</artifactId>
    <version>${qiniu.version}</version>
  </dependency>

  <!--  七牛云 sdk 必须需要 okhttp 客户端  -->
  <dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>3.14.4</version>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### 1.2.七牛云配置类

```java
@Data
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String prefix;
}
```

### 1.3.自动装配类

```java
@Configuration
@ConditionalOnClass({Servlet.class, StandardServletMultipartResolver.class, MultipartConfigElement.class})
@ConditionalOnProperty(prefix = "spring.http.multipart", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties({MultipartProperties.class, QiniuProperties.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UploadAutoConfiguration {
    private final MultipartProperties multipartProperties;
    private final QiniuProperties qiniuProperties;

    /**
     * 上传配置
     */
    @Bean
    @ConditionalOnMissingBean
    public MultipartConfigElement multipartConfigElement() {
        return this.multipartProperties.createMultipartConfig();
    }

    /**
     * 注册解析器
     */
    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean(MultipartResolver.class)
    public StandardServletMultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        multipartResolver.setResolveLazily(this.multipartProperties.isResolveLazily());
        return multipartResolver;
    }

    /**
     * 华东机房
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Region.region0());
    }

    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

    /**
     * 认证信息实例
     */
    @Bean
    public Auth auth() {
        return Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
    }

    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }
}
```

### 1.4.七牛云上传代码

```java
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class QiNiuService implements InitializingBean {
    private final Auth auth;
    private final UploadManager uploadManager;
    private final QiniuProperties qiniuProperties;

    private StringMap putPolicy;


    /**
     * 七牛云上传文件
     *
     * @param file 文件
     * @return 七牛上传Response
     * @throws QiniuException 七牛异常
     */
    public Response uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file, file.getName(), getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, file.getName(), getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public void afterPropertiesSet() {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody",
            "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

    /**
     * 获取上传凭证
     *
     * @return 上传凭证
     */
    private String getUploadToken() {
        return this.auth.uploadToken(qiniuProperties.getBucket(), null, 3600, putPolicy);
    }
}
```

### 1.5.测试Controller及前端页面

> 不是专业前端，就不在 README 献丑了，代码直接参考 `resources/templates/index.html` 即可

## 2.测试

> 注意：测试七牛云时，需要先在 `application.yml` 中配置 `qiniu.xxx` 相关参数

启动 `UploadApplication` ，打开浏览器，输入 `http://localhost:8080/demo`

上传几张图片试试~

![测试上传](https://static.xkcoding.com/spring-boot-demo/demo-base/demo-base-upload/2022-09-21-083229.png)

## 3.参考

- [Spring Boot 官方文档之文件上传](https://docs.spring.io/spring-boot/docs/3.0.0-M5/reference/htmlsingle/#howto.spring-mvc.multipart-file-uploads)

- [七牛云官方文档之 Java SDK](https://developer.qiniu.com/kodo/1239/java)

## 4.特别感谢

- 感谢 [七牛云](https://portal.qiniu.com/signup?utm_source=kaiyuan&utm_media=xkcoding) 提供的免费云存储与 CDN 加速支持
