package com.xkcoding.orm.mybatis.generator.repo;

import com.xkcoding.orm.mybatis.generator.SpringBootDemoOrmMybatisGeneratorApplicationTests;
import com.xkcoding.orm.mybatis.generator.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author qilihui
 * @date 2021/10/12 11:09 上午
 */
@Slf4j
public class UserRepoTest extends SpringBootDemoOrmMybatisGeneratorApplicationTests {
    @Autowired
    private UserRepo userRepo;

    /**
     * 根据name查询测试
     */
    @Test
    public void selectByName() {
        User user = userRepo.selectByName("user_1");
        Assert.assertTrue(Objects.nonNull(user));
        log.debug("【user】= {}", user);
    }

    /**
     * insert 测试
     */
    @Test
    public void insert() {
        User user = new User();
        user.setName("user_3");
        user.setPassword("6c6bf02c8d5d3d128f34b1700cb1e32c");
        user.setSalt("fcbdd0e8a9404a5585ea4e01d0e4d7a0");
        user.setEmail("user3@xkcoding.com");
        user.setPhoneNumber("17300000003");
        user.setStatus(1);
        userRepo.insert(user);

        User select = userRepo.selectByName("user_3");
        Assert.assertTrue(Objects.nonNull(select));
        log.debug("【user】= {}", select);
    }

    /**
     * update 测试
     */
    @Test
    public void updateByNameSelective() {
        User user = new User();
        user.setName("user_2");
        user.setStatus(2);
        int update = userRepo.updateByNameSelective(user);
        Assert.assertEquals(update, 1);

        User select = userRepo.selectByName("user_2");
        Assert.assertTrue(Objects.nonNull(select));
        log.debug("【user】= {}", select);
    }
}