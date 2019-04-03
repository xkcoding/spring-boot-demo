package com.xkcoding.multi.datasource.mybatis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkcoding.multi.datasource.mybatis.mapper.UserMapper;
import com.xkcoding.multi.datasource.mybatis.model.User;
import com.xkcoding.multi.datasource.mybatis.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据服务层 实现
 * </p>
 *
 * @package: com.xkcoding.multi.datasource.mybatis.service.impl
 * @description: 数据服务层 实现
 * @author: yangkai.shen
 * @date: Created in 2019-01-21 14:37
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
@DS("slave")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 类上 {@code @DS("slave")} 代表默认从库，在方法上写 {@code @DS("master")} 代表默认主库
     *
     * @param user 用户
     */
    @DS("master")
    @Override
    public void addUser(User user) {
        baseMapper.insert(user);
    }
}
