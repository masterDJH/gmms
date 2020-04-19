<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/employee.js"></script>
    </head>
    <body>
        <div id="toolbar" style="padding:2px 5px;">
            <%--功能按钮--%>
            <shiro:hasPermission name="employee:save">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" data-method="add" plain="true">添加</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="employee:update">
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" data-method="update" plain="true">编辑</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="employee:delete">
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" data-method="remove" plain="true">删除</a>
            </shiro:hasPermission>
            <%--搜索框--%>
            <form id="searchForm" method="post">
                用户名: <input class="easyui-textbox" style="width:110px" id="username" name="username">
                手机: <input class="easyui-textbox" style="width:110px" id="phone" name="phone">
                <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
            </form>
        </div>
        <%--pagination:true 启用分页工具栏--%>
        <table id="employeeDataGrid" class="easyui-datagrid" fit=true
           data-options="rownumbers:true,singleSelect:true,url:'/employee/page',method:'post',fitColumns:true,toolbar:'#toolbar',pagination:true">
            <thead>
            <tr>
                <!-- 列的属性:
                    field: 字段 和数据的属性对应
                    formatter:格式化(如果要显示的数据和原数据格式不一致，就使用它)--其实就是修改显示该字段的HTML代码
                -->
                <%--此列隐藏,不显示,只提供id值--%>
                <th data-options="field:'id',width:40" hidden=true>ID</th>
                <th data-options="field:'username',width:40,sortable:true">用户名</th>
                <th data-options="field:'age',width:20,align:'center',sortable:true">年龄</th>
                <th data-options="field:'headImg',width:20,align:'center',sortable:true,formatter:imageformat">头像</th>
                <th data-options="field:'email',width:80,align:'center',sortable:true">邮箱</th>
                <th data-options="field:'phone',width:80,align:'center',sortable:true">手机号</th>
                <th data-options="field:'member',width:80,align:'center',sortable:true,formatter:member">用户类型</th>
            </tr>
            </thead>
        </table>
        <%--添加、修改页面--%>
        <div id="editDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="用户" style="width:400px">
            <div style="padding:10px 60px 20px 40px">
                <form id="editForm" class="easyui-form" method="post" data-options="">
                    <input type="hidden" id="employeeId" name="id" >
                    <table cellpadding="5">
                        <tr id="username_update">
                            <td>用户名:</td>
                            <td><input class="easyui-validatebox" type="text" name="username" data-options="required:true"
                                       validType="checkUsername">
                                </input>
                            </td>
                        </tr>
                        <tr data-save="true">
                            <td>密码:</td>
                            <td><input id="password" class="easyui-validatebox" type="password"
                                       name="password" data-options="required:true"></input></td>
                        </tr>
                        <tr data-save="true">
                            <td>重复密码:</td>
                            <td><input class="easyui-validatebox" type="password"
                                       name="repassword" data-options="required:true" id="repassword"></input></td>
                        </tr>
                        <tr>
                            <td>邮件:</td>
                            <td><input class="easyui-validatebox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
                        </tr>
                        <tr>
                            <td>年龄:</td>
                            <td><input class="easyui-validatebox" type="text" name="age" data-options="required:true,validType:'integer'"></input></td>
                        </tr>
                        <tr>
                            <td>手机号:</td>
                            <td><input class="easyui-validatebox" type="text" name="phone" data-options="required:true" validType="checkPhone"></input></td>
                        </tr>
                        <tr>
                            <td>用户类型:</td>
                            <td>
                                <input class="easyui-combobox" name="member.id" id="ecombox"
                                       data-options="valueField:'id',textField:'memberName',panelHeight:'auto',url:'/member/findMembers'"/>
                            </td>
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
