package com.example.awaysuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwaysuseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwaysuseApplication.class, args);
    }

}
