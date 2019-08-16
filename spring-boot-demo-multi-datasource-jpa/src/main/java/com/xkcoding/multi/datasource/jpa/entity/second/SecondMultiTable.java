package com.xkcoding.multi.datasource.jpa.entity.second;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 多数据源测试表
 * </p>
 *
 * @package: com.xkcoding.multi.datasource.jpa.entity.second
 * @description: 多数据源测试表
 * @author: yangkai.shen
 * @date: Created in 2019-01-18 10:06
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Entity
@Table(name = "multi_table")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecondMultiTable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    private String name;
}
