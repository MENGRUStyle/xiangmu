package com.kai.oasys.controller;

import com.kai.oasys.pojo.SysDepartMent;
import com.kai.oasys.service.SysDepartMentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sysdepartment")
public class SysDepartMentController {

    @Autowired
    private SysDepartMentService sysDepartMentService;

/*    @RequestMapping("/query")
    @ResponseBody
    public List<SysDepartMent> query()
    {
        List<SysDepartMent> sysDepartMentList=sysDepartMentService.query();

        for (SysDepartMent sysDepartMent : sysDepartMentList) {
            System.out.println(sysDepartMent);
        }

        return sysDepartMentList;
    }*/

    /*
    * 转发到组织部门列表
    * */
    @RequestMapping("/toSysDepartMentList")
    public String toSysDepartMentList()
    {
        return "sys/sysdepartmentlist";
    }

    /*
    * 实现表格获取数据源接口方法
    * */
    @RequestMapping("/getSysDepartMentList")
    @ResponseBody
    public Map<String, Object> getSysDepartMentList(SysDepartMent sysDepartMent)
    {
        System.out.println("部门名="+sysDepartMent.getName());
        Map<String, Object> map = new HashMap<String, Object>();

        List<SysDepartMent> sysDepartMentList=sysDepartMentService.query(sysDepartMent);


        map.put("code", 0);
        map.put("msg", "");
        map.put("data", sysDepartMentList);

        return map;
    }

    /*
    * 跳转到添加部门界面
    * */
    @RequestMapping("/toAddSysDepartMent")
    public String toAddSysDepartMent()
    {
        return "sys/sysdepartmentAdd";
    }

    /*
    * 实现部门数据保存
    * */
    @RequestMapping("/saveSysDepartMent")
    @ResponseBody
    public String saveSysDepartMent(SysDepartMent sysDepartMent)
    {
        String ajaxReturnData="1";
        try {
            //将表单提交的数据存储到数据表
            sysDepartMentService.insertSelective(sysDepartMent);
            ajaxReturnData="0";
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return ajaxReturnData;
    }

    @RequestMapping("/treeDataJson")
    @ResponseBody
    public List<SysDepartMent> treeDataJson(SysDepartMent sysDepartMent)
    {
        //id值初始化肯定为0
        return sysDepartMentService.treeDataJson(sysDepartMent.getId());
    }

    @RequestMapping("/deleteBatch")
    @ResponseBody
    public String deleteBatch(String ids)
    {
        String ajaxReturnData="1";
        System.out.println("deleteBatch ids="+ids);

        String idArrays[]=ids.split(",");
        int flag=sysDepartMentService.deleteBatch(idArrays);

        if(flag==0)
        {
            ajaxReturnData="0";
        }

        return ajaxReturnData;
    }


    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String id)
    {
        String ajaxReturnData="1";

        int flag=sysDepartMentService.deleteByPrimaryKey(Integer.parseInt(id));

        if(flag>0)
        {
            ajaxReturnData="0";
        }

        return ajaxReturnData;
    }

    @RequestMapping("/toEditSysDepartMent")
    public String toEditSysDepartMent(Integer id,Model model)
    {
        SysDepartMent sysDepartMent=sysDepartMentService.selectByPrimaryKey(id);

        model.addAttribute("sysdepartment",sysDepartMent);

        return "sys/sysdepartmentEdit";

    }


    @ResponseBody
    @RequestMapping("/updateSaveSysDepartMent")
    public String updateSaveSysDepartMent(SysDepartMent sysDepartMent)
    {
        String ajaxReturnData="1";
        try {
            //实现数据更新
            sysDepartMentService.updateByPrimaryKeySelective(sysDepartMent);
            ajaxReturnData="0";
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ajaxReturnData;
    }






}
