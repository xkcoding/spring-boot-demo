package com.xkcoding.neo4j.payload;

import com.xkcoding.neo4j.model.Student;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * <p>
 * 师生关系
 * </p>
 *
 * @package: com.xkcoding.neo4j.payload
 * @description: 师生关系
 * @author: yangkai.shen
 * @date: Created in 2018-12-24 19:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@QueryResult
public class TeacherStudent {
    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 学生信息
     */
    private List<Student> students;
}
