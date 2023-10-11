package com.example.demo.security.justauth;


import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 使用JustAuth实现第三方登录
 *
 * @author 860560622@qq.com
 */
@SpringBootApplication
@RestController
@RequestMapping("/oauth")
public class JustAuthController {

    public static void main(String[] args) {
        SpringApplication.run(JustAuthController.class, args);
    }


    /**
     * 获取授权链接并跳转到第三方授权页面
     *
     * @param response response
     * @throws IOException response可能存在的异常
     */
    @RequestMapping("/link/{source}")
    public void linkAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        System.out.println("生成登录链接：" + authRequest.authorize("yourState"));//打印出生成的登录URL
        response.sendRedirect(authorizeUrl);
    }

    /**
     * 第三方平台授权登录后回调到该地址，并携带用户信息
     *
     * @param callback 第三方回调时的input
     * @return 第三方平台的用户信息output
     */
    @RequestMapping("/callback/{source}")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        // 打印返回的授权信息
        System.out.println(callback.getCode());
        return authRequest.login(callback);
    }


    /**
     * 获取授权Request
     *
     * @return AuthRequest
     */
    private AuthRequest getAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId("your id")
                .clientSecret("your secret")
                .redirectUri("your ad")
                .build());
    }

}
