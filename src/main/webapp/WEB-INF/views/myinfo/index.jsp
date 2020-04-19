<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/myinfo.js"></script>
    </head>
    <body>
        <div class="easyui-layout" style="width:100%;height:100%;">
            <div data-options="region:'center',split:true" style="width:430px;">
                <%--个人信息修改页面--%>
                <div >
                    <div class="self_info">
                        <form id="editForm" class="easyui-form" method="post" enctype="multipart/form-data" data-options="">
                            <input type="hidden" id="employeeId" name="id" >
                            <table cellpadding="5">
                                <tr class="inputInfo">
                                    <td>用户名:</td>
                                    <td><input class="easyui-validatebox" type="text" id="username" name="username" readonly="true" data-options="">
                                        </input>
                                    </td>
                                </tr>
                                <tr class="inputInfo">
                                    <td>邮件:</td>
                                    <td><input class="easyui-validatebox" type="text" id="email" name="email" data-options="required:true,validType:'email'" readonly="true"></input></td>
                                </tr>
                                <tr class="inputInfo">
                                    <td>年龄:</td>
                                    <td><input class="easyui-validatebox" type="text" id="age" name="age" data-options="validType:'integer'" readonly="true"></input></td>
                                </tr>
                                <tr class="inputInfo">
                                    <td>手机号:</td>
                                    <td><input class="easyui-validatebox" type="text" id="phone" name="phone" data-options="required:true" validType="checkPhone" readonly="true"></input></td>
                                </tr>
                                <tr>
                                    <td>头像:</td>
                                    <td>
                                        <input class="easyui-filebox" id="headImg" name="headImage" readonly="true"
                                               data-options="prompt:'选择图片',buttonText:'选择'">
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <div class="div_button_info">
                        <a href="#" class="easyui-linkbutton c4 buttonInfo" iconCls="icon-edit" data-method="update" plain="true">编辑</a>
                        <a href="#" class="easyui-linkbutton c1 buttonInfo" data-options="iconCls:'icon-ok'" data-method="save" plain="true">提交</a>
                        <a href="#" class="easyui-linkbutton c8 buttonInfo" data-options="iconCls:'icon-edit'" data-method="updatePassword" plain="true">密码修改</a>
                    </div>
                </div>
            </div>
            <%--密码修改页面--%>
            <div id="editPasswordDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="密码修改" style="width:400px">
                <div style="padding:10px 60px 20px 40px">
                    <form id="editPasswordForm" class="easyui-form" method="post" data-options="">
                        <input type="hidden" name="id" >
                        <table cellpadding="5">
                            <tr>
                                <td>原密码：</td>
                                <td><input class="easyui-validatebox" type="password" id="password" name="password" id="memberName_update" data-options="required:true" /></td>
                            </tr>
                            <tr>
                                <td>新密码：</td>
                                <td><input class="easyui-validatebox" type="password" id="newPassword" name="newPassword" data-options="required:true" /></td>
                            </tr>
                            <tr>
                                <td>重复密码：</td>
                                <td><input class="easyui-validatebox" type="password" id="rePassword" name="rePassword" data-options="required:true" /></td>
                            </tr>
                        </table>
                    </form>
                    <div style="text-align:center;padding:5px;">
                        <a href="#" class="easyui-linkbutton c1" data-options="iconCls:'icon-ok'" data-method="passSave">提交</a>
                        <a href="#" class="easyui-linkbutton c2" data-options="iconCls:'icon-cancel'" data-method="passClose">取消</a>
                    </div>
                </div>
            </div>
        </div>

    </body>
    <script type="text/javascript">
        // 验证两次输入密码一致
        $("#rePassword").validatebox({
            validType: ['equals["#newPassword","jquery"]']
        });
    </script>
    <style type="text/css">
        .div_button_info{
            text-align:center;
            padding:5px;
            width: 280px;
            height: 28px;
            margin: 0 auto;
        }
        .div_button_pass_info{
            text-align:center;
            padding:5px;
            width: 0px;
            height: 28px;
            margin: 0 auto;
        }
        .self_info{
            width: 253px;
            height: 252px;
            margin: 0 auto;
        }
        .buttonInfo{
            width: 88px;
        }
        .inputInfo{
            height: 50px;
        }
    </style>
</html>
