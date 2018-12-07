package com.xkcoding.rbac.security.repository;

import com.xkcoding.rbac.security.model.Permission;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * 权限 DAO
 * </p>
 *
 * @package: com.xkcoding.rbac.security.repository
 * @description: 权限 DAO
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 16:21
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface PermissionDao extends JpaRepository<Permission, Long>, Specification<Permission> {
}
