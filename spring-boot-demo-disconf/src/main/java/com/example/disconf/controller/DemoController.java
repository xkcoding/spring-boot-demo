package com.example.disconf.controller;

import com.example.disconf.conifg.DemoBean;
import com.example.disconf.conifg.JedisConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private JedisConfig jedisConfig;

    @Autowired
    private DemoBean demoBean;

    @RequestMapping("/")
    public String demo(){
        String base_url = demoBean.getBase_url();
        String tenantId = demoBean.getTenantId();
        return   base_url+"  "+tenantId;
    }


    @RequestMapping("getName")
    public String getName(){
        return jedisConfig.getHost();
    }
}
