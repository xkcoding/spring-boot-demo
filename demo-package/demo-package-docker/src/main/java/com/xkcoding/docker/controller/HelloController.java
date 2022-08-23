package com.xkcoding.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Hello Controller
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-29 14:58
 */
@Slf4j
@RestController
@RequestMapping
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        log.info("[HelloController#hello], receive a request...");
        return "Hello,From Docker!";
    }
}
