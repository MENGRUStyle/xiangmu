package com.kai.oasys.controller;

import com.kai.oasys.pojo.Leave;
import com.kai.oasys.pojo.User;
import com.kai.oasys.service.WorkFlowService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private WorkFlowService workFlowService;

    /*
    * 跳转到填写请假表单
    * */
    @RequestMapping("/toLeaveForm")
    public String toLeaveForm()
    {
        return "leave/leaveForm";
    }

    /*
    * 保存请假表单的数据
    * */
    @RequestMapping("/saveLeave")
    @ResponseBody
    public String saveLeave(Leave leave)
    {
        //shiro身份认证对象获取user信息
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        leave.setUser(user);

        String ajaxReturnData="1";

        try {
            //启动请假工作流实例
            workFlowService.startFlowInstance("emp_leave",leave);
            ajaxReturnData="0";
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return ajaxReturnData;
    }

    /*
    * 实现获取待办列表
    * */
    @RequestMapping("/toTaskList")
    public String toTaskList()
    {
        return "leave/leaveTasklist";
    }

    @RequestMapping("/getLeaveTaskList")
    @ResponseBody
    public Map<String,Object> getLeaveTaskList(Leave leave)
    {
        //shiro身份认证对象获取user信息
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        leave.setUser(user);

        List<Leave> leaveList=workFlowService.getTaskList(leave);

        //将数据加载到map对象
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("data",leaveList);
        return map;
    }

}
