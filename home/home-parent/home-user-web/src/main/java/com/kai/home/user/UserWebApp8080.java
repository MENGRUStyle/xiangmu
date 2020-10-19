package com.kai.home.user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class UserWebApp8080 {
    public static void main(String[] args) {
        SpringApplication.run(UserWebApp8080.class,args);
    }
}
