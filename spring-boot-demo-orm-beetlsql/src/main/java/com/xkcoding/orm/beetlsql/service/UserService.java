package com.xkcoding.orm.beetlsql.service;

import com.xkcoding.orm.beetlsql.entity.User;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * <p>
 * User Service
 * </p>
 *
 * @package: com.xkcoding.orm.beetlsql.service
 * @description: User Service
 * @author: yangkai.shen
 * @date: Created in 2018/11/14 16:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface UserService {
    /**
     * 新增用户
     *
     * @param user 用户
     * @return 保存的用户
     */
    User saveUser(User user);


    /**
     * 批量插入用户
     *
     * @param users 用户列表
     */
    void saveUserList(List<User> users);

    /**
     * 根据主键删除用户
     *
     * @param id 主键
     */
    void deleteUser(Long id);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 更新后的用户
     */
    User updateUser(User user);

    /**
     * 查询单个用户
     *
     * @param id 主键id
     * @return 用户信息
     */
    User getUser(Long id);

    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    List<User> getUserList();

    /**
     * 分页查询
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 分页用户列表
     */
    PageQuery<User> getUserByPage(Integer currentPage, Integer pageSize);
}
