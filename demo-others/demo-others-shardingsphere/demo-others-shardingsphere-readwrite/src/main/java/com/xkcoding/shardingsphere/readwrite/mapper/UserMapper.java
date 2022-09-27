package com.xkcoding.shardingsphere.readwrite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkcoding.shardingsphere.readwrite.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * UserMapper
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-27 11:59
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
