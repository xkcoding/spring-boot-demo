package com.xkcoding.zookeeper;

import com.xkcoding.zookeeper.annotation.LockKeyParam;
import com.xkcoding.zookeeper.annotation.ZooLock;
import com.xkcoding.zookeeper.aspectj.ZooLockAspect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootDemoZookeeperApplicationTests {

    public Integer getCount() {
        return count;
    }

    private Integer count = 10000;
    private ExecutorService executorService = Executors.newFixedThreadPool(1000);

    @Autowired
    private CuratorFramework zkClient;

    /**
     * 不使用分布式锁，程序结束查看count的值是否为0
     */
    @Test
    public void test() throws InterruptedException {
        IntStream.range(0, 10000).forEach(i -> executorService.execute(this::doBuy));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        log.error("count值为{}", count);
    }

    /**
     * 测试AOP分布式锁
     */
    @Test
    public void testAopLock() throws InterruptedException {
        // 测试类中使用AOP需要手动代理
        SpringBootDemoZookeeperApplicationTests target = new SpringBootDemoZookeeperApplicationTests();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        ZooLockAspect aspect = new ZooLockAspect(zkClient);
        factory.addAspect(aspect);
        SpringBootDemoZookeeperApplicationTests proxy = factory.getProxy();
        IntStream.range(0, 10000).forEach(i -> executorService.execute(() -> proxy.aopBuy(i)));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        log.error("count值为{}", proxy.getCount());
    }

    /**
     * 测试AOP分布式锁-动态key
     */
    @Test
    public void testAopLockDynamicKey() throws InterruptedException {
        // 测试类中使用AOP需要手动代理
        SpringBootDemoZookeeperApplicationTests target = new SpringBootDemoZookeeperApplicationTests();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        ZooLockAspect aspect = new ZooLockAspect(zkClient);
        factory.addAspect(aspect);
        SpringBootDemoZookeeperApplicationTests proxy = factory.getProxy();
        IntStream.range(0, 10000).forEach(i -> executorService.execute(() -> proxy.aopBuy(new Goods("asdfasdf"))));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        log.error("count值为{}", proxy.getCount());
    }

    /**
     * 测试手动加锁
     */
    @Test
    public void testManualLock() throws InterruptedException {
        IntStream.range(0, 10000).forEach(i -> executorService.execute(this::manualBuy));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        log.error("count值为{}", count);
    }

    @ZooLock(key = "buy", timeout = 1, timeUnit = TimeUnit.MINUTES)
    public void aopBuy(int userId) {
        log.info("{} 正在出库。。。", userId);
        doBuy();
        log.info("{} 扣库存成功。。。", userId);
    }

    @ZooLock(key = "buy", timeout = 1, timeUnit = TimeUnit.MINUTES)
    public void aopBuy(@LockKeyParam(fields = "skuId") Goods goods) {
        String skuId = goods.getSkuId();
        log.info("{} 正在出库。。。", skuId);
        doBuy();
        log.info("{} 扣库存成功。。。", skuId);
    }

    public void manualBuy() {
        String lockPath = "/buy";
        log.info("try to buy sth.");
        try {
            InterProcessMutex lock = new InterProcessMutex(zkClient, lockPath);
            try {
                if (lock.acquire(1, TimeUnit.MINUTES)) {
                    doBuy();
                    log.info("buy successfully!");
                }
            } finally {
                lock.release();
            }
        } catch (Exception e) {
            log.error("zk error");
        }
    }

    public void doBuy() {
        count--;
        log.info("count值为{}", count);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Goods {
        private String skuId;
    }
}

