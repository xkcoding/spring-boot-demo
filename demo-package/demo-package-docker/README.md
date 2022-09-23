# spring-boot-demo-docker

> 本 demo 主要演示了如何容器化一个  Spring Boot 项目。通过 `Dockerfile` 的方式打包成一个 images 。

## 1.开发步骤

### 1.1.创建一个 HelloWorld 的 SpringBoot 项目

参考 `demo-base-helloworld` 案例即可

### 1.2.创建Dockerfile

```dockerfile
# 多阶段构建
FROM amazoncorretto:17.0.4-alpine3.15 as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
# layertools extract 命令会将 spring boot fatjar 解压成多个 layers
RUN java -Djarmode=layertools -jar application.jar extract


FROM amazoncorretto:17.0.4-alpine3.15
# 作者信息
MAINTAINER "Yangkai.Shen 237497819@qq.com"

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

# 添加一个存储空间
VOLUME /tmp

# 暴露8080端口
EXPOSE 8080
# 入口
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/urandom","org.springframework.boot.loader.JarLauncher"]
```

### 1.2.打包

1. 首先在 spring-boot-demo 根目录下，先执行编译打包 Jar 文件

```shell
$ mvn clean -DskipTests package
```

2. 再进入 `demo-package-docker` 目录，构建镜像

```shell
$ cd demo-package/demo-package-docker
$ docker build -t demo-package-docker:v1 .
```

3. 查看生成镜像

```shell
$ docker images | grep demo
demo-package-docker                                    v3                             58e9b4918f61   19 minutes ago   353MB
demo-package-docker                                    v2                             35303ce1960c   22 minutes ago   351MB
demo-package-docker                                    v1                             7d4a9e953a19   28 minutes ago   351MB
```

4. 测试运行

```shell
$ docker run -p 8080:8080 demo-package-docker:v1
```

> 注意：
> 1.Spring Boot 提供的 maven 插件中已包含 `spring-boot-loader-tools` 依赖，该依赖可以将 SpringBoot FatJar 解压为每个layer，这样在 Docker 多阶段构建的时候，可以让 Docker 复用已存在的 layer，达到加速构建、加速上传、加速下载的目的
> 2.同学们可以通过修改代码、添加依赖等方式重新 build 不同版本的镜像，然后通过 `docker inspect xxx` 命令对比不同版本的 layer，观察输出信息是否存在相同的 layer sha256 值，存在即表示 Docker 已经复用了该 layer

## 2.参考

- [Spring Boot 官方文档之镜像构建](https://docs.spring.io/spring-boot/docs/3.0.0-M5/reference/htmlsingle/#container-images.dockerfiles)
- [Docker 官方文档](https://docs.docker.com/)
- [Dockerfile 命令参考文档](https://docs.docker.com/engine/reference/builder/)
