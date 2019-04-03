package com.xkcoding.cache.ehcache.service;

import com.xkcoding.cache.ehcache.entity.User;

/**
 * <p>
 * UserService
 * </p>
 *
 * @package: com.xkcoding.cache.ehcache.service
 * @description: UserService
 * @author: yangkai.shen
 * @date: Created in 2018/11/16 16:53
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface UserService {
    /**
     * 保存或修改用户
     *
     * @param user 用户对象
     * @return 操作结果
     */
    User saveOrUpdate(User user);

    /**
     * 获取用户
     *
     * @param id key值
     * @return 返回结果
     */
    User get(Long id);

    /**
     * 删除
     *
     * @param id key值
     */
    void delete(Long id);
}
