package com.xkcoding.elasticsearch.service;

import com.xkcoding.elasticsearch.exception.ElasticsearchException;
import com.xkcoding.elasticsearch.autoconfigure.ElasticsearchProperties;
import com.xkcoding.elasticsearch.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * BaseElasticsearchService
 *
 * @author fxbin
 * @version 1.0v
 * @since 2019/9/16 15:44
 */
@Slf4j
public abstract class BaseElasticsearchService {

    @Resource
    protected RestHighLevelClient client;

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    protected static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        // 默认缓冲限制为100MB，此处修改为30MB。
        builder.setHttpAsyncResponseConsumerFactory(
            new HttpAsyncResponseConsumerFactory
                .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    /**
     * create elasticsearch index (asyc)
     *
     * @author fxbin
     * @param index elasticsearch index
     */
    protected void createIndexRequest(String index) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(index);
            // Settings for this index
            request.settings(Settings.builder()
                .put("index.number_of_shards", elasticsearchProperties.getIndex().getNumberOfShards())
                .put("index.number_of_replicas", elasticsearchProperties.getIndex().getNumberOfReplicas()));

            CreateIndexResponse createIndexResponse = client.indices().create(request, COMMON_OPTIONS);

            log.info(" whether all of the nodes have acknowledged the request : {}", createIndexResponse.isAcknowledged());
            log.info(" Indicates whether the requisite number of shard copies were started for each shard in the index before timing out :{}",
                    createIndexResponse.isShardsAcknowledged());
        } catch (IOException e) {
            throw new ElasticsearchException("创建索引 {" + index + "} 失败");
        }
    }

    /**
     * delete elasticsearch index
     *
     * @author fxbin
     * @param index elasticsearch index name
     */
    protected void deleteIndexRequest(String index) {
        DeleteIndexRequest deleteIndexRequest = buildDeleteIndexRequest(index);
        try {
            client.indices().delete(deleteIndexRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException("删除索引 {" + index + "} 失败");
        }
    }

    /**
     * build DeleteIndexRequest
     *
     * @author fxbin
     * @param index elasticsearch index name
     */
    private static DeleteIndexRequest buildDeleteIndexRequest (String index) {
        return new DeleteIndexRequest(index);
    }

    /**
     * build IndexRequest
     *
     * @author fxbin
     * @param index elasticsearch index name
     * @param id request object id
     * @param object request object
     * @return {@link org.elasticsearch.action.index.IndexRequest}
     */
    protected static IndexRequest buildIndexRequest(String index, String id, Object object) {
        return new IndexRequest(index).id(id).source(BeanUtils.toMap(object), XContentType.JSON);
    }

    /**
     * exec updateRequest
     *
     * @author fxbin
     * @param index elasticsearch index name
     * @param id Document id
     * @param object request object
     */
    protected void updateRequest(String index, String id, Object object) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(index, id).doc(BeanUtils.toMap(object), XContentType.JSON);
            client.update(updateRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException("更新索引 {" + index + "} 数据 {" + object + "} 失败");
        }
    }

    /**
     * exec deleteRequest
     *
     * @author fxbin
     * @param index elasticsearch index name
     * @param id Document id
     */
    protected void deleteRequest(String index, String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(index, id);
            client.delete(deleteRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            throw new ElasticsearchException("删除索引 {" + index + "} 数据id {" + id + "} 失败");
        }
    }

    /**
     * search all
     * @param index elasticsearch index name
     * @return {@link SearchResponse}
     */
    protected SearchResponse search(String index) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }
}
