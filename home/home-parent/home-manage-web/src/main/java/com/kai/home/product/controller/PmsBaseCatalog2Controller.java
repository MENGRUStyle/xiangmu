package com.kai.home.product.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.kai.home.product.pojo.PmsBaseCatalog2;
import com.kai.home.product.service.PmsBaseCatalog2Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class PmsBaseCatalog2Controller {

    @Reference
    private PmsBaseCatalog2Service pmsBaseCatalog2Service;

    @RequestMapping("/getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2(Integer catalog1Id)
    {
        return pmsBaseCatalog2Service.getPmsBaseCatalog2(catalog1Id);
    }
}
