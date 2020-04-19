// 查询个人信息，并回显
function findUser() {
    var editForm = $("#editForm");//获取添加修改页面的form表单

    $.post("/myinfo/findUser",{},function (result) {
        if(result.user!=null){// 删除成功
            // 回显选定编辑的数据
            editForm.form("load",result.user);
        }else {// 查询失败
            $.messager.alert("错误","用户信息查询失败","error");
        }
    },"json");
}

// 个人信息文本框编辑功能
function edit(mark) {
    document.getElementById("email").readOnly = mark;
    document.getElementById("age").readOnly = mark;
    document.getElementById("phone").readOnly = mark;
    $('#headImg').filebox(mark==false ? "enable":"disable");
}

$(function () {
    var editForm = $("#editForm");//获取添加修改页面的form表单
    var editPasswordDialog = $("#editPasswordDialog");//获取密码修改页面的对话框
    var editPasswordForm = $("#editPasswordForm");//获取密码修改页面的form表单
    var editMark = true;// 默认禁止修改用户信息
    // 回显用户信息
    findUser();

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        // 只弹出修改页面,功能由save方法完成
        "update": function() {
            // 设置只读信号量
            if(editMark==true){
                editMark=false;// 可编辑
            } else {
                editMark=true;// 禁止编辑
            }
            // 开启或关闭编辑功能（设置只读属性）
            edit(editMark);
        },
        "save":function () {
            var URL = "/myinfo/update?cmd=update";// 默认为 save 操作
            $.messager.confirm("确认对话框", "你确定要提交？ ", function(res) {
                if(res) { // 确定提交
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
                            if(result.success){// 修改成功
                                // 刷新用户信息
                                findUser();
                                $.messager.show({
                                    title:'信息修改成功',
                                    msg:'信息修改成功，请刷新页面',
                                    timeout:2000,
                                    showType:'fade',
                                    style:{
                                        right:'',
                                        top:document.body.scrollTop+document.documentElement.scrollTop,
                                        bottom:''

                                    }
                                });
                            } else {
                                $.messager.alert("错误","修改失败","error");
                            }
                        }
                    });
                } else { // 取消提交
                    $.messager.alert("提示", "已取消提交!", "warnning");
                }
            });
        },
        // 打开密码修改面板
        "updatePassword":function () {
            // 打开密码修改对话框前,清空数据
            editPasswordForm.form("clear");
            // 修改面板先居中,再打开添加框
            editPasswordDialog.dialog("center").dialog("open");
        },
        // 提交密码
        "passSave":function () {
            var passURL = "/myinfo/updatePass";
            $.messager.confirm("确认对话框", "你确定要修改？ ", function(res) {
                if(res) { // 确定提交
                    editPasswordForm.form("submit",{
                        url:passURL,
                        onSubmit: function(){
                            // 返回数据校验的结果,根据结果决定是否执行URL对应的添加方法
                            return $(this).form("validate");
                        },
                        success: function(data){
                            // 将Json字符串转成JSON对象
                            //var result = eval("("+data+")");
                            var result = JSON.parse(data);
                            if(result.success){// 修改成功
                                // 刷新用户信息
                                // findUser();
                                $.messager.show({
                                    title:'成功',
                                    msg:'密码修改成功',
                                    timeout:2000,
                                    showType:'fade',
                                    style:{
                                        right:'',
                                        top:document.body.scrollTop+document.documentElement.scrollTop,
                                        bottom:''

                                    }
                                });
                                // 关闭密码修改面板
                                itsource.passClose();
                            } else {
                                $.messager.alert("错误","密码修改失败 "+result.mes,"error");
                            }
                        }
                    });
                } else { // 取消提交
                    $.messager.alert("提示", "已取消提交!", "warnning");
                }
            });
        },
        // 取消密码修改
        "passClose":function () {
            // 关闭密码修改对话框前,清空数据
            editPasswordForm.form("clear");
            // 关闭密码修改面板
            editPasswordDialog.dialog("close");
        }
    };
});
