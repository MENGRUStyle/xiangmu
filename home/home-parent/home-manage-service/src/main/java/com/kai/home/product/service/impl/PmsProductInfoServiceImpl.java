package com.kai.home.product.service.impl;

import com.kai.home.product.dao.PmsProductImageMapper;
import com.kai.home.product.dao.PmsProductInfoMapper;
import com.kai.home.product.dao.PmsProductSaleAttrMapper;
import com.kai.home.product.dao.PmsProductSaleAttrValueMapper;
import com.kai.home.product.pojo.PmsProductImage;
import com.kai.home.product.pojo.PmsProductInfo;
import com.kai.home.product.pojo.PmsProductSaleAttr;
import com.kai.home.product.pojo.PmsProductSaleAttrValue;
import com.kai.home.product.service.PmsProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PmsProductInfoServiceImpl implements PmsProductInfoService {

    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    private PmsProductImageMapper pmsProductImageMapper;

    /*
    * 根据商品的三级分类id，获取spu的数据
    * */
    @Override
    public List<PmsProductInfo> getSpuListBycatalog3Id(Integer catalog3Id) {

        // select * from pms_product_info where catalog3_id=catalog3Id
        PmsProductInfo pmsProductInfo=new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);

        return pmsProductInfoMapper.select(pmsProductInfo);
    }

    /*
    * 添加spu的数据添加
    * */
    @Override
    @Transactional
    public void saveSpuInfo(PmsProductInfo pmsProductInfo) {

        //将数据存储到spu的主表（pms_product_info）
        pmsProductInfoMapper.insert(pmsProductInfo);

        //将数据存储到spu对应的销售属性表（pms_product_sale_attr）
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductInfo.getSpuSaleAttrList()) {

            //将spu的主键赋值给pms_product_sale_attr的外键
            pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
            pmsProductSaleAttrMapper.insert(pmsProductSaleAttr);
            //将spu对应的销售属性名的分配添加对应值存储到pms_product_sale_attr_value
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttr.getSpuSaleAttrValueList()) {

                pmsProductSaleAttrValue.setProductId(pmsProductInfo.getId());
                pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue);
            }
        }

        //保存到pms_product_image表
        for (PmsProductImage pmsProductImage : pmsProductInfo.getSpuImageList()) {

            //将spu的主键赋值此productId字段
            pmsProductImage.setProductId(pmsProductInfo.getId());
            pmsProductImageMapper.insert(pmsProductImage);

        }
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(Integer spuId) {

        PmsProductSaleAttr pmsProductSaleAttr=new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);

        List<PmsProductSaleAttr> outPmsProductSaleAttrList=new ArrayList<>();

        //select * from pms_product_sale_attr where product_id=spuId
        List<PmsProductSaleAttr> pmsProductSaleAttrList=pmsProductSaleAttrMapper.select(pmsProductSaleAttr);

        //销售属性名对应那些值
        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrList) {

            //select * from pms_product_sale_attr_value where product_id=spuId and sale_attr_id=productSaleAttr.getSaleAttrId()
            PmsProductSaleAttrValue pmsProductSaleAttrValue=new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setProductId(spuId);
            pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList=pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);

            productSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValueList);

            outPmsProductSaleAttrList.add(productSaleAttr);

        }

        return outPmsProductSaleAttrList;
    }

    @Override
    public List<PmsProductImage> getSpuImageList(Integer spuId) {

        //select * from pms_product_image where product_id=spuId;
        PmsProductImage pmsProductImage=new PmsProductImage();
        pmsProductImage.setProductId(spuId);

        return pmsProductImageMapper.select(pmsProductImage);
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSalteAttrListCheckBySkuBySql(Integer productId,Integer skuId){

        return pmsProductInfoMapper.getSpuSalteAttrListCheckBySkuBySql(productId,skuId);
    }
}
