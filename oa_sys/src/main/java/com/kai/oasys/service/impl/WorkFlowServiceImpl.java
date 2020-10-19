package com.kai.oasys.service.impl;

import com.kai.oasys.pojo.Leave;
import com.kai.oasys.service.WorkFlowService;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;


    @Override
    public String startFlowInstance(String processDefinitionKey, Leave leave) {


        //启动者（要配置谁启动此流程）
        identityService.setAuthenticatedUserId(leave.getUser().getLoginName());

        Map<String,Object> variables=new HashMap<>();
        variables.put("title",leave.getTitle());
        variables.put("reason",leave.getReason());
        variables.put("days",leave.getDays());

        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);

        return processInstance.getId();
    }

    //获取待办任务列表
    @Override
    public List<Leave> getTaskList(Leave leave) {

        List<Leave> leaveList=new ArrayList<>();

        String candidateGroup="";

        //获取当前登录用户的对应角色
        Integer roleId=leave.getUser().getRoleId();

        //根据流程图已经固定角色字符串
        //dept_master等于部门主管
        //dept_master等于部门主管
        //dept_leader等于部门领导
        //company_manager等于总经理
        //hr等于人事经理
        if(roleId!=null && roleId ==9)
        {
            candidateGroup="dept_master";
        }else if(roleId!=null && roleId ==7)
        {
            candidateGroup="dept_leader";
        }else if(roleId!=null && roleId ==10)
        {
            candidateGroup="company_manager";
        }else if(roleId!=null && roleId ==4)
        {
            candidateGroup="hr";
        }else if(roleId!=null && roleId==8){
            candidateGroup="emp";
        }

        List<Task> taskList=new ArrayList<>();

        //获取当前提交者（流程实例的发起者）的待办任务
        if(candidateGroup.equals("emp"))
        {
            ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
            TaskService taskService=processEngine.getTaskService();
            //为什么会传入以登录名获取待办任务信息，因此它整个流程实例是以登录名发起的
            taskList=taskService.createTaskQuery().taskAssignee(leave.getUser().getLoginName()).active().orderByTaskCreateTime().desc().list();

        }else {
            //获取非员工角色的待办任务
            TaskQuery taskQuery=taskService.createTaskQuery().taskCandidateGroup(candidateGroup).active().orderByTaskCreateTime().desc();
            taskList=taskQuery.list();

        }

        for (Task task : taskList) {

            Leave inLeave=new Leave();
            inLeave.setTitle((String) taskService.getVariable(task.getId(), "title"));
            inLeave.setReason((String) taskService.getVariable(task.getId(), "reason"));
            inLeave.setDays((Integer) taskService.getVariable(task.getId(), "days"));

            inLeave.setTaskId(task.getId());
            inLeave.setTaskName(task.getName());

            leaveList.add(inLeave);

        }

        return leaveList;
    }

    /*
    * 此方法实现：任务处理
    * 调用taskService.complete方法实现
    * */
    @Override
    public void completeTask(Leave leave) {

        //获取当前登录用户的对应角色
        Integer roleId=leave.getUser().getRoleId();

        String conditionExpression="";

        //因为流程固化了不同角色针对不同的变量表达式
        //此变量名的值为true，此角色针对此流程审核通过
        //此变量名的值为false，此角色针对此流程审核不通过
        if(roleId!=null && roleId ==9)
        {
            conditionExpression="deptmasterpass";
        }else if(roleId!=null && roleId ==7)
        {
            conditionExpression="deptleaderpass";
        }else if(roleId!=null && roleId ==10)
        {
            conditionExpression="managerpass";
        }else if(roleId!=null && roleId ==4)
        {
            conditionExpression="hrpass";
        }else if(roleId!=null && roleId==8){
            conditionExpression="emppass";
        }

        Map<String,Object> vars=new HashMap<>();
        vars.put(conditionExpression,leave.getPass());

        if(roleId!=null && roleId==8) {

            Task task=taskService.createTaskQuery().taskId(leave.getTaskId()).singleResult();

            if(task.getName().equals("调整申请"))
            {
                System.out.println("task name="+task.getName());
                vars.put("title", leave.getTitle());
                vars.put("reason", leave.getReason());
                vars.put("days", leave.getDays());
            }

        }


        taskService.complete(leave.getTaskId(),vars);

    }

    @Override
    public Leave getVarsByTaskId(String taskId) {

        String title= (String) taskService.getVariable(taskId,"title");
        String reason= (String) taskService.getVariable(taskId,"reason");
        Integer days= (Integer) taskService.getVariable(taskId,"days");

        Leave outLeave=new Leave();
        outLeave.setTaskId(taskId);
        outLeave.setTitle(title);
        outLeave.setReason(reason);
        outLeave.setDays(days);

        return outLeave;
    }
}
