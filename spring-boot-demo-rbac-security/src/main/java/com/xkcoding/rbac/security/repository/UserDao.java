package com.xkcoding.rbac.security.repository;

import com.xkcoding.rbac.security.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * 用户 DAO
 * </p>
 *
 * @package: com.xkcoding.rbac.security.repository
 * @description: 用户 DAO
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 16:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface UserDao extends JpaRepository<User, Long>, Specification<User> {
}
