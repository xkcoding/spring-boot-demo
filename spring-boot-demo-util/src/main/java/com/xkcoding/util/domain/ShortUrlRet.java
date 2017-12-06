package com.xkcoding.util.domain;

import lombok.Data;

/**
 * <p>
 * 短地址请求返回类型
 * </p>
 *
 * @package: com.xkcoding.util.domain
 * @description： 短地址请求返回类型
 * @author: yangkai.shen
 * @date: Created in 2017/12/4 上午11:29
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
@Data
public class ShortUrlRet {
	private String short_url;
	private String long_encoded;
	private String long_decoded;
	private String status;
}
