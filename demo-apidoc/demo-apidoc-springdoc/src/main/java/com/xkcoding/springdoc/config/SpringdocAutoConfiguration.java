package com.xkcoding.springdoc.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * Springdoc 基础配置
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-09-06 22:37
 */
@Configuration(proxyBeanMethods = false)
@OpenAPIDefinition(info = @Info(title = "spring-boot-demo-apidoc-swagger", version = "1.0.0-SNAPSHOT", description = "这是一个简单的 Swagger API 演示", contact = @Contact(name = "Yangkai.Shen", url = "https://xkcoding.com", email = "237497819@qq.com")),
    externalDocs = @ExternalDocumentation(description = "springdoc官方文档", url = "https://springdoc.org/"),
    servers = @Server(url = "http://localhost:8080/demo")
)
public class SpringdocAutoConfiguration implements WebMvcConfigurer {

}
