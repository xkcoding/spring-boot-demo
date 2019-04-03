package com.xkcoding.dubbo.common.service;

/**
 * <p>
 * Hello服务接口
 * </p>
 *
 * @package: com.xkcoding.dubbo.common.service
 * @description: Hello服务接口
 * @author: yangkai.shen
 * @date: Created in 2018-12-25 16:56
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface HelloService {
    /**
     * 问好
     *
     * @param name 姓名
     * @return 问好
     */
    String sayHello(String name);
}
