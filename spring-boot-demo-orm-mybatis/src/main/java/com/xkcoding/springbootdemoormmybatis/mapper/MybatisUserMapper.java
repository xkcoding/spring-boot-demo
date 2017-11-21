package com.xkcoding.springbootdemoormmybatis.mapper;

import com.xkcoding.springbootdemoormmybatis.model.MybatisUser;
import com.xkcoding.springbootdemoormmybatis.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface MybatisUserMapper extends MyMapper<MybatisUser> {
	MybatisUser findByName(@Param("name") String name);
}
