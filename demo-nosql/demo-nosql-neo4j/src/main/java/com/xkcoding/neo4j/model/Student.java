package com.xkcoding.neo4j.model;

import com.xkcoding.neo4j.config.CustomIdStrategy;
import com.xkcoding.neo4j.constants.NeoConsts;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * <p>
 * 学生节点
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-24 14:38
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Builder
@NodeEntity
public class Student {
    /**
     * 主键，自定义主键策略，使用UUID生成
     */
    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    /**
     * 学生姓名
     */
    @NonNull
    private String name;

    /**
     * 学生选的所有课程
     */
    @Relationship(NeoConsts.R_LESSON_OF_STUDENT)
    @NonNull
    private List<Lesson> lessons;

    /**
     * 学生所在班级
     */
    @Relationship(NeoConsts.R_STUDENT_OF_CLASS)
    @NonNull
    private Class clazz;

}
