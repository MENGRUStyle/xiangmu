package com.kai.oasys.controller;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 管理流程
* */
@Controller
public class RepositoryActController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/deloy")
    @ResponseBody
    public String deloyBpmnFile()
    {
        return repositoryService.createDeployment().addClasspathResource("leaveflow.bpmn").deploy().getId();
    }

}
