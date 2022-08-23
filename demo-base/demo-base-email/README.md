## spring-boot-demo-email

> 此 demo 主要演示了 Spring Boot 如何整合邮件功能，包括发送简单文本邮件、HTML邮件（包括模板HTML邮件）、附件邮件、静态资源邮件。

### 1.开发步骤

#### 1.1.添加依赖

```xml

<dependencies>
  <dependency>
    <groupId>com.xkcoding</groupId>
    <artifactId>common-tools</artifactId>
  </dependency>

  <!-- Spring Boot 邮件依赖 -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
  </dependency>

  <!-- jasypt配置文件加解密 -->
  <dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>${jasypt.version}</version>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>

  <!-- Spring Boot 模板依赖 -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>
</dependencies>
```

#### 1.2.添加邮件相关配置

```yaml
spring:
  mail:
    host: smtp.mxhichina.com
    port: 465
    from: "SpringBootDemo测试<${spring.mail.username}>"
    username: spring-boot-demo@xkcoding.com
    # 使用 jasypt 加密密码，使用com.xkcoding.email.PasswordTest.testGeneratePassword 生成加密密码，替换 ENC(加密密码)
    password: ENC(aef0+nM5440HO7YFAo7iUz8ZHpkjZVlR0hNw3OI/QOPSkNhYRImE/Oy1LBgFKoB1OjqW0v4ZdM0xNS0eKxELfA==)
    protocol: smtp
    test-connection: true
    default-encoding: UTF-8
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
      mail.smtp.starttls.required: true
      mail.smtp.ssl.enable: true
      mail.display.sendmail: spring-boot-demo
# 为 jasypt 配置解密秘钥
jasypt:
  encryptor:
    password: spring-boot-demo
```

#### 1.3.编写发送邮件代码

- **抽象邮件服务接口**，方便后期替换不同的客户端实现

```java
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
  void sendHtmlMail(String to, String subject, String content, String... cc)
    throws MessagingException, MessagingException;

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
  void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc)
    throws MessagingException;

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
  void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc)
    throws MessagingException;
```

- 接口实现

```java

@Service
public class MailServiceImpl implements MailService {
  @Autowired
  private JavaMailSender mailSender;
  @Value("${spring.mail.from}")
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
   */
  @Override
  public void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException {
    MimeEmail mimeEmail = basicMimeEmailBuilder(to, subject, content, cc);
    mailSender.send(mimeEmail.message());
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
  public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc)
    throws MessagingException {
    MimeEmail mimeEmail = basicMimeEmailBuilder(to, subject, content, cc);

    MimeMessageHelper helper = mimeEmail.helper();
    FileSystemResource file = new FileSystemResource(new File(filePath));
    String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
    helper.addAttachment(fileName, file);

    mailSender.send(mimeEmail.message());
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
  public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc)
    throws MessagingException {
    MimeEmail mimeEmail = basicMimeEmailBuilder(to, subject, content, cc);

    MimeMessageHelper helper = mimeEmail.helper();
    FileSystemResource res = new FileSystemResource(new File(rscPath));
    helper.addInline(rscId, res);

    mailSender.send(mimeEmail.message());
  }

  /**
   * 富文本邮件构造器，抽取重复代码，返回一个 MimeEmail record
   */
  private MimeEmail basicMimeEmailBuilder(String to, String subject, String content, String... cc)
    throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(content, true);
    if (ArrayUtil.isNotEmpty(cc)) {
      helper.setCc(cc);
    }
    return new MimeEmail(message, helper);
  }

  private record MimeEmail(MimeMessage message, MimeMessageHelper helper) {

  }
}
```

- 其他资源文件参考 `classpath://resources` 目录

### 2.测试

参考 `MailServiceTest` 测试用例，分别运行各个方法，进行邮件测试
> 注意：
> 1. 最好是每个方法分别运行，不要直接在测试类上一下跑所有测试用例，因为 Spring 的 `templateResolver` 一旦初始化是不允许修改的
> 2. **强烈建议各位同学测试的时候，把邮箱改成自己的邮箱进行测试，这样才能实际体会到收到的邮件内容**
> 3. 请勿将 `spring-boot-demo@xkcoding.com` 的邮箱用于发送违法内容，否则作者将收回邮箱权限，同时提交给公安依法追究

### 3.参考

- Spring Boot 官方文档：https://docs.spring.io/spring-boot/docs/3.0.0-M4/reference/htmlsingle/#io.email
- Spring 官方文档：https://docs.spring.io/spring-framework/docs/6.0.0-M5/reference/html/integration.html#mail
- jasypt 加解密文档：https://github.com/ulisesbocchio/jasypt-spring-boot#what-to-do-first
