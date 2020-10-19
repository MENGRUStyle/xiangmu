package com.kai.home.product.service;

import com.kai.home.product.pojo.PmsBaseAttrInfo;
import com.kai.home.product.pojo.PmsBaseAttrValue;

import java.util.List;

public interface PmsBaseAttrInfoService {

    public List<PmsBaseAttrInfo> getPmsBaseAttrInfoByCatalog3Id(Integer catalog3Id);

    //实现平台属性数据保存
    public void savePmsBaseAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    public List<PmsBaseAttrValue> getAttrValueListByAttrId(Integer attrId);


}
