package com.xkcoding.websocket.payload.server;

import com.google.common.collect.Lists;
import com.xkcoding.websocket.model.server.Jvm;
import com.xkcoding.websocket.payload.KV;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * JVM相关信息实体VO
 * </p>
 *
 * @package: com.xkcoding.websocket.payload.server
 * @description: JVM相关信息实体VO
 * @author: yangkai.shen
 * @date: Created in 2018-12-14 17:28
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class JvmVO {
    List<KV> data = Lists.newArrayList();

    public static JvmVO create(Jvm jvm) {
        JvmVO vo = new JvmVO();
        vo.data.add(new KV("当前JVM占用的内存总数(M)", jvm.getTotal()));
        vo.data.add(new KV("JVM最大可用内存总数(M)", jvm.getMax()));
        vo.data.add(new KV("JVM空闲内存(M)", jvm.getFree()));
        vo.data.add(new KV("JDK版本", jvm.getVersion()));
        vo.data.add(new KV("JDK路径", jvm.getHome()));
        vo.data.add(new KV("JDK启动时间", jvm.getStartTime()));
        vo.data.add(new KV("JDK运行时间", jvm.getRunTime()));
        return vo;
    }

}