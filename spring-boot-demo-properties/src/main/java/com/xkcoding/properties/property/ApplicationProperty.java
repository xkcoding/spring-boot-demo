package com.xkcoding.properties.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 项目配置
 * </p>
 *
 * @package: com.xkcoding.properties.property
 * @description: 项目配置
 * @author: yangkai.shen
 * @date: Created in 2018/9/29 10:50 AM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@Component
public class ApplicationProperty {
	@Value("${application.name}")
	private String name;
	@Value("${application.version}")
	private String version;
}
