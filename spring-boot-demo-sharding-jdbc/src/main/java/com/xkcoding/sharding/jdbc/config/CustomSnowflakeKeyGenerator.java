package com.xkcoding.sharding.jdbc.config;

import cn.hutool.core.lang.Snowflake;
import io.shardingsphere.core.keygen.KeyGenerator;

/**
 * <p>
 * 自定义雪花算法，替换 DefaultKeyGenerator，避免DefaultKeyGenerator生成的id大几率是偶数
 * </p>
 *
 * @package: com.xkcoding.sharding.jdbc.config
 * @description: 自定义雪花算法，替换 DefaultKeyGenerator，避免DefaultKeyGenerator生成的id大几率是偶数
 * @author: yangkai.shen
 * @date: Created in 2019-03-26 17:07
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class CustomSnowflakeKeyGenerator implements KeyGenerator {
    private Snowflake snowflake;

    public CustomSnowflakeKeyGenerator(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

    @Override
    public Number generateKey() {
        return snowflake.nextId();
    }
}
