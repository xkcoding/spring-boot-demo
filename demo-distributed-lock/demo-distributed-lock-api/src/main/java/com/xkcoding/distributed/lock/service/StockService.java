package com.xkcoding.distributed.lock.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkcoding.distributed.lock.annotation.DLock;
import com.xkcoding.distributed.lock.mapper.StockMapper;
import com.xkcoding.distributed.lock.model.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 模拟仓库 Service
 *
 * @author yangkai.shen
 * @date 2022-09-02 14:05
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StockService {
    private final StockMapper stockMapper;

    /**
     * 减货物
     */
    @DLock(lockKey = "'lock_stock_'+#stockId", lockTime = 3000, timeUnit = TimeUnit.MILLISECONDS, fastFail = false)
    public void reduceStock(Long stockId) {
        // 先查询库存是否充足
        Stock stock = this.stockMapper.selectById(stockId);

        // 再减库存
        if (stock != null && stock.getCount() > 0) {
            stock.setCount(stock.getCount() - 1);
            this.stockMapper.updateById(stock);
        }
    }

    /**
     * 重置货物
     */
    public void resetStock() {
        log.info("start to init stock data...");

        stockMapper.delete(new QueryWrapper<Stock>().gt("id", 0));

        Stock mockStock = new Stock();
        mockStock.setId(1L);
        mockStock.setName("测试商品");
        mockStock.setCount(5000L);

        stockMapper.insert(mockStock);

        log.info("stock data has been initialized...");
    }
}
