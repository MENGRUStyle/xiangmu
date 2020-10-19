package com.kai.home.product.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class PmsSkuImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer skuId;
    private String imgName;
    private String imgUrl;
    @Column(name = "product_img_id")
    private Integer spuImgId;
    private String isDefault;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public Integer getSpuImgId() {
        return spuImgId;
    }

    public void setSpuImgId(Integer spuImgId) {
        this.spuImgId = spuImgId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "PmsSkuImage{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", imgName='" + imgName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", spuImgId=" + spuImgId +
                ", isDefault='" + isDefault + '\'' +
                '}';
    }
}
