package com.xkcoding.orm.jpa.entity;

import com.xkcoding.orm.jpa.entity.base.AbstractAuditModel;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.entity
 * @description: 用户实体类
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 14:06
 * @copyright: Copyright (c)
 * @version: V1.0
 * @modified: yangkai.shen
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orm_user")
@ToString(callSuper = true)
public class User extends AbstractAuditModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 用户名
     */
    private String name;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 加密使用的盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;
}
