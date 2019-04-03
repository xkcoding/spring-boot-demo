package com.xkcoding.websocket.model.server;

import cn.hutool.core.util.NumberUtil;

/**
 * <p>
 * 內存相关信息实体
 * </p>
 *
 * @package: com.xkcoding.websocket.model.server
 * @description: 內存相关信息实体
 * @author: yangkai.shen
 * @date: Created in 2018-12-14 16:09
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class Mem {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public double getUsed() {
        return NumberUtil.div(used, (1024 * 1024 * 1024), 2);
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public void setFree(long free) {
        this.free = free;
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }
}