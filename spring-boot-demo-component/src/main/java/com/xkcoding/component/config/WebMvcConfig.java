package com.xkcoding.component.config;


import com.xkcoding.component.filters.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(myInterceptor()) //指定拦截器类
                .addPathPatterns("/**") // /** 代表拦截所有请求
                .excludePathPatterns("/login"); // 添加不拦截的请求
        }
        //配置bean
        @Bean
        MyInterceptor myInterceptor() {
            return new MyInterceptor();
        }



}
