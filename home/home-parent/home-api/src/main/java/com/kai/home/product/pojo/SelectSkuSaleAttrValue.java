package com.kai.home.product.pojo;

import java.io.Serializable;
import java.util.List;

public class SelectSkuSaleAttrValue implements Serializable {

    private Integer skuId;
    private List<PmsSkuSaleAttrValue> saleAttrValueIdList;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public List<PmsSkuSaleAttrValue> getSaleAttrValueIdList() {
        return saleAttrValueIdList;
    }

    public void setSaleAttrValueIdList(List<PmsSkuSaleAttrValue> saleAttrValueIdList) {
        this.saleAttrValueIdList = saleAttrValueIdList;
    }
}
