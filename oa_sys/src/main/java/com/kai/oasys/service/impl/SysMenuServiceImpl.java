package com.kai.oasys.service.impl;

import com.kai.oasys.dao.SysMenuMapper;
import com.kai.oasys.pojo.SysMenu;
import com.kai.oasys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysMenu record) {
        return sysMenuMapper.insert(record);
    }

    @Override
    public int insertSelective(SysMenu record) {
        return sysMenuMapper.insertSelective(record);
    }

    @Override
    public SysMenu selectByPrimaryKey(Integer id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysMenu> queryMenuByRoleId(int roleId) {
        return sysMenuMapper.queryMenuByRoleId(roleId);
    }

    @Override
    public List<SysMenu> getMenuDataPidTreeByRoleId(Integer roleId, Integer id) {
        return sysMenuMapper.getMenuDataPidTreeByRoleId(roleId,id);
    }

    //获取所有的菜单数据，此数据要对接到菜单树形结构
    //需要children属性赋值，递归查询实现
    @Override
    public List<SysMenu> getAllMenuListTreeByRole(SysMenu sysMenu) {

        //获取最顶菜单的数据（root节点）
        List<SysMenu> sysMenuList=getMenuDataPidTreeByRoleId(sysMenu.getRoleId(),sysMenu.getId());
        diguiMenuThreeByRoleId(sysMenuList,sysMenu.getRoleId());
        return sysMenuList;
    }

    /*
    * 实现递归查询
    * */
    public void  diguiMenuThreeByRoleId(List<SysMenu> list,Integer roleId)
    {
        for (SysMenu sysMenu : list) {

            List<SysMenu> subSysMenuList=getMenuDataPidTreeByRoleId(roleId,sysMenu.getId());
            //当前菜单有子菜单
            if(subSysMenuList!=null&& subSysMenuList.size()>0)
            {
                //只要它是父节点，当checked设为false
                sysMenu.setChecked(false);
                sysMenu.setChildren(subSysMenuList);
                diguiMenuThreeByRoleId(subSysMenuList,roleId);
            }
        }

    }

    /*
    * 实现角色分配菜单的数据保存
    * 参数一：json数据解释出来的数组数据：角色对应分配菜单的数据
    * 参数二：角色ID
    * */
    @Override
    public void addCheckedMenuTreeData(SysMenu[] sysMenus, Integer roleId) {
        //根据角色id，删除数据
        sysMenuMapper.deleteSysRoleMenuByRoleId(roleId);
        addDiguiTreeMenu(Arrays.asList(sysMenus),roleId);
    }

    //递归遍历集合
    public void addDiguiTreeMenu(List<SysMenu> list,Integer roleId)
    {
        for (SysMenu sysMenu : list) {

            //实现数据库表（sys_role_menu）的数据添加
            //参数一：角色id
            //参数二：菜单id
            sysMenuMapper.insertSysRoleMenu(roleId,sysMenu.getId());

            List<SysMenu> childSysMenuList=sysMenu.getChildren();

            if(childSysMenuList!=null && childSysMenuList.size()>0)
            {
                addDiguiTreeMenu(childSysMenuList,roleId);
            }
        }

    }

    @Override
    public List<SysMenu> query(SysMenu sysMenu) {
        return sysMenuMapper.query(sysMenu);
    }

    public List<SysMenu> getMenuDataTreeByPid(Integer id) {
        return sysMenuMapper.getMenuDataTreeByPid(id);
    }


    //获取所有的菜单数据，此数据要对接到菜单树形结构
    //需要children属性赋值，递归查询实现
    @Override
    public List<SysMenu> getAllMenuListTree(SysMenu sysMenu) {

        //获取最顶菜单的数据（root节点）
        //sysMenu的id=75
        List<SysMenu> sysMenuList=getMenuDataTreeByPid(sysMenu.getId());
        System.out.println(sysMenuList.size());
        diguiMenuThree(sysMenuList);
        return sysMenuList;
    }

    /*
     * 实现递归查询
     * */
    public void  diguiMenuThree(List<SysMenu> list)
    {
        for (SysMenu sysMenu : list) {

            List<SysMenu> subSysMenuList=getMenuDataTreeByPid(sysMenu.getId());
            //当前菜单有子菜单
            if(subSysMenuList!=null&& subSysMenuList.size()>0)
            {
                sysMenu.setChildren(subSysMenuList);
                diguiMenuThree(subSysMenuList);
            }
        }
    }

    /*
    * pojo就是一个SysMenu对象
    *   属性 id=1
    *   属性 roleId==5
    * */
    @Override
    public List<SysMenu> queryNavMenuTree(SysMenu sysMenu)
    {
        System.out.println("queryNavMenuTree roleId="+sysMenu.getRoleId());
        //获取根节点的子节点
        List<SysMenu> rootSysMenu=sysMenuMapper.queryMenuByPidAndRoleId(sysMenu);
        diguiNavMenuTree(rootSysMenu,sysMenu.getRoleId());
        return rootSysMenu;
    }


    /*
     * 实现递归查询
     * */
    public void  diguiNavMenuTree(List<SysMenu> list,Integer roleId)
    {
        for (SysMenu sysMenu : list) {
            sysMenu.setRoleId(roleId);
            List<SysMenu> subSysMenuList=sysMenuMapper.queryMenuByPidAndRoleId(sysMenu);
            //当前菜单有子菜单
            if(subSysMenuList!=null&& subSysMenuList.size()>0)
            {
                sysMenu.setChildren(subSysMenuList);
                diguiNavMenuTree(subSysMenuList,roleId);
            }
        }
    }


}
