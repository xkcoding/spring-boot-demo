package com.xkcoding.util;

import com.google.common.collect.Maps;
import com.sun.istack.internal.NotNull;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import com.xkcoding.util.domain.ShortUrlRet;

import java.util.Map;

/**
 * <p>
 * 高频工具类
 * </p>
 *
 * @package: com.xkcoding.util
 * @description： 高频工具类
 * @author: yangkai.shen
 * @date: Created in 2017/12/1 下午4:24
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
public class T {
	private static final String URL_SEPARATOR = "//";
	private static final String URL_PROTOCOL = "http";

	/**
	 * 获取 UUID
	 *
	 * @return 返回没有“-”的 UUID
	 */
	public static String UUID() {
		return RandomUtil.randomUUID().replace("-", "");
	}

	/**
	 * 长地址转短地址
	 *
	 * @param longUrl 长地址
	 * @return 长地址转化后的短地址
	 */
	public static String shortURL(@NotNull String longUrl) {
		Map<String, Object> params = Maps.newHashMap();
		if (StrUtil.isEmpty(longUrl)) {
			return null;
		}
		if (!longUrl.contains(URL_SEPARATOR)) {
			longUrl = URL_PROTOCOL + ":" + URL_SEPARATOR + longUrl;
		}
		params.put("url", longUrl);
		params.put("email", "ws@parg.co");
		params.put("api_key", "4786a32f20f79a70b1ced1bf242a120e");
		String data = HttpUtil.post("https://parg.co/api/shorten", params);
		JSONObject ret = JSONUtil.parseObj(data);
		ShortUrlRet shortUrlRet = ret.toBean(ShortUrlRet.class);
		return shortUrlRet.getShort_url();
	}

}
