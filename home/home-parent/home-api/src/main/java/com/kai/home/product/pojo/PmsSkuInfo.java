package com.kai.home.product.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/*
* sku的主表内容
* */
public class PmsSkuInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //spuId
    @Column(name = "product_id")
    private Integer spuId;
    private Double price;
    private String skuName;
    private String skuDesc;
    private Double weight;
    private Integer tmId;
    private Integer catalog3Id;
    private String skuDefaultImg;

    //存储sku分配那些平台属性值
    private List<PmsSkuAttrValue> skuAttrValueList;
    //存储sku分配那些销售属性值
    private List<PmsSkuSaleAttrValue> skuSaleAttrValueList;
    //存储图片
    private List<PmsSkuImage> skuImageList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getTmId() {
        return tmId;
    }

    public void setTmId(Integer tmId) {
        this.tmId = tmId;
    }

    public Integer getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Integer catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    public List<PmsSkuSaleAttrValue> getSkuSaleAttrValueList() {
        return skuSaleAttrValueList;
    }

    public void setSkuSaleAttrValueList(List<PmsSkuSaleAttrValue> skuSaleAttrValueList) {
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }

    public List<PmsSkuImage> getSkuImageList() {
        return skuImageList;
    }

    public void setSkuImageList(List<PmsSkuImage> skuImageList) {
        this.skuImageList = skuImageList;
    }


}
