# spring-boot-demo-codegen

> 此 demo 主要演示了 Spring Boot 使用**模板技术**生成代码，并提供前端页面，可生成 Entity/Mapper/Service/Controller 等代码。

## 1. 主要功能

1. 使用 `velocity` 代码生成
2. 暂时支持mysql数据库的代码生成
3. 提供前端页面展示，并下载代码压缩包

> 注意：① Entity里使用lombok，简化代码 ② Mapper 和 Service 层集成 Mybatis-Plus 简化代码

## 2. 运行

1. 运行 `SpringBootDemoCodegenApplication` 启动项目
2. 打开浏览器，输入 http://localhost:8080/demo/index.html
3. 输入查询条件，生成代码

## 3. 关键代码

### 3.1. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-boot-demo-codegen</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-demo-codegen</name>
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
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-undertow</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
        </dependency>

        <!--velocity代码生成使用模板 -->
        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity</artifactId>
            <version>1.7</version>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-text</artifactId>
            <version>1.6</version>
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
        <finalName>spring-boot-demo-codegen</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 3.2. 代码生成器配置

```properties
#代码生成器，配置信息
mainPath=com.xkcoding
#包名
package=com.xkcoding
moduleName=generator
#作者
author=Yangkai.Shen
#表前缀(类名不会包含表前缀)
tablePrefix=tb_
#类型转换，配置信息
tinyint=Integer
smallint=Integer
mediumint=Integer
int=Integer
integer=Integer
bigint=Long
float=Float
double=Double
decimal=BigDecimal
bit=Boolean
char=String
varchar=String
tinytext=String
text=String
mediumtext=String
longtext=String
date=LocalDateTime
datetime=LocalDateTime
timestamp=LocalDateTime
```

### 3.3. CodeGenUtil.java

```java
/**
 * <p>
 * 代码生成器   工具类
 * </p>
 *
 * @package: com.xkcoding.codegen.utils
 * @description: 代码生成器   工具类
 * @author: yangkai.shen
 * @date: Created in 2019-03-22 09:27
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@UtilityClass
public class CodeGenUtil {

    private final String ENTITY_JAVA_VM = "Entity.java.vm";
    private final String MAPPER_JAVA_VM = "Mapper.java.vm";
    private final String SERVICE_JAVA_VM = "Service.java.vm";
    private final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";
    private final String CONTROLLER_JAVA_VM = "Controller.java.vm";
    private final String MAPPER_XML_VM = "Mapper.xml.vm";
    private final String API_JS_VM = "api.js.vm";

    private List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Mapper.java.vm");
        templates.add("template/Mapper.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");

        templates.add("template/api.js.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public void generatorCode(GenConfig genConfig, Entity table, List<Entity> columns, ZipOutputStream zip) {
        //配置信息
        Props props = getConfig();
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.getStr("tableName"));

        if (StrUtil.isNotBlank(genConfig.getComments())) {
            tableEntity.setComments(genConfig.getComments());
        } else {
            tableEntity.setComments(table.getStr("tableComment"));
        }

        String tablePrefix;
        if (StrUtil.isNotBlank(genConfig.getTablePrefix())) {
            tablePrefix = genConfig.getTablePrefix();
        } else {
            tablePrefix = props.getStr("tablePrefix");
        }

        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), tablePrefix);
        tableEntity.setCaseClassName(className);
        tableEntity.setLowerClassName(StrUtil.lowerFirst(className));

        //列信息
        List<ColumnEntity> columnList = Lists.newArrayList();
        for (Entity column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.getStr("columnName"));
            columnEntity.setDataType(column.getStr("dataType"));
            columnEntity.setComments(column.getStr("columnComment"));
            columnEntity.setExtra(column.getStr("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setCaseAttrName(attrName);
            columnEntity.setLowerAttrName(StrUtil.lowerFirst(attrName));

            //列的数据类型，转换成Java类型
            String attrType = props.getStr(columnEntity.getDataType(), "unknownType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.getStr("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getCaseClassName());
        map.put("classname", tableEntity.getLowerClassName());
        map.put("pathName", tableEntity.getLowerClassName().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("datetime", DateUtil.now());
        map.put("year", DateUtil.year(new Date()));

        if (StrUtil.isNotBlank(genConfig.getComments())) {
            map.put("comments", genConfig.getComments());
        } else {
            map.put("comments", tableEntity.getComments());
        }

        if (StrUtil.isNotBlank(genConfig.getAuthor())) {
            map.put("author", genConfig.getAuthor());
        } else {
            map.put("author", props.getStr("author"));
        }

        if (StrUtil.isNotBlank(genConfig.getModuleName())) {
            map.put("moduleName", genConfig.getModuleName());
        } else {
            map.put("moduleName", props.getStr("moduleName"));
        }

        if (StrUtil.isNotBlank(genConfig.getPackageName())) {
            map.put("package", genConfig.getPackageName());
            map.put("mainPath", genConfig.getPackageName());
        } else {
            map.put("package", props.getStr("package"));
            map.put("mainPath", props.getStr("mainPath"));
        }
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template, tableEntity.getCaseClassName(), map
                        .get("package")
                        .toString(), map.get("moduleName").toString()))));
                IoUtil.write(zip, StandardCharsets.UTF_8, false, sw.toString());
                IoUtil.close(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    private String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    private String tableToJava(String tableName, String tablePrefix) {
        if (StrUtil.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    private Props getConfig() {
        Props props = new Props("generator.properties");
        props.autoLoad(true);
        return props;
    }

    /**
     * 获取文件名
     */
    private String getFileName(String template, String className, String packageName, String moduleName) {
        // 包路径
        String packagePath = GenConstants.SIGNATURE + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        // 资源路径
        String resourcePath = GenConstants.SIGNATURE + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
        // api路径
        String apiPath = GenConstants.SIGNATURE + File.separator + "api" + File.separator;

        if (StrUtil.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains(ENTITY_JAVA_VM)) {
            return packagePath + "entity" + File.separator + className + ".java";
        }

        if (template.contains(MAPPER_JAVA_VM)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains(SERVICE_JAVA_VM)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(CONTROLLER_JAVA_VM)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains(MAPPER_XML_VM)) {
            return resourcePath + "mapper" + File.separator + className + "Mapper.xml";
        }

        if (template.contains(API_JS_VM)) {
            return apiPath + className.toLowerCase() + ".js";
        }

        return null;
    }
}
```

### 3.4. 其余代码参见demo

## 4. 演示

<video id="video" controls="" preload="none">
      <source id="mp4" src="https://static.xkcoding.com/code/spring-boot-demo/codegen/codegen.mp4" type="video/mp4">
      <p>您的浏览器版本过低，不支持播放视频演示</p>
    </video>

## 5. 参考

- [基于人人开源 自动构建项目_V1](https://qq343509740.gitee.io/2018/12/20/%E7%AC%94%E8%AE%B0/%E8%87%AA%E5%8A%A8%E6%9E%84%E5%BB%BA%E9%A1%B9%E7%9B%AE/%E5%9F%BA%E4%BA%8E%E4%BA%BA%E4%BA%BA%E5%BC%80%E6%BA%90%20%E8%87%AA%E5%8A%A8%E6%9E%84%E5%BB%BA%E9%A1%B9%E7%9B%AE_V1/)

- [Mybatis-Plus代码生成器](https://mybatis.plus/guide/generator.html#%E6%B7%BB%E5%8A%A0%E4%BE%9D%E8%B5%96)

