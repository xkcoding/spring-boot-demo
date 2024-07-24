package com.example.disconf.controller;

import com.example.disconf.conifg.DisconfConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisconfController {
     @Autowired
     private DisconfConfig  disconfConfig;

    @RequestMapping("getConfig")
    public String  getConfig(){
              return  disconfConfig.getBaseUrl();
    }
}
