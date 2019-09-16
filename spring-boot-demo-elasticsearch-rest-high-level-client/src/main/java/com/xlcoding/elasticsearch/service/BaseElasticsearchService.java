package com.xlcoding.elasticsearch.service;

import com.xlcoding.elasticsearch.autoconfigure.ElasticsearchProperties;
import com.xlcoding.elasticsearch.exception.ElasticsearchException;
import com.xlcoding.elasticsearch.model.Person;
import com.xlcoding.elasticsearch.util.BeanUtils;
import com.xlcoding.elasticsearch.util.MapUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * BaseElasticsearchService
 *
 * @author fxbin
 * @version 1.0v
 * @since 2019/9/16 15:44
 */
public abstract class BaseElasticsearchService {

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    /**
     * create elasticsearch index
     *
     * @author fxbin
     * @param index elasticsearch index
     */
    public void createIndex(String index, Person person) {

        try {
            CreateIndexRequest request = new CreateIndexRequest(index);
            // Settings for this index
            request.settings(Settings.builder()
                .put("index.number_of_shards", elasticsearchProperties.getIndex().getNumberOfShards())
                .put("index.number_of_replicas", elasticsearchProperties.getIndex().getNumberOfReplicas()));
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    builder.startObject("message");
                    {
                        Map<String, Object> map =
                            BeanUtils.toMap(person);

                        map.keySet().forEach(key -> {

                            try {
                                builder.field(key, "text");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping(builder);

        } catch (IOException e) {
            throw new ElasticsearchException("创建Elasticsearch索引 {" + index + "} 失败");
        }

    }

    /**
     * build IndexRequest
     *
     * @author fxbin
     * @param index elasticsearch index name
     * @return {@link org.elasticsearch.action.index.IndexRequest}
     */
    public IndexRequest buildIndexRequest(String index) {
        return new IndexRequest(index);
    }

}
