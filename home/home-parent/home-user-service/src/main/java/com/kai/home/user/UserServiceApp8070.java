package com.kai.home.user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.kai.home.user.dao")
public class UserServiceApp8070 {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp8070.class,args);
    }
}
