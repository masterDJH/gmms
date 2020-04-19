// 查询个人信息，并回显
function findUser() {
    var editForm = $("#editForm");//获取添加修改页面的form表单

    $.post("/charge/findUser",{},function (result) {
        if(result.user!=null){// 删除成功
            // 回显选定编辑的数据
            editForm.form("load",result.user);
            // 充值框默认充值 1
            $('#score').textbox({"value":1});
        }else {// 查询失败
            $.messager.alert("错误","用户信息查询失败","error");
        }
    },"json");
}
$(function () {
    var editForm = $("#editForm");//获取添加修改页面的form表单
    var editMark = true;// 默认禁止修改用户信息
    // 回显用户账户名
    findUser();

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        "charge":function () {
            var URL = "/charge/update?cmd=update";// 默认为 save 操作
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
                                    title:'充值成功',
                                    msg:'充值成功，请刷新页面',
                                    timeout:2000,
                                    showType:'fade',
                                    style:{
                                        right:'',
                                        top:document.body.scrollTop+document.documentElement.scrollTop,
                                        bottom:''

                                    }
                                });
                            } else {
                                $.messager.alert("错误","充值失败<br/>"+result.mes,"error");
                            }
                        }
                    });
                } else { // 取消提交
                    $.messager.alert("提示", "已取消提交!", "warnning");
                }
            });
        }
    };
});
