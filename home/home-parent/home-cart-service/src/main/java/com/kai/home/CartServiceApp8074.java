package com.kai.home;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "com.kai.home.cart.dao")
public class CartServiceApp8074 {

	public static void main(String[] args) {
		SpringApplication.run(CartServiceApp8074.class, args);
	}

}
