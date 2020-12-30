# spring-boot-demo-docker

> 本 demo 主要演示了如何容器化一个  Spring Boot 项目。通过 `Dockerfile` 的方式打包成一个 images 。

## Dockerfile

```dockerfile
# 基础镜像
FROM openjdk:8-jdk-alpine

# 作者信息
MAINTAINER "Yangkai.Shen 237497819@qq.com"

# 添加一个存储空间
VOLUME /tmp

# 暴露8080端口
EXPOSE 8080

# 添加变量，如果使用dockerfile-maven-plugin，则会自动替换这里的变量内容
ARG JAR_FILE=target/spring-boot-demo-docker.jar

# 往容器中添加jar包
ADD ${JAR_FILE} app.jar

# 启动镜像自动运行程序
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/app.jar"]
```

## 打包方式

### 手动打包

1. 前往 Dockerfile 目录，打开命令行执行

   ```bash
   $ docker build -t spring-boot-demo-docker .
   ```

2. 查看生成镜像

   ```bash
   $ docker images
   
   REPOSITORY                                                        TAG                 IMAGE ID            CREATED             SIZE
   spring-boot-demo-docker                                           latest	      bc29a29ffca0        2 hours ago         119MB
   openjdk                                                           8-jdk-alpine        97bc1352afde        5 weeks ago         103MB
   ```

3. 运行

   ```bash
   $ docker run -d -p 9090:8080 spring-boot-demo-docker
   ```

###  使用 maven 插件打包

1. pom.xml 中添加插件

2. ```xml
   <properties>
       <dockerfile-version>1.4.9</dockerfile-version>
   </properties>
   
   <plugins>      
       <plugin>
           <groupId>com.spotify</groupId>
           <artifactId>dockerfile-maven-plugin</artifactId>
           <version>${dockerfile-version}</version>
           <configuration>
               <repository>${project.build.finalName}</repository>
               <tag>${project.version}</tag>
               <buildArgs>
                   <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
               </buildArgs>
           </configuration>
           <executions>
               <execution>
                   <id>default</id>
                   <phase>package</phase>
                   <goals>
                       <goal>build</goal>
                   </goals>
               </execution>
           </executions>
       </plugin>
   </plugins>
   ```

2. 执行mvn打包命令，因为插件中 `execution` 节点配置了 package，所以会在打包的时候自动执行 build 命令。

   ```bash
   $ mvn clean package -Dmaven.test.skip=true
   ```

3. 查看镜像

   ```bash
   $ docker images
   
   REPOSITORY                                                        TAG                 IMAGE ID            CREATED             SIZE
   spring-boot-demo-docker                                           1.0.0-SNAPSHOT      bc29a29ffca0        2 hours ago         119MB
   openjdk                                                           8-jdk-alpine        97bc1352afde        5 weeks ago         103MB
   ```

4. 运行

   ```bash
   $ docker run -d -p 9090:8080 spring-boot-demo-docker:1.0.0-SNAPSHOT
   ```

## 参考

- docker 官方文档：https://docs.docker.com/
- Dockerfile 命令，参考文档：https://docs.docker.com/engine/reference/builder/
- maven插件使用，参考地址：https://github.com/spotify/dockerfile-maven