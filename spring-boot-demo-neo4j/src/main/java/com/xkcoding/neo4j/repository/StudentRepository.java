package com.xkcoding.neo4j.repository;

import com.xkcoding.neo4j.model.Student;
import com.xkcoding.neo4j.payload.ClassmateInfoGroupByLesson;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 学生节点Repository
 * </p>
 *
 * @package: com.xkcoding.neo4j.repository
 * @description: 学生节点Repository
 * @author: yangkai.shen
 * @date: Created in 2018-12-24 15:05
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface StudentRepository extends Neo4jRepository<Student, String> {
    /**
     * 根据名称查找学生
     *
     * @param name  姓名
     * @param depth 深度
     * @return 学生信息
     */
    Optional<Student> findByName(String name, @Depth int depth);

    /**
     * 根据班级查询班级人数
     *
     * @param className 班级名称
     * @return 班级人数
     */
    @Query("MATCH (s:Student)-[r:R_STUDENT_OF_CLASS]->(c:Class{name:{className}}) return count(s)")
    Long countByClassName(@Param("className") String className);


    @Query("match (s:Student)-[:R_LESSON_OF_STUDENT]->(l:Lesson),(l:Lesson)<-[:R_LESSON_OF_STUDENT]-(:Student) with l.name as lessonName,collect(distinct s) as students return lessonName,students")
    List<ClassmateInfoGroupByLesson> findByClassmateGroupByLesson();
}
