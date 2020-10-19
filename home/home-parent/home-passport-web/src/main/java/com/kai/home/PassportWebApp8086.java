package com.kai.home;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableDubbo
public class PassportWebApp8086 {

    public static void main(String[] args) {

        SpringApplication.run(PassportWebApp8086.class,args);

    }
}
