package com.xkcoding.social.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import com.xkcoding.social.props.OAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 第三方登录 Controller
 * </p>
 *
 * @package: com.xkcoding.oauth.controller
 * @description: 第三方登录 Controller
 * @author: yangkai.shen
 * @date: Created in 2019-05-17 10:07
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OauthController {
    private final OAuthProperties properties;

    /**
     * 登录类型
     */
    @GetMapping
    public Dict loginType() {
        return Dict.create().set("QQ登录", "http://oauth.xkcoding.com/demo/oauth/login/qq").set("GitHub登录", "http://oauth.xkcoding.com/demo/oauth/login/github").set("微信登录", "http://oauth.xkcoding.com/demo/oauth/login/wechat").set("Google登录", "http://oauth.xkcoding.com/demo/oauth/login/google").set("Microsoft 登录", "http://oauth.xkcoding.com/demo/oauth/login/microsoft").set("小米登录", "http://oauth.xkcoding.com/demo/oauth/login/mi");
    }

    /**
     * 登录
     *
     * @param oauthType 第三方登录类型
     * @param response  response
     * @throws IOException
     */
    @RequestMapping("/login/{oauthType}")
    public void renderAuth(@PathVariable String oauthType, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(oauthType);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    /**
     * 登录成功后的回调
     *
     * @param oauthType 第三方登录类型
     * @param callback  携带返回的信息
     * @return 登录成功后的信息
     */
    @RequestMapping("/{oauthType}/callback")
    public AuthResponse login(@PathVariable String oauthType, AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest(oauthType);
        AuthResponse response = authRequest.login(callback);
        log.info("【response】= {}", JSONUtil.toJsonStr(response));
        return response;
    }

    private AuthRequest getAuthRequest(String oauthType) {
        AuthSource authSource = AuthSource.valueOf(oauthType.toUpperCase());
        switch (authSource) {
            case QQ:
                return getQqAuthRequest();
            case GITHUB:
                return getGithubAuthRequest();
            case WECHAT:
                return getWechatAuthRequest();
            case GOOGLE:
                return getGoogleAuthRequest();
            case MICROSOFT:
                return getMicrosoftAuthRequest();
            case MI:
                return getMiAuthRequest();
            default:
                throw new RuntimeException("暂不支持的第三方登录");
        }
    }

    private AuthRequest getQqAuthRequest() {
        AuthConfig authConfig = properties.getQq();
        return new AuthQqRequest(authConfig);
    }

    private AuthRequest getGithubAuthRequest() {
        AuthConfig authConfig = properties.getGithub();
        return new AuthGithubRequest(authConfig);
    }

    private AuthRequest getWechatAuthRequest() {
        AuthConfig authConfig = properties.getWechat();
        return new AuthWeChatRequest(authConfig);
    }

    private AuthRequest getGoogleAuthRequest() {
        AuthConfig authConfig = properties.getGoogle();
        return new AuthGoogleRequest(authConfig);
    }

    private AuthRequest getMicrosoftAuthRequest() {
        AuthConfig authConfig = properties.getMicrosoft();
        return new AuthMicrosoftRequest(authConfig);
    }

    private AuthRequest getMiAuthRequest() {
        AuthConfig authConfig = properties.getMi();
        return new AuthMiRequest(authConfig);
    }
}
