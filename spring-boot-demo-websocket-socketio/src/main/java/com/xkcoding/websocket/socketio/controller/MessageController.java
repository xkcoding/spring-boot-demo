package com.xkcoding.websocket.socketio.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.xkcoding.websocket.socketio.handler.MessageEventHandler;
import com.xkcoding.websocket.socketio.payload.BroadcastMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

/**
 * <p>
 * 消息发送Controller
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.controller
 * @description: 消息发送Controller
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 19:50
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/send")
@Slf4j
public class MessageController {
    @Autowired
    private MessageEventHandler messageHandler;

    @PostMapping("/broadcast")
    public Dict broadcast(BroadcastMessageRequest message) {
        if (isBlank(message)) {
            return Dict.create().set("flag", false).set("code", 400).set("message", "参数为空");
        }
        messageHandler.sendToBroadcast(message);
        return Dict.create().set("flag", true).set("code", 200).set("message", "发送成功");
    }

    /**
     * 判断Bean是否为空对象或者空白字符串，空对象表示本身为<code>null</code>或者所有属性都为<code>null</code>
     *
     * @param bean Bean对象
     * @return 是否为空，<code>true</code> - 空 / <code>false</code> - 非空
     */
    private boolean isBlank(Object bean) {
        if (null != bean) {
            for (Field field : ReflectUtil.getFields(bean.getClass())) {
                Object fieldValue = ReflectUtil.getFieldValue(bean, field);
                if (null != fieldValue) {
                    if (fieldValue instanceof String && StrUtil.isNotBlank((String) fieldValue)) {
                        return false;
                    } else if (!(fieldValue instanceof String)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
