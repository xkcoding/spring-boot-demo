# spring-boot-demo-neo4j

> 此 demo 主要演示了 Spring Boot 如何集成Neo4j操作图数据库，实现一个校园人物关系网。

## 注意

作者编写本demo时，Neo4j 版本为 `3.5.0`，使用 docker 运行，下面是所有步骤：

1. 下载镜像：`docker pull neo4j:3.5.0`
2. 运行容器：`docker run -d -p 7474:7474 -p 7687:7687 --name neo4j-3.5.0 neo4j:3.5.0`
3. 停止容器：`docker stop neo4j-3.5.0`
4. 启动容器：`docker start neo4j-3.5.0`
5. 浏览器 http://localhost:7474/ 访问 neo4j 管理后台，初始账号/密码 neo4j/neo4j，会要求修改初始化密码，我们修改为 neo4j/admin



