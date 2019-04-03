package com.xkcoding.social.qq.connect;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * <p>
 * ①获取授权码code ②根据code换取令牌accessToken
 * </p>
 *
 * @package: com.xkcoding.social.qq.connect
 * @description: ①获取授权码code ②根据code换取令牌accessToken
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 11:22
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class QQOauth2Template extends OAuth2Template {

    public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);

        // 默认不携带 client_id 和 client_secret，根据文档需要携带这两个参数
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 根据code换取access_token，父类默认处理的是json格式，但是根据QQ文档，格式不是json格式，需要我们重写，自行处理
     *
     * @param accessTokenUrl 换取 access_token 的 URL 地址
     * @param parameters     参数
     * @return {@link AccessGrant}
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String result = getRestTemplate().getForObject(accessTokenUrl, String.class, parameters);

        // result 格式：access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14，文档：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
        log.error("【QQOauth2Template】url = {} ,result = {}", accessTokenUrl, result);
        Map<String, String> data = Splitter.on("&").withKeyValueSeparator("=").split(result);

        String accessToken = data.get("access_token");
        Long expiresIn = Long.valueOf(data.get("expires_in"));
        String refreshToken = data.get("refresh_token");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    /**
     * 父类提供的方法消息转化器没有提供 String 格式的转换，所以这里需要额外加一个
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charsets.UTF_8));
        return restTemplate;
    }
}
