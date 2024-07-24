package com.xkcoding.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.xkcoding.component.filters")
public class SpringbootcomponentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootcomponentApplication.class, args);
    }

}
