package com.kai.home.product.pojo;

import java.io.Serializable;
import java.util.List;

public class PmsSearchParam implements Serializable {

    //三级目录检索
    private Integer catalog3Id;
    //关键字检索
    private String keyword;
    //平台属性检索
    private List<PmsSkuAttrValue> skuAttrValueList;

    public Integer getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Integer catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }
}