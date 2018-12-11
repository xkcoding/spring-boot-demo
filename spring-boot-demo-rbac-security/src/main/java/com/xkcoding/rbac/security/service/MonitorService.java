package com.xkcoding.rbac.security.service;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xkcoding.rbac.security.common.Consts;
import com.xkcoding.rbac.security.common.PageResult;
import com.xkcoding.rbac.security.model.User;
import com.xkcoding.rbac.security.repository.UserDao;
import com.xkcoding.rbac.security.util.RedisUtil;
import com.xkcoding.rbac.security.vo.OnlineUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 监控 Service
 * </p>
 *
 * @package: com.xkcoding.rbac.security.service
 * @description: 监控 Service
 * @author: yangkai.shen
 * @date: Created in 2018-12-12 00:55
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Service
public class MonitorService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserDao userDao;

    /**
     * 在线用户分页列表
     *
     * @param page 当前页
     * @param size 每页条数
     * @return 在线用户分页列表
     */
    public PageResult<OnlineUser> onlineUser(Integer page, Integer size) {
        PageResult<String> keys = redisUtil.findKeysForPage(Consts.REDIS_JWT_KEY_PREFIX + Consts.SYMBOL_STAR, page, size);
        List<String> rows = keys.getRows();
        Long total = keys.getTotal();

        // 根据 redis 中键获取用户名列表
        List<String> usernameList = rows.stream()
                .map(s -> StrUtil.subAfter(s, Consts.REDIS_JWT_KEY_PREFIX, true))
                .collect(Collectors.toList());
        // 根据用户名查询用户信息
        List<User> userList = userDao.findByUsernameIn(usernameList);

        // 封装在线用户信息
        List<OnlineUser> onlineUserList = Lists.newArrayList();
        userList.forEach(user -> onlineUserList.add(OnlineUser.create(user)));

        return new PageResult<>(onlineUserList, total);
    }
}
