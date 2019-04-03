package com.xkcoding.codegen.entity;

import lombok.Data;

/**
 * <p>
 * 生成配置
 * </p>
 *
 * @package: com.xkcoding.codegen.entity
 * @description: 生成配置
 * @author: yangkai.shen
 * @date: Created in 2019-03-22 09:47
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class GenConfig {
    /**
     * 请求参数
     */
    private TableRequest request;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 作者
     */
    private String author;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 表前缀
     */
    private String tablePrefix;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表备注
     */
    private String comments;
}
