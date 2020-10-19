package com.kai.home.user.service.impl;

import com.kai.home.user.dao.UmsMemberMapper;
import com.kai.home.user.pojo.UmsMember;
import com.kai.home.user.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;

    @Override
    public List<UmsMember> getAllUmsMember() {
        return umsMemberMapper.selectAll();
    }

    @Override
    public UmsMember login(UmsMember umsMember) {
        UmsMember umsMember1 = new UmsMember();
        umsMember1.setUsername(umsMember.getUsername());
        umsMember1.setPassword(umsMember.getPassword());
        return umsMemberMapper.selectOne(umsMember1);
    }
}
