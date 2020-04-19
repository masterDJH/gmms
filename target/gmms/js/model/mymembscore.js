function member(value) {
    if(value) {
        return value.memberName;
    } else {
        return "";
    }
}

// 查询个人信息，并回显
function findUser() {
    var editForm = $("#editForm");//获取添加修改页面的form表单

    $.post("/mymembscore/findUser",{},function (result) {
        if(result.user!=null){// 删除成功
            // 回显会员等级展示页面的等级
            $("#member").textbox("setText",result.user.member.memberName);
            // 回显会员等级修改框的等级
            $('#ecombox').combobox('setValue', result.user.member.id);

            // 回显积分数据
            editForm.form("load",result.user);
        }else {// 查询失败
            $.messager.alert("错误","用户信息查询失败","error");
        }
    },"json");
}

$(function () {
    var editForm = $("#editForm");//获取添加修改页面的form表单
    var upgradeDialog = $("#upgradeDialog");//获取升级会员页面的对话框
    var upgradeForm = $("#upgradeForm");//获取升级会员页面的form表单
    // 回显用户信息
    findUser();

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        // 只弹出修改页面,功能由save方法完成
        "upgrade": function() {
            // 打开密码修改对话框前,清空数据
            upgradeForm.form("clear");
            // 回显用户信息
            findUser();
            // 修改面板先居中,再打开添加框
            upgradeDialog.dialog("center").dialog("open");
        },
        "save":function () {
            var URL = "/mymembscore/update";// 默认为 save 操作
            $.messager.confirm("确认对话框", "你确定要提交？ ", function(res) {
                if(res) { // 确定提交
                    upgradeForm.form("submit",{
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
                                    title:'成功',
                                    msg:"会员升级成功<br/>"+result.mes,
                                    timeout:2000,
                                    showType:'fade',
                                    style:{
                                        right:'',
                                        top:document.body.scrollTop+document.documentElement.scrollTop,
                                        bottom:''

                                    }
                                });
                                // 关闭会员升级对话框
                                itsource.close();
                            } else {
                                $.messager.alert("错误","升级失败<br/>"+result.mes,"error");
                            }
                        }
                    });
                } else { // 取消提交
                    $.messager.alert("提示", "已取消提交!", "warnning");
                }
            });
        },
        // 取消密码修改
        "close":function () {
            // 关闭密码修改对话框前,清空数据
            upgradeForm.form("clear");
            // 关闭密码修改面板
            upgradeDialog.dialog("close");
        }
    };
});
