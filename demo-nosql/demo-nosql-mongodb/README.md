# spring-boot-demo-mongodb

> 此 demo 主要演示了 Spring Boot 如何集成 MongoDB，使用官方的 starter 实现增删改查。

## 注意

作者编写本demo时，MongoDB 最新版本为 `4.1`，使用 docker 运行，下面是所有步骤：

1. 下载镜像：`docker pull mongo:4.1`
2. 运行容器：`docker run -d -p 27017:27017 -v /Users/yangkai.shen/docker/mongo/data:/data/db --name mongo-4.1 mongo:4.1`
3. 停止容器：`docker stop mongo-4.1`
4. 启动容器：`docker start mongo-4.1`

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-mongodb</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-mongodb</name>
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
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
        </dependency>

        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <finalName>spring-boot-demo-mongodb</finalName>
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
    mongodb:
      host: localhost
      port: 27017
      database: article_db
logging:
  level:
    org.springframework.data.mongodb.core: debug
```

## Article.java

```java
/**
 * <p>
 * 文章实体类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-28 16:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    /**
     * 文章id
     */
    @Id
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 点赞数量
     */
    private Long thumbUp;

    /**
     * 访客数量
     */
    private Long visits;

}
```

## ArticleRepository.java

```java
/**
 * <p>
 * 文章 Dao
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-28 16:30
 */
public interface ArticleRepository extends MongoRepository<Article, Long> {
    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @return 满足条件的文章列表
     */
    List<Article> findByTitleLike(String title);
}
```

## ArticleRepositoryTest.java

```java
/**
 * <p>
 * 测试操作 MongoDb
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-28 16:35
 */
@Slf4j
public class ArticleRepositoryTest extends SpringBootDemoMongodbApplicationTests {
    @Autowired
    private ArticleRepository articleRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Snowflake snowflake;

    /**
     * 测试新增
     */
    @Test
    public void testSave() {
        Article article = new Article(1L, RandomUtil.randomString(20), RandomUtil.randomString(150), DateUtil.date(), DateUtil
                .date(), 0L, 0L);
        articleRepo.save(article);
        log.info("【article】= {}", JSONUtil.toJsonStr(article));
    }

    /**
     * 测试新增列表
     */
    @Test
    public void testSaveList() {
        List<Article> articles = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            articles.add(new Article(snowflake.nextId(), RandomUtil.randomString(20), RandomUtil.randomString(150), DateUtil
                    .date(), DateUtil.date(), 0L, 0L));
        }
        articleRepo.saveAll(articles);

        log.info("【articles】= {}", JSONUtil.toJsonStr(articles.stream()
                .map(Article::getId)
                .collect(Collectors.toList())));
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        articleRepo.findById(1L).ifPresent(article -> {
            article.setTitle(article.getTitle() + "更新之后的标题");
            article.setUpdateTime(DateUtil.date());
            articleRepo.save(article);
            log.info("【article】= {}", JSONUtil.toJsonStr(article));
        });
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        // 根据主键删除
        articleRepo.deleteById(1L);

        // 全部删除
        articleRepo.deleteAll();
    }

    /**
     * 测试点赞数、访客数，使用save方式更新点赞、访客
     */
    @Test
    public void testThumbUp() {
        articleRepo.findById(1L).ifPresent(article -> {
            article.setThumbUp(article.getThumbUp() + 1);
            article.setVisits(article.getVisits() + 1);
            articleRepo.save(article);
            log.info("【标题】= {}【点赞数】= {}【访客数】= {}", article.getTitle(), article.getThumbUp(), article.getVisits());
        });
    }

    /**
     * 测试点赞数、访客数，使用更优雅/高效的方式更新点赞、访客
     */
    @Test
    public void testThumbUp2() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(1L));
        Update update = new Update();
        update.inc("thumbUp", 1L);
        update.inc("visits", 1L);
        mongoTemplate.updateFirst(query, update, "article");

        articleRepo.findById(1L)
                .ifPresent(article -> log.info("【标题】= {}【点赞数】= {}【访客数】= {}", article.getTitle(), article.getThumbUp(), article
                        .getVisits()));
    }

    /**
     * 测试分页排序查询
     */
    @Test
    public void testQuery() {
        Sort sort = Sort.by("thumbUp", "updateTime").descending();
        PageRequest pageRequest = PageRequest.of(0, 5, sort);
        Page<Article> all = articleRepo.findAll(pageRequest);
        log.info("【总页数】= {}", all.getTotalPages());
        log.info("【总条数】= {}", all.getTotalElements());
        log.info("【当前页数据】= {}", JSONUtil.toJsonStr(all.getContent()
                .stream()
                .map(article -> "文章标题：" + article.getTitle() + "点赞数：" + article.getThumbUp() + "更新时间：" + article.getUpdateTime())
                .collect(Collectors.toList())));
    }

    /**
     * 测试根据标题模糊查询
     */
    @Test
    public void testFindByTitleLike() {
        List<Article> articles = articleRepo.findByTitleLike("更新");
        log.info("【articles】= {}", JSONUtil.toJsonStr(articles));
    }

}
```

## 参考

1. Spring Data MongoDB 官方文档：https://docs.spring.io/spring-data/mongodb/docs/2.1.2.RELEASE/reference/html/
2. MongoDB 官方镜像地址：https://hub.docker.com/_/mongo
3. MongoDB 官方快速入门：https://docs.mongodb.com/manual/tutorial/getting-started/
4. MongoDB 官方文档：https://docs.mongodb.com/manual/
