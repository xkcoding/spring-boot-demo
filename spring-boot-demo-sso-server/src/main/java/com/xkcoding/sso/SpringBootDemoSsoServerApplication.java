package com.xkcoding.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author chen.chao
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootDemoSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoSsoServerApplication.class, args);
    }

}
