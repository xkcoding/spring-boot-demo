package com.xkcoding.dynamic.datasource.datasource;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 数据源缓存释放调度器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/4 14:42
 */
public enum DatasourceScheduler {
    /**
     * 当前实例
     */
    INSTANCE;

    private AtomicInteger cacheTaskNumber = new AtomicInteger(1);
    private ScheduledExecutorService scheduler;

    DatasourceScheduler() {
        create();
    }

    private void create() {
        this.shutdown();
        this.scheduler = new ScheduledThreadPoolExecutor(10, r -> new Thread(r, String.format("Datasource-Release-Task-%s", cacheTaskNumber.getAndIncrement())));
    }

    private void shutdown() {
        if (null != this.scheduler) {
            this.scheduler.shutdown();
        }
    }

    public void schedule(Runnable task,long delay){
        this.scheduler.scheduleAtFixedRate(task, delay, delay, TimeUnit.MILLISECONDS);
    }

}
