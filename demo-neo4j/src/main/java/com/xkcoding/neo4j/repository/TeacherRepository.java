package com.xkcoding.neo4j.repository;

import com.xkcoding.neo4j.model.Teacher;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * <p>
 * 教师节点Repository
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-24 15:05
 */
public interface TeacherRepository extends Neo4jRepository<Teacher, String> {
}
