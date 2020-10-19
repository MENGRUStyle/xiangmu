package com.kai.oasys.service;

import com.kai.oasys.pojo.User;

import java.util.List;

public interface UserService {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> query(User record);

    public Long getCountByUser(User user);

    List<User> queryByPage(User user);

    List<User> queryUserByLoginNameAndPwd(User user);

}
