package com.xkcoding.zookeeper.controller;

import cn.hutool.core.lang.Dict;
import com.xkcoding.zookeeper.annotation.LockKeyParam;
import com.xkcoding.zookeeper.annotation.ZooLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 测试Controller
 * </p>
 *
 * @package: com.xkcoding.zookeeper.controller
 * @description: 测试Controller
 * @author: yangkai.shen
 * @date: Created in 2018-12-27 15:51
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping("/buy")
    public Dict buy(int userId) throws InterruptedException {
        log.info("{} 购买中。。。", userId);
        doBuy(userId);
        return Dict.create().set("flag", true).set("msg", "秒杀成功").set("data", userId);
    }

    @ZooLock(key = "buy", timeout = 1, timeUnit = TimeUnit.MINUTES)
    private void doBuy(@LockKeyParam int userId) throws InterruptedException {
        log.info("{} 正在出库。。。", userId);
        TimeUnit.SECONDS.sleep(5);
        log.info("{} 扣库存成功。。。", userId);
    }
}
