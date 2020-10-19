package com.kai.home.product.service.impl;

import com.kai.home.product.dao.PmsBaseCatalog3Mapper;
import com.kai.home.product.pojo.PmsBaseCatalog3;
import com.kai.home.product.service.PmsBaseCatalog3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PmsBaseCatalog3ServiceImpl implements PmsBaseCatalog3Service {

    @Autowired
    private PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog3> getPmsBaseCatalog3(Integer catalog2Id) {

        //select * from pms_base_catalog3 where catalog2_id=catalog2Id;
        PmsBaseCatalog3 pmsBaseCatalog3=new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);

        return pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
    }
}
