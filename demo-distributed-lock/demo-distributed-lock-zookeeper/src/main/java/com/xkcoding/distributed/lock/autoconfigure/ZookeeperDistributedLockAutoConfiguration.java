package com.xkcoding.distributed.lock.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 基于 Zookeeper 分布式锁自动装配类
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-05 19:36
 */
@Configuration(proxyBeanMethods = false)
public class ZookeeperDistributedLockAutoConfiguration {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ZkClient zkClient() {
        return new ZkClient("127.0.0.1:2181");
    }

    @Bean
    public ZookeeperDistributedLockClient distributedLockClient(ZkClient zkClient) {
        return new ZookeeperDistributedLockClient(zkClient);
    }
}
