package com.kai.oasys.service;

import com.kai.oasys.pojo.Leave;

import java.util.List;

public interface WorkFlowService {

    //启动工作流实例
    public String startFlowInstance(String processDefinitionKey, Leave leave);

    //获取待办任务列表
    public List<Leave> getTaskList(Leave leave);

    //办理任务
    public void completeTask(Leave leave);


    public Leave getVarsByTaskId(String taskId);



}
