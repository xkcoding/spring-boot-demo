package com.xkcoding.rbac.security.payload;

import lombok.Data;

/**
 * <p>
 * 分页请求参数
 * </p>
 *
 * @package: com.xkcoding.rbac.security.payload
 * @description: 分页请求参数
 * @author: yangkai.shen
 * @date: Created in 2018-12-12 18:05
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class PageCondition {
    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 每页条数
     */
    private Integer pageSize;

}
