package com.xkcoding.rbac.security.repository;

import com.xkcoding.rbac.security.SpringBootDemoRbacSecurityApplicationTests;
import com.xkcoding.rbac.security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * UserDao 测试
 * </p>
 *
 * @package: com.xkcoding.rbac.security.repository
 * @description: UserDao 测试
 * @author: yangkai.shen
 * @date: Created in 2018-12-12 01:10
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class UserDaoTest extends SpringBootDemoRbacSecurityApplicationTests {
    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsernameIn() {
        List<String> usernameList = Lists.newArrayList("admin", "user");
        List<User> userList = userDao.findByUsernameIn(usernameList);
        Assert.assertEquals(2, userList.size());
        log.info("【userList】= {}", userList);
    }
}