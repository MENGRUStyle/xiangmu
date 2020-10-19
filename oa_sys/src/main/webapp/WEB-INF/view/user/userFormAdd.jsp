<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <link rel="stylesheet" href="${path}/res/layui/css/layui.css" media="all" >
    <link rel="stylesheet" href="${path}/res/css/public.css" media="all" />
</head>
<body class="childrenBody">

<fieldset class="layui-elem-field layui-field-title site-title">
    <legend><a name="methodRender">添加用户</a></legend>
</fieldset>
<form class="layui-form linksAdd">


    <div class="layui-form-item">
        <label class="layui-form-label">登录名</label>
        <div class="layui-input-block">
            <input type="text" name="loginName" value="" class="layui-input" lay-verify="required" placeholder="请输入登录名" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
            <input type="password" name="pwd" value="" class="layui-input" lay-verify="required" placeholder="请输入密码" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱地址</label>
        <div class="layui-input-block">
            <input type="text" name="email" value="" class="layui-input" lay-verify="emall" placeholder="请输入邮箱地址" />
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">单选框</label>
        <div class="layui-input-block">
            <input type="radio" name="sex" value="男" title="男" checked="">
            <input type="radio" name="sex" value="女" title="女">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机号码</label>
        <div class="layui-input-block">
            <input type="text" name="mobile" value="" class="layui-input" lay-verify="required|phone" placeholder="请输入手机号码" />
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">真实名</label>
        <div class="layui-input-block">
            <input type="text" name="name" value="" class="layui-input" lay-verify="required" placeholder="请输入真实名" />
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">所属角色</label>
        <div class="layui-input-block">
            <select id="sel" name="roleId" lay-verify="required">
                <option value=""></option>
                <c:forEach items="${roleList}" var="role">
                    <option value="${role.id}">${role.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">所属部门</label>
        <div class="layui-input-block">
            <input type="hidden" id="orgId" name="orgId" value="">
            <input type="text" id="tree" lay-filter="tree" class="layui-input" name="title">
        </div>
    </div>
    <%-- <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="text" name="status" value="${dict.status }" class="layui-input" lay-verify="required" placeholder="请输入状态" />
        </div>
    </div> --%>
    <div class="layui-form-item">
        <button class="layui-btn layui-block" lay-filter="addLink" lay-submit>提交</button>
    </div>
</form>
<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
<script type="text/javascript">
    layui.config({
        base: '../res/js/'
    }).extend({
        treeSelect: 'treeSelect'
    });

    var path = "${path}";
    var parentdepartmentid = "12";

    layui.use(['form','layer','jquery','treeSelect'],function(){
        var $ = layui.$,
            form = layui.form,
            treeSelect= layui.treeSelect,
            layer = layui.layer;

        treeSelect.render({
            // 选择器
            elem: '#tree',
            // 数据
            data: path + '/sysdepartment/treeDataJson?id=0',
            // 异步加载方式：get/post，默认get
            type: 'get',
            // 占位符
            placeholder: '请选择部门',
            // 是否开启搜索功能：true/false，默认false
            search: true,
            // 点击回调
            click: function(d){
                $("#orgId").val(d.current.id);
                console.log(d.current.id);
            },
            // 加载完成后的回调函数
            success: function (d) {
                console.log(d);
//	                选中节点，根据id筛选
                treeSelect.checkNode('tree', parentdepartmentid);

//	                获取zTree对象，可以调用zTree方法
                var treeObj = treeSelect.zTree('tree');
                console.log(treeObj);

//	                刷新树结构
                treeSelect.refresh();
            }
        });

        //select赋值
        //$("#sel").find("option[value='" + roleId + "']").attr("selected",true);
        form.render('select');
    });
</script>

<script type="text/javascript" src="${path}/res/js/custom-js/userList.js"></script>
<script type="text/javascript">
    var path = "${path}";
</script>
</body>
</html>