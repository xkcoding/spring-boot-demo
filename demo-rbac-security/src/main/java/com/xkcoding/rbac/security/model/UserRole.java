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
 * @author yangkai.shen
 * @date Created in 2018-12-10 11:18
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
