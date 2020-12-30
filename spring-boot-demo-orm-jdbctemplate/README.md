# spring-boot-demo-orm-jdbctemplate
> 本 demo 主要演示了Spring Boot如何使用 JdbcTemplate 操作数据库，并且简易地封装了一个通用的 Dao 层，包括增删改查。

## pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
   <modelVersion>4.0.0</modelVersion>

   <artifactId>spring-boot-demo-orm-jdbctemplate</artifactId>
   <version>1.0.0-SNAPSHOT</version>
   <packaging>jar</packaging>

   <name>spring-boot-demo-orm-jdbctemplate</name>
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
         <artifactId>spring-boot-starter-jdbc</artifactId>
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
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
      </dependency>

      <dependency>
         <groupId>cn.hutool</groupId>
         <artifactId>hutool-all</artifactId>
      </dependency>

      <dependency>
         <groupId>org.projectlombok</groupId>
         <artifactId>lombok</artifactId>
         <optional>true</optional>
      </dependency>
   </dependencies>

   <build>
      <finalName>spring-boot-demo-orm-jdbctemplate</finalName>
      <plugins>
         <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
         </plugin>
      </plugins>
   </build>

</project>
```

## BaseDao.java

```java
/**
 * <p>
 * Dao基类
 * </p>
 *
 * @package: com.xkcoding.orm.jdbctemplate.dao.base
 * @description: Dao基类
 * @author: yangkai.shen
 * @date: Created in 2018/10/15 11:28 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class BaseDao<T, P> {
   private JdbcTemplate jdbcTemplate;
   private Class<T> clazz;

   @SuppressWarnings(value = "unchecked")
   public BaseDao(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
      clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
   }

   /**
    * 通用插入，自增列需要添加 {@link Pk} 注解
    *
    * @param t          对象
    * @param ignoreNull 是否忽略 null 值
    * @return 操作的行数
    */
   protected Integer insert(T t, Boolean ignoreNull) {
      String table = getTableName(t);

      List<Field> filterField = getField(t, ignoreNull);

      List<String> columnList = getColumns(filterField);

      String columns = StrUtil.join(Const.SEPARATOR_COMMA, columnList);

      // 构造占位符
      String params = StrUtil.repeatAndJoin("?", columnList.size(), Const.SEPARATOR_COMMA);

      // 构造值
      Object[] values = filterField.stream().map(field -> ReflectUtil.getFieldValue(t, field)).toArray();

      String sql = StrUtil.format("INSERT INTO {table} ({columns}) VALUES ({params})", Dict.create().set("table", table).set("columns", columns).set("params", params));
      log.debug("【执行SQL】SQL：{}", sql);
      log.debug("【执行SQL】参数：{}", JSONUtil.toJsonStr(values));
      return jdbcTemplate.update(sql, values);
   }

   /**
    * 通用根据主键删除
    *
    * @param pk 主键
    * @return 影响行数
    */
   protected Integer deleteById(P pk) {
      String tableName = getTableName();
      String sql = StrUtil.format("DELETE FROM {table} where id = ?", Dict.create().set("table", tableName));
      log.debug("【执行SQL】SQL：{}", sql);
      log.debug("【执行SQL】参数：{}", JSONUtil.toJsonStr(pk));
      return jdbcTemplate.update(sql, pk);
   }

   /**
    * 通用根据主键更新，自增列需要添加 {@link Pk} 注解
    *
    * @param t          对象
    * @param pk         主键
    * @param ignoreNull 是否忽略 null 值
    * @return 操作的行数
    */
   protected Integer updateById(T t, P pk, Boolean ignoreNull) {
      String tableName = getTableName(t);

      List<Field> filterField = getField(t, ignoreNull);

      List<String> columnList = getColumns(filterField);

      List<String> columns = columnList.stream().map(s -> StrUtil.appendIfMissing(s, " = ?")).collect(Collectors.toList());
      String params = StrUtil.join(Const.SEPARATOR_COMMA, columns);

      // 构造值
      List<Object> valueList = filterField.stream().map(field -> ReflectUtil.getFieldValue(t, field)).collect(Collectors.toList());
      valueList.add(pk);

      Object[] values = ArrayUtil.toArray(valueList, Object.class);

      String sql = StrUtil.format("UPDATE {table} SET {params} where id = ?", Dict.create().set("table", tableName).set("params", params));
      log.debug("【执行SQL】SQL：{}", sql);
      log.debug("【执行SQL】参数：{}", JSONUtil.toJsonStr(values));
      return jdbcTemplate.update(sql, values);
   }

   /**
    * 通用根据主键查询单条记录
    *
    * @param pk 主键
    * @return 单条记录
    */
   public T findOneById(P pk) {
      String tableName = getTableName();
      String sql = StrUtil.format("SELECT * FROM {table} where id = ?", Dict.create().set("table", tableName));
      RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);
      log.debug("【执行SQL】SQL：{}", sql);
      log.debug("【执行SQL】参数：{}", JSONUtil.toJsonStr(pk));
      return jdbcTemplate.queryForObject(sql, new Object[]{pk}, rowMapper);
   }

   /**
    * 根据对象查询
    *
    * @param t 查询条件
    * @return 对象列表
    */
   public List<T> findByExample(T t) {
      String tableName = getTableName(t);
      List<Field> filterField = getField(t, true);
      List<String> columnList = getColumns(filterField);

      List<String> columns = columnList.stream().map(s -> " and " + s + " = ? ").collect(Collectors.toList());

      String where = StrUtil.join(" ", columns);
      // 构造值
      Object[] values = filterField.stream().map(field -> ReflectUtil.getFieldValue(t, field)).toArray();

      String sql = StrUtil.format("SELECT * FROM {table} where 1=1 {where}", Dict.create().set("table", tableName).set("where", StrUtil.isBlank(where) ? "" : where));
      log.debug("【执行SQL】SQL：{}", sql);
      log.debug("【执行SQL】参数：{}", JSONUtil.toJsonStr(values));
      List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, values);
      List<T> ret = CollUtil.newArrayList();
      maps.forEach(map -> ret.add(BeanUtil.fillBeanWithMap(map, ReflectUtil.newInstance(clazz), true, false)));
      return ret;
   }

   /**
    * 获取表名
    *
    * @param t 对象
    * @return 表名
    */
   private String getTableName(T t) {
      Table tableAnnotation = t.getClass().getAnnotation(Table.class);
      if (ObjectUtil.isNotNull(tableAnnotation)) {
         return StrUtil.format("`{}`", tableAnnotation.name());
      } else {
         return StrUtil.format("`{}`", t.getClass().getName().toLowerCase());
      }
   }

   /**
    * 获取表名
    *
    * @return 表名
    */
   private String getTableName() {
      Table tableAnnotation = clazz.getAnnotation(Table.class);
      if (ObjectUtil.isNotNull(tableAnnotation)) {
         return StrUtil.format("`{}`", tableAnnotation.name());
      } else {
         return StrUtil.format("`{}`", clazz.getName().toLowerCase());
      }
   }

   /**
    * 获取列
    *
    * @param fieldList 字段列表
    * @return 列信息列表
    */
   private List<String> getColumns(List<Field> fieldList) {
      // 构造列
      List<String> columnList = CollUtil.newArrayList();
      for (Field field : fieldList) {
         Column columnAnnotation = field.getAnnotation(Column.class);
         String columnName;
         if (ObjectUtil.isNotNull(columnAnnotation)) {
            columnName = columnAnnotation.name();
         } else {
            columnName = field.getName();
         }
         columnList.add(StrUtil.format("`{}`", columnName));
      }
      return columnList;
   }

   /**
    * 获取字段列表 {@code 过滤数据库中不存在的字段，以及自增列}
    *
    * @param t          对象
    * @param ignoreNull 是否忽略空值
    * @return 字段列表
    */
   private List<Field> getField(T t, Boolean ignoreNull) {
      // 获取所有字段，包含父类中的字段
      Field[] fields = ReflectUtil.getFields(t.getClass());

      // 过滤数据库中不存在的字段，以及自增列
      List<Field> filterField;
      Stream<Field> fieldStream = CollUtil.toList(fields).stream().filter(field -> ObjectUtil.isNull(field.getAnnotation(Ignore.class)) || ObjectUtil.isNull(field.getAnnotation(Pk.class)));

      // 是否过滤字段值为null的字段
      if (ignoreNull) {
         filterField = fieldStream.filter(field -> ObjectUtil.isNotNull(ReflectUtil.getFieldValue(t, field))).collect(Collectors.toList());
      } else {
         filterField = fieldStream.collect(Collectors.toList());
      }
      return filterField;
   }

}
```

## application.yml

```yaml
server:
  port: 8080
  servlet:
    context-path: /demo
spring:
  datasource:
    url: jdbc:mysql://193.112.94.161:3306/spring-boot-demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8
    username: root
    password: JiangKai@Password123
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
    initialization-mode: always
    continue-on-error: true
    schema:
    - "classpath:db/schema.sql"
    data:
    - "classpath:db/data.sql"
    hikari:
      minimum-idle: 5
      connection-test-query: SELECT 1 FROM DUAL
      maximum-pool-size: 20
      auto-commit: true
      idle-timeout: 30000
      pool-name: SpringBootDemoHikariCP
      max-lifetime: 60000
      connection-timeout: 30000
logging:
  level:
    com.xkcoding: debug
```

## 备注

其余详细代码参见 demo
