package com.xkcoding.shardingsphere.readwrite.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-27 11:56
 */
@Data
@TableName("t_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

}
