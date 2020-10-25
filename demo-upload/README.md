# spring-boot-demo-upload

> 本 demo 演示了 Spring Boot 如何实现本地文件上传以及如何上传文件至七牛云平台。前端使用 vue 和 iview 实现上传页面。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-upload</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-upload</name>
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
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
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
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>com.qiniu</groupId>
            <artifactId>qiniu-java-sdk</artifactId>
            <version>[7.2.0, 7.2.99]</version>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-upload</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## UploadConfig.java

```java
/**
 * <p>
 * 上传配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-10-23 14:09
 */
@Configuration
@ConditionalOnClass({Servlet.class, StandardServletMultipartResolver.class, MultipartConfigElement.class})
@ConditionalOnProperty(prefix = "spring.http.multipart", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class)
public class UploadConfig {
   @Value("${qiniu.accessKey}")
   private String accessKey;

   @Value("${qiniu.secretKey}")
   private String secretKey;

   private final MultipartProperties multipartProperties;

   @Autowired
   public UploadConfig(MultipartProperties multipartProperties) {
      this.multipartProperties = multipartProperties;
   }

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
      return new com.qiniu.storage.Configuration(Zone.zone0());
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
      return Auth.create(accessKey, secretKey);
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

## UploadController.java

```java
/**
 * <p>
 * 文件上传 Controller
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-06 16:33
 */
@RestController
@Slf4j
@RequestMapping("/upload")
public class UploadController {
   @Value("${spring.servlet.multipart.location}")
   private String fileTempPath;

   @Value("${qiniu.prefix}")
   private String prefix;

   private final IQiNiuService qiNiuService;

   @Autowired
   public UploadController(IQiNiuService qiNiuService) {
      this.qiNiuService = qiNiuService;
   }

