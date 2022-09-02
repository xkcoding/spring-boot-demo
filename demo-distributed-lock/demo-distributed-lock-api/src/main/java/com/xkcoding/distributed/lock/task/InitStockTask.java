package com.xkcoding.distributed.lock.task;

import com.xkcoding.distributed.lock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 初始化数据
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-02 15:35
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InitStockTask implements ApplicationRunner {
    private final StockService stockService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("================================================");
        stockService.resetStock();
        log.info("================================================");
    }
}
