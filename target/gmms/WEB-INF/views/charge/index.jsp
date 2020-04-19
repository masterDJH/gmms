<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/charge.js"></script>
    </head>
    <body>
        <div class="easyui-layout" style="width:100%;height:100%;">
            <div data-options="region:'center',split:true" style="width:430px;">
                <%--充值页面--%>
                <div >
                    <div class="charge_info">
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
                                    <td>充值金额（￥）:</td>
                                    <td><input class="easyui-validatebox" type="text" id="score" name="score" data-options="required:true,validType:'integer'"></input></td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <div class="div_button_info">
                        <a href="#" class="easyui-linkbutton c1 buttonInfo" data-options="iconCls:'icon-ok'" data-method="charge" plain="true">提交</a>
                    </div>
                </div>
            </div>
        </div>

    </body>
    <style type="text/css">
        .div_button_info{
            text-align:center;
            padding:5px;
            width: 280px;
            height: 28px;
            margin: 0 auto;
        }
        .charge_info{
            width: 285px;
            height: 106px;
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
