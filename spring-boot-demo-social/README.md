# spring-boot-demo-social
> 此 demo 主要演示 Spring Boot 项目如何使用 **[史上最全的第三方登录工具 - JustAuth](https://github.com/zhangyd-c/JustAuth)** 实现第三方登录。
>
> JustAuth，如你所见，它仅仅是一个**第三方授权登录**的**工具类库**，它可以让我们脱离繁琐的第三方登录SDK，让登录变得**So easy!**
>
> 1. **全**：已集成十多家第三方平台（国内外常用的基本都已包含），后续依然还有扩展计划！
> 2. **简**：API就是奔着最简单去设计的（见后面[`快速开始`](https://github.com/zhangyd-c/JustAuth#%E5%BF%AB%E9%80%9F%E5%BC%80%E5%A7%8B)），尽量让您用起来没有障碍感！
>
> PS: 本人十分幸运的参与到了这个SDK的开发，主要开发了**QQ登录、微信登录、小米登录、微软登录、谷歌登录**这 **`5`** 个第三方登录，以及一些BUG的修复工作。再次感谢 [@母狼](https://github.com/zhangyd-c) 开源这个又好用又全面的第三方登录SDK。

## 1. 环境准备

### 1.1. 公网服务器准备

首先准备一台有公网IP的服务器，可以选用阿里云或者腾讯云，如果选用的是阿里云的，可以使用我的[优惠链接](https://chuangke.aliyun.com/invite?userCode=r8z5amhr)购买。

### 1.2. 内网穿透frp搭建

> frp 安装程序：https://github.com/fatedier/frp/releases

#### 1.2.1. frp服务端搭建

服务端搭建在上一步准备的公网服务器上，因为服务器是centos7 x64的系统，因此，这里下载安装包版本为linux_amd64的 [frp_0.27.0_linux_amd64.tar.gz](https://github.com/fatedier/frp/releases/download/v0.27.0/frp_0.27.0_linux_amd64.tar.gz) 。

1. 下载安装包

   ```shell
   $ wget https://github.com/fatedier/frp/releases/download/v0.27.0/frp_0.27.0_linux_amd64.tar.gz
   ```

2. 解压安装包

   ```shell
   $ tar -zxvf frp_0.27.0_linux_amd64.tar.gz
   ```

3. 修改配置文件

   ```shell
   $ cd frp_0.27.0_linux_amd64
   $ vim frps.ini
   
   [common]                                                                                                                  
   bind_port = 7100                                                                                                          
   vhost_http_port = 7200
   ```

4. 启动frp服务端

   ```shell
   $ ./frps -c frps.ini
   2019/06/15 16:42:02 [I] [service.go:139] frps tcp listen on 0.0.0.0:7100
   2019/06/15 16:42:02 [I] [service.go:181] http service listen on 0.0.0.0:7200
   2019/06/15 16:42:02 [I] [root.go:204] Start frps success
   ```

#### 1.2.2. frp客户端搭建

客户端搭建在本地的Mac上，因此下载安装包版本为darwin_amd64的 [frp_0.27.0_darwin_amd64.tar.gz](https://github.com/fatedier/frp/releases/download/v0.27.0/frp_0.27.0_darwin_amd64.tar.gz) 。

1. 下载安装包

   ```shell
   $ wget https://github.com/fatedier/frp/releases/download/v0.27.0/frp_0.27.0_darwin_amd64.tar.gz
   ```

2. 解压安装包

   ```shell
   $ tar -zxvf frp_0.27.0_darwin_amd64.tar.gz
   ```

3. 修改配置文件，配置服务端ip端口及监听的域名信息

   ```shell
   $ cd frp_0.27.0_darwin_amd64
   $ vim frpc.ini
   
   [common]
   server_addr = 120.92.169.103
   server_port = 7100
   
   [web]
   type = http
   local_port = 8080
   custom_domains = oauth.xkcoding.com
   ```

4. 启动frp客户端

   ```shell
   $ ./frpc -c frpc.ini
   2019/06/15 16:48:52 [I] [service.go:221] login to server success, get run id [8bb83bae5c58afe6], server udp port [0]
   2019/06/15 16:48:52 [I] [proxy_manager.go:137] [8bb83bae5c58afe6] proxy added: [web]
   2019/06/15 16:48:52 [I] [control.go:144] [web] start proxy success
   ```

### 1.3. 配置域名解析

前往阿里云DNS解析，将域名解析到我们的公网服务器上，比如我的就是将 `oauth.xkcoding.com -> 120.92.169.103`

![image-20190615165843639](assets/image-20190615165843639.png)

### 1.4. nginx代理

nginx 的搭建就不在此赘述了，只说配置

```nginx
server {
    listen       80;
    server_name  oauth.xkcoding.com;         
                                                                        
    location / {
        proxy_pass http://127.0.0.1:7200;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header Host $http_host;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header   X-Real-IP        $remote_addr;                                                                 
        proxy_buffering off;                                                                                              
        sendfile off;                                                                                                     
        proxy_max_temp_file_size 0;                                                                                       
        client_max_body_size       10m;                                                                                   
        client_body_buffer_size    128k;                                                                                  
        proxy_connect_timeout      90;                                                                                    
        proxy_send_timeout         90;                                                                                    
        proxy_read_timeout         90;                                                                                    
        proxy_temp_file_write_size 64k;                                                                                   
        proxy_http_version 1.1;                                                                                           
        proxy_request_buffering off; 
    }
}
```

测试配置文件是否有问题

```shell
$ nginx -t
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
```

重新加载配置文件，使其生效

```shell
$ nginx -s reload
```

> 现在当我们在浏览器输入 `oauth.xkcoding.com` 的时候，网络流量其实会经历以下几个步骤：
>
> 1. 通过之前配的DNS域名解析会访问到我们的公网服务器 `120.92.169.103` 的 80 端口
> 2. 再经过 nginx，代理到本地的 7200 端口
> 3. 再经过 frp 穿透到我们的 Mac 电脑的 8080 端口
> 4. 此时 8080 就是我们的应用程序端口

### 1.5. 第三方平台申请

#### 1.5.1. QQ互联平台申请



#### 1.5.2. GitHub平台申请



#### 1.5.3 微信开放平台申请



#### 1.5.4. 谷歌开放平台申请



#### 1.5.5. 微软开放平台申请



#### 1.5.6. 小米开放平台申请



## 2. 主要代码

### 2.1. pom.xml



### 2.2. application.yml



### 2.3. OAuthProperties.java



### 2.4. OauthController.java



## 3. 运行方式



## 参考

1. JustAuth 项目地址：https://github.com/zhangyd-c/JustAuth
2. frp内网穿透项目地址：https://github.com/fatedier/frp
3. frp内网穿透官方中文文档：https://github.com/fatedier/frp/blob/master/README_zh.md
4. Frp实现内网穿透：https://zhuanlan.zhihu.com/p/45445979
5. QQ互联文档：http://wiki.connect.qq.com/%E5%87%86%E5%A4%87%E5%B7%A5%E4%BD%9C_oauth2-0
6. 微信开放平台文档：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
7. GitHub第三方登录文档：https://developer.github.com/apps/building-oauth-apps/
8. 谷歌Oauth2文档：https://developers.google.com/identity/protocols/OpenIDConnect
9. 微软Oauth2文档：https://docs.microsoft.com/zh-cn/graph/auth-v2-user
10. 小米开放平台账号服务文档：https://dev.mi.com/console/doc/detail?pId=707



