package com.xkcoding.sharding.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkcoding.sharding.jdbc.mapper.OrderMapper;
import com.xkcoding.sharding.jdbc.model.Order;
import com.xkcoding.sharding.jdbc.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * User Service
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.plus.service.impl
 * @description: User Service
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 18:10
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
