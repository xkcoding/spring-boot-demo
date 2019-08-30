package com.xkcoding.social.config.justauth;

import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * <p>
 * JustAuth自动装配
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-08-09 14:21
 */
@Configuration
public class JustAuthConfig {
    /**
     * 默认情况下的模板只能支持RedisTemplate<String, String>，也就是只能存入字符串，因此支持序列化
     */
    @Bean
    public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public AuthStateCache authStateCache(RedisTemplate<String, String> redisCacheTemplate) {
        return new JustAuthRedisStateCache(redisCacheTemplate);
    }
}