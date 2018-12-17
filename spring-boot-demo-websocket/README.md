# spring-boot-demo-websocket

> 此 demo 主要演示了 Spring Boot 如何集成 WebSocket，实现后端主动往前端推送数据。网上大部分websocket的例子都是聊天室，本例主要是推送服务器状态信息。前端页面基于vue和element-ui实现。

## 1. 代码

### 1.1. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-websocket</artifactId>
    <version>1.0.0-SNAPSHOT</version>

    <name>spring-boot-demo-websocket</name>
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
        <oshi.version>3.9.1</oshi.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.github.oshi</groupId>
            <artifactId>oshi-core</artifactId>
            <version>${oshi.version}</version>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-websocket</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 1.2. WebSocketConfig.java

```java
/**
 * <p>
 * WebSocket配置
 * </p>
 *
 * @package: com.xkcoding.websocket.config
 * @description: WebSocket配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-14 15:58
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个 /notification 端点，前端通过这个端点进行连接
        registry.addEndpoint("/notification")
                //解决跨域问题
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //定义了一个客户端订阅地址的前缀信息，也就是客户端接收服务端发送消息的前缀信息
        registry.enableSimpleBroker("/topic");
    }

}
```

### 1.3. 服务器相关实体

> 此部分实体 参见包路径 [com.xkcoding.websocket.model](./src/main/java/com/xkcoding/websocket/model)

### 1.4. ServerTask.java

```java
/**
 * <p>
 * 服务器定时推送任务
 * </p>
 *
 * @package: com.xkcoding.websocket.task
 * @description: 服务器定时推送任务
 * @author: yangkai.shen
 * @date: Created in 2018-12-14 16:04
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@Component
public class ServerTask {
    @Autowired
    private SimpMessagingTemplate wsTemplate;

    /**
     * 按照标准时间来算，每隔 2s 执行一次
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void websocket() throws Exception {
        log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
        // 查询服务器状态
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        Dict dict = ServerUtil.wrapServerDict(serverVO);
        wsTemplate.convertAndSend(WebSocketConsts.PUSH_SERVER, JSONUtil.toJsonStr(dict));
        log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
    }
}
```

### 1.5. server.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>服务器信息</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/element-ui/2.4.11/theme-chalk/index.css" rel="stylesheet">
    <style>
        .el-row {
            margin-bottom: 20px;
        }

        .el-row:last-child {
            margin-bottom: 0;
        }

        .sysFile {
            margin-bottom: 5px;
        }

        .sysFile:last-child {
            margin-bottom: 0;
        }
    </style>
</head>
<body>
<div id="app">
    <el-container>
        <el-header>
            <el-button @click="_initSockJs" type="primary" :disabled="isConnected">手动连接</el-button>
            <el-button @click="_destroySockJs" type="danger" :disabled="!isConnected">断开连接</el-button>
        </el-header>
        <el-main>
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-card>
                        <div slot="header">
                            <span>CPU信息</span>
                        </div>
                        <el-table size="small" border :data="server.cpu" style="width: 100%">
                            <el-table-column prop="key" label="属性">
                            </el-table-column>
                            <el-table-column prop="value" label="值">
                            </el-table-column>
                        </el-table>
                    </el-card>
                </el-col>
                <el-col :span="12">
                    <el-card>
                        <div slot="header">
                            <span>内存信息</span>
                        </div>
                        <el-table size="small" border :data="server.mem" style="width: 100%">
                            <el-table-column prop="key" label="属性">
                            </el-table-column>
                            <el-table-column prop="value" label="值">
                            </el-table-column>
                        </el-table>
                    </el-card>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-card>
                        <div slot="header">
                            <span>服务器信息</span>
                        </div>
                        <el-table size="small" border :data="server.sys" style="width: 100%">
                            <el-table-column prop="key" label="属性">
                            </el-table-column>
                            <el-table-column prop="value" label="值">
                            </el-table-column>
                        </el-table>
                    </el-card>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-card>
                        <div slot="header">
                            <span>Java虚拟机信息</span>
                        </div>
                        <el-table size="small" border :data="server.jvm" style="width: 100%">
                            <el-table-column prop="key" label="属性">
                            </el-table-column>
                            <el-table-column prop="value" label="值">
                            </el-table-column>
                        </el-table>
                    </el-card>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-card>
                        <div slot="header">
                            <span>磁盘状态</span>
                        </div>
                        <div class="sysFile" v-for="(item,index) in server.sysFile" :key="index">
                            <el-table size="small" border :data="item" style="width: 100%">
                                <el-table-column prop="key" label="属性">
                                </el-table-column>
                                <el-table-column prop="value" label="值">
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </el-main>
    </el-container>
