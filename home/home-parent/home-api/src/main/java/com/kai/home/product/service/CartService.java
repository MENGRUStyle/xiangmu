package com.kai.home.product.service;

import com.kai.home.product.pojo.OmsCartItem;

import java.util.List;

public interface CartService {

    /*
    *根据用户id及skuId，获取对应的购物车数据
    * select * from oms_cart_item where member_id=? and skuId=?
    * */
    OmsCartItem ifCartExistByUser(Integer memberId, Integer skuId);

    /*
    * 添加购物车数据实现
    * */
    void addCart(OmsCartItem omsCartItem);

    /*
    * 实现修改购物车数据功能
    * */
    void updateCart(OmsCartItem omsCartItemFromDb);

    void flushCartCache(Integer memberId);

    /*
    * 获取购物车列表
    * */
    List<OmsCartItem> cartList(Integer userId);

    /*
    * 修改购物车选中状态
    * */
    void checkCart(OmsCartItem omsCartItem);
}
