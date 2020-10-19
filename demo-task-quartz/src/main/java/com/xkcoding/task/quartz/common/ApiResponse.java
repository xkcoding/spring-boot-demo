package com.xkcoding.task.quartz.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * <p>
 * 通用Api封装
 * </p>
 *
 * @package: com.xkcoding.task.quartz.common
 * @description: 通用Api封装
 * @author: yangkai.shen
 * @date: Created in 2018-11-26 13:59
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class ApiResponse implements Serializable {
    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public ApiResponse() {
    }

    private ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    /**
     * 通用封装获取ApiResponse对象
     *
     * @param message 返回信息
     * @param data    返回数据
     * @return ApiResponse
     */
    public static ApiResponse of(String message, Object data) {
        return new ApiResponse(message, data);
    }

    /**
     * 通用成功封装获取ApiResponse对象
     *
     * @param data 返回数据
     * @return ApiResponse
     */
    public static ApiResponse ok(Object data) {
        return new ApiResponse(HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 通用封装获取ApiResponse对象
     *
     * @param message 返回信息
     * @return ApiResponse
     */
    public static ApiResponse msg(String message) {
        return of(message, null);
    }

}
