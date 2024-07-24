package com.example.awaysuse.util;


import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by er on 2018/11/16.
 */
@Component
public class OkhttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(OkhttpUtil.class);
    public static String post(String json, String url){
        logger.info("查询的json为：{}，url为：{}",json,url);
        String courses=null;
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);

        okHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);

        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        MediaType mediaType= MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        //创建一个请求对象

        Request request = new Request
                .Builder()
                .url(url)
                .post(requestBody)
                .build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){

                courses=response.body().string();
                System.out.println("请求成功");
            }else{
                System.out.println("錯誤"+"-----"+response.code()+response.body().string());

            }
        }catch(IOException e) {
            e.printStackTrace();
        }

        return courses;
    }



        public static String get(String url){
            String courses=null;
            //创建一个OkHttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);

            okHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);

            okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
            //创建一个请求对象
            Request request = new Request
                    .Builder()
                    .url(url)
                    .build();
            //发送请求获取响应
            try {
                Response response=okHttpClient.newCall(request).execute();
                //判断请求是否成功
                if(response.isSuccessful()){

                    courses=response.body().string();
                    System.out.println("请求成功");
                }else{
                    System.out.println("錯誤"+"-----"+response.code()+response.body().string());

                }
            }catch(IOException e) {
                e.printStackTrace();
            }

            return courses;
        }

    public static String postWithHead(String alretUrl,String paramBody, String cookie) {
        logger.info("查询的json为：{}，url为：{},param:{},cookie:{}",alretUrl,paramBody,cookie);
        String courses="";
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);

        okHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);

        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        MediaType mediaType= MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, paramBody);

        //创建一个请求对象

        Request request = new Request
                .Builder()
                .url(alretUrl)
                .post(requestBody).addHeader("Cookie",cookie)
                .build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){

                courses=response.body().string();
                logger.info("请求成功");
            }else{
                logger.error("錯誤"+"-----"+response.code()+response.body().string());

            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
