package com.kai.oasys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    /*
    * 跳转到首页
    * */
    @RequestMapping("/index")
    public String index()
    {
        return "sys/index";
    }

    /*
     * 跳转到首页
     * */
    @RequestMapping("/main")
    public String main()
    {
        return "sys/main";
    }
}
