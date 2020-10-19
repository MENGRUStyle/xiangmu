package com.kai.home.product.service.impl;

import com.kai.home.product.dao.PmsBaseAttrInfoMapper;
import com.kai.home.product.dao.PmsBaseAttrValueMapper;
import com.kai.home.product.pojo.PmsBaseAttrInfo;
import com.kai.home.product.pojo.PmsBaseAttrValue;
import com.kai.home.product.service.PmsBaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PmsBaseAttrInfoServiceImpl implements PmsBaseAttrInfoService {

    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> getPmsBaseAttrInfoByCatalog3Id(Integer catalog3Id) {

        PmsBaseAttrInfo pmsBaseAttrInfo=new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);

        //连同属性值也遍历输出
        List<PmsBaseAttrInfo> outPmsBaseAttrInfoList=new ArrayList<>();
        //select * from pms_base_attr_info where catalog3_id=catalog3Id
        List<PmsBaseAttrInfo> pmsBaseAttrInfoList=pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);

        for (PmsBaseAttrInfo baseAttrInfo : pmsBaseAttrInfoList) {

            //select * from pms_base_attr_value where attrId=平台属性名id
            PmsBaseAttrValue pmsBaseAttrValue=new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
            List<PmsBaseAttrValue> pmsBaseAttrValueList =pmsBaseAttrValueMapper.select(pmsBaseAttrValue);

            baseAttrInfo.setAttrValueList(pmsBaseAttrValueList);

            outPmsBaseAttrInfoList.add(baseAttrInfo);

        }
        return outPmsBaseAttrInfoList;
    }

    /*
    * 实现平台属性保存/修改功能
    * */
    @Override
    @Transactional
    public void savePmsBaseAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {

        Integer id=pmsBaseAttrInfo.getId();

        //如果id值等于null，则实现添加数据功能
        if(id==null)
        {
            //将数据存储pms_base_attr_info表
            pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfo);

            for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {

                //将成功添加属性平台表的主键数据回设到属性值的外键里面
                //pmsBaseAttrValue.setAttrId(id);  //error
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                //将属性值存储到pms_base_attr_value表
                pmsBaseAttrValueMapper.insert(pmsBaseAttrValue);

            }
        }else {

            //实现修改功能
            pmsBaseAttrInfoMapper.updateByPrimaryKey(pmsBaseAttrInfo);

            //针对从表：修改=先删除+后添加

            PmsBaseAttrValue pmsBaseAttrValue=new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            //先删除从表数据
            pmsBaseAttrValueMapper.delete(pmsBaseAttrValue);
            //后添加从表数据
            for (PmsBaseAttrValue pmsBaseAttrValue2 : pmsBaseAttrInfo.getAttrValueList()) {

                //将成功添加属性平台表的主键数据回设到属性值的外键里面
                pmsBaseAttrValue2.setAttrId(id);
                //将属性值存储到pms_base_attr_value表
                pmsBaseAttrValueMapper.insert(pmsBaseAttrValue2);

            }

        }



    }

    //根据attrId的数据值，获取对应的属性值列表
    @Override
    public List<PmsBaseAttrValue> getAttrValueListByAttrId(Integer attrId) {

        // select * from pms_base_attr_value where attr_id=attrId;
        PmsBaseAttrValue pmsBaseAttrValue=new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        return pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
    }
}
