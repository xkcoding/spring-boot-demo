package com.xkcoding.dynamic.datasource.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-09-04 16:41
 */
@Data
@Table(name = "test_user")
public class User implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "`name`")
    private String name;
}
