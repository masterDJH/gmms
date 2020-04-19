<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>健身房会员管理系统</title>
    <%@ include file="/WEB-INF/views/head.jsp"%>
    <link href="/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body >
    <div id="loginDiv" >
        <div style="margin: 0 auto;width: 320px;">
            <form id="loginForm" action="" method="post">
                <div>
                    <h1 style="text-align: center">健身房会员管理系统</h1>
                </div>
                <div>
                    <input id="username" name="username" class="easyui-textbox"  data-options="iconCls:'icon-man',iconWidth:30,iconAlign:'left',prompt:'用户名'" style="width:100%;height:35px;"/>
                </div>
                <div>
                    <input name="password" class="easyui-passwordbox" data-options="iconWidth:30,iconAlign:'left',prompt:'密码'" style="width:100%;height:35px;"/>
                </div>
                <div>
                    <input name="validatecode" class="easyui-textbox" data-options="iconWidth:30,iconAlign:'left',prompt:'验证码'" style="width:50%;height: 35px" maxlength="6" onfocus="check_yzm(1)" onblur="check_yzm(2);" />
                    <a href="javascript:;" class="easyui-linkbutton" style="width:48%;height:35px;right:0;">
                        <img src="randomCode" id="vcodesrc" name="vcodesrc" onclick="randomCode();" style="width:100%; height:35px;right: 0;"  alt="点击，换一张！"/>
                    </a>
                </div>
                <div>
                    <a href="javascript:;" class="easyui-linkbutton" style="width:100%;height:35px;" onclick="submitForm();">登陆</a>
                </div>
                <div>
                    <div style="display:inline;">
                        <a id="forget" href="" onclick="forget()">忘记密码？</a>
                    </div>
                    <div style="display:inline;margin-left:180px;">
                        <a href="javascript:void(0)"class="easyui-linkbutton" onclick="resetForm();">重置</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
<script type="text/javascript">
    function submitForm() {
        $('#loginForm').form('submit', {
            url:"/login",
            onSubmit: function(){
                return true;
            },
            success:function(data){
                // 将Json字符串转成JSON对象
                var result = JSON.parse(data);
                if (result.success){// 登录成功,发送请求"/main",跳到main.jsp
                    window.location.href = "/main";
                }else {// 登录失败
                    $.messager.alert("温馨提示", result.mes, "info");
                }
            }
        });

    };
    // 生成验证码
    function randomCode() {
        document.getElementById("vcodesrc").src='randomCode?'+Math.random();
    };
    function resetForm(){
        // 清空表单
        $("#loginForm").form("clear");
        // 重新生成验证码
        randomCode();
    };
    function forget(){
        // 动态改变 忘记密码 的a标签的href地址值
        document.getElementById("forget").href="/forget?username="+$('#username').val();
    };
    $(function () {
        $(document.documentElement).on("keyup", function(event) {
            //console.debug(event.keyCode);
            var keyCode = event.keyCode;
            if (keyCode === 13) { // 捕获回车
                submitForm(); // 提交表单
            }
        });
        // 动态监听值
        // $("#username").textbox({
        //     onChange:function(value,o){
        //         console.log("改变："+value);
        //         $("forget").attr("href","/forget?"+value);
        //     }
        // });
        // 检查自己是否是顶级页面
        if (top != window) {// 如果不是顶级
            //把子页面的地址，赋值给顶级页面显示
            window.top.location.href = window.location.href;
        }
    });
    //------------------------------------------------------------------------------------------
</script>
</html>