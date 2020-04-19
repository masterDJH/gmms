<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>忘记密码</title>
    <%@ include file="/WEB-INF/views/head.jsp"%>
    <link href="/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body style="background-color: rgb(29, 107, 143);">
    <div id="loginDiv" >
        <div style="margin: 0 auto;width: 320px;">
            <form id="resetForm" action="" method="post">
                <div>
                    <h3 style="text-align: center">重置密码</h3>
                </div>
                <div>
                    <%--隐藏域 方便向重置密码方法传递 用户名--%>
                    <input id="username" name="username" type="hidden" value=${username}>
                    <input id="validatecode" name="validatecode" class="easyui-textbox" data-options="iconWidth:30,iconAlign:'left',prompt:'验证码'" style="width:50%;height: 35px" maxlength="6" onfocus="check_yzm(1)" onblur="check_yzm(2);" style="width:100%;height:35px;"/>
                    <a href="javascript:;" class="easyui-linkbutton" style="width:48%;height:35px;right: 0;">
                        <img src="randomCode" id="vcodesrc" name="vcodesrc" onclick="randomCode();" style="width:100%; height:35px;right: 0;"  alt="点击，换一张！"/>
                        <%--this.src='randomCode?'+Math.random()--%>
                    </a>
                </div>
                <div>
                    <input id="email" name="email" class="easyui-validatebox radis" type="text" data-options="iconCls:'icon-man',iconWidth:30,iconAlign:'left',missingMessage:'邮箱名',required:true,validType:'email'" style="width:50%;height:35px;"/>
                    <a href="javascript:;" class="easyui-linkbutton" style="width:48%;height:35px;right: 0;" onclick="sendEmailCode();">发送邮箱验证码</a>
                </div>
                <div>
                    <input id="emailCode" name="emailCode" class="easyui-textbox" data-options="iconWidth:30,iconAlign:'left',prompt:'邮箱验证码'" style="width:100%;height:35px;"/>
                </div>
                <div>
                    <input id="newPassword" name="newPassword" class="easyui-validatebox radis" type="password" data-options="iconWidth:30,iconAlign:'left',missingMessage:'新密码',required:true" style="width:100%;height:35px;"/>
                </div>
                <div>
                    <input id="rePassword" name="rePassword" class="easyui-validatebox radis" type="password" data-options="iconWidth:30,iconAlign:'left',missingMessage:'请重复密码',required:true" style="width:100%;height:35px;"/>
                </div>
                <div>
                    <a href="javascript:;" class="easyui-linkbutton" style="width:100%;height:35px;" onclick="resetPW();">重置密码</a>
                </div>
                <div>
                    <div style="display:inline;">
                        <a href="/login">返回登录</a>
                    </div>
                    <div style="display:inline;margin-left:180px;">
                        <a href="javascript:void(0)"class="easyui-linkbutton" onclick="resetForm();">重置</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
<style type="text/css">
    .radis{
        border-radius: 5px 5px 5px 5px;
    }
</style>
<script type="text/javascript">
    // 发送邮箱验证码
    function sendEmailCode() {
        var data={
            "email":$('#email').val(),
            "emailCode":$('#emailCode').val(),
            "username":$('#username').val()
        };
        $.post("/sendEmailCode",data,function (result) {
            $.messager.alert("温馨提示", result.mes, "info");
        });
    };
    // 生成验证码
    function randomCode() {
        document.getElementById("vcodesrc").src='randomCode?'+Math.random();
    };
    // 重置用户密码
    function resetPW() {
        $('#resetForm').form('submit', {
            url:"/resetPW",
            onSubmit: function(){
                return true;
            },
            success:function(data){
                // 将Json字符串转成JSON对象
                var result = JSON.parse(data);
                if (result.success){// 发送成功,发送请求"/main",跳到main.jsp
                    $.messager.alert("温馨提示", result.mes, "info");
                }else {// 发送失败
                    $.messager.alert("温馨提示", result.mes, "error");
                }
            }
        });
    }
    function resetForm(){
        // 清空表单
        $("#resetForm").form("clear");
        // 重新生成验证码
        randomCode();
    };
    // 验证两次输入密码一致
    $("#rePassword").validatebox({
        validType: ['equals["#newPassword","jquery"]']
    });
    $(function () {
        $(document.documentElement).on("keyup", function(event) {
            var keyCode = event.keyCode;
            console.debug(keyCode);
            if (keyCode === 13) { // 捕获回车
                submitForm(); // 提交表单
            }
        });
    });
</script>
</html>