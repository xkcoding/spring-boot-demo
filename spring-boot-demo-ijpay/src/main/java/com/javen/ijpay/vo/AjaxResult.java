package com.javen.ijpay.vo;

import java.io.Serializable;

/**
 * <p>IJPay 让支付触手可及，封装了微信支付、支付宝支付、银联支付常用的支付方式以及各种常用的接口。</p>
 *
 * <p>不依赖任何第三方 mvc 框架，仅仅作为工具使用简单快速完成支付模块的开发，可轻松嵌入到任何系统里。 </p>
 *
 * <p>IJPay 交流群: 723992875</p>
 *
 * <p>Node.js 版: https://gitee.com/javen205/TNWX</p>
 *
 * @author Javen
 */
public class AjaxResult implements Serializable {

    private static final long serialVersionUID = 6439646269084700779L;

    private int code = 0;

    /**
     * 返回的中文消息
     */
    private String message;

    /**
     * 成功时携带的数据
     */
    private Object data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 校验错误
     *
     * @return
     */
    public boolean hasError() {
        return this.code != 0;
    }

    /**
     * 添加错误，用于alertError
     *
     * @param message
     * @return
     */
    public AjaxResult addError(String message) {
        this.message = message;
        this.code = 1;
        return this;
    }

    /**
     * 用于Confirm的错误信息
     *
     * @param message 描述消息
     * @return {AjaxResult}
     */
    public AjaxResult addConfirmError(String message) {
        this.message = message;
        this.code = 2;
        return this;
    }

    /**
     * 封装成功时的数据
     *
     * @param data Object
     * @return {AjaxResult}
     */
    public AjaxResult success(Object data) {
        this.data = data;
        this.code = 0;
        return this;
    }

}