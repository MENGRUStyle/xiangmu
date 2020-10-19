package com.kai.home.product.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

public class PmsBaseAttrInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String attrName;
    private Integer catalog3Id;
    private String isEnabled;

    //存储平台属性对应的value值数据
    private List<PmsBaseAttrValue> attrValueList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Integer getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(Integer catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<PmsBaseAttrValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<PmsBaseAttrValue> attrValueList) {
        this.attrValueList = attrValueList;
    }

    @Override
    public String toString() {
        return "PmsBaseAttrInfo{" +
                "id=" + id +
                ", attrName='" + attrName + '\'' +
                ", catalog3Id=" + catalog3Id +
                ", isEnabled='" + isEnabled + '\'' +
                '}';
    }
}
