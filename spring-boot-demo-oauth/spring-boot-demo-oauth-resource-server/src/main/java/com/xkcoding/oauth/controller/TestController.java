package com.xkcoding.oauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/9 下午2:37
 */
@RestController
public class TestController {

    /**
     * 拥有 ROLE_ADMIN 的用户才能访问的资源
     *
     * @return ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "ADMIN";
    }

    /**
     * 拥有 ROLE_TEST 的用户才能访问的资源
     *
     * @return TEST
     */
    @PreAuthorize("hasRole('TEST')")
    @GetMapping("/test")
    public String test() {
        return "TEST";
    }

    /**
     * scope 有 READ 的用户资源才能访问
     *
     * @return READ
     */
    @PreAuthorize("#oauth2.hasScope('READ')")
    @GetMapping("/read")
    public String read() {
        return "READ";
    }

    /**
     * scope 有 WRITE 的用户资源才能访问
     *
     * @return WRITE
     */
    @PreAuthorize("#oauth2.hasScope('WRITE')")
    @GetMapping("/write")
    public String write() {
        return "WRITE";
    }

}
