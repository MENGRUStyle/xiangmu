package com.kai.home.product.service.impl;

import com.kai.home.product.dao.PmsBaseSaleAttrMapper;
import com.kai.home.product.pojo.PmsBaseSaleAttr;
import com.kai.home.product.service.PmsBaseSaleAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PmsBaseSaleAttrServiceImpl implements PmsBaseSaleAttrService {

    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;


    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }
}
