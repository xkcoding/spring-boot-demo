package com.xkcoding.dynamic.datasource.config;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <p>
 * 通用 mapper 自定义 mapper 文件
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/4 16:23
 */
@RegisterMapper
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
