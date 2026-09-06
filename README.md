<h1 align="center"><a href="https://github.com/xkcoding" target="_blank">Spring Boot Demo</a></h1>
<p align="center">
  <a href="https://travis-ci.com/xkcoding/spring-boot-demo"><img alt="Travis-CI" src="https://travis-ci.com/xkcoding/spring-boot-demo.svg?branch=master"/></a>
  <a href="https://www.codacy.com/app/xkcoding/spring-boot-demo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=xkcoding/spring-boot-demo&amp;utm_campaign=Badge_Grade"><img alt="Codacy" src="https://api.codacy.com/project/badge/Grade/1f2e3d437b174bfc943dae1600332ec1"/></a>
  <a href="https://xkcoding.com"><img alt="author" src="https://img.shields.io/badge/author-Yangkai.Shen-blue.svg"/></a>
  <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8.0_162-orange.svg"/></a>
  <a href="https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/html/"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.1.0.RELEASE-brightgreen.svg"/></a>
  <a href="https://github.com/xkcoding/spring-boot-demo/blob/master/LICENSE"><img alt="LICENSE" src="https://img.shields.io/github/license/xkcoding/spring-boot-demo.svg"/></a>
</p>

<p align="center">
  <a href="https://github.com/xkcoding/spring-boot-demo/stargazers"><img alt="star" src="https://img.shields.io/github/stars/xkcoding/spring-boot-demo.svg?label=Stars&style=social"/></a>
  <a href="https://github.com/xkcoding/spring-boot-demo/network/members"><img alt="star" src="https://img.shields.io/github/forks/xkcoding/spring-boot-demo.svg?label=Fork&style=social"/></a>
  <a href="https://github.com/xkcoding/spring-boot-demo/watchers"><img alt="star" src="https://img.shields.io/github/watchers/xkcoding/spring-boot-demo.svg?label=Watch&style=social"/></a>
</p>

<p align="center">
  <span>中文 | <a href="./README.en.md">English</a></span>
</p>

## 项目简介

`spring boot demo` 是一个用来深度学习并实战 `spring boot` 的项目，目前总共包含 **`66`** 个集成demo，已经完成 **`55`** 个。

该项目已成功集成 actuator(`监控`)、admin(`可视化监控`)、logback(`日志`)、aopLog(`通过AOP记录web请求日志`)、统一异常处理(`json级别和页面级别`)、freemarker(`模板引擎`)、thymeleaf(`模板引擎`)、Beetl(`模板引擎`)、Enjoy(`模板引擎`)、JdbcTemplate(`通用JDBC操作数据库`)、JPA(`强大的ORM框架`)、mybatis(`强大的ORM框架`)、通用Mapper(`快速操作Mybatis`)、PageHelper(`通用的Mybatis分页插件`)、mybatis-plus(`快速操作Mybatis`)、BeetlSQL(`强大的ORM框架`)、upload(`本地文件上传和七牛云文件上传`)、redis(`缓存`)、ehcache(`缓存`)、email(`发送各种类型邮件`)、task(`基础定时任务`)、quartz(`动态管理定时任务`)、xxl-job(`分布式定时任务`)、swagger(`API接口管理测试`)、security(`基于RBAC的动态权限认证`)、SpringSession(`Session共享`)、Zookeeper(`结合AOP实现分布式锁`)、RabbitMQ(`消息队列`)、Kafka(`消息队列`)、websocket(`服务端推送监控服务器运行信息`)、socket.io(`聊天室`)、ureport2(`中国式报表`)、打包成`war`文件、集成 ElasticSearch(`基本操作和高级查询`)、Async(`异步任务`)、集成Dubbo(`采用官方的starter`)、MongoDB(`文档数据库`)、neo4j(`图数据库`)、docker(`容器化`)、`JPA多数据源`、`Mybatis多数据源`、`代码生成器`、GrayLog(`日志收集`)、JustAuth(`第三方登录`)、LDAP(`增删改查`)、`动态添加/切换数据源`、单机限流(`AOP + Guava RateLimiter`)、分布式限流(`AOP + Redis + Lua`)、ElasticSearch 7.x(`使用官方 Rest High Level Client`)、HTTPS、Flyway(`数据库初始化`)、UReport2(`中国式复杂报表`)。

