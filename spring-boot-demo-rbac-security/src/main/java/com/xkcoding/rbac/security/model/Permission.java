package com.xkcoding.rbac.security.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * 权限
 * </p>
 *
 * @package: com.xkcoding.rbac.security.model
 * @description: 权限
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 16:04
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Entity
@Table(name = "sec_permission")
public class Permission {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址
     */
    private String url;

    /**
     * 权限类型，页面-1，按钮-2
     */
    private Integer type;

    /**
     * 权限表达式
     */
    private String permission;

    /**
     * 后端接口访问方式
     */
    private String method;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Long parentId;
}
