package com.kai.home.product.service.impl;

import com.kai.home.product.dao.PmsBaseCatalog1Mapper;
import com.kai.home.product.pojo.PmsBaseCatalog1;
import com.kai.home.product.service.PmsBaseCatalog1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PmsBaseCatalog1ServiceImpl implements PmsBaseCatalog1Service {

    @Autowired
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Override
    public List<PmsBaseCatalog1> getPmsBaseCatalog1AllList() {
        return pmsBaseCatalog1Mapper.selectAll();
    }
}
