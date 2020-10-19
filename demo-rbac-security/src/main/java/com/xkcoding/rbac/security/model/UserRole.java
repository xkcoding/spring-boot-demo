package com.xkcoding.rbac.security.model;

import com.xkcoding.rbac.security.model.unionkey.UserRoleKey;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 * 用户角色关联
 * </p>
 *
 * @package: com.xkcoding.rbac.security.model
 * @description: 用户角色关联
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 11:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Entity
@Table(name = "sec_user_role")
public class UserRole {
    /**
     * 主键
     */
    @EmbeddedId
    private UserRoleKey id;
}
