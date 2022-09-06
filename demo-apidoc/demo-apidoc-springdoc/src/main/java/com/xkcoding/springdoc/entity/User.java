package com.xkcoding.springdoc.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 测试用户
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2022-09-06 22:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User", title = "用户实体", description = "User Entity")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 5057954049311281252L;
    /**
     * 主键id
     */
    @Schema(title = "主键id", required = true)
    private Integer id;
    /**
     * 用户名
     */
    @Schema(title = "用户名", required = true)
    private String name;
    /**
     * 工作岗位
     */
    @Schema(title = "工作岗位", required = true)
    private String job;
}
