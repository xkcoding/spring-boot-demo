package com.xkcoding.springbootdemomybatis.mapper;

import com.xkcoding.springbootdemomybatis.model.MybatisUser;
import com.xkcoding.springbootdemomybatis.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface MybatisUserMapper extends MyMapper<MybatisUser> {
	MybatisUser findByName(@Param("name") String name);
}
