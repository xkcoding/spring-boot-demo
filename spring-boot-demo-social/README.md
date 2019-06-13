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

服务端搭建在上一步准备的公网服务器上，因为服务器是centos7 x64的系统，因此，这里下载安装包版本为amd64的 [frp_0.27.0_linux_amd64.tar.gz](https://github.com/fatedier/frp/releases/download/v0.27.0/frp_0.27.0_linux_amd64.tar.gz) 。





#### 1.2.2. frp客户端搭建



### 1.3. nginx代理



### 1.4. 第三方平台申请



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



