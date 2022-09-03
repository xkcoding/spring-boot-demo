package com.xkcoding.distributed.lock.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 基于Curator分布式锁自动装配类
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 13:05
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class CuratorDistributedLockAutoConfiguration {
    @Bean
    public CuratorFramework curatorFramework() {
        // 指数重试
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 创建 Curator
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        // 启动 Curator
        client.start();
        log.info("===========> curator connected <===========");
        return client;
    }

    @Bean
    public CuratorDistributedLockClient distributedLockClient(CuratorFramework curatorFramework) {
        return new CuratorDistributedLockClient(curatorFramework);
    }
}
