package com.xkcoding.distributed.lock.autoconfigure;

import com.xkcoding.distributed.lock.aop.DistributedLockAspect;
import com.xkcoding.distributed.lock.api.DistributedLockService;
import com.xkcoding.distributed.lock.api.LockClient;
import com.xkcoding.distributed.lock.api.impl.DistributedLockServiceImpl;
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
    public LockClient lockClient() {
        return new DummyDistributedLockClient();
    }

    @Bean
    public DistributedLockService distributedLockService(LockClient lockClient) {
        return new DistributedLockServiceImpl(lockClient);
    }

    @Bean
    public DistributedLockAspect distributedLockAspect(DistributedLockService distributedLockService) {
        return new DistributedLockAspect(distributedLockService);
    }
}
