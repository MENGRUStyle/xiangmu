package com.kai.home.product.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kai.home.product.pojo.PmsSkuInfo;
import com.kai.home.product.service.PmsSkuInfoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PmsSkuInfoController {

    @Reference
    private PmsSkuInfoService pmsSkuInfoService;

    @RequestMapping("/saveSkuInfo")
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo)
    {

        pmsSkuInfoService.saveSkuInfo(pmsSkuInfo);

        return "success";
    }
}
