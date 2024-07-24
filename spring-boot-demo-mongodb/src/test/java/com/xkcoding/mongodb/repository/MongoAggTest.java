package com.xkcoding.mongodb.repository;

import com.xkcoding.mongodb.SpringBootDemoMongodbApplicationTests;
import com.xkcoding.mongodb.model.Zips;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.BucketOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;



@Slf4j
public class MongoAggTest extends SpringBootDemoMongodbApplicationTests {


    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test(){

        //$group
        GroupOperation groupOperation = Aggregation.group("state")
            .sum("pop").as("totalPop");
        //$match
        MatchOperation matchOperation = Aggregation
            .match(Criteria.where("totalPop").gt(10*1000*1000));
        //$sort
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"totalPop");
        // 按顺序组合每一个聚合步骤
        TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(Zips.class,
            groupOperation, matchOperation,sortOperation);
        //调对应的api获取结果
        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate
            .aggregate(typedAggregation, Map.class);
        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
            System.out.println(map);
        }

    }

    @Test
    public void test2(){

        //$group
        GroupOperation groupOperation = Aggregation.group("state","city")
            .sum("pop").as("cityPop");
        //$group
        GroupOperation groupOperation2 = Aggregation.group("_id.state")
            .avg("cityPop").as("avgCityPop");
        //$sort
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"avgCityPop");

        // 按顺序组合每一个聚合步骤
        TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(Zips.class,
            groupOperation, groupOperation2,sortOperation);

        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate
            .aggregate(typedAggregation, Map.class);
        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
            System.out.println(map);
        }


    }

    @Test
    public void test3(){

        //$group
        GroupOperation groupOperation = Aggregation.group("state","city")
            .sum("pop").as("pop");

        //$sort
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC,"pop");

        //$group
        GroupOperation groupOperation2 = Aggregation.group("_id.state")
            .last("_id.city").as("biggestCity")
            .last("pop").as("biggestPop")
            .first("_id.city").as("smallestCity")
            .first("pop").as("smallestPop");

        //$project
        ProjectionOperation projectionOperation = Aggregation
            .project("biggestCity","smallestCity","state")
            .andExclude("_id")
            .andExpression(" { name: \"$biggestCity\",  pop: \"$biggestPop\" }")
            .as("biggestCity")
            .andExpression("{ name: \"$smallestCity\", pop: \"$smallestPop\" }")
            .as("smallestCity")
            .and("_id").as("state");


        //$sort
        SortOperation sortOperation2 = Aggregation.sort(Sort.Direction.ASC,"state");



        // 按顺序组合每一个聚合步骤
        TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(Zips.class,
            groupOperation, sortOperation,groupOperation2,projectionOperation,
            sortOperation2);

        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults = mongoTemplate
            .aggregate(typedAggregation, Map.class);
        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
            System.out.println(map);
        }


    }


}
