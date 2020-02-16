package com.mooc.boss.house.web;

import com1.mooc.boss.house.web.config.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mooc.boss.house"})
@EnableHttpClient
@EnableAsync
public class HouseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseWebApplication.class, args);
    }

}
