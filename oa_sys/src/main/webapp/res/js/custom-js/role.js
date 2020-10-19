layui.use(['form','layer','laydate','table','upload','tree'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        table = layui.table;
    var tree=layui.tree;
    //角色列表
    var tableIns = table.render({
        elem: '#roleTables',
        url : path + '/role/getdata',
        //启动分页
        page : false,
        cellMinWidth : 95,
        height : "full-104",
        id : "roleTables",
        cols : [[
            {
                type : "checkbox",
                fixed : "left",
                width : 50
            },
            {
                field: 'id',
                width: 120,
                title: '角色id',
                sort: true
            },
            {
                field : 'name',
                title : '角色名',
                width : 100
            },
            {
                field : 'useable',
                title : '状态',
                width : 80,
                templet:'#status'
            },
            {
                title : '操作',
                width : 360,
                fixed : "right",
                align : "center",
                toolbar: '#rolebar',
                fixed:"right"
            }
        ]]
    });

    //定义一个方法，此方法用于显示菜单树形结构
    var renderTree= function(inRoleId,roleName) {

        $("#roleName").html(roleName);
        roleId=inRoleId;

        tree.render({
            elem: '#menuTreeId'
            ,data: getMenuTreeData(roleId)
            ,showCheckbox: true
            ,id: 'menuTreeId'
        });
    };

    function getMenuTreeData(roleId)
    {
        var data=[];
        //发送异步请求（ajax）
        $.ajax(
            {
                url: "/menu/getMenuDataTreeByRoleIdJson?roleId="+roleId,
                type: "post",
                async:false,
                success:function (result) {

                    data=result;
                }

            }
        );
        return data;
    }

    //renderTree();

    //列表操作
    //建立table的操作事件
    table.on('tool(roleTables)', function(obj){
        //获取事件组件定义的layEvent，data是触发事件组件所在的行数据
        var layEvent = obj.event,data = obj.data;
        if(layEvent === 'edit'){ //编辑
            //editLink(data);
        } else if(layEvent === 'shouquan'){ //删除
            renderTree(obj.data.id,obj.data.name);
        }
    });

    $(".savepermisson_btn").on("click",function(){

        //获取选中角色的json数据格式
        var checkedMenuTreeData = tree.getChecked('menuTreeId');
        savePermisson(checkedMenuTreeData)

    });

    function savePermisson(checkedMenuTreeData){
        var index  = layer.msg('保存中', {
            icon: 16
            ,shade: 0.1
        });
        var ajaxReturnData;
        //保存
        $.ajax({
            url: path + '/role/savePermission?roleId='+roleId,
            type: 'post',
            async: false,
            contentType:"application/json",
            data:JSON.stringify(checkedMenuTreeData),
            success: function (data) {
                ajaxReturnData = data;
            }
        });
        //结果回应
        if (ajaxReturnData == '0') {
            layer.close(index);
            top.layer.msg('保存成功', {icon: 1});
            renderTree(roleId);
        } else {
            top.layer.msg('保存失败', {icon: 5});
        }
    }

    $('#one_group .layui-btn').on('click',function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    var active = {
        add:function(){
            var index = layer.open({
                title: "角色添加",
                type: 2,
                skin:'',
                offset: ['85px', '530px'],
                area: ['540px', '350px'],
                content: path + "/role/toRoleForm",
            });
        }
    };

})