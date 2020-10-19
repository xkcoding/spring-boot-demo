package com.xkcoding.mq.rabbitmq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 测试消息体
 * </p>
 *
 * @package: com.xkcoding.mq.rabbitmq.message
 * @description: 测试消息体
 * @author: yangkai.shen
 * @date: Created in 2018-12-29 16:22
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageStruct implements Serializable {
    private static final long serialVersionUID = 392365881428311040L;

    private String message;
}
