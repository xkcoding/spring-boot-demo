package com.example.awaysuse.util;

import java.util.Collection;

/**
 * @Author
 * @Date 2020/10/27 12:10
 * @Description   判断是否为空
 */
public class NullUtils {

    public static <T> boolean isNull(Collection<T> collection){
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotNull(Collection<T> collection){
        return collection != null && !collection.isEmpty();
    }

    public static boolean isNull(String string){
        return string == null || "".equals(string);
    }

    public static boolean isNotNull(String string){
        return string != null && !"".equals(string);
    }

}
