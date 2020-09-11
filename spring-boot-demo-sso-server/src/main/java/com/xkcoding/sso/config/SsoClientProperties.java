package com.xkcoding.sso.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/14 17:47
 * @description
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sso")
public class SsoClientProperties {

    private List<SsoClient> client;

    @Data
    static class SsoClient{

        private String clientName;

        private String clientPassword;

        private String uri;

    }
}
