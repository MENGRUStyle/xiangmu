package com.kai.oasys.service.impl;

import com.kai.oasys.dao.SysDepartMentMapper;
import com.kai.oasys.pojo.SysDepartMent;
import com.kai.oasys.service.SysDepartMentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysDepartMentServiceImpl implements SysDepartMentService {

    @Autowired
    private SysDepartMentMapper sysDepartMentMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysDepartMentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysDepartMent record) {
        return sysDepartMentMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDepartMent record) {
        return sysDepartMentMapper.insertSelective(record);
    }

    @Override
    public SysDepartMent selectByPrimaryKey(Integer id) {
        return sysDepartMentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDepartMent record) {
        return sysDepartMentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDepartMent record) {
        return sysDepartMentMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysDepartMent> query(SysDepartMent record) {
        return sysDepartMentMapper.query(record);
    }

    @Override
    public List<SysDepartMent> querySysDepartMentByPid(int pid) {
        return sysDepartMentMapper.querySysDepartMentByPid(pid);
    }

    //递归查询
    @Override
    public List<SysDepartMent> treeDataJson(int pid) {

        List sysDepartMentList=querySysDepartMentByPid(pid);
        //要实现的是递归查询
        diguiTree(sysDepartMentList);
        return sysDepartMentList;

    }

    //此方法实现递归查询
    //此方法主要功能：将所有的子部门信息赋值到父部门的children属性里面
    public void diguiTree(List<SysDepartMent> sysDepartMentList)
    {
        for (SysDepartMent sysDepartMent : sysDepartMentList) {

            int pid=sysDepartMent.getId();
            List<SysDepartMent> sysChildDepartMentList=querySysDepartMentByPid(pid);

            if(sysChildDepartMentList!=null && sysChildDepartMentList.size()>0)
            {
                sysDepartMent.setChildren(sysChildDepartMentList);
                diguiTree(sysChildDepartMentList);
            }
        }
    }

    //批量删除
    @Override
    public int deleteBatch(String[] ids) {

        //flag标志位：
        //如果flag=0，则删除数据成功
        //如果flag=1，则删除数据失败
        int flag=1;

        try
        {
            if(ids!=null && ids.length>0)
            {
                for (String sId : ids) {
                    Integer id=Integer.parseInt(sId);
                    sysDepartMentMapper.deleteByPrimaryKey(id);
                }

                flag=0;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return flag;
    }
}
