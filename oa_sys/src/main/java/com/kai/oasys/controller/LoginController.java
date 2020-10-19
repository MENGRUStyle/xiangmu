package com.kai.oasys.controller;

import com.kai.oasys.pojo.AjaxReturnJson;
import com.kai.oasys.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    /*
    * 转发到登录页
    * */
    @RequestMapping("/toLoginForm")
    public String toLoginForm()
    {
        return "user/loginform";
    }

    @RequestMapping("/login")
    @ResponseBody
    public AjaxReturnJson login(User user)
    {
        System.out.println("login user="+user);
        AjaxReturnJson ajaxReturnJson=new AjaxReturnJson();
        //获取主体对象
        Subject subject=SecurityUtils.getSubject();

        //创建一个令牌
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getLoginName(),user.getPwd());
        System.out.println("哈哈"+usernamePasswordToken);
        try {
            subject.login(usernamePasswordToken);
            ajaxReturnJson.setCode("0");
            ajaxReturnJson.setMsg("登录成功");
        }catch (IncorrectCredentialsException e)
        {
            ajaxReturnJson.setCode("1");
            ajaxReturnJson.setMsg(e.getMessage());
        }catch (LockedAccountException e)
        {
            ajaxReturnJson.setCode("1");
            ajaxReturnJson.setMsg(e.getMessage());
        }
        System.out.println("哈哈"+ajaxReturnJson.getCode());

        return ajaxReturnJson;
    }

}
