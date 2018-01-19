package com.xkcoding.springbootdemoelasticsearch.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * ES 的配置类
 * </p>
 *
 * @package: com.xkcoding.springbootdemoelasticsearch.config
 * @description： ES 的配置类
 * @author: yangkai.shen
 * @date: Created in 2018/1/18 下午4:41
 * @copyright: Copyright (c) 2018
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Configuration
public class ElasticSearchConfig {
	@Value("${elasticsearch.host}")
	private String host;

	@Value("${elasticsearch.port}")
	private int port;

	@Value("${elasticsearch.cluster.name}")
	private String clusterName;

	@Bean
	public TransportClient esClient() throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", this.clusterName).put("client.transport.sniff", true).build();

		TransportAddress master = new TransportAddress(InetAddress.getByName(host), port);
		TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(master);
		return client;
	}
}
