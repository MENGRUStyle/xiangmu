package com.kai.home.product.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PmsSearchSkuInfo implements Serializable {

    private Integer id;
    private String skuName;
    private String skuDesc;
    private Integer catalog3Id;
    private BigDecimal price;
    private String skuDefaultImg;
    private double hotScore;
    private Integer productId;
    private List<PmsSkuAttrValue> skuAttrValueList;



    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }



    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public double getHotScore() {
        return hotScore;
    }

    public void setHotScore(double hotScore) {
        this.hotScore = hotScore;
    }


    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Integer catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
