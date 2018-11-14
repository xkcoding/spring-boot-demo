package com.xkcoding.orm.beetlsql.service.impl;

import com.xkcoding.orm.beetlsql.SpringBootDemoOrmBeetlsqlApplicationTests;
import com.xkcoding.orm.beetlsql.entity.User;
import com.xkcoding.orm.beetlsql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.SQLManager;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * User Service测试
 * </p>
 *
 * @package: com.xkcoding.orm.beetlsql.service.impl
 * @description: User Service测试
 * @author: yangkai.shen
 * @date: Created in 2018/11/14 16:30
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class UserServiceImplTest extends SpringBootDemoOrmBeetlsqlApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void saveUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void getUser() {
        User user = userService.getUser(1L);
        Assert.assertNotNull(user);
        log.debug("【user】= {}", user);
    }

    @Test
    public void getUserList() {
    }

    @Test
    public void getUserByPage() {
    }
}