package com.xkcoding.social.qq.connect;

import com.xkcoding.social.qq.api.QQ;
import com.xkcoding.social.qq.api.impl.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * <p>
 * 定义获取授权码地址和获取令牌地址，同时定义QQImpl
 * </p>
 *
 * @package: com.xkcoding.social.qq.connect
 * @description: 定义获取授权码地址和获取令牌地址，同时定义QQImpl
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 13:28
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    private String clientId;

    /**
     * 获取授权码的地址，文档：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
     */
    private static final String URL_FOR_CODE = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 获取token的地址，文档：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
     */
    private static final String URL_FOR_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String clientId, String clientSecret) {
        super(new QQOauth2Template(clientId, clientSecret, URL_FOR_CODE, URL_FOR_ACCESS_TOKEN));
        this.clientId = clientId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, clientId);
    }
}
