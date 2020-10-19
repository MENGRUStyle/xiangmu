package com.kai.home.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.kai.home.cart.dao.OmsCartItemMapper;
import com.kai.home.cart.utils.RedisUtil;
import com.kai.home.product.pojo.OmsCartItem;
import com.kai.home.product.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@org.springframework.stereotype.Service
public class CartServiceImpl implements CartService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    OmsCartItemMapper omsCartItemMapper;

    /*
    * 根据memberId和skuId，获取购物车数据
    * */
    @Override
    public OmsCartItem ifCartExistByUser(Integer memberId, Integer skuId) {

        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        OmsCartItem omsCartItem1 = omsCartItemMapper.selectOne(omsCartItem);
        return omsCartItem1;

    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {
        System.out.println(omsCartItem.getQuantity());
        if(omsCartItem.getMemberId()!=null)
        {
            omsCartItemMapper.insertSelective(omsCartItem);//避免添加空值
        }
    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {

        Example e = new Example(OmsCartItem.class);
        e.createCriteria().andEqualTo("id", omsCartItemFromDb.getId());

        omsCartItemMapper.updateByExampleSelective(omsCartItemFromDb, e);

    }

    @Override
    public void flushCartCache(Integer memberId) {

        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        //根据memberId，获取购物车有那些商品
        List<OmsCartItem> omsCartItems = omsCartItemMapper.select(omsCartItem);

        // 同步到redis缓存中
        Map<String, String> map = new HashMap<>();
        for (OmsCartItem cartItem : omsCartItems) {
            cartItem.setTotalPrice(cartItem.getPrice().multiply(cartItem.getQuantity()));
            map.put(String.valueOf(cartItem.getProductSkuId()), JSON.toJSONString(cartItem));
        }

        redisUtil.del(0, "user:" + memberId + ":cart");
        redisUtil.hmset("user:" + memberId + ":cart", map, 0);

    }

    /*
    * 从redis缓存，获取购物车数据
    * */
    @Override
    public List<OmsCartItem> cartList(Integer userId) {
        Jedis jedis = null;
        List<OmsCartItem> omsCartItems = new ArrayList<>();

        List<String> hvals = redisUtil.hvals("user:" + userId + ":cart");

        for (String hval : hvals) {
            OmsCartItem omsCartItem = JSON.parseObject(hval, OmsCartItem.class);
            omsCartItems.add(omsCartItem);
        }


        return omsCartItems;
    }

    @Override
    public void checkCart(OmsCartItem omsCartItem) {

        Example e = new Example(OmsCartItem.class);

        e.createCriteria().andEqualTo("memberId", omsCartItem.getMemberId()).andEqualTo("productSkuId", omsCartItem.getProductSkuId());

        omsCartItemMapper.updateByExampleSelective(omsCartItem, e);

        // 缓存同步
        flushCartCache(omsCartItem.getMemberId());

    }
}
