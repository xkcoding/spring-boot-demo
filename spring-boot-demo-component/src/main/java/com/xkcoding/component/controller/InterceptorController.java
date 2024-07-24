package com.xkcoding.component.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterceptorController {

        //该方法会被拦截
        @GetMapping("/get")
        public String get() {
            System.out.println("进入get方法");
            return "hello Spinrg Boot==>get";
        }
        //该方法会被放行
        @GetMapping("/login")
        public String login() {
            System.out.println("进入login方法");
            return "hello Spinrg Boot==>login";
        }


}
