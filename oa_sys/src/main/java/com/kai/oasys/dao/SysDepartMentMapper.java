package com.kai.oasys.dao;

import com.kai.oasys.pojo.SysDepartMent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDepartMentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDepartMent record);

    int insertSelective(SysDepartMent record);

    SysDepartMent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDepartMent record);

    int updateByPrimaryKey(SysDepartMent record);

    List<SysDepartMent> query(SysDepartMent record);

    //根据pid，获取它的子部门数据
    List<SysDepartMent> querySysDepartMentByPid(int pid);
}