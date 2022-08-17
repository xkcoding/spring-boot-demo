package com.xkcoding.websocket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 键值匹配
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-14 17:41
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