> 如果大家还有想要集成的demo，也可在 [issue](https://github.com/xkcoding/spring-boot-demo/issues/new) 里提需求。我会额外添加在 [TODO](./TODO.md) 列表里。✊

## 分支介绍

- master 分支：基于 Spring Boot 版本 `2.1.0.RELEASE`，每个 Module 的 parent 依赖根目录下的 pom.xml，主要用于管理每个 Module 的通用依赖版本，方便大家学习。
- v-1.5.x 分支：基于 Spring Boot 版本 `1.5.8.RELEASE`，每个 Module 均依赖 spring-boot-demo-parent，有挺多同学们反映这种方式对新手不是很友好，运行起来有些难度，因此 ***此分支(v-1.5.x)会停止开发维护*** ，所有内容会慢慢以 master 分支的形式同步过去，此分支暂未完成的，也会直接在 master 分支上加，在此分支学习的同学们，仍然可以在此分支学习，但是建议后期切换到master分支，会更加容易，毕竟官方已经将 Spring Boot 升级到 2.x 版本。🙂

## 开发环境

- **JDK 1.8 +**
- **Maven 3.5 +**
- **IntelliJ IDEA ULTIMATE 2018.2 +** (*注意：务必使用 IDEA 开发，同时保证安装 `lombok` 插件*)
- **Mysql 5.7 +** (*尽量保证使用 5.7 版本以上，因为 5.7 版本加了一些新特性，同时不向下兼容。本 demo 里会尽量避免这种不兼容的地方，但还是建议尽量保证 5.7 版本以上*)

## 运行方式

> 提示：如果是 fork 的朋友，同步代码的请参考：https://xkcoding.com/2018/09/18/how-to-update-the-fork-project.html

1. `git clone https://github.com/xkcoding/spring-boot-demo.git`
2. 使用 IDEA 打开 clone 下来的项目
3. 在 IDEA 中 Maven Projects 的面板导入项目根目录下 的 `pom.xml` 文件
4. Maven Projects 找不到的童鞋，可以勾上 IDEA 顶部工具栏的 View -> Tool Buttons ，然后 Maven Projects 的面板就会出现在 IDEA 的右侧
5. 找到各个 Module 的 Application 类就可以运行各个 demo 了
6. **`注意：每个 demo 均有详细的 README 配套，食用 demo 前记得先看看哦~`**
7. **`注意：运行各个 demo 之前，有些是需要事先初始化数据库数据的，亲们别忘记了哦~`**

## 项目趋势

[![Stargazers over time](https://starchart.cc/xkcoding/spring-boot-demo.svg)](https://starchart.cc/xkcoding/spring-boot-demo)

## 其他

###  团队纳新

组内招人啦，HC 巨多，Base 杭州，感兴趣的小伙伴，查看 [岗位详情](./jd.md)

### 开源推荐

![11628591293_.pic_hd](https://static.aliyun.xkcoding.com/2021/08/14/11628591293pichd.jpg?x-oss-process=style/tag_compress)

- `JustAuth`：史上最全的整合第三方登录的开源库，https://github.com/justauth/JustAuth
- `Mica`：SpringBoot 微服务高效开发工具集，https://github.com/lets-mica/mica
- `awesome-collector`：https://github.com/P-P-X/awesome-collector
- `SpringBlade`：完整的线上解决方案(企业开发必备)，https://github.com/chillzhuang/SpringBlade
- `Pig`：宇宙最强微服务认证授权脚手架(架构师必备)，https://github.com/pigxcloud/pig

### 开发计划

查看 [TODO](./TODO.md) 文件

### 各 Module 介绍

| Module 名称                                                  | Module 介绍                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [demo-helloworld](./demo-helloworld)                         | spring-boot 的一个 helloworld                                |
| [demo-properties](./demo-properties)                         | spring-boot 读取配置文件中的内容                             |
| [demo-actuator](./demo-actuator)                             | spring-boot 集成 spring-boot-starter-actuator 用于监控 spring-boot 的启动和运行状态 |
| [demo-admin-client](./demo-admin/admin-client)               | spring-boot 集成 spring-boot-admin 来可视化的监控 spring-boot 程序的运行状态，可以与 actuator 互相搭配使用，客户端示例 |
| [demo-admin-server](./demo-admin/admin-server)               | spring-boot 集成 spring-boot-admin 来可视化的监控 spring-boot 程序的运行状态，可以与 actuator 互相搭配使用，服务端示例 |
| [demo-logback](./demo-logback)                               | spring-boot 集成 logback 日志                                |
| [demo-log-aop](./demo-log-aop)                               | spring-boot 使用 AOP 切面的方式记录 web 请求日志             |
| [demo-exception-handler](./demo-exception-handler)           | spring-boot 统一异常处理，包括2种，第一种返回统一的 json 格式，第二种统一跳转到异常页面 |
| [demo-template-freemarker](./demo-template-freemarker)       | spring-boot 集成 Freemarker 模板引擎                         |
| [demo-template-thymeleaf](./demo-template-thymeleaf)         | spring-boot 集成 Thymeleaf 模板引擎                          |
| [demo-template-beetl](./demo-template-beetl)                 | spring-boot 集成 Beetl 模板引擎                              |
| [demo-template-enjoy](./demo-template-enjoy)                 | spring-boot 集成 Enjoy 模板引擎                              |
| [demo-orm-jdbctemplate](./demo-orm-jdbctemplate)             | spring-boot 集成 Jdbc Template 操作数据库，并简易封装通用 Dao 层 |
| [demo-orm-jpa](./demo-orm-jpa)                               | spring-boot 集成 spring-boot-starter-data-jpa 操作数据库     |
| [demo-orm-mybatis](./demo-orm-mybatis)                       | spring-boot 集成原生mybatis，使用 [mybatis-spring-boot-starter](https://github.com/mybatis/spring-boot-starter) 集成 |
| [demo-orm-mybatis-mapper-page](./demo-orm-mybatis-mapper-page) | spring-boot 集成[通用Mapper](https://github.com/abel533/Mapper)和[PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)，使用 [mapper-spring-boot-starter](https://github.com/abel533/Mapper/tree/master/spring-boot-starter) 和 [pagehelper-spring-boot-starter](https://github.com/pagehelper/pagehelper-spring-boot) 集成 |
| [demo-orm-mybatis-plus](./demo-orm-mybatis-plus)             | spring-boot 集成 [mybatis-plus](https://mybatis.plus/)，使用 [mybatis-plus-boot-starter](http://mp.baomidou.com/) 集成，集成 BaseMapper、BaseService、ActiveRecord 操作数据库 |
| [demo-orm-beetlsql](./demo-orm-beetlsql)                     | spring-boot 集成 [beetl-sql](http://ibeetl.com/guide/#beetlsql)，使用 [beetl-framework-starter](http://ibeetl.com/guide/#beetlsql) 集成 |
| [demo-upload](./demo-upload)                                 | spring-boot 文件上传示例，包含本地文件上传以及七牛云文件上传 |
| [demo-cache-redis](./demo-cache-redis)                       | spring-boot 整合 redis，操作redis中的数据，并使用redis缓存数据 |
| [demo-cache-ehcache](./demo-cache-ehcache)                   | spring-boot 整合 ehcache，使用 ehcache 缓存数据              |
| [demo-email](./demo-email)                                   | spring-boot 整合 email，包括发送简单文本邮件、HTML邮件（包括模板HTML邮件）、附件邮件、静态资源邮件 |
| [demo-task](./demo-task)                                     | spring-boot 快速实现定时任务                                 |
| [demo-task-quartz](./demo-task-quartz)                       | spring-boot 整合 quartz，并实现对定时任务的管理，包括新增定时任务，删除定时任务，暂停定时任务，恢复定时任务，修改定时任务启动时间，以及定时任务列表查询，`提供前端页面` |
| [demo-task-xxl-job](./demo-task-xxl-job)                     | spring-boot 整合[xxl-job](http://www.xuxueli.com/xxl-job/en/#/)，并提供绕过 `xxl-job-admin` 对定时任务的管理的方法，包括定时任务列表，触发器列表，新增定时任务，删除定时任务，停止定时任务，启动定时任务，修改定时任务，手动触发定时任务 |
| [demo-swagger](./demo-swagger)                               | spring-boot 集成原生的 `swagger` 用于统一管理、测试 API 接口 |
| [demo-swagger-beauty](./demo-swagger-beauty)                 | spring-boot 集成第三方 `swagger` [swagger-bootstrap-ui](https://github.com/xiaoymin/Swagger-Bootstrap-UI) 美化API文档样式，用于统一管理、测试 API 接口 |
| [demo-rbac-security](./demo-rbac-security)                   | spring-boot 集成 spring security 完成基于RBAC权限模型的权限管理，支持自定义过滤请求，动态权限认证，使用 JWT 安全认证，支持在线人数统计，手动踢出用户等操作 |
| [demo-rbac-shiro](./demo-rbac-shiro)                         | spring-boot 集成 shiro 实现权限管理<br /> <span style="color:pink;">待完成</span> |
| [demo-session](./demo-session)                               | spring-boot 集成 Spring Session 实现Session共享、重启程序Session不失效 |
| [demo-oauth](./demo-oauth)                                   | spring-boot 实现 oauth 服务器功能，实现授权码机制<br /> <span style="color:pink;">待完成</span> |
| [demo-social](./demo-social)                                 | spring-boot 集成第三方登录，集成 `justauth-spring-boot-starter` 实现QQ登录、GitHub登录、微信登录、谷歌登录、微软登录、小米登录、企业微信登录。 |
| [demo-zookeeper](./demo-zookeeper)                           | spring-boot 集成 Zookeeper 结合AOP实现分布式锁               |
| [demo-mq-rabbitmq](./demo-mq-rabbitmq)                       | spring-boot 集成 RabbitMQ 实现基于直接队列模式、分列模式、主题模式、延迟队列的消息发送和接收 |
| [demo-mq-rocketmq](./demo-mq-rocketmq)                       | spring-boot 集成 RocketMQ，实现消息的发送和接收<br /> <span style="color:pink;">待完成</span> |
| [demo-mq-kafka](./demo-mq-kafka)                             | spring-boot 集成 kafka，实现消息的发送和接收                 |
| [demo-websocket](./demo-websocket)                           | spring-boot 集成 websocket，后端主动推送前端服务器运行信息   |
| [demo-websocket-socketio](./demo-websocket-socketio)         | spring-boot 使用 netty-socketio 集成 websocket，实现一个简单的聊天室 |
| [demo-ureport2](./demo-ureport2)                             | spring-boot 集成 ureport2 实现复杂的自定义的中国式报表<br /> <span style="color:pink;">待完成</span> |
| [demo-uflo](./demo-uflo)                                     | spring-boot 集成 uflo 快速实现轻量级流程引擎<br /> <span style="color:pink;">待完成</span> |
| [demo-urule](./demo-urule)                                   | spring-boot 集成 urule 快速实现规则引擎<br /> <span style="color:pink;">待完成</span> |
| [demo-activiti](./demo-activiti)                             | spring-boot 集成 activiti 7  流程引擎<br /> <span style="color:pink;">待完成</span> |
| [demo-async](./demo-async)                                   | spring-boot 使用原生提供的异步任务支持，实现异步执行任务     |
| [demo-war](./demo-war)                                       | spring-boot 打成 war 包的配置                                |
| [demo-elasticsearch](./demo-elasticsearch)                   | spring-boot 集成 ElasticSearch，集成 `spring-boot-starter-data-elasticsearch` 完成对 ElasticSearch 的高级使用技巧，包括创建索引、配置映射、删除索引、增删改查基本操作、复杂查询、高级查询、聚合查询等 |
| [demo-dubbo](./demo-dubbo)                                   | spring-boot 集成 Dubbo，分别为公共模块 `spring-boot-demo-dubbo-common`、服务提供方`spring-boot-demo-dubbo-provider`、服务调用方`spring-boot-demo-dubbo-consumer` |
| [demo-mongodb](./demo-mongodb)                               | spring-boot 集成 MongoDB，使用官方的 starter 实现增删改查    |
| [demo-neo4j](./demo-neo4j)                                   | spring-boot 集成 Neo4j 图数据库，实现一个校园人物关系网的demo |
| [demo-docker](./demo-docker)                                 | spring-boot 容器化                                           |
| [demo-multi-datasource-jpa](./demo-multi-datasource-jpa)     | spring-boot 使用JPA集成多数据源                              |
| [demo-multi-datasource-mybatis](./demo-multi-datasource-mybatis) | spring-boot 使用Mybatis集成多数据源，使用 Mybatis-Plus 提供的开源解决方案实现 |
| [demo-sharding-jdbc](./demo-sharding-jdbc)                   | spring-boot 使用 `sharding-jdbc` 实现分库分表，同时ORM采用 Mybatis-Plus |
| [demo-tio](./demo-tio)                                       | spring-boot 集成 tio 网络编程框架<br /> <span style="color:pink;">待完成</span> |
| demo-grpc                                                    | spring-boot 集成grpc，配置tls/ssl，参见[ISSUE#5](https://github.com/xkcoding/spring-boot-demo/issues/5)<br /> <span style="color:pink;">待完成</span> |
| [demo-codegen](./demo-codegen)                               | spring-boot 集成 velocity 模板技术实现的代码生成器，简化开发 |
| [demo-graylog](./demo-graylog)                               | spring-boot 集成 graylog 实现日志统一收集                    |
| demo-sso                                                     | spring-boot 集成 SSO 单点登录，参见 [ISSUE#12](https://github.com/xkcoding/spring-boot-demo/issues/12)<br /> <span style="color:pink;">待完成</span> |
| [demo-ldap](./demo-ldap)                                     | spring-boot 集成 LDAP，集成 `spring-boot-starter-data-ldap` 完成对 Ldap 的基本 CURD操作, 并给出以登录为实战的 API 示例，参见 [ISSUE#23](https://github.com/xkcoding/spring-boot-demo/issues/23)，感谢 [@fxbin](https://github.com/fxbin) |
| [demo-dynamic-datasource](./demo-dynamic-datasource)         | spring-boot 动态添加数据源、动态切换数据源                   |
| [demo-ratelimit-guava](./demo-ratelimit-guava)               | spring-boot 使用 Guava RateLimiter 实现单机版限流，保护 API  |
| [demo-ratelimit-redis](./demo-ratelimit-redis)               | spring-boot 使用 Redis + Lua 脚本实现分布式限流，保护 API    |
| [demo-https](./demo-https)                                   | spring-boot 集成 HTTPS                                       |
| [demo-elasticsearch-rest-high-level-client](./demo-elasticsearch-rest-high-level-client) | spring boot 集成 ElasticSearch 7.x 版本，使用官方 Rest High Level Client 操作 ES 数据 |
| [demo-flyway](./demo-flyway)                                 | spring boot 集成 Flyway，项目启动时初始化数据库表结构，同时支持数据库脚本版本控制 |
| [demo-ureport2](./demo-ureport2)                             | spring boot 集成 Ureport2，实现中国式复杂报表设计            |


### 特别感谢

- 感谢 [七牛云](https://portal.qiniu.com/signup?utm_source=kaiyuan&utm_media=xkcoding) 提供的免费云存储与 CDN 加速支持
- 感谢史上最牛的代码生成插件 [MyBatisCodeHelper-Pro](https://gejun123456.github.io/MyBatisCodeHelper-Pro/#/?id=mybatiscodehelper-pro) 提供的永久激活码
- <a href="https://www.jetbrains.com/?from=spring-boot-demo"><img src="http://static.xkcoding.com/spring-boot-demo/064312.jpg" width="100px" alt="jetbrains">**感谢 JetBrains 提供的免费开源 License**</a>

### License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2018 Yangkai.Shen


## 🌐 Web Resources & Interactive Index
- [MERGE RACER STUNTS CAR](https://themindzone.pages.dev/merge-racer-stunts-car.html)
- [CATEGORY ANIMAL](https://quizverses.github.io/category-animal.html)
- [TEACHER SIMULATOR](https://studyquests.pages.dev/teacher-simulator.html)
- [BUBBLE BLASTERS](https://quizverses.github.io/bubble-blasters.html)
- [TIC TAC TOE MATCH THREE](https://studyplayings.web.app/tic-tac-toe-match-three.html)
- [BLOCK PUZZLE SLIDE BLOCK JAM](https://quizverses.github.io/block-puzzle-slide-block-jam.html)
- [QUACKVENTURE](https://studyquesthub.web.app/quackventure.html)
- [21 CARDS](https://quizverses-9d2f2.web.app/21-cards.html)
- [PIRATE NOOB APOCALYPSE](https://studyquests.github.io/pirate-noob-apocalypse.html)
- [TEACHER SIMULATOR](https://studyplayings.pages.dev/teacher-simulator.html)
- [CANNON SHOOTER](https://studyplayings.web.app/cannon-shooter.html)
- [TINY BAKER OCEAN JELLY CAKE](https://learnquester.github.io/tiny-baker-ocean-jelly-cake.html)
- [GUIVOIO](https://studyquests.github.io/guivoio.html)
- [CATEGORY LOVE](https://thelearnquester.web.app/category-love.html)
- [DINO SIMULATOR CITY ATTACK](https://learnquester.github.io/dino-simulator-city-attack.html)
- [CATEGORY FASHION](https://studyplayings.pages.dev/category-fashion.html)
- [RELAX MINI GAMES COLLECTION](https://studyplaying.github.io/relax-mini-games-collection.html)
- [CATEGORY ROGUELIKE38](https://studyplayings.pages.dev/category-roguelike38.html)
- [DINOSAUR CARDS](https://quizverses-9d2f2.web.app/dinosaur-cards.html)
- [CRAZY VAN](https://studyplayings.pages.dev/crazy-van.html)
- [HAPPY JUMP](https://studyquests.github.io/happy-jump.html)
- [CANDY BATTLE SWEET SURVIVORS](https://studyplaying.github.io/candy-battle-sweet-survivors.html)
- [SANTA GO](https://studyplaying.github.io/santa-go.html)
- [CATEGORY MERGE GAMES](https://studyquests.pages.dev/category-merge-games.html)
- [CATEGORY BASKETBALL](https://studyquests.pages.dev/category-basketball.html)
- [CATEGORY GOGUARDIAN](https://quizverses-9d2f2.web.app/category-goguardian.html)
- [CUT N FILL](https://studyquests.github.io/cut-n-fill.html)
- [PERFECT CAKE MAKER](https://studyquests.github.io/perfect-cake-maker.html)
- [HOME ISLAND](https://learnquester.github.io/home-island.html)
- [PYRAMID JEWELS](https://quizverses-9d2f2.web.app/pyramid-jewels.html)
- [SUSHI PUZZLE](https://studyplaying.github.io/sushi-puzzle.html)
- [HIDDEN OBJECT EMILYS CASE](https://studyquests.github.io/hidden-object-emilys-case.html)
- [CATEGORY PARKOUR55](https://studyplayings.web.app/category-parkour55.html)
- [POOL BUBBLES](https://quizverses.github.io/pool-bubbles.html)
- [DUCK HUNTING OPEN SEASON](https://studyquests.github.io/duck-hunting-open-season.html)
- [QUIZ X](https://quizverses.github.io/quiz-x.html)
- [RESTAURANT VIP MASTERCHEF](https://studyquests.github.io/restaurant-vip-masterchef.html)
- [TOW N GO](https://quizverses.github.io/tow-n-go.html)
- [ANNOYING BOSS PUNCH GAME](https://studyquests.github.io/annoying-boss-punch-game.html)
- [INDEX21](https://quizverses-9d2f2.web.app/index21.html)
- [SIMON SUPER RABBIT](https://studyplayings.pages.dev/simon-super-rabbit.html)
- [PATH ICE](https://studyquesthub.web.app/path-ice.html)
- [TILE LIVING](https://quizverses.github.io/tile-living.html)
- [ONLINE PORTAL](https://thelearnquester.web.app/)
- [SUDOKU BRAIN BLOCKS](https://studyplayings.pages.dev/sudoku-brain-blocks.html)
- [SPIDER NOOB OBSTACLE COURSE](https://quizverses.github.io/spider-noob-obstacle-course.html)
- [MUSIC CAT PIANO TILES GAME 3D](https://studyquests.github.io/music-cat-piano-tiles-game-3d.html)
- [SUPER BITCOIN BOY](https://studyquests.github.io/super-bitcoin-boy.html)
- [CHICKEN BANANA QUEST](https://studyquests.github.io/chicken-banana-quest.html)
- [MAGIC BEAUTY MAKEUP](https://quizverses.github.io/magic-beauty-makeup.html)
- [ENERGY CLICKER](https://studyquests.github.io/energy-clicker.html)
- [PET ME MAZE](https://quizverses.github.io/pet-me-maze.html)
- [DIGIT SHOOTER](https://quizverses.github.io/digit-shooter.html)
- [FRUIT MERGE JUICY DROP GAME](https://quizverses.github.io/fruit-merge-juicy-drop-game.html)
- [AROUND ELBRUS](https://quizverses.github.io/around-elbrus.html)
- [RAGDOLL MEGA DUNK](https://studyplaying.github.io/ragdoll-mega-dunk.html)
- [BARRY PRISON HIDE AND SEEK](https://studyquests.github.io/barry-prison-hide-and-seek.html)
- [MUSTANG CITY DRIVER](https://studyplayings.web.app/mustang-city-driver.html)
- [SITEMAP](https://cryptotify.pages.dev/sitemap.html)
- [MOTO STUNTS DRIVING RACING](https://studyquesthub.web.app/moto-stunts-driving-racing.html)
- [ARROW CUBE ESCAPE](https://studyquests.github.io/arrow-cube-escape.html)
- [VALENTINE S DAY COUPLE DATE](https://studyplayings.pages.dev/valentine-s-day-couple-date.html)
- [AYLA WORLD PRINCESS LIFE](https://studyplaying.github.io/ayla-world-princess-life.html)
- [CATEGORY PUZZLE 3](https://learnquester.github.io/category-puzzle-3.html)
- [CATEGORY LOGIC538](https://learnquester.github.io/category-logic538.html)
- [AIRPORT MASTER PLANE TYCOON](https://quizverses.github.io/airport-master-plane-tycoon.html)
- [RACING IN CITY](https://studyquests.github.io/racing-in-city.html)
- [MATCH MASTER](https://studyquesthub.web.app/match-master.html)
- [JUMP BALL CLASSIC](https://studyplaying.github.io/jump-ball-classic.html)
- [WORDS OR DIE](https://studyplaying.github.io/words-or-die.html)
- [HEAD JUMP](https://quizverses.github.io/head-jump.html)
- [BLOCKAPOLYPSE ZOMBIE SHOOTER](https://studyquesthub.web.app/blockapolypse-zombie-shooter.html)
- [MAX CRUSHER CRAZY DESTRUCTION AND CAR CRASHES](https://studyquests.github.io/max-crusher-crazy-destruction-and-car-crashes.html)
- [V AND N PIZZA COOKING GAME](https://studyquests.github.io/v-and-n-pizza-cooking-game.html)
- [CATEGORY SNAKE40](https://studyquesthub.web.app/category-snake40.html)
- [CROSS THE ROAD](https://studyquesthub.web.app/cross-the-road.html)
- [BUBBLE SHOOTER VALENTINE](https://studyquests.github.io/bubble-shooter-valentine.html)
- [BOMBER BATTLE ARENA](https://quizverses-9d2f2.web.app/bomber-battle-arena.html)
- [SHOTTING BALLS](https://quizverses.github.io/shotting-balls.html)
- [INDEX12](https://studyquesthub.web.app/index12.html)
- [PIECE OF CAKE MERGE AND BAKE](https://studyquesthub.web.app/piece-of-cake-merge-and-bake.html)
- [IMAGE CROSSWORD](https://studyquests.github.io/image-crossword.html)
- [CUTE ANIMAL WORLD](https://quizverses.github.io/cute-animal-world.html)
- [STACK FALL](https://studyplaying.github.io/stack-fall.html)
- [CATEGORY 2D1 060](https://studyquesthub.web.app/category-2d1-060.html)
- [BARRY PRISON CHRISTMAS ADVENTURE](https://studyplayings.web.app/barry-prison-christmas-adventure.html)
- [CRYSTAL CONNECT](https://learnquester.github.io/crystal-connect.html)
- [HEAT INCREMENTAL](https://studyquests.github.io/heat-incremental.html)
- [BUBBLE BALL](https://studyplayings.web.app/bubble-ball.html)
- [CATEGORY PIXEL313](https://quizverses.github.io/category-pixel313.html)
- [CAR PAINT](https://studyquests.github.io/car-paint.html)
- [WATERMELON MERGE](https://studyplaying.github.io/watermelon-merge.html)
- [ONLINE PORTAL](https://cryptotify.netlify.app/)
- [LAST PLAY RAGDOLL SANDBOX KQB](https://studyquests.github.io/last-play-ragdoll-sandbox-kqb.html)
- [CYBERPUNK AGENT](https://studyplayings.pages.dev/cyberpunk-agent.html)
- [DESTRUCTION OF STICKMAN ZOMBIE](https://studyquests.github.io/destruction-of-stickman-zombie.html)
- [SWORD AND SPIN](https://studyquests.github.io/sword-and-spin.html)
- [CATEGORY CAR 3](https://quizverses.github.io/category-car-3.html)
- [RUN 3D](https://studyquests.github.io/run-3d.html)
- [CATEGORY DIRT BIKE](https://quizverses.github.io/category-dirt-bike.html)
- [WORLDGUESSR](https://studyquesthub.web.app/worldguessr.html)
- [CATEGORY COOKING46](https://studyquesthub.web.app/category-cooking46.html)
- [YOUTUBER MCRAFT 2PLAYER](https://studyquests.github.io/youtuber-mcraft-2player.html)
- [CATEGORY SANDBOX](https://studyplayings.web.app/category-sandbox.html)
- [BATTLEDUDES IO](https://studyquests.github.io/battledudes-io.html)
- [LITTLE BUGS](https://quizverses.github.io/little-bugs.html)
- [CATEGORY CLASSIC97](https://quizverses.github.io/category-classic97.html)
- [INDEX6](https://studyquesthub.web.app/index6.html)
- [HELIX CRUSH](https://quizverses-9d2f2.web.app/helix-crush.html)
- [CATEGORY COOKING](https://quizverses.github.io/category-cooking.html)
- [NOOB JAILBREAK 2](https://studyplaying.github.io/noob-jailbreak-2.html)
- [SEEK FIND](https://studyquests.github.io/seek-find.html)
- [TOWER DEFENDERS](https://quizverses-9d2f2.web.app/tower-defenders.html)
- [CATEGORY BUSINESS135](https://studyquests.pages.dev/category-business135.html)
- [TERMS](https://cryptotify.pages.dev/terms.html)
- [COUNT AND BOUNCE](https://studyplayings.pages.dev/count-and-bounce.html)
- [CATEGORY FIGHTING](https://quizverses.github.io/category-fighting.html)
- [VENETIAN LOVE AFFAIR](https://studyquests.github.io/venetian-love-affair.html)
- [ROCKET FEST](https://studyplaying.github.io/rocket-fest.html)
- [NATURAL DISASTER SURVIVAL OBBY](https://learnquester.github.io/natural-disaster-survival-obby.html)
- [TILEMAN IO](https://studyplaying.github.io/tileman-io.html)
- [FLIGHT PILOT AIRPLANE GAMES 24](https://studyplayings.web.app/flight-pilot-airplane-games-24.html)
- [CATEGORY BOARDGAMES](https://studyquesthub.web.app/category-boardgames.html)
- [PRIVACY](https://brainquests.vercel.app/privacy.html)
- [INDEX20](https://quizverses-9d2f2.web.app/index20.html)
- [CATEGORY UNBLOCKERS](https://studyquests.github.io/category-unblockers.html)
- [OCEAN POP](https://studyplaying.github.io/ocean-pop.html)
- [MERRY CHRISTMAS CONNECT](https://studyplayings.pages.dev/merry-christmas-connect.html)
- [ARROW SURVIVAL 15 SECONDS](https://learnquester.github.io/arrow-survival-15-seconds.html)
- [SID GINNY Y2K GLAM CLASH](https://studyplaying.github.io/sid-ginny-y2k-glam-clash.html)
