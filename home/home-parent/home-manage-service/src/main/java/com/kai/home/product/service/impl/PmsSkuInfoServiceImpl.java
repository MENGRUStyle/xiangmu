package com.kai.home.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.kai.home.product.dao.PmsSkuAttrValueMapper;
import com.kai.home.product.dao.PmsSkuImageMapper;
import com.kai.home.product.dao.PmsSkuInfoMapper;
import com.kai.home.product.dao.PmsSkuSaleAttrValueMapper;
import com.kai.home.product.pojo.*;
import com.kai.home.product.service.PmsSkuInfoService;
import com.kai.home.product.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PmsSkuInfoServiceImpl implements PmsSkuInfoService {

    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    @Transactional
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        //先保存主表数据（pms_sku_info）
        pmsSkuInfoMapper.insert(pmsSkuInfo);

        //将数据保存到平台属性列表（pms_sku_attr_value）
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuInfo.getSkuAttrValueList()) {
            //将sku表的主键id赋值给pms_sku_attr_value的skuID外键
            pmsSkuAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsSkuAttrValueMapper.insert(pmsSkuAttrValue);
        }

        //将数据存储到pms_sku_sale_attr_value表
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuInfo.getSkuSaleAttrValueList()) {

            pmsSkuSaleAttrValue.setSkuId(pmsSkuInfo.getId());
            pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
        }

        //将数据存储到pms_sku_image表
        for (PmsSkuImage pmsSkuImage : pmsSkuInfo.getSkuImageList()) {

            pmsSkuImage.setSkuId(pmsSkuInfo.getId());
            pmsSkuImageMapper.insert(pmsSkuImage);
        }
    }

    /*
    * 根据skuId，获取sku的数据
    * */
    @Override
    public PmsSkuInfo findPmsSkuInfoById(Integer skuId) {


        PmsSkuInfo pmsSkuInfo=new PmsSkuInfo();
        //判断redis是否存在此skuId的key
        //约定此key的格式：sku:skuId:info
        //相应value的数据内容：skuInfo对象的json数据格式
        String skuKey="sku:"+skuId+":info";
        String skuJson=redisUtil.get(skuKey,0);

        /*
         * isNotBlank：判断字符串是否为空（包括空格），如果为空，则为false，否则为true
         * 如果StringUtils.isNotBlank(skuJson)返回值为true，则证明redis存在此key
         * */
        if(StringUtils.isNotBlank(skuJson))
        {
            System.out.println("使用缓存，获取skuinfo对象");
            //将json数据反序列化成一个skuInfo对象
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }else {

            //添加分布式锁
            String lockKey="sku:"+skuId+":lock";
            //redis实现锁（此锁存活的时间最多：10秒）
            String lockResult=redisUtil.setnxpx(lockKey,"1",10*1000,0);
            System.out.println("lockResult="+lockResult);
            if(StringUtils.isNotBlank(lockResult) && lockResult.equals("OK")) {

                System.out.println("使用访问数据库，获取skuinfo对象");
                //如果不存在此key，则从数据库查询获取
                pmsSkuInfo = getSkuInfoByDataBaseDb(skuId);
                //将第一次查询skuinfo的数据结果，存储到redis缓存里面
                //key就是：sku:xxx:info
                //value就是：skuinfo对象序列化为json数据字符串格式存储
                if (pmsSkuInfo != null) {
                    redisUtil.set(skuKey, JSON.toJSONString(pmsSkuInfo), 0);
                } else {
                    //主要解决缓存击穿问题
                    //配置此缓存数据的有效期只有3分钟：意味用户在3分钟内如果多次请求同一个不存在的skuId，则直接从redis缓存获取
                    redisUtil.setex(skuKey, 60 * 3, JSON.toJSONString(""));
                }

                //释放此锁对象
                redisUtil.del(0,lockKey);
            }else {

                //自旋：每隔3秒钟，重新获取锁对象
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                findPmsSkuInfoById(skuId);
            }
        }
        return pmsSkuInfo;
    }


    //此方法实现：根据skuId，从数据库获取skuInfo的数据
    public PmsSkuInfo getSkuInfoByDataBaseDb(Integer skuId) {

        //select * from pms_sku_info where skuId=id
        PmsSkuInfo inPmsSkuInfo=new PmsSkuInfo();
        inPmsSkuInfo.setId(skuId);
        PmsSkuInfo pmsSkuInfo=pmsSkuInfoMapper.selectOne(inPmsSkuInfo);

        if(pmsSkuInfo!=null) {
            //获取sku对应的图片列表
            PmsSkuImage pmsSkuImage = new PmsSkuImage();
            pmsSkuImage.setSkuId(skuId);
            pmsSkuInfo.setSkuImageList(pmsSkuImageMapper.select(pmsSkuImage));
        }

        return pmsSkuInfo;
    }

    @Override
    public List<SelectSkuSaleAttrValue> selectSkuSaleAttrValueListBySpuId(Integer spuId) {
        return pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpuId(spuId);
    }


    @Override
    public List<PmsSkuInfo> getAllSku() {

        //查询pms_sku_info主表的内容
        //select  * from  pms_sku_info
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            Integer skuId = pmsSkuInfo.getId();

            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> select = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);

            pmsSkuInfo.setSkuAttrValueList(select);
        }
        return pmsSkuInfos;
    }
}
