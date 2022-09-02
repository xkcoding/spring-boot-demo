package com.xkcoding.admin.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 测试路由-Just for testing
 *
 * @author yangkai.shen
 * @date 2022-08-17 16:51
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        int range = new Random().nextInt(1, 11);
        for (int i = 0; i < range; i++) {
            log.info("#TestController#test: info log total: {}, now is {}", range, (i + 1));
        }
        return "Just for testing";
    }

}
