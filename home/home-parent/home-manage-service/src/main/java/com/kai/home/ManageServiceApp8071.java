package com.kai.home;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.kai.home.product.dao")
public class ManageServiceApp8071 {

    public static void main(String[] args) {

        SpringApplication.run(ManageServiceApp8071.class,args);
    }
}
