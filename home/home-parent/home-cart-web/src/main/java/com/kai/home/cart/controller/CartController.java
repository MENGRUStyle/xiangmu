package com.kai.home.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.kai.home.annotations.LoginRequired;
import com.kai.home.cart.util.CookieUtil;
import com.kai.home.product.pojo.OmsCartItem;
import com.kai.home.product.pojo.PmsSkuInfo;
import com.kai.home.product.service.CartService;
import com.kai.home.product.service.PmsSkuInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {

    @Reference
    PmsSkuInfoService pmsSkuInfoService;

    @Reference

    CartService cartService;

    @RequestMapping("/toTrade")
    @LoginRequired(loginSuccess = true)
    public String toTrade()
    {
        return "toTrade";
    }


    /*
        request.setAttribute("memberId", successMap.get("memberId"));
        request.setAttribute("nickname", successMap.get("nickname"));
    */
    @RequestMapping("checkCart")
    @LoginRequired(loginSuccess = false)
    public String checkCart(String isChecked,Integer skuId,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap) {

        List<OmsCartItem> omsCartItems=null;
        System.out.println("checkCart isChecked="+isChecked+"   skuId="+skuId);
        String memberId = request.getParameter("memberId");

        // 调用服务，修改状态
        OmsCartItem omsCartItem = new OmsCartItem();

        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setIsChecked(isChecked);

        if(memberId!=null){

            omsCartItem.setMemberId(Integer.parseInt(memberId));
            cartService.checkCart(omsCartItem);
            // 将最新的数据从缓存中查出，渲染给内嵌页
            omsCartItems = cartService.cartList(Integer.parseInt(memberId));


        }else{
            // 没有登录查询cookie
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);

            if (StringUtils.isNotBlank(cartListCookie)) {

                // cookie不为空
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                // 判断添加的购物车数据在cookie中是否存在
                boolean exist = if_cart_exist(omsCartItems, omsCartItem);
                if (exist) {
                    // 之前添加过，更新购物车添加数量
                    for (OmsCartItem cartItem : omsCartItems) {
                        if (cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())) {
                            cartItem.setTotalPrice(cartItem.getPrice().multiply(cartItem.getQuantity()));
                            cartItem.setIsChecked(omsCartItem.getIsChecked());
                        }
                    }


                    System.out.println(omsCartItems);
                    CookieUtil.setCookie(request, response, "cartListCookie", JSON.toJSONString(omsCartItems), 60 * 60 * 72, true);
                }
            }

        }
        modelMap.put("cartList",omsCartItems);
        // 被勾选商品的总额
        BigDecimal totalAmount =getTotalAmount(omsCartItems);
        System.out.println("checkCart  totalAmount="+totalAmount);
        modelMap.put("totalAmount",totalAmount);
        return "cartListInner";
    }


    //实现购物车商品列表
    @RequestMapping("cartList")
    @LoginRequired(loginSuccess = false)
    public String cartList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap modelMap) {

        List<OmsCartItem> omsCartItems = new ArrayList<>();
        //memberId：如果此变量为空，则用户处于未登录状态
        //memberId：如果此变量不为空，则用户处于登录状态
        String memberId = request.getParameter("memberId");

        if(memberId!=null){
            // 已经登录查询db
            //从缓存获取数据
           omsCartItems = cartService.cartList(Integer.parseInt(memberId));
        }else{
            // 没有登录查询cookie
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if(StringUtils.isNotBlank(cartListCookie)){
                omsCartItems = JSON.parseArray(cartListCookie,OmsCartItem.class);
            }
        }

        //计算每个item的总价
        for (OmsCartItem omsCartItem : omsCartItems) {
            omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(omsCartItem.getQuantity()));
        }

        modelMap.put("cartList",omsCartItems);
        // 被勾选商品的总额
        BigDecimal totalAmount =getTotalAmount(omsCartItems);

        System.out.println("cartList  totalAmount="+totalAmount);
        modelMap.put("totalAmount",totalAmount);
        return "cartList";
    }

    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItems) {
        BigDecimal totalAmount = new BigDecimal("0");

        for (OmsCartItem omsCartItem : omsCartItems) {
            BigDecimal totalPrice = omsCartItem.getTotalPrice();

            if(StringUtils.isNotBlank(omsCartItem.getIsChecked()) && omsCartItem.getIsChecked().equals("1")){
                totalAmount = totalAmount.add(totalPrice);
            }
        }

        return totalAmount;
    }

    //添加商品数据到购物车
    @RequestMapping("addToCart")
    @LoginRequired(loginSuccess = false)
    public String addToCart(Integer skuId, Integer quantity, HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model) {

        System.out.println("addToCart skuId="+skuId+"  quantity="+quantity);
        List<OmsCartItem> omsCartItems = new ArrayList<>();

        // 调用商品服务查询商品信息
        PmsSkuInfo skuInfo = pmsSkuInfoService.findPmsSkuInfoById(skuId);

        // 将商品信息封装成购物车信息
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setModifyDate(new Date());
        omsCartItem.setPrice(new BigDecimal(skuInfo.getPrice()));
        omsCartItem.setProductAttr("");
        omsCartItem.setProductBrand("");
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setProductId(skuInfo.getSpuId());
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("11111111111");
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(new BigDecimal(quantity));
        omsCartItem.setIsChecked("1");


        // 判断用户是否登录
        String memberId = request.getParameter("memberId");

        if (memberId==null) {
            // 用户没有登录
            System.out.println("用户没有登录");
            // cookie里原有的购物车数据
            // key =value保存（key="cartListCookie"）
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isBlank(cartListCookie)) {
                // cookie为空
                omsCartItems.add(omsCartItem);
            } else {
                // cookie不为空
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                // 判断添加的购物车数据在cookie中是否存在
                //此方法的第一个参数：指cookie的数据
                //此方法的正在添加到购物车的对象(skuId数据)
                boolean exist = if_cart_exist(omsCartItems, omsCartItem);
                if (exist) {
                    // 之前添加过，更新购物车添加数量
                    for (OmsCartItem cartItem : omsCartItems) {
                        if (cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())) {
                            cartItem.setQuantity(cartItem.getQuantity().add(omsCartItem.getQuantity()));
                        }
                    }
                } else {
                    // 之前没有添加，新增当前的购物车
                    omsCartItems.add(omsCartItem);
                }
            }

            // 更新cookie
            CookieUtil.setCookie(request, response, "cartListCookie", JSON.toJSONString(omsCartItems), 60 * 60 * 72, true);
        } else {
           // 用户已经登录
            // 从db中查出购物车数据
            System.out.println("用户已经登录");
            OmsCartItem omsCartItemFromDb = cartService.ifCartExistByUser(Integer.parseInt(memberId),skuId);

            if(omsCartItemFromDb==null){
                // 该用户没有添加过当前商品
                omsCartItem.setMemberId(Integer.parseInt(memberId));
                omsCartItem.setMemberNickname("test小明");
                omsCartItem.setQuantity(new BigDecimal(quantity));
                cartService.addCart(omsCartItem);

            }else{
                // 该用户添加过当前商品
                omsCartItemFromDb.setQuantity(omsCartItemFromDb.getQuantity().add(omsCartItem.getQuantity()));
                cartService.updateCart(omsCartItemFromDb);
            }

            // 同步缓存
            cartService.flushCartCache(Integer.parseInt(memberId));
        }

        model.addAttribute("skuInfo",skuInfo);
        model.addAttribute("quantity",quantity);
        return "success.html";
    }

    private boolean if_cart_exist(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {

        boolean b = false;

        for (OmsCartItem cartItem : omsCartItems) {
            Integer productSkuId = cartItem.getProductSkuId();

            if(productSkuId==omsCartItem.getProductSkuId())
            {
                b=true;
            }

        }


        return b;
    }


}
