package com.javen.ijpay.bean;


import com.alibaba.fastjson.JSON;

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
public class H5SceneInfo {
    private H5 h5_info;

    public H5 getH5Info() {
        return h5_info;
    }

    public void setH5Info(H5 h5_info) {
        this.h5_info = h5_info;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    public static class H5 {
        private String type;
        private String app_name;
        private String bundle_id;
        private String package_name;
        private String wap_url;
        private String wap_name;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getBundle_id() {
            return bundle_id;
        }

        public void setBundle_id(String bundle_id) {
            this.bundle_id = bundle_id;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getWap_url() {
            return wap_url;
        }

        public void setWap_url(String wap_url) {
            this.wap_url = wap_url;
        }

        public String getWap_name() {
            return wap_name;
        }

        public void setWap_name(String wap_name) {
            this.wap_name = wap_name;
        }
    }
}