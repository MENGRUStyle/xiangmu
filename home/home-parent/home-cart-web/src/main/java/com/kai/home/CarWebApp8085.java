package com.kai.home;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class CarWebApp8085 {

    public static void main(String[] args) {

        SpringApplication.run(CarWebApp8085.class,args);

    }
}
