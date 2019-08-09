package com.xkcoding.social.config.justauth;

import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Redis作为JustAuth的State的缓存
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-09 14:22
 */
@RequiredArgsConstructor
public class JustAuthRedisStateCache implements AuthStateCache {
    private final RedisTemplate<String, String> redisTemplate;
    private static final long DEF_TIMEOUT = 3 * 60 * 1000;

    /**
     * 存入缓存
     *
     * @param key   缓存key
     * @param value 缓存内容
     */
    @Override
    public void cache(String key, String value) {
        this.cache(key, value, DEF_TIMEOUT);
    }

    /**
     * 存入缓存
     *
     * @param key     缓存key
     * @param value   缓存内容
     * @param timeout 指定缓存过期时间（毫秒）
     */
    @Override
    public void cache(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取缓存内容
     *
     * @param key 缓存key
     * @return 缓存内容
     */
    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 是否存在key，如果对应key的value值已过期，也返回false
     *
     * @param key 缓存key
     * @return true：存在key，并且value没过期；false：key不存在或者已过期
     */
    @Override
    public boolean containsKey(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        if (expire == null) {
            expire = 0L;
        }
        return expire > 0;
    }
}
