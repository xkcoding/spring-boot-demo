package com.xkcoding.distributed.lock.controller;

import com.xkcoding.distributed.lock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 模拟仓库接口
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-03 00:46
 */
@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StockController {
    private final StockService stockService;

    /**
     * 模拟减库存
     */
    @GetMapping("/reduce")
    public String reduceStock() {
        stockService.reduceStock(1L);
        return "reduce success";
    }

    /**
     * 模拟减库存
     */
    @GetMapping("/reset")
    public String resetStock() {
        stockService.resetStock();
        return "reset success";
    }
}
