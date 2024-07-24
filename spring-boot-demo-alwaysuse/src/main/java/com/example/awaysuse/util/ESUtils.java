package com.example.awaysuse.util;


import com.example.awaysuse.model.Doc;
import com.example.awaysuse.model.PageData;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author fengh
 * @Date: 2020/8/18 10:17
 * @Description: elasticsearch工具类
 */
@Slf4j
@Component
public class ESUtils {

    @Resource(name = "restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 检查索引是否存在
     *
     * @param index 索引名称
     * @return boolean
     */
    public boolean existIndex(String index) {
        try {
            GetIndexRequest request = new GetIndexRequest(index);
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建索引
     *
     * @param index  索引名称
     * @return boolean
     */
    public boolean createIndex(String index) {
        return createIndex(index, null);
    }

    /**
     * 创建索引
     *
     * @param index  索引名称
     * @param source 配置
     * @return boolean
     */
    public boolean createIndex(String index, String source) {
        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
            //指定字段个数最大10000
            createIndexRequest.settings(Settings.builder().put("index.mapping.total_fields.limit", 10000));
            if (!StringUtils.isEmpty(source)) {
                createIndexRequest.mapping(source, XContentType.JSON);
            }
            CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (Exception e) {
            log.error("创建索引{}异常", index, e);
        }
        return false;
    }

    /**
     * 删除索引
     *
     * @param index 索引名称
     * @return boolean
     */
    public boolean deleteIndex(String index) {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            AcknowledgedResponse acknowledgedResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
        } catch (Exception e) {
            log.error("删除索引{}异常", index, e);
        }
        return false;
    }

    /**
     * 新增(更新)文档:自动生成文档id
     *
     * @param index 索引
     * @param map   保存的数据
     * @return string
     */
    public String save(String index, Map<String, Object> map) {
        return save(index, map, null);
    }

    /**
     * 新增(更新)文档:自定义文档id
     *
     * @param index 索引
     * @param docId 文档id
     * @param map   保存的数据
     * @return string
     */
    public String save(String index, Map<String, Object> map, String docId) {
        try {
            //文档id为空则新增
            if(StringUtils.isEmpty(docId)){
                IndexRequest indexRequest = new IndexRequest().index(index).source(map);
                IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
                return indexResponse.getId();
            }
            UpdateRequest updateRequest = new UpdateRequest().index(index).id(docId).doc(map).docAsUpsert(true);
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            return updateResponse.getId();
        } catch (Exception e) {
            log.error("索引{}插入(更新)数据异常:{}", index, e);
        }
        return null;
    }

    /**
     * 批量插入(更新)文档: 自动生成id
     *
     * @param index 索引
     * @param list  保存的数据
     * @return boolean
     */
    public boolean bulkSave(String index, List<Map<String, Object>> list) {
        return bulkSave(index, list, null);
    }

    /**
     * 批量插入(更新)文档: 自定义id
     *
     * @param index   索引
     * @param list    保存的数据
     * @param idField map中作为文档id的字段名称
     * @return boolean
     */
    public boolean bulkSave(String index, List<Map<String, Object>> list, String idField) {
        return bulkSave(index, list, idField, null);
    }

    /**
     * 批量插入(更新)文档: 自定义id字段 自定义id前缀
     *
     * @param index   索引
     * @param list    保存的数据
     * @param idField map中作为文档id的字段名称
     * @param prefix  id前缀
     * @return boolean
     */
    public boolean bulkSave(String index, List<Map<String, Object>> list, String idField, String prefix) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        try {
            BulkRequest bulkRequest = new BulkRequest();
            for (Map<String, Object> map : list) {
                String docId = null;
                if (!StringUtils.isEmpty(idField) && StringUtils.isEmpty(prefix)) {
                    docId = String.valueOf(map.get(idField));
                } else if (!StringUtils.isEmpty(idField) && !StringUtils.isEmpty(prefix)) {
                    docId = prefix + map.get(idField);
                }
                //文档id为空则新增
                if(StringUtils.isEmpty(docId)){
                    IndexRequest indexRequest = new IndexRequest().index(index).source(map);
                    bulkRequest.add(indexRequest);
                } else {
                    UpdateRequest updateRequest = new UpdateRequest().index(index).id(docId).doc(map).docAsUpsert(true);
                    bulkRequest.add(updateRequest);
                }
            }
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            if(bulkResponse.hasFailures()){
                log.error("索引{}批量插入(更新)数据错误:{}", index, bulkResponse.buildFailureMessage());
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("索引{}批量插入(更新)数据异常", index, e);
        }
        return false;
    }

    /**
     * 根据文档id查询文档
     *
     * @param index 索引
     * @param docId 文档id
     * @return map
     */
    public Doc searchById(String index, String docId) {
        try {
            GetRequest getRequest = new GetRequest(index).id(docId);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                return Doc.put(getResponse.getSourceAsMap()).build();
            }
        } catch (Exception e) {
            log.error("索引{}查询文档id={}异常", index, docId, e);
        }
        return null;
    }

    /**
     * 根据文档id删除文档
     *
     * @param index 索引
     * @param docId 文档id
     * @return boolean
     */
    public boolean deleteById(String index, String docId) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(index).id(docId);
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            return deleteResponse.status() == RestStatus.OK;
        } catch (Exception e) {
            log.error("删除索引{}中文档id={}异常", index, docId);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 分页查询
     *
     * @param index    索引
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return pageData
     */
    public PageData<List<Doc>> searchPage(String index, Integer pageNum, Integer pageSize) {
        return searchPage(index, null, pageNum, pageSize, null, null);
    }

    /**
     * 分页查询: 按指定排序
     *
     * @param index    索引
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return pageData
     */
    public PageData<List<Doc>> searchPage(String index, Integer pageNum, Integer pageSize, String sortField, Boolean desc) {
        return searchPage(index, null, pageNum, pageSize, sortField, desc);
    }

    /**
     * 分页查询: 带查询条件
     *
     * @param index    索引
     * @param query    查询条件
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return pageData
     */
    public PageData<List<Doc>> searchPage(String index, QueryBuilder query, Integer pageNum, Integer pageSize) {
        return searchPage(index, query, pageNum, pageSize, null, null);
    }

    /**
     * 分页查询: 带查询条件 指定字段排序
     *
     * @param index     索引
     * @param query     查询条件
     * @param pageNum   当前页码
     * @param pageSize  每页条数
     * @param sortField 排序字段
     * @param desc      是否倒序
     * @return pageData
     */
    public PageData<List<Doc>> searchPage(String index, QueryBuilder query, Integer pageNum, Integer pageSize, String sortField, Boolean desc) {
        return searchPageExecute(index, query, pageNum, pageSize, sortField, desc);
    }

    /**
     * 执行分页查询
     *
     * @param index     索引
     * @param query     查询条件
     * @param pageNum   当前页码
     * @param pageSize  每页条数
     * @param sortField 排序字段
     * @param desc      是否倒序
     * @return pageData
     */
    private PageData<List<Doc>> searchPageExecute(String index, QueryBuilder query, Integer pageNum, Integer pageSize, String sortField, Boolean desc) {
        List<Doc> searchResult = new ArrayList<>();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(query);
        try {
            if (pageNum == null || pageNum <= 0) {
                pageNum = 1;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            int from = (pageNum - 1) * pageSize;//计算起始下标
            sourceBuilder.from(from);//起始下标,从0开始
            sourceBuilder.size(pageSize);//每页记录数

            setSortQuery(sourceBuilder, sortField, desc);//设置排序信息

            SearchRequest searchRequest = new SearchRequest(index).source(sourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            long totalCount = hits.getTotalHits().value;
            if (totalCount == 0) {
                return new PageData<>();
            }
            int totalPage = Math.toIntExact(hits.getTotalHits().value / pageSize);
            if(totalCount % 10 != 0){
                totalPage ++;
            }
            SearchHit[] searchHits = hits.getHits();
            Arrays.stream(searchHits).forEach(doc -> searchResult.add(Doc.put(doc.getSourceAsMap()).build()));
            return new PageData<>(pageNum, pageSize, totalCount, totalPage, searchResult);
        } catch (Exception e) {
            log.error("索引{}查询分页数据失败, 查询条件{}", index, sourceBuilder.toString(), e);
        }
        return new PageData<>();
    }

    /**
     * 查询
     *
     * @param index 索引
     * @return list
     */
    public List<Doc> search(String index) {
        return search(index, null);
    }

    /**
     * 查询
     *
     * @param index 索引
     * @param query 查询条件
     * @return list
     */
    public List<Doc> search(String index, QueryBuilder query) {
        return search(index, query, null, null);
    }

    /**
     * 查询
     *
     * @param index     索引名称
     * @param sortField 排序字段
     * @param desc      是否倒序
     * @return list
     */
    public List<Doc> search(String index, String sortField, Boolean desc) {
        return search(index, null, sortField, desc);
    }

    /**
     * 查询
     *
     * @param index     索引名称
     * @param query     查询条件
     * @param sortField 排序字段
     * @param desc      是否倒序
     * @return list
     */
    public List<Doc> search(String index, QueryBuilder query, String sortField, Boolean desc) {
        return search(index, query, sortField, desc, null);
    }

    /**
     * 查询
     *
     * @param index     索引名称
     * @param sortField 排序字段
     * @param desc      是否倒序
     * @param size      查询条数 null 或 小于0表示查询所有
     * @return list
     */
    public List<Doc> search(String index, String sortField, Boolean desc, Integer size) {
        return search(index, null, sortField, desc, size);
    }

    /**
     * 查询
     *
     * @param index     索引名称
     * @param query     查询条件
     * @param sortField 排序字段
     * @param desc      是否倒序
     * @param size      查询条数 null 或 小于0表示查询所有
     * @return list
     */
    public List<Doc> search(String index, QueryBuilder query, String sortField, Boolean desc, Integer size) {
        return searchExecute(index, query, sortField, desc, size);
    }

    /**
     * 执行查询: 游标查询，每次查询一万条，查询所有数据
     *
     * @param index     索引
     * @param query     查询条件
     * @param sortField 排序字段
     * @param desc      是否倒序
     * @param size      查询条数 null 或 小于0表示查询所有
     * @return list
     */
    private List<Doc> searchExecute(String index, QueryBuilder query, String sortField, Boolean desc, Integer size) {
        List<Doc> searchResult = new ArrayList<>();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(query);
        try {
            setSortQuery(sourceBuilder, sortField, desc);//设置排序信息
             /*
              * 当size不为空且大于0时，限制查询条数为size
              */
            if(size != null && size > 0){
                sourceBuilder.size(size);
                SearchRequest searchRequest = new SearchRequest(index).source(sourceBuilder);
                SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
                SearchHits hits = searchResponse.getHits();
                int totalCount = hits.getHits().length;
                if (totalCount == 0) {
                    return searchResult;
                }
                SearchHit[] searchHits = hits.getHits();
                Arrays.stream(searchHits).forEach(doc -> searchResult.add(Doc.put(doc.getSourceAsMap()).build()));
                return searchResult;
            }

            /*
             * 否则查询所有，设置游标size为10000，每次查询10000条，查询全部数据
             */
            sourceBuilder.size(10000);
            Scroll scroll = new Scroll(TimeValue.timeValueSeconds(30));
            SearchRequest searchRequest = new SearchRequest(index).source(sourceBuilder).scroll(scroll);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            if (hits.getHits().length == 0) {
                return searchResult;
            }
            SearchHit[] searchHits = hits.getHits();
            Arrays.stream(searchHits).forEach(doc -> searchResult.add(Doc.put(doc.getSourceAsMap()).build()));
            String scrollId = searchResponse.getScrollId();
            while (true) {
                SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId).scroll(scroll);
                searchResponse = client.scroll(searchScrollRequest, RequestOptions.DEFAULT);
                searchHits = searchResponse.getHits().getHits();
                if (searchHits == null || searchHits.length == 0) {
                    break;
                }
                Arrays.stream(searchHits).forEach(doc -> searchResult.add(Doc.put(doc.getSourceAsMap()).build()));
                scrollId = searchResponse.getScrollId();
            }
            //清除滚屏
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
            return searchResult;
        } catch (Exception e) {
            log.error("索引{}查询数据失败, 查询条件{}", index, sourceBuilder.toString(), e);
        }
        return searchResult;
    }

    /**
     * 单个字段聚合统计count
     *
     * @param index 索引
     * @param field 聚合字段
     * @param size  条数: 空默认10条
     * @return map
     */
    public Map<String, Long> searchAggCount(String index, String field, Integer size) {
        return searchAggCount(index, null, field, size);
    }

    /**
     * 单个字段聚合统计count: 带查询条件
     *
     * @param index 索引
     * @param field 聚合字段
     * @param size  条数: 空默认10条
     * @return map
     */
    public Map<String, Long> searchAggCount(String index, BoolQueryBuilder query, String field, Integer size) {
        try {
            AggregationBuilder aggregationBuilder = AggregationBuilders.terms("aggCount").field(field).size(size == null || size < 0 ? 10 : size);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(query).aggregation(aggregationBuilder);
            SearchRequest searchRequest = new SearchRequest(index).source(sourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            Terms terms = searchResponse.getAggregations().get("aggCount");
            Map<String, Long> map = new LinkedHashMap<>();
            for (Terms.Bucket entry : terms.getBuckets()) {
                String key = (String) entry.getKey();
                long total = entry.getDocCount();
                map.put(key, total);
            }
            return map;
        } catch (Exception e) {
            log.error("统计字段{} count异常", field, e);
        }
        return null;
    }

    /**
     * 单个字段聚合统计sum
     *
     * @param index 索引
     * @param field 聚合字段
     * @return map
     */
    public Map<String, Double> searchAggSum(String index, String field) {
        try {
            AggregationBuilder aggregationBuilder = AggregationBuilders.sum("aggSum").field(field);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().aggregation(aggregationBuilder);
            SearchRequest searchRequest = new SearchRequest(index).source(sourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            Sum sum = searchResponse.getAggregations().get("aggSum");
            Map<String, Double> map = new HashMap<>();
            map.put(field, sum.getValue());
            return map;
        } catch (Exception e) {
            log.error("统计字段{} sum异常", field, e);
        }
        return null;
    }

    /**
     * 单个字段聚合(去重)查询数据
     *
     * @param index    索引
     * @param query    查询条件
     * @param field    统计字段
     * @param pageNum  页数
     * @param pageSize 每页条数
     * @return pageData
     */
    public PageData<List<Doc>> searchAggDocs(String index, QueryBuilder query, String field, Integer pageNum, Integer pageSize, String sortField, Boolean desc) {
        try {
            AggregationBuilder aggregationBuilder = AggregationBuilders
                    .terms("agg").field(field).size(100000)
                    .subAggregation(AggregationBuilders.topHits("dataHits")
                            .size(1));//聚合后只需要重复中的一条

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(query).aggregation(aggregationBuilder);
            setSortQuery(sourceBuilder, sortField, desc);//设置排序信息
            SearchRequest searchRequest = new SearchRequest(index).source(sourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            Terms terms = searchResponse.getAggregations().get("agg");
            List<Doc> searchList = new ArrayList<>();
            for (Terms.Bucket entry : terms.getBuckets()) {
                TopHits top = entry.getAggregations().get("dataHits");
                for (SearchHit hit : top.getHits()) {
                    searchList.add(Doc.put(hit.getSourceAsMap()).build());
                }
            }
            if (pageNum == null || pageNum <= 0) {
                pageNum = 1;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            int from = (pageNum - 1) * pageSize;//起始下标
            int totalCount = searchList.size();//全部条数
            int totalPage = searchList.size() / pageSize + 1;//全部页数

            if (from >= searchList.size()) {
                return new PageData<>();
            }
            if (from + pageSize >= searchList.size()) {
                return new PageData<>(pageNum, pageSize, totalCount, totalPage, searchList.subList(from, searchList.size()));
            }
            return new PageData<>(pageNum, pageSize, totalCount, totalPage, searchList.subList(from, from + pageSize));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageData<>();
    }

    /**
     * 查询count
     *
     * @param index 索引
     * @param query 查询条件
     * @return long
     */
    public long count(String index, QueryBuilder query) {
        try {
            CountRequest countRequest = new CountRequest().indices(index).query(query);
            CountResponse countResponse= client.count(countRequest, RequestOptions.DEFAULT);
            return countResponse.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置排序信息
     *
     * @param sourceBuilder
     * @param sortField
     * @param desc
     */
    private void setSortQuery(SearchSourceBuilder sourceBuilder, String sortField, Boolean desc) {
        if (!StringUtils.isEmpty(sortField) && desc != null) {
            if (desc) {
                sourceBuilder.sort(sortField, SortOrder.DESC);
            } else {
                sourceBuilder.sort(sortField, SortOrder.ASC);
            }
        }
    }


}
