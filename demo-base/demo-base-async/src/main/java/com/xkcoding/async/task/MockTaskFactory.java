package com.xkcoding.async.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 模拟任务工厂
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-08-19 21:19
 */
@Component
@Slf4j
public class MockTaskFactory {

    /**
     * 模拟5秒的异步任务
     */
    @Async
    public CompletableFuture<Boolean> asyncTask1() throws InterruptedException {
        doTask("asyncTask1", 5);
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    /**
     * 模拟2秒的异步任务
     */
    @Async
    public CompletableFuture<Boolean> asyncTask2() throws InterruptedException {
        doTask("asyncTask2", 2);
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    /**
     * 模拟3秒的异步任务
     */
    @Async
    public CompletableFuture<Boolean> asyncTask3() throws InterruptedException {
        doTask("asyncTask3", 3);
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    /**
     * 模拟5秒的同步任务
     */
    public void task1() throws InterruptedException {
        doTask("task1", 5);
    }

    /**
     * 模拟2秒的同步任务
     */
    public void task2() throws InterruptedException {
        doTask("task2", 2);
    }

    /**
     * 模拟3秒的同步任务
     */
    public void task3() throws InterruptedException {
        doTask("task3", 3);
    }

    private void doTask(String taskName, Integer time) throws InterruptedException {
        log.info("{}开始执行，当前线程名称【{}】", taskName, Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(time);
        log.info("{}执行成功，当前线程名称【{}】", taskName, Thread.currentThread().getName());
    }
}
