package com.example.awaysuse.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: fengh
 * @Date: 2020/7/17 14:34
 * @Description: map工具类
 */
@Slf4j
@Component
public class MapUtils<K, V> {

    private final Map<K, V> map;

    public MapUtils(){
        this.map = new LinkedHashMap<>();
    }

    public MapUtils(Map<K, V> map){
        this.map = map;
    }

    public static <K, V> MapUtils<K, V> create(){
        return new MapUtils<>(new LinkedHashMap<>());
    }

    public MapUtils<K, V> put(K key, V value){
        this.map.put(key, value);
        return this;
    }

    public Map<K, V> build(){
        return map;
    }

//    public static void main(String[] args) {
//        //默认格式<Object, Object>
//        Map<Object, Object> map1 = MapUtils.create().put("name", "nicai").build();
//        //自定义格式
//        Map<Integer, Integer> map2 = new MapUtils<Integer, Integer>().put(1, 2).put(2, 3).build();
//        System.out.println(String.format("%s\n%s", map1, map2));
//    }

}
