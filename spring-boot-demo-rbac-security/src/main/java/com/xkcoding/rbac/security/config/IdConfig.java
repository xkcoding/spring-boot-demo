package com.xkcoding.rbac.security.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 雪花主键生成器
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: 雪花主键生成器
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 11:28
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
public class IdConfig {
    /**
     * 雪花生成器
     */
    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }
}
