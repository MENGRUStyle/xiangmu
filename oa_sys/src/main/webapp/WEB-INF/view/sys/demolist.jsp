<%--
  Created by IntelliJ IDEA.
  User: cheng
  Date: 2020/4/13
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/res/layui/css/layui.css" media="all">
</head>
<body>

<table id="demo" lay-filter="test"></table>

<script src="/res/layui/layui.js"></script>
<script>
    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo'
            ,height: 312
            ,url: '/sys/getDemoList' //数据接口
            ,page: false //开启分页
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
                ,{field: 'username', title: '用户名', width:200,sort: true}
                ,{field: 'sex', title: '性别', width:200, sort: true}
                ,{field: 'city', title: '城市', width:200,sort: true}
            ]]
        });

    });
</script>
</body>
</html>
