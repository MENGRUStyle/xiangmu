<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${path}/res/layui/css/layui.css" media="all" >
    <link rel="stylesheet" href="${path}/res/css/public.css" media="all" />
</head>
<body>
    <form class="layui-form">
        <div class="layui-inline">
            登录名：
            <div class="layui-input-inline">
                <input type="text" class="layui-input loginName" placeholder="请输入登录名搜索内容" />
            </div>
            <div class="layui-input-inline">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-block">
                    <select name="sex" lay-filter="aihao"  id="sex">
                        <option value="">全部</option>
                        <option value="男">男</option>
                        <option value="女" >女</option>
                    </select>
                </div>
            </div>
            <a class="layui-btn search_btn" data-type="reload"><i class="layui-icon">&#xe615;</i>搜索</a>
        </div>
        <shiro:hasPermission name="user:add">
        <div class="layui-inline">
            <a class="layui-btn layui-btn-normal addLink_btn"><i class="layui-icon">&#xe608;</i>添加用户</a>
        </div>
        </shiro:hasPermission>
        <div class="layui-inline">
            <a class="layui-btn layui-btn-danger layui-btn-normal delAll_btn"><i class="layui-icon">&#xe640;</i>批量删除</a>
        </div>
    </form>

<table class="layui-table" id="list" lay-filter="tables"></table>
<script type="text/html" id="flinkbar">
    <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
</script>
<script type="text/html" id="status">
    {{#  if(d.status === undefined){ }}
    未知
    {{#  } else { }}
    {{#  if(d.status === '0'){ }}
    可用
    {{#  } else { }}
    禁用
    {{#  } }}
    {{#  } }}
</script>

<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
<script type="text/javascript" src="${path}/res/js/custom-js/userList.js"></script>
<script type="text/javascript">
    var path = "${path}";
</script>

</body>
</html>
