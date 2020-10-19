package com.kai.home.product.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.kai.home.product.pojo.PmsBaseSaleAttr;
import com.kai.home.product.service.PmsBaseSaleAttrService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class PmsBaseSaleAttrController {

    @Reference
    private PmsBaseSaleAttrService pmsBaseSaleAttrService;

    @RequestMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList()
    {
        return pmsBaseSaleAttrService.baseSaleAttrList();
    }
}
