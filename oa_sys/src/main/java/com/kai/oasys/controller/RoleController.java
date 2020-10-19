package com.kai.oasys.controller;

import com.alibaba.fastjson.JSONArray;
import com.kai.oasys.pojo.Role;
import com.kai.oasys.pojo.SysMenu;
import com.kai.oasys.service.RoleService;
import com.kai.oasys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private SysMenuService sysMenuService;

    /*
    * 跳转角色列表
    * */
    @RequestMapping("/toRoleList")
    public String toRoleList()
    {
        return "role/rolelist";
    }

    //获取角色数据
    @RequestMapping("/getdata")
    @ResponseBody
    public Map<String,Object> data(Role role)
    {
        List<Role> roleList=roleService.query(role);

        //封装map数据，以json的数据格式返回
        Map<String,Object> map=new HashMap<>();

        map.put("code",0);
        map.put("data",roleList);
        map.put("msg","");
        return map;
    }

    @RequestMapping("/savePermission")
    @ResponseBody
    public String savePermission(Integer roleId,@RequestBody String checkedMenuTreeDataJson)
    {
        String result="1";
        System.out.println("roleId="+roleId);
        System.out.println(checkedMenuTreeDataJson);
        //依赖fastjson提供的api实现json数据解释
        //JSONArray：json数据的数组对象
        JSONArray jsonArray=JSONArray.parseArray(checkedMenuTreeDataJson);
        //定义一个菜单数组
        SysMenu sysMenuArrays[]=new SysMenu[jsonArray.size()];
        //遍历json数据数组
        for (int i = 0; i < jsonArray.size(); i++) {

            //获取每个json数组里面的对象，通过fastjson，能够自动将此对象属性转换成pojo对象
            SysMenu sysMenu=jsonArray.getObject(i, SysMenu.class);
            sysMenuArrays[i]=sysMenu;
        }

        try {
            sysMenuService.addCheckedMenuTreeData(sysMenuArrays,roleId);
            result="0";
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }


    @RequestMapping("/toRoleForm")
    public String toRoleForm()
    {
        return "role/roleForm";
    }


    @RequestMapping("/save")
    @ResponseBody
    public String save(Role role)
    {
        String result="1";
        try {
            int flag=roleService.insertSelective(role);
            if (flag>0) {
                result = "0";
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }
}
