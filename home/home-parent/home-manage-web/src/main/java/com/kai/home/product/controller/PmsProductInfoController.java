package com.kai.home.product.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kai.home.product.pojo.PmsProductImage;
import com.kai.home.product.pojo.PmsProductInfo;
import com.kai.home.product.pojo.PmsProductSaleAttr;
import com.kai.home.product.service.PmsProductInfoService;
import com.kai.home.product.utils.PmsUploadUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class PmsProductInfoController {

    @Reference
    private PmsProductInfoService pmsProductInfoService;

    @RequestMapping("/spuList")
    public List<PmsProductInfo> spuList(Integer catalog3Id)
    {
        return pmsProductInfoService.getSpuListBycatalog3Id(catalog3Id);
    }


    @RequestMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo)
    {
        pmsProductInfoService.saveSpuInfo(pmsProductInfo);
        return "success";
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestBody MultipartFile file)
    {
        System.out.println("fileUpload="+file.getOriginalFilename());
        //将file对象的数据存储到fastdfs
        String imgUrl=PmsUploadUtil.uploadImage(file);
        System.out.println(imgUrl);

        return imgUrl;
    }


    //显示spu对应那些销售属性值
    @RequestMapping("/spuSaleAttrList")
    public List<PmsProductSaleAttr> spuSaleAttrList(Integer spuId){

        return pmsProductInfoService.spuSaleAttrList(spuId);

    }

    @RequestMapping("/spuImageList")
    public List<PmsProductImage> getSpuImageList(Integer spuId){

        return pmsProductInfoService.getSpuImageList(spuId);

    }



}
