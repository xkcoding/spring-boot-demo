package com.xkcoding.orm.mybatis.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.xkcoding.orm.mybatis.entity.User;
import com.xkcoding.orm.mybatis.mapper.UserMapper;
import com.xkcoding.orm.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(User user) {
        String names = user.getName();
        List<User> paramList=new ArrayList<>();
        List<String> nameList = Arrays.asList(StringUtils.split(names, ","));
        nameList.forEach( name->{
            User   u=new User();
            u.setName(name);
            Random random = new Random();
            int ends = random.nextInt(99);
            u.setEmail("185019666"+ends+"@163.com");
            u.setPassword(SecureUtil.md5("123456" +  IdUtil.fastSimpleUUID()));
            u.setSalt( IdUtil.fastSimpleUUID());
            u.setPhoneNumber("18501966"+ends);
            u.setStatus(1);
            u.setCreateTime(new Date());
            u.setLastUpdateTime(new Date());
            paramList.add(u);
        });
        int i = userMapper.saveBatchUser(paramList);
        log.info("'结果是",i);

    }
}