   @PostMapping(value = "/local", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public Dict local(@RequestParam("file") MultipartFile file) {
      if (file.isEmpty()) {
         return Dict.create().set("code", 400).set("message", "文件内容为空");
      }
      String fileName = file.getOriginalFilename();
      String rawFileName = StrUtil.subBefore(fileName, ".", true);
      String fileType = StrUtil.subAfter(fileName, ".", true);
      String localFilePath = StrUtil.appendIfMissing(fileTempPath, "/") + rawFileName + "-" + DateUtil.current(false) + "." + fileType;
      try {
         file.transferTo(new File(localFilePath));
      } catch (IOException e) {
         log.error("【文件上传至本地】失败，绝对路径：{}", localFilePath);
         return Dict.create().set("code", 500).set("message", "文件上传失败");
      }

      log.info("【文件上传至本地】绝对路径：{}", localFilePath);
      return Dict.create().set("code", 200).set("message", "上传成功").set("data", Dict.create().set("fileName", fileName).set("filePath", localFilePath));
   }

   @PostMapping(value = "/yun", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public Dict yun(@RequestParam("file") MultipartFile file) {
      if (file.isEmpty()) {
         return Dict.create().set("code", 400).set("message", "文件内容为空");
      }
      String fileName = file.getOriginalFilename();
      String rawFileName = StrUtil.subBefore(fileName, ".", true);
      String fileType = StrUtil.subAfter(fileName, ".", true);
      String localFilePath = StrUtil.appendIfMissing(fileTempPath, "/") + rawFileName + "-" + DateUtil.current(false) + "." + fileType;
      try {
         file.transferTo(new File(localFilePath));
         Response response = qiNiuService.uploadFile(new File(localFilePath));
         if (response.isOK()) {
            JSONObject jsonObject = JSONUtil.parseObj(response.bodyString());

            String yunFileName = jsonObject.getStr("key");
            String yunFilePath = StrUtil.appendIfMissing(prefix, "/") + yunFileName;

            FileUtil.del(new File(localFilePath));

            log.info("【文件上传至七牛云】绝对路径：{}", yunFilePath);
            return Dict.create().set("code", 200).set("message", "上传成功").set("data", Dict.create().set("fileName", yunFileName).set("filePath", yunFilePath));
         } else {
            log.error("【文件上传至七牛云】失败，{}", JSONUtil.toJsonStr(response));
            FileUtil.del(new File(localFilePath));
            return Dict.create().set("code", 500).set("message", "文件上传失败");
         }
      } catch (IOException e) {
         log.error("【文件上传至七牛云】失败，绝对路径：{}", localFilePath);
         return Dict.create().set("code", 500).set("message", "文件上传失败");
      }
   }
}
```

## QiNiuServiceImpl.java

```java
/**
 * <p>
 * 七牛云上传Service
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-06 17:22
 */
@Service
@Slf4j
public class QiNiuServiceImpl implements IQiNiuService, InitializingBean {
   private final UploadManager uploadManager;

   private final Auth auth;

   @Value("${qiniu.bucket}")
   private String bucket;

   private StringMap putPolicy;

   @Autowired
   public QiNiuServiceImpl(UploadManager uploadManager, Auth auth) {
      this.uploadManager = uploadManager;
      this.auth = auth;
   }

   /**
    * 七牛云上传文件
    *
    * @param file 文件
    * @return 七牛上传Response
    * @throws QiniuException 七牛异常
    */
   @Override
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
      putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
   }

   /**
    * 获取上传凭证
    *
    * @return 上传凭证
    */
   private String getUploadToken() {
      return this.auth.uploadToken(bucket, null, 3600, putPolicy);
   }
}
```

## index.html

```html
<!doctype html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta name="viewport"
         content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
   <meta http-equiv="X-UA-Compatible" content="ie=edge">
   <title>spring-boot-demo-upload</title>
   <!-- import Vue.js -->
   <script src="https://cdn.bootcss.com/vue/2.5.17/vue.min.js"></script>
   <!-- import stylesheet -->
   <link href="https://cdn.bootcss.com/iview/3.1.4/styles/iview.css" rel="stylesheet">
   <!-- import iView -->
   <script src="https://cdn.bootcss.com/iview/3.1.4/iview.min.js"></script>
</head>
<body>
<div id="app">
   <Row :gutter="16" style="background:#eee;padding:10%">
      <i-col span="12">
         <Card style="height: 300px">
            <p slot="title">
               <Icon type="ios-cloud-upload"></Icon>
               本地上传
            </p>
            <div style="text-align: center;">
               <Upload
                     :before-upload="handleLocalUpload"
                     action="/demo/upload/local"
                     ref="localUploadRef"
                     :on-success="handleLocalSuccess"
                     :on-error="handleLocalError"
               >
                  <i-button icon="ios-cloud-upload-outline">选择文件</i-button>
               </Upload>
               <i-button
                     type="primary"
                     @click="localUpload"
                     :loading="local.loadingStatus"
                     :disabled="!local.file">
                  {{ local.loadingStatus ? '本地文件上传中' : '本地上传' }}
               </i-button>
            </div>
            <div>
               <div v-if="local.log.status != 0">状态：{{local.log.message}}</div>
               <div v-if="local.log.status === 200">文件名：{{local.log.fileName}}</div>
               <div v-if="local.log.status === 200">文件路径：{{local.log.filePath}}</div>
            </div>
         </Card>
      </i-col>
      <i-col span="12">
         <Card style="height: 300px;">
            <p slot="title">
               <Icon type="md-cloud-upload"></Icon>
               七牛云上传
            </p>
            <div style="text-align: center;">
               <Upload
                     :before-upload="handleYunUpload"
                     action="/demo/upload/yun"
                     ref="yunUploadRef"
                     :on-success="handleYunSuccess"
                     :on-error="handleYunError"
               >
                  <i-button icon="ios-cloud-upload-outline">选择文件</i-button>
               </Upload>
               <i-button
                     type="primary"
                     @click="yunUpload"
                     :loading="yun.loadingStatus"
                     :disabled="!yun.file">
                  {{ yun.loadingStatus ? '七牛云文件上传中' : '七牛云上传' }}
               </i-button>
            </div>
            <div>
               <div v-if="yun.log.status != 0">状态：{{yun.log.message}}</div>
               <div v-if="yun.log.status === 200">文件名：{{yun.log.fileName}}</div>
               <div v-if="yun.log.status === 200">文件路径：{{yun.log.filePath}}</div>
            </div>
         </Card>
      </i-col>
   </Row>
</div>
<script>
   new Vue({
      el: '#app',
      data: {
         local: {
            // 选择文件后，将 beforeUpload 返回的 file 保存在这里，后面会用到
            file: null,
            // 标记上传状态
            loadingStatus: false,
            log: {
               status: 0,
               message: "",
               fileName: "",
               filePath: ""
            }
         },
         yun: {
            // 选择文件后，将 beforeUpload 返回的 file 保存在这里，后面会用到
            file: null,
            // 标记上传状态
            loadingStatus: false,
            log: {
               status: 0,
               message: "",
               fileName: "",
               filePath: ""
            }
         }
      },
      methods: {
         // beforeUpload 在返回 false 或 Promise 时，会停止自动上传，这里我们将选择好的文件 file 保存在 data里，并 return false
         handleLocalUpload(file) {
            this.local.file = file;
            return false;
         },
         // 这里是手动上传，通过 $refs 获取到 Upload 实例，然后调用私有方法 .post()，把保存在 data 里的 file 上传。
         // iView 的 Upload 组件在调用 .post() 方法时，就会继续上传了。
         localUpload() {
            this.local.loadingStatus = true;  // 标记上传状态
            this.$refs.localUploadRef.post(this.local.file);
         },
         // 上传成功后，清空 data 里的 file，并修改上传状态
         handleLocalSuccess(response) {
            this.local.file = null;
            this.local.loadingStatus = false;
            if (response.code === 200) {
               this.$Message.success(response.message);
               this.local.log.status = response.code;
               this.local.log.message = response.message;
               this.local.log.fileName = response.data.fileName;
               this.local.log.filePath = response.data.filePath;
               this.$refs.localUploadRef.clearFiles();
            } else {
               this.$Message.error(response.message);
               this.local.log.status = response.code;
               this.local.log.message = response.message;
            }
         },
         // 上传失败后，清空 data 里的 file，并修改上传状态
         handleLocalError() {
            this.local.file = null;
            this.local.loadingStatus = false;
            this.$Message.error('上传失败');
         },
         // beforeUpload 在返回 false 或 Promise 时，会停止自动上传，这里我们将选择好的文件 file 保存在 data里，并 return false
         handleYunUpload(file) {
            this.yun.file = file;
            return false;
         },
         // 这里是手动上传，通过 $refs 获取到 Upload 实例，然后调用私有方法 .post()，把保存在 data 里的 file 上传。
         // iView 的 Upload 组件在调用 .post() 方法时，就会继续上传了。
         yunUpload() {
            this.yun.loadingStatus = true;  // 标记上传状态
            this.$refs.yunUploadRef.post(this.yun.file);
         },
         // 上传成功后，清空 data 里的 file，并修改上传状态
         handleYunSuccess(response) {
            this.yun.file = null;
            this.yun.loadingStatus = false;
            if (response.code === 200) {
               this.$Message.success(response.message);
               this.yun.log.status = response.code;
               this.yun.log.message = response.message;
               this.yun.log.fileName = response.data.fileName;
               this.yun.log.filePath = response.data.filePath;
               this.$refs.yunUploadRef.clearFiles();
            } else {
               this.$Message.error(response.message);
               this.yun.log.status = response.code;
               this.yun.log.message = response.message;
            }
         },
         // 上传失败后，清空 data 里的 file，并修改上传状态
         handleYunError() {
            this.yun.file = null;
            this.yun.loadingStatus = false;
            this.$Message.error('上传失败');
         }
      }
   })
</script>
</body>
</html>
```

## 参考

1. Spring 官方文档：https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#howto-multipart-file-upload-configuration
2. 七牛云官方文档：https://developer.qiniu.com/kodo/sdk/1239/java#5

