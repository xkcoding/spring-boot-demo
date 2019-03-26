package com.xkcoding.sharding.jdbc;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xkcoding.sharding.jdbc.mapper.OrderMapper;
import com.xkcoding.sharding.jdbc.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <p>
 * 测试sharding-jdbc分库分表
 * </p>
 *
 * @package: com.xkcoding.sharding.jdbc
 * @description: 测试sharding-jdbc分库分表
 * @author: yangkai.shen
 * @date: Created in 2019-03-26 13:44
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoShardingJdbcApplicationTests {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 测试新增
     */
    @Test
    public void testInsert() {
        for (long i = 1; i < 10; i++) {
            for (long j = 1; j < 20; j++) {
                Order order = Order.builder().userId(i).orderId(j).remark(RandomUtil.randomString(20)).build();
                orderMapper.insert(order);
            }
        }
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        Order update = new Order();
        update.setRemark("修改备注信息");
        orderMapper.update(update, Wrappers.<Order>update().lambda().eq(Order::getOrderId, 2).eq(Order::getUserId, 2));
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        orderMapper.delete(new QueryWrapper<>());
    }

    /**
     * 测试查询
     */
    @Test
    public void testSelect() {
        List<Order> orders = orderMapper.selectList(Wrappers.<Order>query().lambda().in(Order::getOrderId, 1, 2));
        log.info("【orders】= {}", JSONUtil.toJsonStr(orders));
    }


}

