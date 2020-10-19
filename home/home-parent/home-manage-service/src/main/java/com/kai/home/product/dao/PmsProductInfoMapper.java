package com.kai.home.product.dao;

import com.kai.home.product.pojo.PmsProductInfo;
import com.kai.home.product.pojo.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsProductInfoMapper extends Mapper<PmsProductInfo> {

    //根据spuId和skuId，获取选中的销售属性值，及遍历全部spuId对应的销售属性值
    public List<PmsProductSaleAttr> getSpuSalteAttrListCheckBySkuBySql(@Param("productId") Integer productId, @Param("skuId") Integer skuId);

}
