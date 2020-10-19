package com.kai.oasys.service;

import com.kai.oasys.pojo.Role;

import java.util.List;

public interface RoleService {

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> query(Role record);
}
