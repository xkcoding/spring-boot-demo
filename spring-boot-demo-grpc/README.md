# spring-boot-demo-grpc

> 此 demo 主要演示了如何使用 Spring Boot 集成 RPC并配置开启tls/ssl

## 项目结构
根项目（spring-boot-demo-grpc）下有3个子模块：

- spring-boot-demo-grpc-server：GRPC服务端
- spring-boot-demo-grpc-protocol：包含原始proto文件，以及其转换的java代码
- spring-boot-demo-grpc-client ： GRPC客户端

## 配置指南
### .proto生成java文件
1、定义好proto文件放在项目"src\main\proto"目录下 如：spring-boot-demo-grpc-protocol\src\main\proto

2、生成java代码
1. 直接在IDE里用proto插件运行compile生成
2. 直接下载[protobuf-window版](https://github.com/protocolbuffers/protobuf/releases/download/v3.11.4/protoc-3.11.4-win64.zip) 然后执行```./protoc.exe --java_out=./ demo.proto```

### 证书生成：
如果没有证书(例如内部测试服务器)，可以使用openssl生成证书：

[openssl下载](http://slproweb.com/download/Win64OpenSSL-1_1_1e.msi) 可以参考这里[配置](https://github.com/kiinlam/kiinlam.github.io/issues/11)

服务端：
```openssl req -x509 -nodes -subj "/CN=localhost/C=CN" -newkey rsa:4096 -sha256 -keyout server.key -out server.crt -days 3650```

客户端：
```openssl req -x509 -nodes -subj "/CN=localhost/C=CN" -newkey rsa:4096 -sha256 -keyout server.key -out server.crt -days 3650```

**请注意** 如果没有额外配置，这些证书不受任何应用程序的信任。 我们建议您使用受全球CA或您公司CA信任的证书。

最后分别放入server、client的resources/certificates/下

### spring-boot-demo-grpc-client: GRPC客户端
```yaml
server:
  port: 8090
spring:
  application:
    name: demo-grpc-client
grpc:
  client:
    demo-grpc-server:
      address: 'static://localhost:9090'
      # 禁用传输图层安全 请勿在生产环境中这样做
      #negotiationType: plaintext
      # gRPC 默认使用 TLS 连接服务端 该配置启用传输图层安全
      negotiationType: TLS
      security:
        #如果您信任的证书不在常规信任存储区， 或者您想要限制您信任的 证书。您可以使用以下属性
        trustCertCollection: classpath:certificates/trusted-server.crt.list
        #要指定证书对哪个名字有效：
        authorityOverride: localhost
        #如果需要双向证书认证配置如下属性
        clientAuthEnabled: true
        certificateChain: classpath:certificates/client.crt
        privateKey: classpath:certificates/client.key
```

### spring-boot-demo-grpc-server: GRPC服务端
```yaml
spring:
  application:
    name: demo-grpc-server
grpc:
  server:
    port: 9090
  # 开启TLS相关配置
    security:
      enabled: true
      certificateChain: classpath:certificates/server.crt
      privateKey: classpath:certificates/server.key
      #指定受信任客户端证书 直接把client.crt复制到该文件即可
      trustCertCollection: classpath:certificates/trusted-clients.crt.list
      #REQUIRE：客户端证书必须通过认证 OPTIONAL：对客户端的证书进行身份验证，但不会强制这么做。
      clientAuth: REQUIRE
```

### 运行
client、server直接运行起来之后访问http://localhost:8090/hello 出现Hi~说明你成功了

## 详细配置说明
请参考[文档](https://yidongnan.github.io/grpc-spring-boot-starter/zh-CN/index)

## 常见问题
请参考[文档](https://yidongnan.github.io/grpc-spring-boot-starter/en/trouble-shooting.html)
