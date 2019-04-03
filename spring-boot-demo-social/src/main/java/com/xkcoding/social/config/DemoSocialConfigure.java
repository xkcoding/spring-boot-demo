package com.xkcoding.social.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * <p>
 * 继承默认的社交登录配置，加入自定义的后处理逻辑
 * </p>
 *
 * @package: com.xkcoding.social.config
 * @description: 继承默认的社交登录配置，加入自定义的后处理逻辑
 * @author: yangkai.shen
 * @date: Created in 2019-02-21 14:01
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class DemoSocialConfigure extends SpringSocialConfigurer {
    private String filterProcessUrl;

    public DemoSocialConfigure(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) super.postProcess(object);
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessUrl);
        return (T) socialAuthenticationFilter;
    }
}
