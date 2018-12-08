package com.xkcoding.rbac.security.controller;

import com.xkcoding.rbac.security.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 认证 Controller，包括用户注册，用户登录请求
 * </p>
 *
 * @package: com.xkcoding.rbac.security.controller
 * @description: 认证 Controller，包括用户注册，用户登录请求
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 17:23
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * 登录
     */
    @PostMapping("/login")
    public ApiResponse login() {
        return ApiResponse.ofSuccess();
    }
}
