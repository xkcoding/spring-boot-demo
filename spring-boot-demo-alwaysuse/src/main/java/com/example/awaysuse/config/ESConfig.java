package com.example.awaysuse.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: fengh
 * @Date: 2020/7/2 17:04
 * @Description:
 */
@Configuration
public class ESConfig {

    @Value("${elasticsearch.hosts}")
    private String hosts;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.passowrd}")
    private String password;
    @Value("${elasticsearch.schema}")
    private String schema;
    @Value("${elasticsearch.connectTimeOut}")
    private int connectTimeOut;
    @Value("${elasticsearch.socketTimeOut}")
    private int socketTimeOut;
    @Value("${elasticsearch.connectionRequestTimeOut}")
    private int connectionRequestTimeOut;
    @Value("${elasticsearch.maxConnectNum}")
    private int maxConnectNum;
    @Value("${elasticsearch.maxConnectPerRoute}")
    private int maxConnectPerRoute;

    @Bean("restHighLevelClient")
    public RestHighLevelClient client(){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        List<HttpHost> hostList = new ArrayList<>();
        String[] hostArray = hosts.split(",");
        for (String host : hostArray) {
            String[] ipPort = host.split(":");
            hostList.add(new HttpHost(ipPort[0], Integer.parseInt(ipPort[1]), schema));
        }
        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
        // 异步httpclient连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeOut);
            requestConfigBuilder.setSocketTimeout(socketTimeOut);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
            return requestConfigBuilder;
        });
        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }

}
