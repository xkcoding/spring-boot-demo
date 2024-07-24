package com.xkcoding.ratelimit.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redisTest")
public class Test2Controller {

   @Autowired
   private RedisTemplate redisTemplate;

   @RequestMapping("testRedis")
   public void  testRedis(){
       redisTemplate.opsForValue().set("name","lucy");
       String name = (String) redisTemplate.opsForValue().get("name");
       System.out.println(name);

   }


}
