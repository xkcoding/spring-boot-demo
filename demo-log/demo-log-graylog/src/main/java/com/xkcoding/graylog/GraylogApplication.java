package com.xkcoding.graylog;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * <p>
 * 启动器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-09-05 22:15
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class GraylogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraylogApplication.class, args);
    }

    /**
     * 定时模拟输出日志
     */
    @Scheduled(initialDelay = 5000, fixedRate = 10000)
    public void testLog() {
        log.info("[测试日志]log test, name: {}", RandomUtil.randomString(RandomUtil.randomInt(4, 8)));
        int seed = RandomUtil.randomInt(0, 20);
        if (seed < 5) {
            log.error("[测试日志]log error, {}", seed);
        }
    }

}
