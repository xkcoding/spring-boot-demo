# spring-boot-demo-https

> 此 demo 主要演示了 Spring Boot 如何集成 https

## 1.开发步骤

### 1.1.添加依赖

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

### 1.2.生成证书

首先使用 jdk 自带的 keytool 命令生成证书复制到项目的 `resources` 目录下

```bash
$ keytool -genkey -alias spring-boot-demo -keyalg RSA -validity 3650 -keystore ./spring-boot-demo.key
输入密钥库口令:
您的名字与姓氏是什么?
  [Unknown]:  xkcoding
您的组织单位名称是什么?
  [Unknown]:  spring-boot-demo
您的组织名称是什么?
  [Unknown]:  spring-boot-demo
您所在的城市或区域名称是什么?
  [Unknown]:  Hangzhou
您所在的省/市/自治区名称是什么?
  [Unknown]:  Zhejiang
该单位的双字母国家/地区代码是什么?
  [Unknown]:  CN
CN=xkcoding, OU=spring-boot-demo, O=spring-boot-demo, L=Hangzhou, ST=Zhejiang, C=CN是否正确?
  [否]:  y

正在为以下对象生成 2,048 位RSA密钥对和自签名证书 (SHA256withRSA) (有效期为 3,650 天):
	 CN=xkcoding, OU=spring-boot-demo, O=spring-boot-demo, L=Hangzhou, ST=Zhejiang, C=CN
```

### 1.3.添加配置

1. 在配置文件配置生成的证书

```yaml
server:
  ssl:
    # 证书路径
    key-store: classpath:spring-boot-demo.key
    key-alias: spring-boot-demo
    enabled: true
    key-store-type: JKS
    # 与申请时输入一致
    key-store-password: 123456
    # 浏览器默认端口 和 80 类似
  port: 8443
  servlet:
    context-path: /demo

```

2. 配置 Tomcat

```java
@Configuration
public class HttpsConfig {
  /**
   * 配置 http(80) -> 强制跳转到 https(443)
   */
  @Bean
  public Connector connector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(8080);
    connector.setSecure(false);
    connector.setRedirectPort(8443);
    return connector;
  }

  @Bean
  public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector) {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
      }
    };
    tomcat.addAdditionalTomcatConnectors(connector);
    return tomcat;
  }
}
```

## 2.测试

启动 `HttpsApplication`，浏览器访问 `http://localhost:8080/demo/` 将自动跳转到 `https://localhost:8443/demo/` ，同时浏览器地址栏前面还会加一把小锁的标志，代表https已经生效。

> 注意：
> 1. 自己生成的证书浏览器会有危险提示，去ssl网站上使用金钱申请则不会
> 2. Chrome 浏览器会因为证书不可信导致无法访问，因此测试请使用 FireFox 浏览器

## 3.参考

- `keytool`命令参考

```bash
$ keytool --help
密钥和证书管理工具

命令:

 -certreq            生成证书请求
 -changealias        更改条目的别名
 -delete             删除条目
 -exportcert         导出证书
 -genkeypair         生成密钥对
 -genseckey          生成密钥
 -gencert            根据证书请求生成证书
 -importcert         导入证书或证书链
 -importpass         导入口令
 -importkeystore     从其他密钥库导入一个或所有条目
 -keypasswd          更改条目的密钥口令
 -list               列出密钥库中的条目
 -printcert          打印证书内容
 -printcertreq       打印证书请求的内容
 -printcrl           打印 CRL 文件的内容
 -storepasswd        更改密钥库的存储口令
 -showinfo           显示安全相关信息

使用 "keytool -?, -h, or --help" 可输出此帮助消息
使用 "keytool -command_name --help" 可获取 command_name 的用法。
使用 -conf <url> 选项可指定预配置的选项文件。
```

- [Spring Boot 官方文档之配置 SSL](https://docs.spring.io/spring-boot/docs/3.0.0-M4/reference/htmlsingle/#howto.webserver.configure-ssl)
- [Java Keytool工具简介](https://blog.csdn.net/liumiaocn/article/details/61921014)
