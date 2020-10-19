package com.kai.home.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/*
* 此注解标注：
*   loginSuccess的值=true，访问此方法必须先登录后访问
*   loginSuccess的值=false，访问此请求方法可以直接访问，不需要登录
* */
public @interface LoginRequired {

    boolean loginSuccess() default true;

}
