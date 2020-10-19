package com.kai.home.product.service.impl;

import com.kai.home.product.dao.PmsBaseCatalog2Mapper;
import com.kai.home.product.pojo.PmsBaseCatalog2;
import com.kai.home.product.service.PmsBaseCatalog2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PmsBaseCatalog2ServiceImpl implements PmsBaseCatalog2Service {

    @Autowired
    private PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;


    @Override
    public List<PmsBaseCatalog2> getPmsBaseCatalog2(Integer catalogId) {

        //select * from pms_base_catalog2 where catalog1_id=catalogId;

        PmsBaseCatalog2 pmsBaseCatalog2=new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalogId);

        return pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);
    }
}
