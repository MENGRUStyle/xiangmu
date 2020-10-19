package com.kai.home.user.service;

import com.kai.home.user.pojo.UmsMember;

import java.util.List;

public interface UmsMemberService {

    //获取所有的会员信息
    public List<UmsMember> getAllUmsMember();

    public UmsMember login(UmsMember umsMember);

}
