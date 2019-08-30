package com.xkcoding.codegen.entity;

import lombok.Data;

/**
 * <p>
 * 列属性： https://blog.csdn.net/lkforce/article/details/79557482
 * </p>
 *
 * @package: com.xkcoding.codegen.entity
 * @description: 列属性： https://blog.csdn.net/lkforce/article/details/79557482
 * @author: yangkai.shen
 * @date: Created in 2019-03-22 09:46
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class ColumnEntity {
    /**
     * 列表
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 备注
     */
    private String comments;
    /**
     * 驼峰属性
     */
    private String caseAttrName;
    /**
     * 普通属性
     */
    private String lowerAttrName;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * 其他信息
     */
    private String extra;
}
