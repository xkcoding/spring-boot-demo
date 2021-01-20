package com.xkcoding.oauth.service;

import com.xkcoding.oauth.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020-01-06 15:44
 */
public interface SysUserService extends UserDetailsService {
    /**
     * 查询所有用户
     *
     * @return 用户
     */
    List<SysUser> findAll();

    /**
     * 通过 id 查询用户
     *
     * @param id id
     * @return 用户
     */
    SysUser findById(Long id);

    /**
     * 创建用户
     *
     * @param sysUser 用户
     */
    void createUser(SysUser sysUser);

    /**
     * 更新用户
     *
     * @param sysUser 用户
     */
    void updateUser(SysUser sysUser);

    /**
     * 更新用户 密码
     *
     * @param id       用户 id
     * @param password 用户密码
     */
    void updatePassword(Long id, String password);

    /**
     * 删除用户.
     *
     * @param id id
     */
    void deleteUser(Long id);
}
