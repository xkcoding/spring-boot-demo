package com.xkcoding.orm.beetlsql.service.impl;

import com.xkcoding.orm.beetlsql.dao.UserDao;
import com.xkcoding.orm.beetlsql.entity.User;
import com.xkcoding.orm.beetlsql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * User Service
 * </p>
 *
 * @package: com.xkcoding.orm.beetlsql.service.impl
 * @description: User Service
 * @author: yangkai.shen
 * @date: Created in 2018/11/14 16:28
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 新增用户
     *
     * @param user 用户
     */
    @Override
    public void saveUser(User user) {

    }

    /**
     * 根据主键删除用户
     *
     * @param id 主键
     */
    @Override
    public void deleteUser(Long id) {

    }

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 更新后的用户
     */
    @Override
    public User updateUser(User user) {
        return null;
    }

    /**
     * 查询单个用户
     *
     * @param id 主键id
     * @return 用户信息
     */
    @Override
    public User getUser(Long id) {
        return userDao.single(id);
    }

    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    @Override
    public List<User> getUserList() {
        return null;
    }

    /**
     * 分页查询
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 分页用户列表
     */
    @Override
    public List<User> getUserByPage(Integer currentPage, Integer pageSize) {
        return null;
    }
}
