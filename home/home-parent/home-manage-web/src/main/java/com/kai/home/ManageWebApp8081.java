package com.kai.home;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ManageWebApp8081 {

    public static void main(String[] args) {

        SpringApplication.run(ManageWebApp8081.class,args);
    }
}
