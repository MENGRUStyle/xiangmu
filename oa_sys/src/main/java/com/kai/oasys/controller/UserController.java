package com.kai.oasys.controller;

import com.kai.oasys.pojo.Role;
import com.kai.oasys.pojo.User;
import com.kai.oasys.service.RoleService;
import com.kai.oasys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/touserList")
    public String toUserList()
    {
        return "user/userlist";
    }

    @RequestMapping("/getUserListByPage")
    @ResponseBody
    public Map<String,Object> getUserListByPage(User user)
    {
        System.out.println("getUserList="+user.getLoginName()+"   sex="+user.getSex());
        List<User> userlist=userService.queryByPage(user);
        Long count=userService.getCountByUser(user);
        //将数据加载到map对象
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",count);
        map.put("data",userlist);
        return map;
    }

    //跳转到添加用户表单
    @RequestMapping("/toAddUserForm")
    public String toAddUserForm(Model model)
    {
        //获取全部的角色数据
        Role role=new Role();
        List<Role> roleList=roleService.query(role);

        model.addAttribute("roleList",roleList);

        return "user/userFormAdd";
    }

    //实现用户保存
    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(User user)
    {
        System.out.println("saveUser="+user);
        String result="1";

        try {
            int flag=userService.insertSelective(user);
            if(flag>0)
            {
                result="0";
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /*
    * 根据用户id,获取用户信息
    * */
    @RequestMapping("/getUserById")
    public String getUserById(Integer id,Model model)
    {

        User user=userService.selectByPrimaryKey(id);

        model.addAttribute("user",user);


        return "user/personInfo";

    }

    @RequestMapping("/personInfo")
    public String personInfo(Integer id,Model model)
    {

        //获取当前用户身份认证成功后的对象信息
        User user= (User) SecurityUtils.getSubject().getPrincipal();

        model.addAttribute("user",user);


        return "user/personInfo";

    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(User user)
    {
        String ajaxReturnData="1";

        try {
            int flag=userService.updateByPrimaryKeySelective(user);

            if(flag>0)
            {
                ajaxReturnData="0";
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return ajaxReturnData;

    }

}
