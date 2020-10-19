package com.xkcoding.codegen.entity;

import lombok.Data;

/**
 * <p>
 * 表格请求参数
 * </p>
 *
 * @package: com.xkcoding.codegen.entity
 * @description: 表格请求参数
 * @author: yangkai.shen
 * @date: Created in 2019-03-22 10:24
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class TableRequest {
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * jdbc-前缀
     */
    private String prepend;
    /**
     * jdbc-url
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 表名
     */
    private String tableName;
}
