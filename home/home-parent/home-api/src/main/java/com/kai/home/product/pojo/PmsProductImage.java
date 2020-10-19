package com.kai.home.product.pojo;

import java.io.Serializable;

public class PmsProductImage implements Serializable {

    private Integer id;
    //spu的主键
    private Integer productId;
    private String imgName;
    private String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    @Override
    public String toString() {
        return "PmsProductImage{" +
                "id=" + id +
                ", productId=" + productId +
                ", imgName='" + imgName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
