package com.xkcoding.mq.kafka.utils;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class KafkaManager {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private AdminClient adminClient;

    @PostConstruct
    private void initAdminClient() {
        Map<String, Object> props = new HashMap<>(1);
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        adminClient = KafkaAdminClient.create(props);
    }



    /**
     * 新增topic，支持批量
     */
    public CreateTopicsResult createTopic(Collection<NewTopic> newTopics) {
        return adminClient.createTopics(newTopics);
    }

    /**
     * 删除topic，支持批量
     */
    public void deleteTopic(Collection<String> topics) {
        adminClient.deleteTopics(topics);
    }

    /**
     * 获取指定topic的信息
     */
    public String getTopicInfo(Collection<String> topics) {
        AtomicReference<String> info = new AtomicReference<>("");
        try {
            adminClient.describeTopics(topics).all().get().forEach((topic, description) -> {
                for (TopicPartitionInfo partition : description.partitions()) {
                    info.set(info + partition.toString() + "\n");
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return info.get();
    }

    /**
     * 获取全部topic
     */
    public List<String> getAllTopic() {
        try {
            return adminClient.listTopics().listings().get().stream().map(TopicListing::name).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 往topic中发送消息
     */
    public void sendMessage(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败：" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                String content = String.format("发送消息成功：%s-%s-%s",

                    result.getRecordMetadata().topic(),

                    result.getRecordMetadata().partition(),

                    result.getRecordMetadata().offset());

                System.out.println(content);
            }

        });
    }

}
