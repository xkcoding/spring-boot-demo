package com.xkcoding.mongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("zips")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zips {

    @Id   //映射文档中的_id
    private String id;
    @Field
    private String city;
    @Field
    private Double[] loc;
    @Field
    private Integer pop;
    @Field
    private String state;
}
