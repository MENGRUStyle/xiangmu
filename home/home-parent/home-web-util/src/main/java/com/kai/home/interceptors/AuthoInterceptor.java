package com.kai.home.interceptors;

import com.alibaba.fastjson.JSON;
import com.kai.home.annotations.LoginRequired;
import com.kai.home.util.CookieUtil;
import com.kai.home.util.HttpclientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/*
* 拦截器实现是整体统一认证的关键
* */
public class AuthoInterceptor implements HandlerInterceptor
{
    public synchronized void test()
    {

    }

    @Override
    public boolean  preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if(handler!=null) {

            if(handler instanceof HandlerMethod) {
                // 拦截代码
                // 判断被拦截的请求的访问的方法的注解(是否时需要拦截的)
                HandlerMethod hm = (HandlerMethod) handler;
                //是否有定义LoginRequired的注解，如果LoginRequired=true
                LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);

                StringBuffer url = request.getRequestURL();
                System.out.println(url);

                // 是否拦截
                if (methodAnnotation == null) {
                    return true;
                }

                String token = "";

                //从cookie获取token的数据
                String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
                if (StringUtils.isNotBlank(oldToken)) {
                    token = oldToken;
                }

                //从request作用域获取token的数据
                //因为每次产生一个新的令牌数据，都会存储到request作用域
                String newToken = request.getParameter("token");
                if (StringUtils.isNotBlank(newToken)) {
                    token = newToken;
                }


                // 是否必须登录
                //如果此变量为loginSuccess=true,必须先登录后，才能访问
                //如果此变量为loginSuccess=false,不需要登录，就能访问
                boolean loginSuccess = methodAnnotation.loginSuccess();// 获得该请求是否必登录成功

                // 调用认证中心进行验证
                String success = "fail";
                Map<String,String> successMap = new HashMap<>();
                //如果此token不为空，
                if(StringUtils.isNotBlank(token)){
                    String ip = request.getHeader("x-forwarded-for");// 通过nginx转发的客户端ip
                    if(StringUtils.isBlank(ip)){
                        ip = request.getRemoteAddr();// 从request中获取ip
                        if(StringUtils.isBlank(ip)){
                            ip = "127.0.0.1";
                        }
                    }
                    //发送请求地址：http://localhost:8086/verify，token以参数形式传入，
                    // 验证此token是否有效，如果有效，则返回success状态值
                    String successJson  = HttpclientUtil.doGet("http://localhost:8086/verify?token=" + token+"&currentIp="+ip);

                    successMap = JSON.parseObject(successJson,Map.class);

                    success = successMap.get("status");

                }

                if (loginSuccess) {
                    // 必须登录成功才能使用
                    if (!success.equals("success")) {
                        //重定向会passport登录
                        StringBuffer requestURL = request.getRequestURL();
                        response.sendRedirect("http://localhost:8086/index?ReturnUrl="+requestURL);
                        return false;
                    }

                    // 需要将token携带的用户信息写入
                    request.setAttribute("memberId", successMap.get("memberId"));
                    request.setAttribute("nickname", successMap.get("nickname"));
                    //验证通过，覆盖cookie中的token
                    if(StringUtils.isNotBlank(token)){
                        CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
                    }

                } else {
                    // 没有登录也能用，但是必须验证
                    if (success.equals("success")) {
                        // 需要将token携带的用户信息写入
                        request.setAttribute("memberId", successMap.get("memberId"));
                        request.setAttribute("nickname", successMap.get("nickname"));

                        //验证通过，覆盖cookie中的token
                        if(StringUtils.isNotBlank(token)){
                            CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
                        }
                    }
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
