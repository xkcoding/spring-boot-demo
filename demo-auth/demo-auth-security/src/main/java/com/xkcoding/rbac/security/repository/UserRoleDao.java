package com.xkcoding.rbac.security.repository;

import com.xkcoding.rbac.security.model.UserRole;
import com.xkcoding.rbac.security.model.unionkey.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <p>
 * 用户角色 DAO
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-10 11:24
 */
public interface UserRoleDao extends JpaRepository<UserRole, UserRoleKey>, JpaSpecificationExecutor<UserRole> {

}
