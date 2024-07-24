package com.xkcoding.mq.kafka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DelayMessage {


    private String topic;
    private String key;
    private String data;
    private long expire;
    private long expire2;

    public DelayMessage(String topic, String key, String data, long expire, long expire2) {
        this.topic = topic;
        this.key = key;
        this.data = data;
        this.expire = expire;
        this.expire2 = expire2;
    }

    public long getExpire2() {
        return expire2;
    }

    public void setExpire2(long expire2) {
        this.expire2 = expire2;
    }


}
