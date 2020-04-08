package com.xkcoding.exception.handler.exception;

/**
 * 定义顶级数据传输方式为json或视图
 *
 * @author FYT
 * @since 2020/4/7
 */
public interface IDataType {

    /**
     * 控制移动端返回格式是否为json
     *
     * @return true / false
     */
    default boolean isJson() {
        return true;
    }

    /**
     * 控制非移动端返回格式是否为视图
     *
     * @return true / false
     */
    default boolean isView() {
        return true;
    }

}
