package com.xkcoding.distributed.lock.autoconfigure;

import com.xkcoding.distributed.lock.aop.DistributedLockAspect;
import com.xkcoding.distributed.lock.api.DistributedLockClient;
import com.xkcoding.distributed.lock.api.impl.DummyDistributedLockClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 自动装配类
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 21:57
 */
@Configuration(proxyBeanMethods = false)
@MapperScan("com.xkcoding.distributed.lock.mapper")
public class DistributedLockAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DistributedLockClient distributedLockClient() {
        return new DummyDistributedLockClient();
    }

    @Bean
    public DistributedLockAspect distributedLockAspect(DistributedLockClient distributedLockClient) {
        return new DistributedLockAspect(distributedLockClient);
    }
}
