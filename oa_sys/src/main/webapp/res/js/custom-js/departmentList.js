
layui.config({
    base:"../res/js/"
}).use(['form','layer','laydate','table','upload','treetable'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        table = layui.table;
    var treetable=layui.treetable;

    // 渲染表格
    var renderTable = function () {
        treetable.render({
            treeColIndex: 1,
            treeSpid: 0,
            treeIdName: 'id',
            treePidName: 'parentdepartmentid',
            treeDefaultClose: true,
            treeLinkage: false,
            elem: '#list',
            url: path + '/sysdepartment/getSysDepartMentList',
            page: false,
            id : "tables",
            cols : [[
                {
                    type : "checkbox",
                    fixed : "left",
                    width : 50
                },
                {
                    field : 'name',
                    title : '部门名',
                    width : 300
                },
                {
                    field : 'description',
                    title : '描述',
                    align : 'center',
                    width : 200
                },
                {
                    field : 'sort',
                    title : '排序',
                    align : 'center',
                    width : 200
                },
                /*{
                    field : 'status',
                    title : '状态',
                    align : 'center',
                    width : 200
                },*/
                {
                    title : '操作',
                    width : 200,
                    fixed : "right",
                    align : "center",
                    templet : '#flinkbar'
                }
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });
    };


/*
    //部门列表
    var tableIns = table.render({
        elem: '#list',
        url : path + '/sysdepartment/getSysDepartMentList',
        page : false,
        cellMinWidth : 95,
        height : "full-104",
        id : "tables",
        cols : [[
            {
                type : "checkbox",
                fixed : "left",
                width : 50
            },
            {
                field: 'id',
                width: 80,
                title: 'ID',
                sort: true
            },
            {
                field : 'name',
                title : '部门名',
                width : 200
            },
            {
                field : 'type',
                title : '机构类型',
                width : 200
            },
            {
                field : 'area',
                title : '所属区域',
                width : 280,
                align : 'center'
            },
            {
                field : 'description',
                title : '描述',
                align : 'center',
                width : 300
            },
            {
                field : 'sort',
                title : '排序',
                align : 'center',
                width : 200
            },
            /!*{
                field : 'status',
                title : '状态',
                align : 'center',
                width : 200
            },*!/
            {
                title : '操作',
                width : 200,
                fixed : "right",
                align : "center",
                templet : '#flinkbar'
            }
        ]]
    });*/
    renderTable();

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
                            renderTable();
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
                    //table.reload('tables');
                    renderTable();
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
            title : "添加部门",
            type : 2,
            area: ['540px', '550px'],
            content : path + "/sysdepartment/toAddSysDepartMent"
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

    //搜索
    $(".search_btn").on("click",function(){
        var keyword = $(".name").val();

        var searchCount = 0;
        $('#list').next('.treeTable').find('.layui-table-body tbody tr td').each(function () {
            $(this).css('background-color', 'transparent');
            var text = $(this).text();
            if (keyword != '' && text.indexOf(keyword) >= 0) {
                $(this).css('background-color', 'rgba(250,230,160,0.5)');
                if (searchCount == 0) {
                    treetable.expandAll('#list');
                    $('html,body').stop(true);
                    $('html,body').animate({scrollTop: $(this).offset().top - 150}, 500);
                }
                searchCount++;
            }
        });
        if (keyword == '') {
            layer.msg("请输入搜索内容", {icon: 5});
        } else if (searchCount == 0) {
            layer.msg("没有匹配结果", {icon: 5});
        }

    });



    form.on("submit(addLink)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var ajaxReturnData;
        //登陆验证
        $.ajax({
            url: path + '/sysdepartment/saveSysDepartMent',
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
            //renderTable();
            parent.location.reload();
            //刷新父页面
            //$(".layui-tab-item.layui-show",parent.document).find("iframe")[0].contentWindow.location.reload();
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
            //$(".layui-tab-item.layui-show",parent.document).find("iframe")[0].contentWindow.location.reload();
            parent.location.reload();
        } else {
            top.layer.msg('修改失败', {icon: 5});
        }
        return false;
    })

})