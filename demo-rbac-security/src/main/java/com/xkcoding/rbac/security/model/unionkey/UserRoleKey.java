package com.xkcoding.rbac.security.model.unionkey;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * <p>
 * 用户-角色联合主键
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-10 11:20
 */
@Embeddable
@Data
public class UserRoleKey implements Serializable {
    private static final long serialVersionUID = 5633412144183654743L;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;
}
