layui.use(['form','layer','laydate','table','upload'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        table = layui.table;
    //部门列表
    var tableIns = table.render({
        elem: '#list',
        url : path + '/user/getUserListByPage',
        //启动分页
        page : true,
        cellMinWidth : 95,
        height : "full-104",
        //设置每页显示的记录条数
        limit : 10,
        //选择项：limit:10或者15或者20或者25
        limits : [10,15,20,25],
        id : "tables",
        cols : [[
            {
                type : "checkbox",
                fixed : "left",
                width : 50
            },
            {
                field: 'loginName',
                width: 120,
                title: '登名录',
                sort: true
            },
            {
                field : 'pwd',
                title : '密码',
                width : 100
            },
            {
                field : 'sex',
                title : '性别',
                width : 80
            },
            {
                field : 'status',
                title : '状态',
                width : 100,
                align : 'center',
                templet: '#status'
            },
            {
                field : 'name',
                title : '真实名',
                align : 'center',
                width : 100
            },
            {
                field : 'roleName',
                title : '角色名',
                align : 'center',
                width : 100
            },
            {
                field : 'departmentName',
                title : '部门名',
                align : 'center',
                width : 100
            },
            {
                title : '操作',
                width : 200,
                fixed : "right",
                align : "center",
                templet : '#flinkbar'
            }
        ]]
    });

    //搜索
    $(".search_btn").on("click",function(){
        table.reload("tables",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                loginName: $(".loginName").val(),
                sex:$("#sex").val()
            }
        })
    });

    $(".delAll_btn").on("click",function(){

        //获取复选框的组件
        var checkStatus = table.checkStatus('tables'),
        data = checkStatus.data,
        linkId = [];
        if(data.length > 0) {
            for (var i in data) {
                linkId.push(data[i].id);
            }
            layer.confirm('确定删除选中的部门？', {icon: 3, title: '提示信息'}, function (index) {
                var ajaxReturnData;
                //调用jquery实现异步请求提交，以post方式提交
                $.ajax({
                    url: path + '/sysdepartment/deleteBatch',
                    type: 'post',
                    async: false,
                    data: {ids:linkId.toString()},
                    success: function (data) {
                        ajaxReturnData = data;
                        //删除结果
                        if (ajaxReturnData == '0') {
                            //重新加载一个table
                            table.reload('tables');
                            layer.msg('删除成功', {icon: 1});
                        } else {
                            layer.msg('删除失败', {icon: 5});
                        }
                    }
                });
            })
        }else{
            layer.msg("请选择需要删除的部门");
        }
    });

    //列表操作
    //建立table的操作事件
    table.on('tool(tables)', function(obj){
        //获取事件组件定义的layEvent，data是触发事件组件所在的行数据
        var layEvent = obj.event,data = obj.data;
        if(layEvent === 'edit'){ //编辑
            editLink(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此部门？',{icon:3, title:'提示信息'},function(index){
                var ajaxReturnData;
                $.ajax({
                    url: path + '/sysdepartment/delete',
                    type: 'post',
                    async: false,
                    data: {id:data.id},
                    success: function (data) {
                        ajaxReturnData = data;
                    }
                });
                //删除结果
                if (ajaxReturnData == '0') {
                    table.reload('tables');
                    layer.msg('删除成功', {icon: 1});
                } else {
                    layer.msg('删除失败', {icon: 5});
                }

                layer.close(index);
            });
        }
    });

    //绑定添加事件
    $(".addLink_btn").click(function(){
        addLink();
    })

    //添加部门
    function addLink(){
        var index = layer.open({
            title : "添加用户",
            //type等于2，以iframe形式打开对话框
            type : 2,
            area: ['540px', '650px'],
            content : path + "/user/toAddUserForm"
        })
    }

    /*
    * 转发到修改页
    * */
    function editLink(data){
        var index = layer.open({
            title : "修改部门信息",
            type : 2,
            area: ['540px', '550px'],
            content : path + "/sysdepartment/toEditSysDepartMent?id="+data.id
        })
    }


    form.on("submit(addLink)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var ajaxReturnData;
        //登陆验证
        $.ajax({
            url: path + '/user/saveUser',
            type: 'post',
            async: false,
            data: data.field,
            success: function (data) {
                ajaxReturnData = data;
            }
        });
        //结果回应
        if (ajaxReturnData == '0') {
            top.layer.close(index);
            top.layer.msg('保存成功', {icon: 1});
            layer.closeAll("iframe");
            //刷新父页面
            $(".layui-tab-item.layui-show",parent.document).find("iframe")[0].contentWindow.location.reload();
        } else {
            top.layer.msg('保存失败', {icon: 5});
        }
        return false;
    })


    form.on("submit(editLink)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var ajaxReturnData;
        //登陆验证
        $.ajax({
            url: path + '/sysdepartment/updateSaveSysDepartMent',
            type: 'post',
            async: false,
            data: data.field,
            success: function (data) {
                ajaxReturnData = data;
            }
        });
        //结果回应
        if (ajaxReturnData == '0') {
            top.layer.close(index);
            top.layer.msg('修改保存成功', {icon: 1});
            layer.closeAll("iframe");
            //刷新父页面
            $(".layui-tab-item.layui-show",parent.document).find("iframe")[0].contentWindow.location.reload();
        } else {
            top.layer.msg('修改失败', {icon: 5});
        }
        return false;
    })

})