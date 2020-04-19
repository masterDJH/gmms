function imageformat(value, row, index) {
    if (value){
        return "<img src='" + value + "' alt='没有头像' style='width: 50px;height: 50px;' />";
        // return `<img src=' + ${value} + ' alt="没有头像" style="width: 50px;height: 50px;" /> `;
    }else {
        return "没有头像";
    }
}

function member(value) {
    if(value) {
        return value.memberName;
    } else {
        return "";
    }
}
// 验证用户名是否重复,EasyUI 验证规则扩展
$.extend($.fn.validatebox.defaults.rules, {
    checkUsername: {
        validator: function(value,param){
            var result = $.ajax({
                type: "GET",
                url: "/employee/findSameUserNum",
                data:{username:value},
                async: false //同步
            }).responseText;
            result = JSON.parse(result);//解析返回结果;结果为true,则用户重复;结果为false,则用户不重复
            return result==false;
        },
        message: '用户名不能重复'
    },
    checkPhone: { //验证手机号
        validator: function(value, param){
            return /^1[3-8]+\d{9}$/.test(value);
        },
        message: '请输入正确的手机号码。'
    }
});
$(function () {
    var  employeeDataGrid = $("#employeeDataGrid"); //获取页面显示表格
    var searchForm = $("#searchForm");//获取form搜索表单
    var editDialog = $("#editDialog");//获取添加修改页面的数据表格
    var editForm = $("#editForm");//获取添加修改页面的form表单
    var ecombox = $("#ecombox");//获取添加修改页面的部门下拉框

    // 验证两次输入密码一致
    $("#repassword").validatebox({
        validType: ['equals["#password","jquery"]']
    });
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
            itsource.enablePwd();// 启用密码框
            itsource.enableUsername(); // 强制启用用户名框

            // 先居中,再打开添加框
            editDialog.dialog("center").dialog("open");
        },
        // 只弹出修改页面,功能由save方法完成
        "update": function() {
            var row = employeeDataGrid.datagrid("getSelected");
            if(row){
                itsource.disablePwd();// 禁用密码框
                itsource.disableUsername(); // 强制禁用用户名框

                // 回显会员类型的数据
                if(row.member){
                    //指定原先选定的会员类型
                    row["member.id"] = row.member.id;
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
            var row = employeeDataGrid.datagrid("getSelected");
            if(row) { // 判断是否选中一列数据
                $.messager.confirm("确认对话框", "你确定要删除: " + row.username + " 吗？", function(res) {
                    if(res) { // 确定删除
                        $.post("/employee/delete",{id:row.id},function (result) {
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
                            employeeDataGrid.datagrid("reload"); // 刷新数据
                        },200);
                    } else { // 取消删除
                        $.messager.alert("提示", "已取消删除!", "warnning");
                        employeeDataGrid.datagrid("reload"); // 刷新数据
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
            employeeDataGrid.datagrid('load',params);
        },
        "save":function () {
            var URL = "/employee/save";// 默认为 save 操作
            var employeeId = $("#employeeId").val();// 获取添加页面的隐藏域 id 值
            if (employeeId){// 添加面板的Employee的id不为空,表示是 update 操作
                URL = "/employee/update?cmd=update";
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
                        $("#employeeDataGrid").datagrid("reload"); // 刷新数据
                        // 关闭添加框前,清空数据
                        editForm.form("clear");
                        // 关闭添加框
                        itsource.close();
                        $.messager.show({
                            title:'成功',
                            msg:'信息保存成功',
                            timeout:2000,
                            showType:'fade',
                            style:{
                                right:'',
                                top:document.body.scrollTop+document.documentElement.scrollTop,
                                bottom:''

                            }
                        });
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
        },
        "disablePwd":function(){
            //禁用密码框
            $("*[data-save] input").validatebox('disable');
            //修改数据时，隐藏密码框
            $("*[data-save]").hide();
            //禁用密码框的验证
            $('*[data-save] input').validatebox('disableValidation');
        },
        "enablePwd":function(){
            //启用密码框
            $("*[data-save] input").validatebox('enable');
            //添加数据时，显示密码框
            $("*[data-save]").show();
            //启用密码框的验证
            $('*[data-save] input').validatebox('enableValidation');
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