package com.kai.home.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kai.home.user.pojo.UmsMember;
import com.kai.home.user.service.UmsMemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UmsmemberController {

    @Reference
    private UmsMemberService umsMemberService;

    @RequestMapping("/getUmsMemberAllList")
    public List<UmsMember> getUmsMemberAllList()
    {
        return umsMemberService.getAllUmsMember();
    }
}
