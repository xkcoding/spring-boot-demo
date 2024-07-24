package com.example.spring.controller;


import com.example.spring.service.RetryableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryableController {

    @Autowired
    private RetryableService retryableService;

    @RequestMapping("retry")
    public String retry(Integer code){
        int test = 0;
        try {
            test = retryableService.test(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return  String.valueOf(test);
    }

}
