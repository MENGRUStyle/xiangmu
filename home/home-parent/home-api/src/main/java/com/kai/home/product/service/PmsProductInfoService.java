package com.kai.home.product.service;

import com.kai.home.product.pojo.PmsProductImage;
import com.kai.home.product.pojo.PmsProductInfo;
import com.kai.home.product.pojo.PmsProductSaleAttr;

import java.util.List;

public interface PmsProductInfoService {

    public List<PmsProductInfo> getSpuListBycatalog3Id(Integer catalog3Id);

    //添加spu数据
    public void saveSpuInfo(PmsProductInfo pmsProductInfo);

    //获取spu对应那些销售属性
    public List<PmsProductSaleAttr> spuSaleAttrList(Integer spuId);

    //获取spu对应的图片列表
    public List<PmsProductImage> getSpuImageList(Integer spuId);

    public List<PmsProductSaleAttr> getSpuSalteAttrListCheckBySkuBySql(Integer productId, Integer skuId);

}
