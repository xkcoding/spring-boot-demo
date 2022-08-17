package com.xkcoding.admin.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试路由-首页
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-08-17 16:43
 */
@RestController
public class IndexController {
    @GetMapping(value = {"", "/"})
    public String index() {
        return "This is a Spring Boot Admin Client Demo.";
    }
}
