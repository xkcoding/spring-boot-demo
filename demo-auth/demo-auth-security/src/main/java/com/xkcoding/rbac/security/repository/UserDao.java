package com.xkcoding.rbac.security.repository;

import com.xkcoding.rbac.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户 DAO
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-07 16:18
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * 根据用户名、邮箱、手机号查询用户
     *
     * @param username 用户名
     * @param email    邮箱
     * @param phone    手机号
     * @return 用户信息
     */
    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);

    /**
     * 根据用户名列表查询用户列表
     *
     * @param usernameList 用户名列表
     * @return 用户列表
     */
    List<User> findByUsernameIn(List<String> usernameList);
}
