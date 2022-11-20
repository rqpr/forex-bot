package com.dpf.forex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.dpf.forex.mapper")
public class ForexApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForexApplication.class, args);
    }

}