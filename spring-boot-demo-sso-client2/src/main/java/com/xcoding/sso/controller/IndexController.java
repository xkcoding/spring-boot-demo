package com.xcoding.sso.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/14 17:25
 * @description
 */
@RestController
public class IndexController {


    @GetMapping("/free")
    public String normal( ) {
        return "不需要授权路径!";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String medium() {
        return "用户权限路径";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin() {
        return "管理员权限路径";
    }
}
