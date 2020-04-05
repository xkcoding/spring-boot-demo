package com.xkcoding.helloworld

import cn.hutool.core.util.StrUtil
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 *
 *
 * SpringBoot启动类
 *
 *
 * @package: com.xkcoding.helloworld
 * @description: SpringBoot启动类
 * @author: yangkai.shen
 * @date: Created in 2018/9/28 2:49 PM
 * @copyright: Copyright (c)
 * @version: V1.0
 * @modified: yangkai.shen
 */
@SpringBootApplication
@RestController
 class SpringBootDemoHelloworldKotlinApplication {
    /**
     * Hello，World
     *
     * @param who 参数，非必须
     * @return Hello, ${who}
     */
    @GetMapping("/hello")
    fun sayHello(@RequestParam(name = "who") who: String?): String {
        var who = who
        if (who.isNullOrBlank()) {
            who = "World"
        }
        return "hello,$who"
    }
}
fun main() {
    SpringApplication.run(SpringBootDemoHelloworldKotlinApplication::class.java)
}
