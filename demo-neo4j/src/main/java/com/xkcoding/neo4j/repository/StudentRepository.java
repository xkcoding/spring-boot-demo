package com.xkcoding.neo4j.repository;

import com.xkcoding.neo4j.model.Student;
import com.xkcoding.neo4j.payload.ClassmateInfoGroupByLesson;
import com.xkcoding.neo4j.payload.TeacherStudent;
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
 * @author yangkai.shen
 * @date Created in 2018-12-24 15:05
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


    /**
     * 查询满足 (学生)-[选课关系]-(课程)-[选课关系]-(学生) 关系的 同学
     *
     * @return 返回同学关系
     */
    @Query("match (s:Student)-[:R_LESSON_OF_STUDENT]->(l:Lesson)<-[:R_LESSON_OF_STUDENT]-(:Student) with l.name as lessonName,collect(distinct s) as students return lessonName,students")
    List<ClassmateInfoGroupByLesson> findByClassmateGroupByLesson();

    /**
     * 查询师生关系，(学生)-[班级学生关系]-(班级)-[班主任关系]-(教师)
     *
     * @return 返回师生关系
     */
    @Query("match (s:Student)-[:R_STUDENT_OF_CLASS]->(:Class)-[:R_BOSS_OF_CLASS]->(t:Teacher) with t.name as teacherName,collect(distinct s) as students return teacherName,students")
    List<TeacherStudent> findTeacherStudentByClass();

    /**
     * 查询师生关系，(学生)-[选课关系]-(课程)-[任教老师关系]-(教师)
     *
     * @return 返回师生关系
     */
    @Query("match ((s:Student)-[:R_LESSON_OF_STUDENT]->(:Lesson)-[:R_TEACHER_OF_LESSON]->(t:Teacher))with t.name as teacherName,collect(distinct s) as students return teacherName,students")
    List<TeacherStudent> findTeacherStudentByLesson();
}
