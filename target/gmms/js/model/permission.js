function menu(value) {
    if(value) {
        return value.name;
    } else {
        return "";
    }
}
$(function () {
    var permissionDataGrid = $("#permissionDataGrid");//获取Employee展示页面数据表格
    var searchForm = $("#searchForm");//获取form搜索表单
    var editDialog = $("#editDialog");//获取添加修改页面的数据表格
    var editForm = $("#editForm");//获取添加修改页面的form表单

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        // 只弹出添加页面,功能由save方法完成
        "add": function() {
            // 打开添加框前,清空数据
            editForm.form("clear");
            //谁调用，this就指向谁(这个this是普通dom对象)
            //$(dom对象) -> 变成jQuery对象，有很多jQuery特有的功能(更加强大)
            //console.debug($(this));

            // 先居中,再打开添加框
            editDialog.dialog("center").dialog("open");
        },
        // 只弹出修改页面,功能由save方法完成
        "update": function() {
            var row = permissionDataGrid.datagrid("getSelected");
            if(row){
                // 回显子菜单的数据
                if(row.menu){
                    //指定原先选定的子菜单
                    // ecombox.combobox("select",row.menu.id);
                    console.debug(row.menu.name);
                    row["menu.id"] = row.menu.id;
                }
                // 回显选定编辑的数据
                editForm.form("load",row);
                // 修改面板先居中,再打开添加框
                editDialog.dialog("center").dialog("open");
            }else {
                $.messager.alert("提示", "你还没有选择修改哪一列哦!", "warnning");
                return ;
            };
        },
        "remove": function() {
            var row = permissionDataGrid.datagrid("getSelected");
            if(row) { // 判断是否选中一列数据
                $.messager.confirm("确认对话框", "你确定要删除: " + row.perName + " 吗？", function(res) {
                    if(res) { // 确定删除
                        $.post("/permission/delete",{id:row.id},function (result) {
                            if(result.success){// 删除成功
                                /*
                                *   alert()不建议使用：
                                *   1.不同浏览器的显示效果不同
                                *   2.alert()显示时,会阻塞进程,不便于以后实行异步请求
                                * */
                                // $.messager.alert(result.mes);
                            }else {// 删除失败
                                $.messager.alert("错误原因","删除失败，失败原因："+result.mes,"error");
                            }
                        },"json");
                        // 延时刷新数据，防止后台数据更新不及时，导致页面数据更新仍为旧数据
                        setTimeout(function () {
                            permissionDataGrid.datagrid("reload"); // 刷新数据
                        },200);
                    } else { // 取消删除
                        $.messager.alert("提示", "已取消删除!", "warnning");
                        permissionDataGrid.datagrid("reload"); // 刷新数据
                    }
                });
            } else {
                $.messager.alert("提示", "你还没有选择删除哪一列哦!", "warnning");
                return ;
            }
        },
        "search": function() {
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var params = searchForm.serializeObject();

            // 使用原有的DataGrid表格的URL发送请求,再加上下面的参数,最后将数据加载到原有的DataGrid表格中
            permissionDataGrid.datagrid('load',params);
        },
        "save":function () {
            var URL = "/permission/save";// 默认为 save 操作
            var permissionId = $("#permissionId").val();// 获取添加页面的隐藏域 id 值
            if (permissionId){// 添加面板的Employee的id不为空,表示是 update 操作
                URL = "/permission/update?cmd=update";
            }
            editForm.form("submit",{
                url:URL,
                onSubmit: function(){
                    // 返回数据校验的结果,根据结果决定是否执行URL对应的添加方法
                    return $(this).form("validate");
                },
                success: function(data){
                    // 将Json字符串转成JSON对象
                    //var result = eval("("+data+")");
                    var result = JSON.parse(data);
                    if(result.success){// 添加成功
                        $("#permissionDataGrid").datagrid("reload"); // 刷新数据
                        // 关闭添加框前,清空数据
                        editForm.form("clear");
                        // 关闭添加框
                        itsource.close();
                    } else {
                        $.messager.alert("错误原因","添加失败，失败原因："+result.mes,"error");
                    }
                }
            });
        },
        "close":function () {
            // 打开添加框前,清空数据
            editForm.form("clear");
            // 关闭添加面板
            editDialog.dialog("close");
        }
    };
});