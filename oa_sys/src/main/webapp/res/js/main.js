layui.config({
	base: '../res/js/'
}).use(['element', 'layer', 'tab'], function() {

	$ = layui.jquery,
		layer = layui.layer,



	$("#toTaskListId").on("click",function () {

        tab = parent.layui.tab({
            elem: '.layout-nav-card', //设置选项卡容器
            contextMenu:true
        });

		console.log("toTaskListId");
		var data={
			icon:'fa-vcard-o',
			href:'/leave/toTaskList',
			title:'待办任务'
		};

		tab.tabAdd(data);
		
    })

    $("#leaveformId").on("click",function () {

        tab = parent.layui.tab({
            elem: '.layout-nav-card', //设置选项卡容器
            contextMenu:true
        });

        console.log("leaveformId");
        var data={
            icon:'fa-vcard-o',
            href:'/leave/toLeaveForm',
            title:'填写请假单'
        };

        tab.tabAdd(data);

    })




});