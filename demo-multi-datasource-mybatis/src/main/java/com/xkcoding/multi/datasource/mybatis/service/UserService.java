package com.xkcoding.multi.datasource.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xkcoding.multi.datasource.mybatis.model.User;

/**
 * <p>
 * 数据服务层
 * </p>
 *
 * @package: com.xkcoding.multi.datasource.mybatis.service
 * @description: 数据服务层
 * @author: yangkai.shen
 * @date: Created in 2019-01-21 14:31
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface UserService extends IService<User> {

    /**
     * 添加 User
     *
     * @param user 用户
     */
    void addUser(User user);
}
