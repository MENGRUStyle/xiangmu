package com.kai.oasys.dao;

import com.kai.oasys.pojo.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> queryMenuByRoleId(int roleId);

    List<SysMenu> getMenuDataPidTreeByRoleId(@Param("roleId") Integer roleId, @Param("id") Integer id);

    int insertSysRoleMenu(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int deleteSysRoleMenuByRoleId(@Param("roleId") Integer roleId);

    List<SysMenu> query(SysMenu sysMenu);

    List<SysMenu> getMenuDataTreeByPid(@Param("id") Integer id);

    List<SysMenu> queryMenuByPidAndRoleId(SysMenu sysMenu);


}