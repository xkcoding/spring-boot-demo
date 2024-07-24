package com.example.awaysuse.util;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtils {

    private static final Log LOG = LogFactory.getLog(HttpClientUtils.class);

    private static RequestConfig requestConfig = null;

    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 60 * 60).setConnectTimeout(1000 * 60 * 60).build();
    }

    /**
     * post请求
     * 解决没有响应信息（是因为之前没有释放连接，导致获取不到响应）
     *
     * @param uri
     * @param body
     */
    public static Map<String, String> post(String uri, JSONObject header, JSONObject body, Integer timeout) {
        Map<String, String> retMap = new HashMap<>();
        // 创建请求对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        try {
            // 设置请求头信息
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            if (header != null) {
                for (String key : header.keySet()) {
                    Object value = header.get(key);
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        httpPost.addHeader(key, (value == null) ? null : String.valueOf(value));
                    }
                }
            }
            LOG.info("请求头" + httpPost);
            // 设置请求体信息
            String jsonstr = com.alibaba.fastjson.JSON.toJSONString(body);
            StringEntity entity = new StringEntity(jsonstr, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json;charset=utf-8");
            httpPost.setEntity(entity);

            // 发出post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //解析响应结果
            retMap.put("status", "success");
            int statusCode = response.getStatusLine().getStatusCode();
            retMap.put("statusCode", statusCode + "");
            if (statusCode == 200) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                String loginToken = response.getFirstHeader("X-Auth-Token").getValue();
                LOG.info("返回的token" + loginToken);
                retMap.put("result", result);
                if (loginToken != null) {
                    retMap.put("loginToken", loginToken);
                }
                retMap.put("loginToken", loginToken);
            } else if (statusCode == 201) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                String loginToken = response.getFirstHeader("X-Auth-Token").getValue();
                LOG.info("返回的token" + loginToken);
                retMap.put("result", result);
                if (loginToken != null) {
                    retMap.put("loginToken", loginToken);
                }

            } else {
                retMap.put("result", EntityUtils.toString(response.getEntity(), "utf-8"));
            }

        } catch (Exception e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            retMap.put("status", "failed");
            retMap.put("exMsg", e.getLocalizedMessage());
        } finally {
            httpPost.releaseConnection();
        }
        return retMap;
    }


    public static Map<String, String> postLogin(String uri, JSONObject header, JSONObject body, Integer timeout) {

        Map<String, String> retMap = new HashMap<>();
        // 创建请求对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        try {
            // 设置请求头信息
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            if (header != null) {
                for (String key : header.keySet()) {
                    Object value = header.get(key);
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        httpPost.addHeader(key, (value == null) ? null : String.valueOf(value));
                    }
                }
            }
            LOG.info("请求头" + httpPost);
            // 设置请求体信息
            String jsonstr = com.alibaba.fastjson.JSON.toJSONString(body);
            StringEntity entity = new StringEntity(jsonstr, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json;charset=utf-8");
            httpPost.setEntity(entity);

            // 发出post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //解析响应结果
            retMap.put("status", "success");
            int statusCode = response.getStatusLine().getStatusCode();
            retMap.put("statusCode", statusCode + "");
            LOG.info("返回的状态码是： " + statusCode);
            if (statusCode == 201) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                String loginToken = response.getFirstHeader("X-Auth-Token").getValue();
                LOG.info("返回的token" + loginToken);
                retMap.put("result", result);
                if (loginToken != null) {
                    retMap.put("loginToken", loginToken);
                }

            } else {
                retMap.put("result", EntityUtils.toString(response.getEntity(), "utf-8"));
            }

        } catch (Exception e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            retMap.put("status", "failed");
            retMap.put("exMsg", e.getLocalizedMessage());
        } finally {
            httpPost.releaseConnection();
        }
        return retMap;
    }


    public static Map<String, String> post(String uri, JSONObject header, String body, Integer timeout) {
        Map<String, String> retMap = new HashMap<>();
        // 创建请求对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        try {

            // 设置请求头信息
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            if (header != null) {
                for (String key : header.keySet()) {
                    Object value = header.get(key);
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        httpPost.addHeader(key, (value == null) ? null : String.valueOf(value));
                    }
                }
            }
            // 设置请求体信息
            StringEntity entity = new StringEntity(body, "utf-8");
            //entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            // 设置超时时间
