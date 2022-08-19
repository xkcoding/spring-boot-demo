package com.xkcoding.async.task;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 异步任务单元测试
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-08-19 21:21
 */
@Slf4j
@SpringBootTest
@DisplayName("异步化测试")
public class MockTaskFactoryTest {
    @Autowired
    private MockTaskFactory task;

    /**
     * 测试异步任务
     */
    @Test
    @DisplayName("异步任务")
    public void asyncTaskTest() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        CompletableFuture<Boolean> asyncResult1 = task.asyncTask1();
        CompletableFuture<Boolean> asyncResult2 = task.asyncTask2();
        CompletableFuture<Boolean> asyncResult3 = task.asyncTask3();

        CompletableFuture<Void> allResult = CompletableFuture.allOf(asyncResult1, asyncResult2, asyncResult3);
        // 调用 get() 阻塞主线程
        allResult.get();
        long end = System.currentTimeMillis();

        log.info("异步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }

    /**
     * 测试同步任务
     */
    @Test
    @DisplayName("同步任务")
    public void taskTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        task.task1();
        task.task2();
        task.task3();
        long end = System.currentTimeMillis();

        log.info("同步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }
}
