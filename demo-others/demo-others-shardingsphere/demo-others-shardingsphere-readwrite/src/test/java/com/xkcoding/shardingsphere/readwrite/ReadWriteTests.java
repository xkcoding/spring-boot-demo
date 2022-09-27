package com.xkcoding.shardingsphere.readwrite;

import cn.hutool.core.util.RandomUtil;
import com.xkcoding.shardingsphere.readwrite.entity.User;
import com.xkcoding.shardingsphere.readwrite.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 读写分离测试
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-27 13:26
 */
@SpringBootTest
public class ReadWriteTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername(RandomUtil.randomString(5));
        userMapper.insert(user);
    }

    @Test
    public void testSelect() {
        // 测试负载均衡
        for (int i = 0; i < 6; i++) {
            List<User> users = userMapper.selectList(null);
            Optional.ofNullable(users).ifPresent(x -> x.forEach(System.out::println));
        }
    }

    /**
     * 开启事务之后，读写操作均在 master 上
     */
    @Test
    @Transactional
    public void testTransactional() {
        User user = new User();
        user.setUsername(RandomUtil.randomString(5));
        userMapper.insert(user);

        List<User> users = userMapper.selectList(null);
        Optional.ofNullable(users).ifPresent(x -> x.forEach(System.out::println));
    }

}
