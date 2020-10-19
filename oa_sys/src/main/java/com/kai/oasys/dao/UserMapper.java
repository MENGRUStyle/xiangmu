package com.kai.oasys.dao;

import com.kai.oasys.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> query(User record);

    public Long getCountByUser(User user);
    List<User> queryByPage(User user);

    public List<User> queryUserByLoginNameAndPwd(User user);
}