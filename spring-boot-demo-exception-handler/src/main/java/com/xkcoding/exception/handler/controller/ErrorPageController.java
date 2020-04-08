package com.xkcoding.exception.handler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 错误页面路由
 *
 * @author FYT
 * @since 2020/4/7
 */
@Controller
public class ErrorPageController {

    @GetMapping(value = "/4xx")
    public String resourcesNotFound(Model model) {
        model.addAttribute("default", "4xx 页面默认值");
        return "4xx";
    }

    @GetMapping(value = "/5xx")
    public String internalError() {
        return "5xx";
    }

}
