package com.xkcoding.websocket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 键值匹配
 * </p>
 *
 * @package: com.xkcoding.websocket.payload
 * @description: 键值匹配
 * @author: yangkai.shen
 * @date: Created in 2018-12-14 17:41
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KV {
    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private Object value;
}
