# spring-boot-demo-websocket-socketio

> 此 demo 主要演示了 Spring Boot 如何使用 `netty-socketio` 集成 WebSocket，实现一个简单的聊天室。

## 1. 代码

### 1.1. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-websocket-socketio</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-websocket-socketio</name>
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
        <netty-socketio.version>1.7.16</netty-socketio.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.corundumstudio.socketio</groupId>
            <artifactId>netty-socketio</artifactId>
            <version>${netty-socketio.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
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
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-websocket-socketio</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 1.2. ServerConfig.java

> websocket服务器配置，包括服务器IP、端口信息、以及连接认证等配置

```java
/**
 * <p>
 * websocket服务器配置
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.config
 * @description: websocket服务器配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 16:42
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableConfigurationProperties({WsConfig.class})
public class ServerConfig {

    @Bean
    public SocketIOServer server(WsConfig wsConfig) {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(wsConfig.getHost());
        config.setPort(wsConfig.getPort());

        //这个listener可以用来进行身份验证
        config.setAuthorizationListener(data -> {
            // http://localhost:8081?token=xxxxxxx
            // 例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
            String token = data.getSingleUrlParam("token");
            // 校验token的合法性，实际业务需要校验token是否过期等等，参考 spring-boot-demo-rbac-security 里的 JwtUtil
            // 如果认证不通过会返回一个 Socket.EVENT_CONNECT_ERROR 事件
            return StrUtil.isNotBlank(token);
        });

        return new SocketIOServer(config);
    }

    /**
     * Spring 扫描自定义注解
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }
}
```

### 1.3. MessageEventHandler.java

> 核心事件处理类，主要处理客户端发起的消息事件，以及主动往客户端发起事件

```java
/**
 * <p>
 * 消息事件处理
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.handler
 * @description: 消息事件处理
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 18:57
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class MessageEventHandler {
    @Autowired
    private SocketIOServer server;

    @Autowired
    private DbTemplate dbTemplate;

    /**
     * 添加connect事件，当客户端发起连接时调用
     *
     * @param client 客户端对象
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        if (client != null) {
            String token = client.getHandshakeData().getSingleUrlParam("token");
            // 模拟用户id 和token一致
            String userId = client.getHandshakeData().getSingleUrlParam("token");
            UUID sessionId = client.getSessionId();

            dbTemplate.save(userId, sessionId);
            log.info("连接成功,【token】= {},【sessionId】= {}", token, sessionId);
        } else {
            log.error("客户端为空");
        }
    }

    /**
     * 添加disconnect事件，客户端断开连接时调用，刷新客户端信息
     *
     * @param client 客户端对象
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        if (client != null) {
            String token = client.getHandshakeData().getSingleUrlParam("token");
            // 模拟用户id 和token一致
            String userId = client.getHandshakeData().getSingleUrlParam("token");
            UUID sessionId = client.getSessionId();

            dbTemplate.deleteByUserId(userId);
            log.info("客户端断开连接,【token】= {},【sessionId】= {}", token, sessionId);
            client.disconnect();
        } else {
            log.error("客户端为空");
        }
    }

    /**
     * 加入群聊
     *
     * @param client  客户端
     * @param request 请求
     * @param data    群聊
     */
    @OnEvent(value = Event.JOIN)
    public void onJoinEvent(SocketIOClient client, AckRequest request, JoinRequest data) {
        log.info("用户：{} 已加入群聊：{}", data.getUserId(), data.getGroupId());
        client.joinRoom(data.getGroupId());

        server.getRoomOperations(data.getGroupId()).sendEvent(Event.JOIN, data);
    }


    @OnEvent(value = Event.CHAT)
    public void onChatEvent(SocketIOClient client, AckRequest request, SingleMessageRequest data) {
        Optional<UUID> toUser = dbTemplate.findByUserId(data.getToUid());
        if (toUser.isPresent()) {
            log.info("用户 {} 刚刚私信了用户 {}：{}", data.getFromUid(), data.getToUid(), data.getMessage());
            sendToSingle(toUser.get(), data);
            client.sendEvent(Event.CHAT_RECEIVED, "发送成功");
        } else {
            client.sendEvent(Event.CHAT_REFUSED, "发送失败，对方不想理你");
        }
    }

    @OnEvent(value = Event.GROUP)
    public void onGroupEvent(SocketIOClient client, AckRequest request, GroupMessageRequest data) {
        Collection<SocketIOClient> clients = server.getRoomOperations(data.getGroupId()).getClients();

        boolean inGroup = false;
        for (SocketIOClient socketIOClient : clients) {
            if (ObjectUtil.equal(socketIOClient.getSessionId(), client.getSessionId())) {
                inGroup = true;
                break;
            }
        }
        if (inGroup) {
            log.info("群号 {} 收到来自 {} 的群聊消息：{}", data.getGroupId(), data.getFromUid(), data.getMessage());
            sendToGroup(data);
        } else {
            request.sendAckData("请先加群！");
        }
    }

    /**
     * 单聊
     */
    public void sendToSingle(UUID sessionId, SingleMessageRequest message) {
        server.getClient(sessionId).sendEvent(Event.CHAT, message);
    }

    /**
     * 广播
     */
    public void sendToBroadcast(BroadcastMessageRequest message) {
        log.info("系统紧急广播一条通知：{}", message.getMessage());
        for (UUID clientId : dbTemplate.findAll()) {
            if (server.getClient(clientId) == null) {
                continue;
            }
            server.getClient(clientId).sendEvent(Event.BROADCAST, message);
        }
    }

    /**
     * 群聊
     */
    public void sendToGroup(GroupMessageRequest message) {
        server.getRoomOperations(message.getGroupId()).sendEvent(Event.GROUP, message);
    }
}
```

### 1.4. ServerRunner.java

>  websocket 服务器启动类

```java
/**
 * <p>
 * websocket服务器启动
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.init
 * @description: websocket服务器启动
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 17:07
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Slf4j
public class ServerRunner implements CommandLineRunner {
    @Autowired
    private SocketIOServer server;

    @Override
    public void run(String... args) {
        server.start();
        log.info("websocket 服务器启动成功。。。");
    }
}
```

## 2. 运行方式

1. 启动 `SpringBootDemoWebsocketSocketioApplication.java`
2. 使用不同的浏览器，访问 http://localhost:8080/demo/index.html

## 3. 运行效果

**浏览器1：**![image-20181219152318079](http://static.xkcoding.com/spring-boot-demo/websocket/socketio/064155.jpg)

**浏览器2：**![image-20181219152330156](http://static.xkcoding.com/spring-boot-demo/websocket/socketio/064154.jpg)

## 4. 参考

### 4.1. 后端

1. Netty-socketio 官方仓库：https://github.com/mrniko/netty-socketio
2. SpringBoot系列 - 集成SocketIO实时通信：https://www.xncoding.com/2017/07/16/spring/sb-socketio.html
3. Spring Boot 集成 socket.io 后端实现消息实时通信：http://alexpdh.com/2017/09/03/springboot-socketio/
4. Spring Boot实战之netty-socketio实现简单聊天室：http://blog.csdn.net/sun_t89/article/details/52060946

### 4.2. 前端

1. socket.io 官网：https://socket.io/
2. axios.js 用法：https://github.com/axios/axios#example