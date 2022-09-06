package com.xkcoding.elasticsearch.repository;

import com.xkcoding.elasticsearch.model.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * <p>
 * 用户持久层
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-20 19:00
 */
public interface PersonRepository extends ElasticsearchRepository<Person, Long> {

    /**
     * 根据年龄区间查询
     *
     * @param min 最小值
     * @param max 最大值
     * @return 满足条件的用户列表
     */
    List<Person> findByAgeBetween(Integer min, Integer max);
}
