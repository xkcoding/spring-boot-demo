package com.xkcoding.orm.mybatis.generator.repo;

import cn.hutool.core.collection.CollectionUtil;
import com.xkcoding.orm.mybatis.generator.entity.User;
import com.xkcoding.orm.mybatis.generator.entity.UserExample;
import com.xkcoding.orm.mybatis.generator.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qilihui
 * @date 2021/10/11 9:50 下午
 */
@Repository
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserRepo {
    private final UserMapper userMapper;

    public User selectByName(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> userList = userMapper.selectByExample(example);
        if (CollectionUtil.isNotEmpty(userList)) {
            return userList.get(0);
        }
        return null;
    }

    public int insert(User user) {
        return userMapper.insertSelective(user);
    }

    public int updateByNameSelective(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(user.getName());
        return userMapper.updateByExampleSelective(user, example);
    }
}