</div>
</body>
<script src="js/sockjs.min.js"></script>
<script src="js/stomp.js"></script>
<script src="https://cdn.bootcss.com/vue/2.5.21/vue.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/element-ui/2.4.11/index.js"></script>
<script src="https://cdn.bootcss.com/axios/0.19.0-beta.1/axios.min.js"></script>
<script>
    const wsHost = "http://localhost:8080/demo/notification";
    const wsTopic = "/topic/server";

    const app = new Vue({
        el: '#app',
        data: function () {
            return {
                isConnected: false,
                stompClient: {},
                socket: {},
                server: {
                    cpu: [],
                    mem: [],
                    jvm: [],
                    sys: [],
                    sysFile: []
                }
            }
        },
        methods: {
            _getServerInfo() {
                axios.get('/demo/server')
                    .then((response) => {
                        this.server = response.data
                    });
            },
            _initSockJs() {
                this._getServerInfo();
                this.socket = new SockJS(wsHost);
                this.stompClient = Stomp.over(this.socket);

                this.stompClient.connect({}, (frame) => {
                    console.log('websocket连接成功:' + frame);
                    this.isConnected = true;
                    this.$message('websocket服务器连接成功');

                    // 另外再注册一下消息推送
                    this.stompClient.subscribe(wsTopic, (response) => {
                        this.server = JSON.parse(response.body);
                    });
                });
            },
            _destroySockJs() {
                if (this.stompClient != null) {
                    this.stompClient.disconnect();
                    this.socket.onclose;
                    this.socket.close();
                    this.stompClient = {};
                    this.socket = {};
                    this.isConnected = false;
                    this.server.cpu = [];
                    this.server.mem = [];
                    this.server.jvm = [];
                    this.server.sys = [];
                    this.server.sysFile = [];
                }
                console.log('websocket断开成功！');
                this.$message.error('websocket断开成功！');
            }
        },
        mounted() {
            this._initSockJs();
        },
        beforeDestroy() {
            this._destroySockJs();
        }

    })
</script>
</html>
```

## 2. 运行方式

1. 启动 `SpringBootDemoWebsocketApplication.java`
2. 访问 http://localhost:8080/demo/server.html

## 3. 运行效果

![image-20181217110240322](assets/image-20181217110240322-5015760.png)

![image-20181217110304065](assets/image-20181217110304065-5015784.png)

![image-20181217110328810](assets/image-20181217110328810-5015808.png)

![image-20181217110336017](assets/image-20181217110336017-5015816.png)

## 4. 参考

### 4.1. 后端

1. Spring Boot 整合 Websocket 官方文档：https://docs.spring.io/spring/docs/5.1.2.RELEASE/spring-framework-reference/web.html#websocket
2. 服务器信息采集 oshi 使用：https://github.com/oshi/oshi

### 4.2. 前端

1. vue.js 语法：https://cn.vuejs.org/v2/guide/
2. element-ui 用法：http://element-cn.eleme.io/#/zh-CN
3. stomp.js 用法：https://github.com/jmesnil/stomp-websocket
4. sockjs 用法：https://github.com/sockjs/sockjs-client