layui.use(['form','layer','table','tree'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    var tree=layui.tree;
    //部门列表
    var tableIns = table.render({
        elem: '#flinklist',
        url : path + '/menu/getMenuData',
        id : "flinklist",
        cols : [[
            {
                type : "checkbox",
                fixed : "left",
                width : 50
            },
            {
                field: 'id',
                width: 80,
                title: '菜单ID',
                sort: true
            },
            {
                field : 'title',
                title : '菜单名',
                width : 200,
                align : 'center',
            },
            {
                field : 'icon',
                title : '图标',
                width : 100,
                align : 'center',
                templet: '#linkTpl'
            },
            {
                field : 'href',
                title : '路径',
                width : 220,
                align : 'center'
            },
            {
                field : 'sort',
                title : '排序',
                align : 'center',
                width : 80
            },
            {
                field : 'type',
                title : '菜单类型',
                align : 'center',
                width : 100,
                templet: '#type'
            },
            {
                field : 'permission',
                title : '权限标识',
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

        console.log($("#search_input").val());
        table.reload("flinklist",{
            where: {
                title:$("#search_input").val()
            }
        })
    });


    //定义一个方法，此方法用于显示菜单树形结构
    var renderTree= function() {

        tree.render({
            elem: '#menuTreeId'
            ,data: getMenuTreeData()
            ,id: 'menuTreeId'
            ,click: function(obj){
                console.log(obj.data.id); //得到当前点击的节点数据
                table.reload("flinklist",{
                    where: {
                        id:obj.data.id
                    }
                })
            }
        });
    };

    function getMenuTreeData()
    {
        var data=[];
        //发送异步请求（ajax）
        $.ajax(
            {
                url: "/menu/getMenuDataTreeJson",
                type: "post",
                async:false,
                success:function (result) {

                    data=result;
                }

            }
        );
        return data;
    };

    //初始化树形结构
    renderTree();




})