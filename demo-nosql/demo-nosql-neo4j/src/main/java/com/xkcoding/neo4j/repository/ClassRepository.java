package com.xkcoding.neo4j.repository;

import com.xkcoding.neo4j.model.Class;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

/**
 * <p>
 * 班级节点Repository
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-24 15:05
 */
public interface ClassRepository extends Neo4jRepository<Class, String> {
    /**
     * 根据班级名称查询班级信息
     *
     * @param name 班级名称
     * @return 班级信息
     */
    Optional<Class> findByName(String name);
}
