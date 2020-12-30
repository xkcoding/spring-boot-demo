package com.xkcoding.orm.mybatis.MapperAndPage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @package: com.xkcoding.orm.mybatis.MapperAndPage.entity
 * @description: 用户实体类
 * @author: yangkai.shen
 * @date: Created in 2018/11/8 14:14
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orm_user")
public class User implements Serializable {
    private static final long serialVersionUID = -1840831686851699943L;

    /**
     * 主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

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
    private String phoneNumber;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;

    private int test;
}
