package com.kai.home.product.service;

import com.kai.home.product.pojo.*;

import java.util.List;

public interface PmsSkuInfoService {

    //将sku的数据保存到数据表
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo);
    //根据skuId，获取sku的数据
    public PmsSkuInfo findPmsSkuInfoById(Integer skuId);

    public List<SelectSkuSaleAttrValue> selectSkuSaleAttrValueListBySpuId(Integer spuId);

    //获取全部的sku数据
    public List<PmsSkuInfo> getAllSku();

}
