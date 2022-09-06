package com.xkcoding.oauth.config;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

/**
 * token 相关配置，jwt 相关.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-09  14:39
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class OauthResourceTokenConfig {

    private final ResourceServerProperties resourceServerProperties;

    /**
     * 这里并不是对令牌的存储,他将访问令牌与身份验证进行转换
     * 在需要 {@link TokenStore} 的任何地方可以使用此方法
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * jwt 令牌转换
     *
     * @return jwt
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        return converter;
    }

    /**
     * 非对称密钥加密，获取 public key。
     * 自动选择加载方式。
     *
     * @return public key
     */
    private String getPubKey() {
        // 如果本地没有密钥，就从授权服务器中获取
        return StringUtils.isEmpty(resourceServerProperties.getJwt().getKeyValue()) ? getKeyFromAuthorizationServer() : resourceServerProperties.getJwt().getKeyValue();
    }

    /**
     * 本地没有公钥的时候，从服务器上获取
     * 需要进行 Basic 认证
     *
     * @return public key
     */
    private String getKeyFromAuthorizationServer() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, encodeClient());
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        String pubKey = new RestTemplate().getForObject(resourceServerProperties.getJwt().getKeyUri(), String.class, requestEntity);
        try {
            JSONObject body = objectMapper.readValue(pubKey, JSONObject.class);
            log.info("Get Key From Authorization Server.");
            return body.getStr("value");
        } catch (IOException e) {
            log.error("Get public key error: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 客户端信息
     *
     * @return basic
     */
    private String encodeClient() {
        return "Basic " + Base64.getEncoder().encodeToString((resourceServerProperties.getClientId() + ":" + resourceServerProperties.getClientSecret()).getBytes());
    }
}
