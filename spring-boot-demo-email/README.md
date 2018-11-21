# spring-boot-demo-email

> 此 demo 主要演示了 Spring Boot 如何整合邮件功能，包括发送简单文本邮件、HTML邮件（包括模板HTML邮件）、附件邮件、静态资源邮件。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-email</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-email</name>
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
        <!-- Spring Boot 邮件依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
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

        <!-- Spring Boot 模板依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-email</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## application.yml

> 此处特别感谢 **路神**([@路小磊](https://github.com/looly)) 的开源工具集 [Hutool](https://github.com/looly/hutool) ，此处邮箱使用 Hutool 提供的公共邮箱做为测试邮箱，请童鞋们不要违规使用。

```yaml
spring:
  mail:
    host: smtp.yeah.net
    port: 994
    username: hutool@yeah.net
    password: q1w2e3
    protocol: smtp
    test-connection: true
    default-encoding: UTF-8
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
      mail.smtp.starttls.required: true
      mail.smtp.ssl.enable: true
      mail.display.sendmail: xkcoding
```

## MailService.java

```java
/**
 * <p>
 * 邮件接口
 * </p>
 *
 * @package: com.xkcoding.email.service
 * @description: 邮件接口
 * @author: yangkai.shen
 * @date: Created in 2018/11/21 11:16
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface MailService {
    /**
     * 发送文本邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     */
    void sendSimpleMail(String to, String subject, String content, String... cc);

    /**
     * 发送HTML邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     * @throws MessagingException 邮件发送异常
     */
    void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException;

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人地址
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param filePath 附件地址
     * @param cc       抄送地址
     * @throws MessagingException 邮件发送异常
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) throws MessagingException;

    /**
     * 发送正文中有静态资源的邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param rscPath 静态资源地址
     * @param rscId   静态资源id
     * @param cc      抄送地址
     * @throws MessagingException 邮件发送异常
     */
    void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) throws MessagingException;

} 
```

## MailServiceImpl.java

```java
/**
 * <p>
 * 邮件接口
 * </p>
 *
 * @package: com.xkcoding.email.service.impl
 * @description: 邮件接口
 * @author: yangkai.shen
 * @date: Created in 2018/11/21 13:49
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送文本邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        if (ArrayUtil.isNotEmpty(cc)) {
            message.setCc(cc);
        }
        mailSender.send(message);
    }

    /**
     * 发送HTML邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     * @throws MessagingException 邮件发送异常
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        if (ArrayUtil.isNotEmpty(cc)) {
            helper.setCc(cc);
        }
        mailSender.send(message);
    }

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人地址
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param filePath 附件地址
     * @param cc       抄送地址
     * @throws MessagingException 邮件发送异常
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        if (ArrayUtil.isNotEmpty(cc)) {
            helper.setCc(cc);
        }
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);

        mailSender.send(message);
    }

    /**
     * 发送正文中有静态资源的邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param rscPath 静态资源地址
     * @param rscId   静态资源id
     * @param cc      抄送地址
     * @throws MessagingException 邮件发送异常
     */
    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        if (ArrayUtil.isNotEmpty(cc)) {
            helper.setCc(cc);
        }
        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        mailSender.send(message);
    }
}
```

## MailServiceTest.java

```java
/**
 * <p>
 * 邮件测试
 * </p>
 *
 * @package: com.xkcoding.email.service
 * @description: 邮件测试
 * @author: yangkai.shen
 * @date: Created in 2018/11/21 13:49
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class MailServiceTest extends SpringBootDemoEmailApplicationTests {
    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 测试简单邮件
     */
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("237497819@qq.com", "这是一封简单邮件", "这是一封普通的SpringBoot测试邮件");
    }

    /**
     * 测试HTML邮件
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    public void sendHtmlMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("project", "Spring Boot Demo");
        context.setVariable("author", "yangkai.shen");
        context.setVariable("url", "https://github.com/xkcoding/spring-boot-demo");

        String emailTemplate = templateEngine.process("welcome", context);
        mailService.sendHtmlMail("237497819@qq.com", "这是一封模板HTML邮件", emailTemplate);
    }

    /**
     * 测试附件邮件
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    public void sendAttachmentsMail() throws MessagingException {
        URL resource = ResourceUtil.getResource("static/xkcoding.png");
        mailService.sendAttachmentsMail("237497819@qq.com", "这是一封带附件的邮件", "邮件中有附件，请注意查收！", resource.getPath());
    }

    /**
     * 测试静态资源邮件
     *
     * @throws MessagingException 邮件异常
     */
    @Test
    public void sendResourceMail() throws MessagingException {
        String rscId = "xkcoding";
        String content = "<html><body>这是带静态资源的邮件<br/><img src=\'cid:" + rscId + "\' ></body></html>";
        URL resource = ResourceUtil.getResource("static/xkcoding.png");
        mailService.sendResourceMail("237497819@qq.com", "这是一封带静态资源的邮件", content, resource.getPath(), rscId);
    }
}
```

## welcome.html

> 此文件为邮件模板，位于 resources/templates 目录下

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>SpringBootDemo(入门SpringBoot的首选Demo)</title>
    <style>
        body {
            text-align: center;
            margin-left: auto;
            margin-right: auto;
        }

        #welcome {
            text-align: center;
        }
    </style>
</head>
<body>
<div id="welcome">
    <h3>欢迎使用 <span th:text="${project}"></span> - Powered By <span th:text=" ${author}"></span></h3>
    <span th:text="${url}"></span>
    <div style="text-align: center; padding: 10px">
        <a style="text-decoration: none;" href="#" th:href="@{${url}}" target="_bank">
            <strong>spring-boot-demo，入门Spring Boot的首选Demo！:)</strong>
        </a>
    </div>
    <div style="text-align: center; padding: 4px">
        如果对你有帮助,请任意打赏
    </div>
    <div style="width: 100%;height: 100%;text-align: center;display: flex">
        <div style="flex: 1;"></div>
        <div style="display: flex;width: 400px;">
            <div style="flex: 1;text-align: center;">
                <div>
                    <img width="180px" height="180px" src="http://xkcoding.com/resources/wechat-reward-image.png">
                </div>
                <div>微信打赏</div>
            </div>
            <div style="flex: 1;text-align: center;">
                <div><img width="180px" height="180px" src="http://xkcoding.com/resources/alipay-reward-image.png">
                </div>
                <div>支付宝打赏</div>
            </div>
        </div>
        <div style="flex: 1;"></div>
    </div>

</div>
</body>
</html>
```

## 参考

- Spring Boot 官方文档：https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-email
- Spring Boot 官方文档：https://docs.spring.io/spring/docs/5.1.2.RELEASE/spring-framework-reference/integration.html#mail