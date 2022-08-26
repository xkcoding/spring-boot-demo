package com.xkcoding.log.aop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogData {
    /**
     * 线程id
     */
    private String threadId;
    /**
     * 线程名称
     */
    private String threadName;
    /**
     * ip
     */
    private String ip;
    /**
     * url
     */
    private String url;
    /**
     * 类方法
     */
    private String classMethod;
    /**
     * http方法 GET
     * POST PUT
     * DELETE PATCH
     */
    private String httpMethod;
    /**
     * 请求参数
     */
    private Object requestParams;
    /**
     * 返回参数
     */
    private Object result;
    /**
     * 接口耗时
     */
    private Long timeCost;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * user-agent
     */
    private String userAgent;
}
