package com.xkcoding.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DemoDto {
    private  long  id;
    private  String  tag;
    private  String  title;
    private  Date  publishTime;
}
