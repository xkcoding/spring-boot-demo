package com.xkcoding.elasticsearch.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * MapUtils
 *
 * @author fxbin
 * @version 1.0v
 * @since 2019/9/16 16:26
 */
public class MapUtils {

    /** 默认初始大小 */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    /** 默认增长因子，当Map的size达到 容量*增长因子时，开始扩充Map */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75
     * @param isOrder Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     * @since 3.0.4
     */
    public static <K, V> HashMap<K, V> newHashMap(int size, boolean isOrder) {
        int initialCapacity = (int) (size / DEFAULT_LOAD_FACTOR);
        return isOrder ? new LinkedHashMap<K, V>(initialCapacity) : new HashMap<K, V>(initialCapacity);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(int size) {
        return newHashMap(size, false);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param isOrder Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(boolean isOrder) {
        return newHashMap(DEFAULT_INITIAL_CAPACITY, isOrder);
    }

}
