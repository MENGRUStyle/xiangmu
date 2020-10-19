package com.kai.home.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.kai.home.product.pojo.PmsProductSaleAttr;
import com.kai.home.product.pojo.PmsSkuInfo;
import com.kai.home.product.pojo.PmsSkuSaleAttrValue;
import com.kai.home.product.pojo.SelectSkuSaleAttrValue;
import com.kai.home.product.service.PmsProductInfoService;
import com.kai.home.product.service.PmsSkuInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    private PmsSkuInfoService pmsSkuInfoService;
    @Reference
    private PmsProductInfoService pmsProductInfoService;

    //http://localhost:8084/111.html
    //http://localhost:8084/112.html
    @RequestMapping("{skuId}.html")
    public String item(@PathVariable Integer skuId, Model model)
    {
        System.out.println("skuId="+skuId);

        PmsSkuInfo pmsSkuInfo=pmsSkuInfoService.findPmsSkuInfoById(skuId);

        model.addAttribute("skuInfo",pmsSkuInfo);

        List<PmsProductSaleAttr> pmsProductSaleAttrList=pmsProductInfoService.getSpuSalteAttrListCheckBySkuBySql(pmsSkuInfo.getSpuId(),skuId);
        model.addAttribute("spuSaleAttrListCheckBySku",pmsProductSaleAttrList);

        Map<String,String> map=new HashMap<>();

        List<SelectSkuSaleAttrValue> skuSaleAttrValueList= pmsSkuInfoService.selectSkuSaleAttrValueListBySpuId(pmsSkuInfo.getSpuId());

        for (SelectSkuSaleAttrValue selectSkuSaleAttrValue : skuSaleAttrValueList) {

            System.out.println("skuId值="+selectSkuSaleAttrValue.getSkuId());

            String valueStr="";

            for (PmsSkuSaleAttrValue value : selectSkuSaleAttrValue.getSaleAttrValueIdList()) {

                System.out.println("销售属性值id="+value.getSaleAttrValueId());
                valueStr+=value.getSaleAttrValueId()+"|";

            }

            map.put(valueStr,String.valueOf(selectSkuSaleAttrValue.getSkuId()));
            System.out.println("-----------------");

        }

        //封装json的数据格式：[{"240|245":"108","241|246":"109","242|247":"110"}]
        //将map对象转换成json数据格式
     /*   Map<String,String> map=new HashMap<>();
        map.put("240|245","108");
        map.put("241|246","109");*/

        String jsonStr=JSON.toJSONString(map);

        System.out.println("jsonStr="+jsonStr);

        model.addAttribute("skuSaleAttrHashJsonStr",jsonStr);

        return "item";
    }
}
