<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/mymembscore.js"></script>
    </head>
    <body>
        <div class="easyui-layout" style="width:100%;height:100%;">
            <div data-options="region:'center',split:true" style="width:430px;">
                <%--等级积分页面--%>
                <div >
                    <div class="membscore_info">
                        <form id="editForm" class="easyui-form" method="post" data-options="">
                            <input type="hidden" id="employeeId" name="id" >
                            <table cellpadding="5">
                                <tr class="inputInfo">
                                    <td>会员等级:</td>
                                    <td><input class="easyui-textbox" type="text" id="member" name="memberName" data-options="formatter:member" readonly="true"></input></td>
                                </tr>
                                <tr class="inputInfo">
                                    <td>当前积分:</td>
                                    <td><input class="easyui-textbox" type="text" id="score" name="score" data-options="" readonly="true"></input></td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <div class="div_button_info">
                        <a href="#" class="easyui-linkbutton c1 buttonInfo" iconCls="icon-edit" data-method="upgrade" plain="true">升级</a>
                    </div>
                </div>
            </div>
            <%--会员等级升级页面--%>
            <div id="upgradeDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="会员升级" style="width:400px">
                <div style="padding:10px 60px 20px 40px">
                    <form id="upgradeForm" class="easyui-form" method="post" data-options="">
                        <input type="hidden" name="id" >
                        <table cellpadding="5">
                            <tr>
                                <td>会员等级:</td>
                                <td>
                                    <input class="easyui-combobox" name="member.id" id="ecombox"
                                           data-options="valueField:'id',textField:'memberName',panelHeight:'auto',url:'/mymembscore/findMembers'"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <div style="text-align:center;padding:5px;">
                        <a href="#" class="easyui-linkbutton c1" data-options="iconCls:'icon-ok'" data-method="save">提交</a>
                        <a href="#" class="easyui-linkbutton c2" data-options="iconCls:'icon-cancel'" data-method="close">取消</a>
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
        .membscore_info{
            width: 253px;
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
