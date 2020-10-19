package com.kai.oasys.service.impl;

import com.kai.oasys.dao.UserMapper;
import com.kai.oasys.pojo.User;
import com.kai.oasys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<User> query(User record) {
        return userMapper.query(record);
    }

    @Override
    public Long getCountByUser(User user) {
        return userMapper.getCountByUser(user);
    }

    @Override
    public List<User> queryByPage(User user) {
        return userMapper.queryByPage(user);
    }

    @Override
    public List<User> queryUserByLoginNameAndPwd(User user) {
        return userMapper.queryUserByLoginNameAndPwd(user);
    }
}
