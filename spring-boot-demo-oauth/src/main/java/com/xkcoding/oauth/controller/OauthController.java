package com.xkcoding.oauth.controller;

import cn.hutool.core.lang.Dict;
import com.xkcoding.oauth.config.props.OAuthProperties;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthSource;
import me.zhyd.oauth.request.*;
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
                .set("Google登录", "http://oauth.xkcoding.com/demo/oauth/login/google");
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
     * @param code      携带的授权码
     * @return 登录成功后的信息
     */
    @RequestMapping("/{oauthType}/callback")
    public AuthResponse login(@PathVariable String oauthType, String code) {
        AuthRequest authRequest = getAuthRequest(oauthType);
        return authRequest.login(code);
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
            default:
                throw new RuntimeException("暂不支持的第三方登录");
        }
    }

    private AuthRequest getQqAuthRequest() {
        return new AuthQqRequest(properties.getQq());
    }

    private AuthRequest getGithubAuthRequest() {
        return new AuthGithubRequest(properties.getGithub());
    }

    private AuthRequest getWechatAuthRequest() {
        return new AuthWeChatRequest(properties.getWechat());
    }

    private AuthRequest getGoogleAuthRequest() {
        return new AuthGoogleRequest(properties.getGoogle());
    }
}
