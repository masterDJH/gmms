<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/score.js"></script>
    </head>
    <body>
        <div id="toolbar" style="padding:2px 5px;">
            <%--功能按钮--%>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" data-method="update" plain="true">编辑</a>
            <%--搜索框--%>
            <form id="searchForm" method="post">
                用户名: <input class="easyui-textbox" style="width:110px" id="username" name="username">
                手机: <input class="easyui-textbox" style="width:110px" id="phone" name="phone">
                <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
            </form>
        </div>
        <%--pagination:true 启用分页工具栏--%>
        <table id="scoreDataGrid" class="easyui-datagrid" fit=true
           data-options="rownumbers:true,singleSelect:true,url:'/score/page',method:'post',fitColumns:true,toolbar:'#toolbar',pagination:true">
            <thead>
            <tr>
                <!-- 列的属性:
                    field: 字段 和数据的属性对应
                    formatter:格式化(如果要显示的数据和原数据格式不一致，就使用它)--其实就是修改显示该字段的HTML代码
                -->
                <%--此列隐藏,不显示,只提供id值--%>
                <th data-options="field:'id',width:50" hidden=true>ID</th>
                <th data-options="field:'username',width:50">用户名</th>
                <th data-options="field:'score',width:50,align:'center'">积分</th>
            </tr>
            </thead>
        </table>
        <%--修改页面--%>
        <div id="editDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="积分" style="width:400px">
            <div style="padding:10px 60px 20px 40px">
                <form id="editForm" class="easyui-form" method="post" data-options="">
                    <input type="hidden" id="employeeId" name="id" >
                    <table cellpadding="5">
                        <tr id="username_update">
                            <td>用户名:</td>
                            <td><input class="easyui-validatebox" type="text" name="username" data-options="required:true">
                                </input>
                            </td>
                        </tr>
                        <tr>
                            <td>积分:</td>
                            <td><input class="easyui-validatebox" type="text" name="score" data-options="required:true,validType:'zeroInteger'"></input></td>
                        </tr>
                    </table>
                </form>
                <div style="text-align:center;padding:5px">
                    <a href="#" class="easyui-linkbutton c1" data-options="iconCls:'icon-ok'" data-method="save">提交</a>
                    <a href="#" class="easyui-linkbutton c2" data-options="iconCls:'icon-cancel'" data-method="close">取消</a>
                </div>
            </div>
        </div>
    </body>
</html>
