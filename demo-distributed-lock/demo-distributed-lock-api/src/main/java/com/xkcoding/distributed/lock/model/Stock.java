package com.xkcoding.distributed.lock.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 货物
 *
 * @author yangkai.shen
 * @date 2022-09-02 14:07
 */
@Data
@TableName("db_stock")
public class Stock {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 货物名称
     */
    private String name;
    /**
     * 货物总数
     */
    private Long count;
}
