package com.xkcoding.rbac.security.repository;

import com.xkcoding.rbac.security.model.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * 角色 DAO
 * </p>
 *
 * @package: com.xkcoding.rbac.security.repository
 * @description: 角色 DAO
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 16:20
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface RoleDao extends JpaRepository<Role, Long>, Specification<Role> {
}
