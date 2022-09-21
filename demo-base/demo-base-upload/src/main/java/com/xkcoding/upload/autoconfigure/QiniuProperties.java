package com.xkcoding.upload.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * 七牛云配置
 * </p>
 *
 * @author yangkai.shen
 * @date 2022-09-21 16:09
 */

@Data
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String prefix;
}
