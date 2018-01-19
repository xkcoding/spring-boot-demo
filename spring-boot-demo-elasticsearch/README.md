# spring-boot-demo-elasticsearch

依赖 [spring-boot-demo-parent](../spring-boot-demo-parent)

ElasticSearch 的 demo 我这里没有使用 spring-data-elasticsearch，我使用的是原生的方式

操作 ElasticSearch 由很多种方式：

1. ES 官方提供的原生方式，**本例子使用这种方式**，这种方式的好处是高度自定义，并且可以使用最新的 ES 版本，缺点就是所有操作都得自己写。
2. 使用 Spring 官方提供的 spring-data-elasticsearch，这里给出地址 https://projects.spring.io/spring-data-elasticsearch/ ，采用的方式类似 JPA，并且为 SpringBoot 提供了一个 `spring-boot-starter-data-elasticsearch`，优点是操作 ES 的方式采用了 JPA 的方式，都已经封装好了，缺点是版本得随着官方慢慢迭代，不能使用 ES 的最新特性。

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-boot-demo-elasticsearch</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>spring-boot-demo-elasticsearch</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>com.xkcoding</groupId>
		<artifactId>spring-boot-demo-parent</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<relativePath>../spring-boot-demo-parent/pom.xml</relativePath>
	</parent>

	<properties>
		<!--默认 Spring-Boot 依赖的 ES 版本是 2.X 版本，这里采用最新版-->
		<elasticsearch.version>6.1.1</elasticsearch.version>
	</properties>

	<dependencies>
		<!-- ES -->
		<dependency>
			<groupId>org.elasticsearch.client</groupId>
			<artifactId>transport</artifactId>
			<version>${elasticsearch.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
			<version>2.7</version>
		</dependency>
	</dependencies>

	<build>
		<finalName>spring-boot-demo-elasticsearch</finalName>
	</build>

</project>
```

### application.yml

```yaml
server:
  port: 8080
  context-path: /demo
elasticsearch:
  host: 127.0.0.1
  port: 9300
  cluster:
    name: xkcoding
```

ElasticSearchConfig.java

```java
/**
 * <p>
 * ES 的配置类
 * </p>
 *
 * @package: com.xkcoding.springbootdemoelasticsearch.config
 * @description： ES 的配置类
 * @author: yangkai.shen
 * @date: Created in 2018/1/18 下午4:41
 * @copyright: Copyright (c) 2018
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Configuration
public class ElasticSearchConfig {
	@Value("${elasticsearch.host}")
	private String host;

	@Value("${elasticsearch.port}")
	private int port;

	@Value("${elasticsearch.cluster.name}")
	private String clusterName;

	@Bean
	public TransportClient esClient() throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", this.clusterName).put("client.transport.sniff", true).build();

		TransportAddress master = new TransportAddress(InetAddress.getByName(host), port);
		TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(master);
		return client;
	}
}
```

PersonController.java

```java
/**
 * <p>
 * Person Controller
 * </p>
 *
 * @package: com.xkcoding.springbootdemoelasticsearch.web.controller
 * @description： Person Controller
 * @author: yangkai.shen
 * @date: Created in 2018/1/18 下午5:06
 * @copyright: Copyright (c) 2018
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@RestController
@Slf4j
public class PersonController {
	public static final String INDEX = "people";
	public static final String TYPE = "person";

	@Autowired
	private TransportClient esClient;

	/**
	 * 插入一条数据到 ES 中，id 由 ES 生成
	 *
	 * @param name     名称
	 * @param country  国籍
	 * @param age      年龄
	 * @param birthday 生日
	 * @return 插入数据的主键
	 */
	@PostMapping("/person")
	public ApiResponse add(@RequestParam String name, @RequestParam String country, @RequestParam Integer age, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date birthday) {
		try {
			XContentBuilder content = XContentFactory.jsonBuilder().startObject().field("name", name).field("country", country).field("age", age).field("birthday", birthday.getTime()).endObject();

			IndexResponse response = esClient.prepareIndex(INDEX, TYPE).setSource(content).get();
			return ApiResponse.ofSuccess(response.getId());
		} catch (IOException e) {
			e.printStackTrace();
			return ApiResponse.ofStatus(Status.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 根据 id 删除 ES 的一条记录
	 *
	 * @param id ES 中的 id
	 * @return DELETED 代表删除
	 */
	@DeleteMapping("/person/{id}")
	public ApiResponse delete(@PathVariable String id) {
		DeleteResponse response = esClient.prepareDelete(INDEX, TYPE, id).get();
		return ApiResponse.ofSuccess(response.getResult());
	}

	/**
	 * 根据主键，修改传递字段对应的值
	 *
	 * @param id       ES 中的 id
	 * @param name     姓名
	 * @param country  国籍
	 * @param age      年龄
	 * @param birthday 生日
	 * @return UPDATED 代表文档修改成功
	 */
	@PutMapping("/person/{id}")
	public ApiResponse update(@PathVariable String id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "country", required = false) String country, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "birthday", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date birthday) {
		UpdateRequest request = new UpdateRequest(INDEX, TYPE, id);
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
			if (!Strings.isNullOrEmpty(name)) {
				builder.field("name", name);
			}
			if (!Strings.isNullOrEmpty(country)) {
				builder.field("country", country);
			}
			if (age != null && age > 0) {
				builder.field("age", age);
			}
			if (birthday != null) {
				builder.field("birthday", birthday.getTime());
			}
			builder.endObject();
			request.doc(builder);
		} catch (IOException e) {
			e.printStackTrace();
			return ApiResponse.ofStatus(Status.INTERNAL_SERVER_ERROR);
		}
		try {
			UpdateResponse response = esClient.update(request).get();
			return ApiResponse.ofSuccess(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.ofStatus(Status.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 简单查询 根据 id 查 ES 中的文档内容
	 *
	 * @param id ES 中存储的 id
	 * @return 对应 id 的文档内容
	 */
	@GetMapping("/person/{id}")
	public ApiResponse get(@PathVariable String id) {
		GetResponse response = esClient.prepareGet(INDEX, TYPE, id).get();
		if (!response.isExists() || response.isSourceEmpty()) {
			return ApiResponse.ofStatus(Status.NOT_FOUND);
		}
		return ApiResponse.ofSuccess(response.getSource());
	}

	/**
	 * 复合查询，根据传进来的条件，查询具体内容
	 *
	 * @param name    根据姓名匹配
	 * @param country 根据国籍匹配
	 * @param gtAge   大于年龄
	 * @param ltAge   小于年龄
	 * @return 满足条件的文档内容
	 */
	@PostMapping("/person/query")
	public ApiResponse query(@RequestParam(value = "name", required = false) String name,
	                         @RequestParam(value = "country", required = false) String country,
	                         @RequestParam(value = "gt_age", defaultValue = "0") int gtAge,
	                         @RequestParam(value = "lt_age", required = false) Integer ltAge) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		if (!Strings.isNullOrEmpty(name)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("name", name));
		}

		if (!Strings.isNullOrEmpty(country)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("country", country));
		}

		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").from(gtAge);

		if (ltAge != null && ltAge > 0) {
			rangeQueryBuilder.to(ltAge);
		}

		boolQueryBuilder.filter(rangeQueryBuilder);

		SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch(INDEX)
				.setTypes(TYPE)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder)
				.setFrom(0)
				.setSize(20);

		log.info("【query】:{}", searchRequestBuilder);

		SearchResponse searchResponse = searchRequestBuilder.get();
		List<Map<String, Object>> result = Lists.newArrayList();
		searchResponse.getHits().forEach(hit -> {
			result.add(hit.getSourceAsMap());
		});

		return ApiResponse.ofSuccess(result);
	}

}
```

