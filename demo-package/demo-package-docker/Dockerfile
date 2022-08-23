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
