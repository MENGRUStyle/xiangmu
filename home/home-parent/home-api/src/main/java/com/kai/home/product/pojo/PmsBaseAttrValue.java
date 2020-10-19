package com.kai.home.product.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class PmsBaseAttrValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String valueName;
    private Integer attrId;
    private String isEnabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "PmsBaseAttrValue{" +
                "id=" + id +
                ", valueName='" + valueName + '\'' +
                ", attrId=" + attrId +
                ", isEnabled='" + isEnabled + '\'' +
                '}';
    }
}
