package com.xkcoding.social.controller;

import cn.hutool.core.lang.Dict;
import com.xkcoding.social.props.OAuthProperties;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthState;
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
        return Dict.create()
                .set("QQ登录", "http://oauth.xkcoding.com/demo/oauth/login/qq")
                .set("GitHub登录", "http://oauth.xkcoding.com/demo/oauth/login/github")
                .set("微信登录", "http://oauth.xkcoding.com/demo/oauth/login/wechat")
                .set("Google登录", "http://oauth.xkcoding.com/demo/oauth/login/google")
                .set("Microsoft 登录", "http://oauth.xkcoding.com/demo/oauth/login/microsoft")
                .set("小米登录", "http://oauth.xkcoding.com/demo/oauth/login/mi");
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
        response.sendRedirect(authRequest.authorize());
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
        // 移除校验通过的state
        AuthState.delete(oauthType);
        return response;
    }

    private AuthRequest getAuthRequest(String oauthType) {
        AuthSource authSource = AuthSource.valueOf(oauthType.toUpperCase());
        String state = AuthState.create(oauthType);
        switch (authSource) {
            case QQ:
                return getQqAuthRequest(state);
            case GITHUB:
                return getGithubAuthRequest(state);
            case WECHAT:
                return getWechatAuthRequest(state);
            case GOOGLE:
                return getGoogleAuthRequest(state);
            case MICROSOFT:
                return getMicrosoftAuthRequest(state);
            case MI:
                return getMiAuthRequest(state);
            default:
                throw new RuntimeException("暂不支持的第三方登录");
        }
    }

    private AuthRequest getQqAuthRequest(String state) {
        AuthConfig authConfig = properties.getQq();
        authConfig.setState(state);
        return new AuthQqRequest(authConfig);
    }

    private AuthRequest getGithubAuthRequest(String state) {
        AuthConfig authConfig = properties.getGithub();
        authConfig.setState(state);
        return new AuthGithubRequest(authConfig);
    }

    private AuthRequest getWechatAuthRequest(String state) {
        AuthConfig authConfig = properties.getWechat();
        authConfig.setState(state);
        return new AuthWeChatRequest(authConfig);
    }

    private AuthRequest getGoogleAuthRequest(String state) {
        AuthConfig authConfig = properties.getGoogle();
        authConfig.setState(state);
        return new AuthGoogleRequest(authConfig);
    }

    private AuthRequest getMicrosoftAuthRequest(String state) {
        AuthConfig authConfig = properties.getMicrosoft();
        authConfig.setState(state);
        return new AuthMicrosoftRequest(authConfig);
    }

    private AuthRequest getMiAuthRequest(String state) {
        AuthConfig authConfig = properties.getMi();
        authConfig.setState(state);
        return new AuthMiRequest(authConfig);
    }
}
