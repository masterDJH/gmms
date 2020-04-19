$(function () {
    var  scoreDataGrid = $("#scoreDataGrid"); //获取页面显示表格
    var searchForm = $("#searchForm");//获取form搜索表单
    var editDialog = $("#editDialog");//获取修改页面的数据表格
    var editForm = $("#editForm");//获取修改页面的form表单

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        // 只弹出修改页面,功能由save方法完成
        "update": function() {
            var row = scoreDataGrid.datagrid("getSelected");
            if(row){
                itsource.disableUsername(); // 强制禁用用户名框

                // 回显选定编辑的数据
                editForm.form("load",row);
                // 修改面板先居中,再打开添加框
                editDialog.dialog("center").dialog("open");
            }else {
                $.messager.alert("提示", "你还没有选择修改哪一列哦!", "warnning");
                return ;
            };
        },
        "search": function() {
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var params = searchForm.serializeObject();

            // 使用原有的DataGrid表格的URL发送请求,再加上下面的参数,最后将数据加载到原有的DataGrid表格中
            scoreDataGrid.datagrid('load', params);
        },
        "save":function () {
            var URL = "/score/update?cmd=update";// 默认为 save 操作
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
                        // 延时刷新数据，防止后台数据更新不及时，导致页面数据更新仍为旧数据
                        setTimeout(function () {
                            scoreDataGrid.datagrid("reload"); // 刷新数据
                        },100);
                        // 关闭添加框前,清空数据
                        editForm.form("clear");
                        // 关闭添加框
                        itsource.close();
                        $.messager.show({
                            title:'积分修改成功',
                            msg:'积分修改成功',
                            timeout:3000,
                            showType:'fade',
                            style:{
                                right:'',
                                top:document.body.scrollTop+document.documentElement.scrollTop,
                                bottom:''

                            }
                        });
                    } else {
                        $.messager.alert("错误原因","积分修改失败 "+result.mes,"error");
                    }

                }
            });
        },
        "close":function () {
            // 打开添加框前,清空数据
            editForm.form("clear");
            // 关闭添加面板
            editDialog.dialog("close");
        },
        "disableUsername":function(){
            //禁用用户名框的验证
            $("#username_update :input").validatebox('disableValidation');
            //用户名框只读
            $("#username_update :input").attr("readonly", "readonly");
        },
        "enableUsername":function(){
            //启用用户名框的验证
            $("#username_update :input").validatebox('enableValidation');
            //用户名框可修改
            $("#username_update :input").removeAttr("readonly");
        }
    };
});