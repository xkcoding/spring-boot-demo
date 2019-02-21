package com.xkcoding.social.qq.api.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xkcoding.social.qq.api.QQ;
import com.xkcoding.social.qq.api.QQUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.util.MultiValueMap;

/**
 * <p>
 * 获取QQ用户信息接口实现
 * </p>
 *
 * @package: com.xkcoding.social.qq.api.impl
 * @description: 获取QQ用户信息接口实现
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 10:57
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private String clientId;

    private String openId;

    /**
     * 获取 openId，文档：http://wiki.connect.qq.com/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7openid_oauth2-0
     */
    private static final String URL_FOR_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取用户信息 URL，文档：http://wiki.connect.qq.com/get_user_info
     */
    private static final String URL_FOR_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%sopenid=%s";

    /**
     * {@link TokenStrategy#ACCESS_TOKEN_PARAMETER} 将 token 封装为参数传递，key为 access_token <br/>
     * {@link TokenStrategy#OAUTH_TOKEN_PARAMETER} 将 token 封装为参数传递，key为 oauth_token <br/>
     * {@link TokenStrategy#AUTHORIZATION_HEADER} 将 token 封装为header传递，key为 Authorization，value为 "Bearer token" 格式<br/>
     *
     * @param accessToken token 由{@link org.springframework.social.oauth2.OAuth2Template#exchangeForAccess(String, String, MultiValueMap)} 返回
     * @param clientId    从配置文件获取
     */
    public QQImpl(String accessToken, String clientId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.clientId = clientId;

        // 根据 token 获取 openId
        String url = String.format(URL_FOR_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        // result 格式：callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} ); 文档：http://wiki.connect.qq.com/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7openid_oauth2-0
        log.error("【QQImpl】url = {} ,result = {}", url, result);
        this.openId = StrUtil.subBetween(result, "\"openid\":\"", "\"}");
    }

    /**
     * 获取QQ用户信息
     *
     * @return QQ用户信息
     */
    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_FOR_USER_INFO, clientId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        log.error("【QQImpl】url = {} ,result = {}", url, result);
        QQUserInfo userInfo = JSONUtil.toBean(result, QQUserInfo.class, true);
        userInfo.setOpenId(openId);
        return userInfo;
    }
}
