package com.xkcoding.exception.handler.exception;

import cn.hutool.core.lang.Dict;
import com.xkcoding.exception.handler.constant.Status;

/**
 * 自定义业务异常
 *
 * @author FYT
 * @since 2020/4/7
 */
public class BizException extends BaseException {

    public BizException(Status status) {
        super(status);
    }

    public BizException(Status status, Dict attributes) {
        super(status, attributes);
    }

    public BizException(Integer code, String message) {
        super(code, message);
    }

    public BizException(Integer code, String message, Object data) {
        super(code, message, data);
    }

    /**
     * 移动端使用json
     *
     * @return json
     */
    @Override
    public boolean isJson() {
        return true;
    }

    /**
     * 浏览器使用 视图
     *
     * @return 视图
     */
    @Override
    public boolean isView() {
        return true;
    }
}
