package com.kai.oasys.controller;

import com.kai.oasys.pojo.SysMenu;
import com.kai.oasys.pojo.User;
import com.kai.oasys.service.SysMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /*
    * 请求方法：根据roleId获取菜单信息
    * 遍历全部的menu数据也是以树型结构呈现
    *  所属角色的菜单以checked标识为true体现
    * */
    @ResponseBody
    @RequestMapping("/getMenuDataTreeByRoleIdJson")
    public List<SysMenu> getMenuDataTreeByRoleIdJson(SysMenu sysMenu)
    {
        //默认菜单的顶级id
        sysMenu.setId(75);
        return sysMenuService.getAllMenuListTreeByRole(sysMenu);

    }

    /*
    * 获取所有的菜单数据（根具体角色无关），以树形结构的json数据返回
    * 遍历全部的menu数据也是以树型结构呈现
    * */
    @ResponseBody
    @RequestMapping("/getMenuDataTreeJson")
    public List<SysMenu> getMenuDataTreeJson(SysMenu sysMenu)
    {
        //默认菜单的顶级id
        sysMenu.setId(0);
        return sysMenuService.getAllMenuListTree(sysMenu);

    }


    @RequestMapping("/toMenuList")
    public String toMenuList()
    {
        return "menu/menuList";
    }

    @RequestMapping("/getMenuData")
    @ResponseBody
    public Map<String,Object> toMenuList(SysMenu sysMenu)
    {
        if(sysMenu.getId()!=null && sysMenu.getId()==75)
        {
            sysMenu.setId(null);
            sysMenu.setTitle(null);
        }

        System.out.println("title="+sysMenu.getTitle());
        Map<String,Object> map=new HashMap<>();
        List<SysMenu> sysMenuList=sysMenuService.query(sysMenu);

        map.put("msg","");
        map.put("code",0);
        map.put("data",sysMenuList);

        return map;
    }


    @RequestMapping("/getMenuTop")
    @ResponseBody
    public List<SysMenu> getMenuTop()
    {
        //获取当前用户身份认证成功后的对象信息
        User user= (User) SecurityUtils.getSubject().getPrincipal();

        //获取顶级菜单数据
        //条件：is_top=1 并且 del_flag=0
        SysMenu sysMenu=new SysMenu();
        sysMenu.setIsTop("1");
        sysMenu.setRoleId(user.getRoleId());

        return sysMenuService.query(sysMenu);
    }


    @RequestMapping("/getMenuByPidRec")
    @ResponseBody
    public List<SysMenu> getMenuByPidRec(SysMenu sysMenu)
    {
        //获取当前用户身份认证成功后的对象信息
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        sysMenu.setRoleId(user.getRoleId());
        return sysMenuService.queryNavMenuTree(sysMenu);
    }

    /*
    * 获取顶级菜单第一个菜单元素的ID值
    * */
    @RequestMapping("/getTopMenuFirst")
    @ResponseBody
    public String getTopMenuFirst()
    {
        //获取当前用户身份认证成功后的对象信息
        User user= (User) SecurityUtils.getSubject().getPrincipal();

        //获取顶级菜单数据
        //条件：is_top=1 并且 del_flag=0
        SysMenu sysMenu=new SysMenu();
        sysMenu.setIsTop("1");
        sysMenu.setRoleId(user.getRoleId());

        List<SysMenu> sysMenuList=sysMenuService.query(sysMenu);

        if(sysMenuList!=null && sysMenuList.size()>0)
        {
            return String.valueOf(sysMenuList.get(0).getId());
        }

        return null;

    }

}
