package com.xkcoding.sharding.jdbc.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @package: com.xkcoding.sharding.jdbc.model
 * @description: 订单表
 * @author: yangkai.shen
 * @date: Created in 2019-03-26 13:35
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "t_order")
public class Order extends Model<Order> {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 备注
     */
    private String remark;
}
