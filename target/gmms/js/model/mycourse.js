function imageformat(value, row, index) {
    if (value){
        return "<img src='" + value + "' alt='图片暂时无法显示' style='width: 50px;height: 50px;' />";
        // return `<img src=' + ${value} + ' alt="没有头像" style="width: 50px;height: 50px;" /> `;
    }else {
        return "没有课程图片";
    }
}

$(function () {
    var  myCourseDataGrid = $("#myCourseDataGrid"); //获取页面显示表格
    var searchForm = $("#searchForm");//获取form搜索表单

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {

        "search": function() {
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var params = searchForm.serializeObject();

            // 使用原有的DataGrid表格的URL发送请求,再加上下面的参数,最后将数据加载到原有的DataGrid表格中
            myCourseDataGrid.datagrid('load',params);
        },
        "remove": function() {
            var row = myCourseDataGrid.datagrid("getSelected");
            if(row) { // 判断是否选中一列数据
                $.messager.confirm("确认对话框", "你确定要退订: " + row.courseName + " 吗？", function(res) {
                    if(res) { // 确定退订
                        $.post("/course/unsubscribe",{courseId:row.id},function (result) {
                            if(result.success){// 退订成功
                                /*
                                *   alert()不建议使用：
                                *   1.不同浏览器的显示效果不同
                                *   2.alert()显示时,会阻塞进程,不便于以后实行异步请求
                                * */
                                $.messager.alert("退订成功",result.mes,"info");
                            }else {// 退订失败
                                $.messager.alert("退订失败","退订失败","error");
                            }
                        },"json");
                        // 延时刷新数据，防止后台数据更新不及时，导致页面数据更新仍为旧数据
                        setTimeout(function () {
                            myCourseDataGrid.datagrid("reload"); // 刷新数据
                        },200);
                    } else { // 取消退订
                        $.messager.alert("提示", "已取消退订!", "warnning");
                        myCourseDataGrid.datagrid("reload"); // 刷新数据
                    }
                });
            } else {
                $.messager.alert("提示", "你还没有选择退订哪一列哦!", "warnning");
                return ;
            }
        },
    };
});