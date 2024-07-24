package com.example.awaysuse.model;

import java.util.HashMap;
import java.util.Map;

/**
 * ES查询结果封装类
 * @author
 */
public class Doc extends HashMap<Object, Object> {
    private static final long serialVersionUID = 4978196894185418504L;

    public Doc(){

    }

    public Doc(Map<String, Object> map){
        this.putAll(map);
    }

    public static Doc put(Map<String, Object> map){
        return new Doc(map);
    }

    public Doc build(){
        return this;
    }

}
