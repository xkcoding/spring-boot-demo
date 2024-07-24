package com.xkcoding.component.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff/info/")
public class CommonFilterController {

    @RequestMapping("getTest")
    public String getTest(){
        return  "过滤";
    }

    @RequestMapping("/fq")
    public String fq(){
        return  "放行";
    }

}
