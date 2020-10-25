package com.xkcoding.docker.controller;

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
@RestController
@RequestMapping
public class HelloController {
    @GetMapping
    public String hello() {
        return "Hello,From Docker!";
    }
}
