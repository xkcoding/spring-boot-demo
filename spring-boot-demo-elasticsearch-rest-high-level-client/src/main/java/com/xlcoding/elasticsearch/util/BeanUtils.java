package com.xlcoding.elasticsearch.util;

import com.xlcoding.elasticsearch.model.Person;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * BeanUtils
 *
 * @author fxbin
 * @version 1.0v
 * @since 2019/9/16 16:26
 */
public class BeanUtils {

    /**
     * Java Bean to Map
     *
     * @author fxbin
     * @param object Object
     * @return Map
     */
    public static Map<String,Object> toFieldNameAndFieldTypeMap(Object object){
        Map<String, Object> map = MapUtils.newHashMap();
        try {
            // 获取javaBean的BeanInfo对象
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(),Object.class);

            // 获取属性描述器
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                // 获取属性名
                String key = propertyDescriptor.getName();
                Class<?> value = propertyDescriptor.getPropertyType();
                // 获取该属性的值
//                Method readMethod = propertyDescriptor.getReadMethod();
                // 通过反射来调用javaBean定义的getName()方法
//                Object value = readMethod.invoke(object);
                map.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static void main(String[] args) {

        Person person = new Person();

        Map<String, Object> stringObjectMap = toFieldNameAndFieldTypeMap(person);

        System.out.println(stringObjectMap);

    }

}
