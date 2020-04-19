<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/role.js"></script>
    </head>
    <body>
        <div id="toolbar" style="padding:2px 5px;">
            <%--功能按钮--%>
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" data-method="add" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" data-method="update" plain="true">编辑</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" data-method="remove" plain="true">删除</a>
            <%--搜索框--%>
            <form id="searchForm" method="post">
                名称: <input class="easyui-textbox" style="width:100px" id="roleName" name="roleName">
                <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
            </form>
        </div>
        <%--pagination:true 启用分页工具栏--%>
        <table id="roleDataGrid" class="easyui-datagrid back" fit=true
           data-options="rownumbers:true,rowTooltip: false,singleSelect:true,url:'/role/page',method:'post',fitColumns:true,toolbar:'#toolbar',pagination:true">
            <thead>
            <tr>
                <!-- 列的属性:
                    field: 字段 和数据的属性对应
                    formatter:格式化(如果要显示的数据和原数据格式不一致，就使用它)--其实就是修改显示该字段的HTML代码
                -->
                <%--此列隐藏,不显示,只提供id值--%>
                <th data-options="field:'id',width:40" hidden=true>ID</th>
                <th data-options="field:'roleName',width:40">名称</th>
                <th data-options="field:'sn',width:100,align:'left'">编码</th>
                <th data-options="field:'permissions',width:80,align:'center',formatter:permsFormat,tooltip:true">权限</th>
            </tr>
            </thead>
        </table>
        <%--添加、修改页面--%>
        <div id="editDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="角色" style="width:900px">
            <div style="padding:10px 30px 20px 40px">
                <form id="editForm" class="easyui-form" method="post" data-options="">
                    <input type="hidden" id="roleId" name="id" >
                    <table>
                        <tr>
                            <td>
                                名称:<input class="easyui-validatebox" type="text" name="roleName" data-options="required:true">
                                    </input>
                            </td>
                            <td>
                                编码:<input id="sn" class="easyui-validatebox" type="text" name="sn" data-options="required:true">
                                    </input>
                            </td>
                        </tr>
                    </table>
                    <div class="easyui-layout" style="width:100%;height:350px;">
                        <div data-options="region:'west',split:true" style="width:430px;">
                            <table id="rolePermsGrid"></table>
                        </div>
                        <div data-options="region:'center'">
                            <table id="allPermsGrid"></table>
                        </div>
                    </div>
                </form>
                <div id="btns" style="text-align:right;padding:5px">
                    <a href="#" class="easyui-linkbutton c1" data-options="iconCls:'icon-ok'" data-method="save">提交</a>
                    <a href="#" class="easyui-linkbutton c2" data-options="iconCls:'icon-cancel'" data-method="close">取消</a>
                </div>
            </div>
        </div>
    </body>
    <script type="text/css">
        back{
            background: url(/images/main_head.jpg) no-repeat;
            background-size: cover;
        }
    </script>
</html>
