package com.xkcoding.springbootdemoelasticsearch.web.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.xkcoding.springbootdemoelasticsearch.web.base.ApiResponse;
import com.xkcoding.springbootdemoelasticsearch.web.base.Status;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
