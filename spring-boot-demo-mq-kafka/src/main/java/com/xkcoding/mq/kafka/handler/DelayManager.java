package com.xkcoding.mq.kafka.handler;

import com.alibaba.fastjson.JSON;
import com.xkcoding.mq.kafka.consumer.DelayConsumer;
import com.xkcoding.mq.kafka.model.DelayMessage;
import com.xkcoding.mq.kafka.utils.KafkaManager;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DelayManager /*implements CommandLineRunner */{
    private final boolean redirect = true;

    final List<DelayConsumer> consumers = new ArrayList<>();

    @Autowired
    private KafkaManager kafkaManager;

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    private final int[] delayTimes = new int[]{1, 5, 10, 30, 60, 120, 180, 240, 300, 360
        , 420, 480, 540, 600, 1200, 1800, 3600, 7200};

    public static boolean exit=false;


    public void run(String... args) {
        List<NewTopic> list = new ArrayList<>();
        int n = delayTimes.length;
        for (int i = 0; i < n; i++) {
            list.add(new NewTopic(topicName(i), 1, (short) 1));
        }
        kafkaManager.createTopic(list);
        for (int i = 0; i < n; i++) {
            DelayConsumer dc = new DelayConsumer(i, delayTimes[i], topicName(i), servers, this);
            dc.initConsumer();
            dc.initTimer();
            consumers.add(dc);
            System.out.println("add consumer:" + i);
        }

    }

    private String topicName(int idx) {
        return "delay-" + idx;
    }

    public void sendDelay(String topic, String key, String data, int delay) {
        int next = Arrays.binarySearch(delayTimes, delay);
        if (next < 0) {
            next = -next - 2;
        }
        long now = System.currentTimeMillis();
        long expire = now + delayTimes[next] * 1000;
        long expire2 = now + delay * 1000;
        DelayMessage dm = new DelayMessage(topic, key, data, expire, expire2);
        sendDelayMessage(dm, next);
    }

    private void sendDelayMessage(DelayMessage dm, int idx) {
        kafkaManager.sendMessage(topicName(idx), null, JSON.toJSONString(dm));
        // System.out.println("send to delay-"+idx);
    }

    public boolean sendAndWait(DelayMessage dm) {
        if (dm == null) return false;
        long now = System.currentTimeMillis();
        if (now < dm.getExpire()) {
            return true;
        }
        long delay = dm.getExpire2() - now;
        if (delay < 1000 || !redirect) {
            //send to target topic
            kafkaManager.sendMessage(dm.getTopic(), dm.getKey(), dm.getData());
        } else {
            //redirect to next
            int next = Arrays.binarySearch(delayTimes, (int) (delay / 1000));
            if (next < 0) {
                next = -next - 2;
            }
            dm.setExpire(now + delayTimes[next] * 1000);
            sendDelayMessage(dm, next);
        }
        return false;
    }

    public void sendDelayOnLevel(String topic, String key, String data, int level) {
        long now = System.currentTimeMillis();
        int next = level - 1;
        long expire = now + delayTimes[next];
        DelayMessage dm = new DelayMessage(topic, key, data,
            expire, expire);
        sendDelayMessage(dm, next);
    }

    public void shutdown(){
        exit=true;
    }

    public void on(){
        exit=false;
    }

}
