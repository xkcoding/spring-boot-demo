package com.xkcoding.cache.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户实体
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-15 16:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 2892248514883451461L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
}
