package com.xkcoding.neo4j;

import cn.hutool.json.JSONUtil;
import com.xkcoding.neo4j.model.Lesson;
import com.xkcoding.neo4j.model.Student;
import com.xkcoding.neo4j.service.NeoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 测试Neo4j
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-24 15:17
 */
@Slf4j
public class Neo4jTest extends SpringBootDemoNeo4jApplicationTests {
    @Autowired
    private NeoService neoService;

    /**
     * 测试保存
     */
    @Test
    public void testSave() {
        neoService.initData();
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        neoService.delete();
    }

    /**
     * 测试查询 鸣人 学了哪些课程
     */
    @Test
    public void testFindLessonsByStudent() {
        // 深度为1，则课程的任教老师的属性为null
        // 深度为2，则会把课程的任教老师的属性赋值
        List<Lesson> lessons = neoService.findLessonsFromStudent("漩涡鸣人", 2);

        lessons.forEach(lesson -> log.info("【lesson】= {}", JSONUtil.toJsonStr(lesson)));
    }

    /**
     * 测试查询班级人数
     */
    @Test
    public void testCountStudent() {
        Long all = neoService.studentCount(null);
        log.info("【全校人数】= {}", all);
        Long seven = neoService.studentCount("第七班");
        log.info("【第七班人数】= {}", seven);
    }

    /**
     * 测试根据课程查询同学关系
     */
    @Test
    public void testFindClassmates() {
        Map<String, List<Student>> classmates = neoService.findClassmatesGroupByLesson();
        classmates.forEach((k, v) -> log.info("因为一起上了【{}】这门课，成为同学关系的有：{}", k, JSONUtil.toJsonStr(v.stream()
                .map(Student::getName)
                .collect(Collectors.toList()))));
    }

    /**
     * 查询所有师生关系，包括班主任/学生，任课老师/学生
     */
    @Test
    public void testFindTeacherStudent() {
        Map<String, Set<Student>> teacherStudent = neoService.findTeacherStudent();
        teacherStudent.forEach((k, v) -> log.info("【{}】教的学生有 {}", k, JSONUtil.toJsonStr(v.stream()
                .map(Student::getName)
                .collect(Collectors.toList()))));
    }
}
