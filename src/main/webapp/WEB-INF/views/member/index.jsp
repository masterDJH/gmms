<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/member.js"></script>
    </head>
    <body>
        <div id="toolbar" style="padding:2px 5px;">
            <%--功能按钮--%>
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" data-method="add" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" data-method="update" plain="true">编辑</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" data-method="remove" plain="true">删除</a>
            <%--搜索框--%>
            <form id="searchForm" method="post">
                会员名: <input class="easyui-textbox" style="width:80px" id="memberName" name="memberName">
                <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
            </form>
        </div>
        <%--pagination:true 启用分页工具栏--%>
        <table id="memberDataGrid" class="easyui-datagrid" fit=true
           data-options="rownumbers:true,singleSelect:true,url:'/member/page',method:'post',fitColumns:true,toolbar:'#toolbar',pagination:true">
            <thead>
            <tr>
                <!-- 列的属性:
                    field: 字段 和数据的属性对应
                    formatter:格式化(如果要显示的数据和原数据格式不一致，就使用它)--其实就是修改显示该字段的HTML代码
                -->
                <%--此列隐藏,不显示,只提供id值--%>
                <th data-options="field:'memberName',width:100">会员名称</th>
                <th data-options="field:'memberGrade',width:100">会员等级</th>
                <th data-options="field:'discount',width:100">会员折扣</th>
                <th data-options="field:'memberCost',width:100">升级所耗积分</th>
            </tr>
            </thead>
        </table>
        <%--添加、修改页面--%>
        <div id="editDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="会员类型" style="width:400px">
            <div style="padding:10px 60px 20px 40px">
                <form id="editForm" class="easyui-form" method="post" data-options="">
                    <input type="hidden" id="memberId" name="id" >
                    <table cellpadding="5">
                        <tr>
                            <td>会员名称</td>
                            <td><input class="easyui-validatebox" type="text" name="memberName" id="memberName_update" data-options="required:true" validType="checkName"/></td>
                        </tr>
                        <tr>
                            <td>会员等级</td>
                            <td><input class="easyui-validatebox" type="text" name="memberGrade" data-options="required:true" validType="checkGrade" /></td>
                        </tr>
                        <tr>
                            <td>会员折扣</td>
                            <td><input class="easyui-validatebox" type="text" name="discount" data-options="required:true" validType="checkCost" /></td>
                        </tr>
                        <tr>
                            <td>升级所耗积分</td>
                            <td><input id="ss" class="easyui-numberspinner" name="memberCost" style="width:143px;" data-options="required:true,min:0,editable:true"></td>
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
