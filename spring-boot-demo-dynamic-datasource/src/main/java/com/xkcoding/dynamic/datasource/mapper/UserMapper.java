package com.xkcoding.dynamic.datasource.mapper;

import com.xkcoding.dynamic.datasource.config.MyMapper;
import com.xkcoding.dynamic.datasource.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/9/4 16:49
 */
@Mapper
public interface UserMapper extends MyMapper<User> {
}
