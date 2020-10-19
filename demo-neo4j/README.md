# spring-boot-demo-neo4j

> 此 demo 主要演示了 Spring Boot 如何集成Neo4j操作图数据库，实现一个校园人物关系网。

## 注意

作者编写本demo时，Neo4j 版本为 `3.5.0`，使用 docker 运行，下面是所有步骤：

1. 下载镜像：`docker pull neo4j:3.5.0`
2. 运行容器：`docker run -d -p 7474:7474 -p 7687:7687 --name neo4j-3.5.0 neo4j:3.5.0`
3. 停止容器：`docker stop neo4j-3.5.0`
4. 启动容器：`docker start neo4j-3.5.0`
5. 浏览器 http://localhost:7474/ 访问 neo4j 管理后台，初始账号/密码 neo4j/neo4j，会要求修改初始化密码，我们修改为 neo4j/admin

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-neo4j</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-neo4j</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.xkcoding</groupId>
        <artifactId>spring-boot-demo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-neo4j</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-neo4j</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

## application.yml

```yaml
spring:
  data:
    neo4j:
      uri: bolt://localhost
      username: neo4j
      password: admin
      open-in-view: false
```

## CustomIdStrategy.java

```java
/**
 * <p>
 * 自定义主键策略
 * </p>
 *
 * @package: com.xkcoding.neo4j.config
 * @description: 自定义主键策略
 * @author: yangkai.shen
 * @date: Created in 2018-12-24 14:40
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class CustomIdStrategy implements IdStrategy {
    @Override
    public Object generateId(Object o) {
        return IdUtil.fastUUID();
    }
}
```

## 部分Model代码

### Student.java

```java
/**
 * <p>
 * 学生节点
 * </p>
 *
 * @package: com.xkcoding.neo4j.model
 * @description: 学生节点
 * @author: yangkai.shen
 * @date: Created in 2018-12-24 14:38
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
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
```

## 部分Repository代码

### StudentRepository.java

```java
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
```

## Neo4jTest.java

```java
/**
 * <p>
 * 测试Neo4j
 * </p>
 *
 * @package: com.xkcoding.neo4j
 * @description: 测试Neo4j
 * @author: yangkai.shen
 * @date: Created in 2018-12-24 15:17
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
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
```

## 截图

运行测试类之后，可以通过访问 http://localhost:7474 ，查看neo里所有节点和关系

![image-20181225150513101](http://static.xkcoding.com/spring-boot-demo/neo4j/063605.jpg)



## 参考

- spring-data-neo4j 官方文档：https://docs.spring.io/spring-data/neo4j/docs/5.1.2.RELEASE/reference/html/
- neo4j 官方文档：https://neo4j.com/docs/getting-started/3.5/