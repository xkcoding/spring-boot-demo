package com.xkcoding.mq.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.xkcoding.mq.kafka.handler.DelayManager;
import com.xkcoding.mq.kafka.model.DelayMessage;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class DelayConsumer {
    private KafkaConsumer<String, String> consumer;
    private final DelayManager delayManager;
    private final int idx;
    private final int t;
    private final int t2;
    private int interval;
    private final String servers;
    private final Object lock = new Object();
    private final String topic;
    private Thread thread;

    public DelayConsumer(int idx, int t, String topic, String servers,
                         DelayManager dm) {
        this.idx = idx;
        this.topic = topic;
        this.t = t;
        this.interval = t<=5?500:1000;
        this.servers = servers;
        this.t2 = 200;
        this.delayManager = dm;
    }

    public void initTimer() {
        List<String> topics = Collections.singletonList(topic);
        consumer.subscribe(topics);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (lock) {
                    if(DelayManager.exit)return;
                    consumer.resume(consumer.paused());
                    lock.notify();
                }
            }
        }, 0, interval);

        thread = new Thread(this::loop);
        thread.start();
    }

    private void loop() {
        do {
            synchronized (lock) {
                try {
                    ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(t2));
                    if (consumerRecords.isEmpty()) {
                        lock.wait();
                        continue;
                    }
                    boolean timed = false;
                    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                        // long timestamp = consumerRecord.timestamp();
                        TopicPartition topicPartition = new TopicPartition(consumerRecord.topic(), consumerRecord.partition());
                        String value = consumerRecord.value();
                        System.out.println("消费的数据的时间是"+new Date()+"  数据是"+value);
                        DelayMessage dm = null;
                        try {
                            dm = JSON.parseObject(value, DelayMessage.class);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (delayManager.sendAndWait(dm)) {
                            consumer.pause(Collections.singletonList(topicPartition));
                            consumer.seek(topicPartition, consumerRecord.offset());
                            timed = true;
                            break;
                        } else {
                            OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(consumerRecord.offset() + 1);
                            HashMap<TopicPartition, OffsetAndMetadata> metadataHashMap = new HashMap<>();
                            metadataHashMap.put(topicPartition, offsetAndMetadata);
                            consumer.commitSync(metadataHashMap);
                        }
                    }
                    if (timed) {
                        lock.wait();
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } while (!DelayManager.exit);
    }

   public void initConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "d");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "5000");
        consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer());
    }
}
