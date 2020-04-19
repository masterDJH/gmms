<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/permission.js"></script>
    </head>
    <body>
        <div id="toolbar" style="padding:2px 5px;">
            <%--功能按钮--%>
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" data-method="add" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" data-method="update" plain="true">编辑</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" data-method="remove" plain="true">删除</a>
            <%--搜索框--%>
            <form id="searchForm" method="post">
                权限名: <input class="easyui-textbox" style="width:80px" id="perName" name="perName">
                <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
            </form>
        </div>
        <%--pagination:true 启用分页工具栏--%>
        <table id="permissionDataGrid" class="easyui-datagrid" fit=true
           data-options="rownumbers:true,singleSelect:true,url:'/permission/page',method:'post',fitColumns:true,toolbar:'#toolbar',pagination:true">
            <thead>
            <tr>
                <!-- 列的属性:
                    field: 字段 和数据的属性对应
                    formatter:格式化(如果要显示的数据和原数据格式不一致，就使用它)--其实就是修改显示该字段的HTML代码
                -->
                <%--此列隐藏,不显示,只提供id值--%>
                <th data-options="field:'perName',width:100">名称</th>
                <th data-options="field:'sn',width:100">编码</th>
                <th data-options="field:'perUrl',width:100">路径</th>
                <th data-options="field:'descs',width:100">描述</th>
                <th data-options="field:'menu',width:80,align:'center',formatter:menu">菜单</th>
            </tr>
            </thead>
        </table>
        <%--添加、修改页面--%>
        <div id="editDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="权限" style="width:400px">
            <div style="padding:10px 60px 20px 40px">
                <form id="editForm" class="easyui-form" method="post" data-options="">
                    <input type="hidden" id="permissionId" name="id" >
                    <table cellpadding="5">
                        <tr>
                            <td>名称</td>
                            <td><input class="easyui-validatebox" type="text" name="perName" data-options="required:true" /></td>
                        </tr>
                        <tr>
                            <td>编码</td>
                            <td><input class="easyui-validatebox" type="text" name="sn" data-options="required:true" /></td>
                        </tr>
                        <tr>
                            <td>路径</td>
                            <td><input class="easyui-validatebox" type="text" name="perUrl" data-options="required:true" /></td>
                        </tr>
                        <tr>
                            <td>描述</td>
                            <td><input class="easyui-validatebox" type="text" name="descs" data-options="required:true" /></td>
                        </tr>
                        <tr>
                            <td>菜单:</td>
                            <td>
                                <input class="easyui-combobox" name="menu.id" id="ecombox"
                                       data-options="valueField:'id',textField:'name',panelHeight:'200',url:'/menu/findAllChildrenMenus'"/>
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
