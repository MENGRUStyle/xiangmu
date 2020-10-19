package com.kai.home.product.dao;

import com.kai.home.product.pojo.PmsSkuInfo;
import com.kai.home.product.pojo.SelectSkuSaleAttrValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {

    public List<SelectSkuSaleAttrValue> selectSkuSaleAttrValueListBySpuId(@Param("productId") Integer productId);

}
