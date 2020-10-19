package com.kai.oasys.service;

import com.kai.oasys.pojo.SysMenu;

import java.util.List;

public interface SysMenuService {

    int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> queryMenuByRoleId(int roleId);

    List<SysMenu> getMenuDataPidTreeByRoleId(Integer roleId, Integer id);

    List<SysMenu> getAllMenuListTreeByRole(SysMenu sysMenu);

    public void  addCheckedMenuTreeData(SysMenu[] sysMenus, Integer roleId);

    List<SysMenu> query(SysMenu sysMenu);

    public List<SysMenu> getAllMenuListTree(SysMenu sysMenu);

    public List<SysMenu> queryNavMenuTree(SysMenu sysMenu);


}
