package com.xkcoding.cache.redis;

import com.xkcoding.cache.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * <p>
 * redis 基础用法
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-09-07 13:48
 */
@Slf4j
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    /**
     * 测试 Redis 操作
     */
    @Test
    public void get() throws InterruptedException {
        // 测试线程安全，程序结束查看redis中count的值是否为1000
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch wait = new CountDownLatch(1000);
        IntStream.range(0, 1000).forEach(i -> executorService.execute(() -> {
            stringRedisTemplate.opsForValue().increment("count", 1);
            wait.countDown();
        }));
        wait.await();
        log.debug("【count】= {}", stringRedisTemplate.opsForValue().getAndExpire("count", Duration.ofSeconds(10)));

        stringRedisTemplate.opsForValue().set("k1", "v1");
        String k1 = stringRedisTemplate.opsForValue().getAndExpire("k1", Duration.ofSeconds(10));
        log.debug("【k1】= {}", k1);

        // 以下演示整合，具体Redis命令可以参考官方文档
        String key = "xkcoding:user:1";
        redisCacheTemplate.opsForValue().set(key, new User(1L, "user1"));
        // 对应 String（字符串）
        User user = (User) redisCacheTemplate.opsForValue().get(key);
        String userSerialized = stringRedisTemplate.opsForValue().getAndExpire(key, Duration.ofSeconds(10));
        log.debug("【user】= {}", user);
        log.debug("【userSerialized】= {}", userSerialized);
    }
}