/*            if (timeout != null) {
                httpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            }*/
            // 发出post请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //解析响应结果
            retMap.put("status", "success");
            int statusCode = response.getStatusLine().getStatusCode();
            retMap.put("statusCode", statusCode + "");
            if (statusCode == 200) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                retMap.put("result", result);
            } else {
                retMap.put("result", EntityUtils.toString(response.getEntity(), "utf-8"));
            }

        } catch (Exception e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            retMap.put("status", "failed");
            retMap.put("exMsg", e.getLocalizedMessage());
        } finally {
            httpPost.releaseConnection();
        }
        return retMap;
    }


    /**
     * get请求
     *
     * @param uri
     * @param body
     */
    public static Map<String, String> get(String uri, JSONObject header, JSONObject body) {
        return get(uri, header, body, null);
    }

    /**
     * get请求
     *
     * @param uri
     * @param body
     */
    public static Map<String, String> get(String uri, JSONObject header, JSONObject body, Integer timeout) {
        Map<String, String> retMap = new HashMap<>();
        HttpGet request = new HttpGet(uri);
        try {
            HttpClient client = HttpClients.createDefault();//创建HttpClient对象

            if (header != null) {
                for (String key : header.keySet()) {
                    Object value = header.get(key);
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        request.addHeader(key, (value == null) ? null : String.valueOf(value));
                    }
                }
            }
            HttpResponse response = client.execute(request);//发送get请求
            int statusCode = response.getStatusLine().getStatusCode();

            retMap.put("status", "success");
            retMap.put("statusCode", statusCode + "");

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
                retMap.put("result", strResult);
            } else {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                    retMap.put("result", "");
                } else {
                    retMap.put("result", EntityUtils.toString(response.getEntity()));
                }
            }
        } catch (IOException e) {

            LOG.info(ExceptionUtils.getFullStackTrace(e));
            retMap.put("status", "failed");
            retMap.put("exMsg", e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            request.releaseConnection();
        }
        return retMap;
    }

    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    LOG.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            LOG.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url      url地址
     * @param strParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, String strParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("utf-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    LOG.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            LOG.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(strResult);
            } else {
                LOG.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            LOG.error("get请求提交失败:" + url, e);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }


    //httpput请求
    public static Map<String, String> httpPut(String uri, JSONObject header, String body, Integer timeout) {
        Map<String, String> retMap = new HashMap<>();
        // 创建请求对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(uri);
        httpput.setConfig(requestConfig);
        try {

            // 设置请求头信息
            httpput.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            if (header != null) {
                for (String key : header.keySet()) {
                    Object value = header.get(key);
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        httpput.addHeader(key, (value == null) ? null : String.valueOf(value));
                    }
                }
            }
            // 设置请求体信息
            StringEntity entity = new StringEntity(body, "utf-8");
            //entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpput.setEntity(entity);

            // 设置超时时间
/*            if (timeout != null) {
                httpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            }*/
            // 发出post请求
            CloseableHttpResponse response = httpClient.execute(httpput);
            //解析响应结果
            retMap.put("status", "success");
            int statusCode = response.getStatusLine().getStatusCode();
            retMap.put("statusCode", statusCode + "");
            if (statusCode == 200) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                retMap.put("result", result);
            } else {
                retMap.put("result", EntityUtils.toString(response.getEntity(), "utf-8"));
            }

        } catch (Exception e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            retMap.put("status", "failed");
            retMap.put("exMsg", e.getLocalizedMessage());
        } finally {
            httpput.releaseConnection();
        }
        return retMap;
    }

}
