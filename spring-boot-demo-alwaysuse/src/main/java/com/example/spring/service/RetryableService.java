package com.example.spring.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.stereotype.Service;


import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class RetryableService {


    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 500, multiplier = 1.5))
    public int test(int code) throws Exception {
        System.out.println("test被调用,时间：" + LocalTime.now());
        HttpResponse execute = HttpRequest.post("https://www.baidu.com/").execute();
        int status = execute.getStatus();
        if (status != 200) {
           throw new Exception("测试失败");
          // return  500;
        }
        System.out.println("test被调用,情况对头了！");

        return 200;
    }
}
