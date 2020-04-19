<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>导出</title>
    <%@include file="/WEB-INF/views/head.jsp" %>
    <script type="text/javascript" src="/js/model/export.js"></script>
</head>
<body>

    <div id="exportEmp" style="width: 100%;height:50%">
        <div id="toolbar1" style="padding:2px 5px;">
            <span class="exportTitle">导出用户（搜索用户并导出）</span>
            <%--搜索框--%>
            <form id="exportEmpForm" action="/export/exportEmpXlsx" method="post">
                用户名: <input class="easyui-textbox" style="width:110px" id="username" name="username">
                手机: <input class="easyui-textbox" style="width:110px" id="phone" name="phone">
                <%--<a href="#" data-method="exportEmp" class="easyui-linkbutton" iconCls="icon-search">导出</a>--%>
                <a href="#" data-method="empSearch" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
                <!-- button不加type属性就是提交 -->
                <button class="easyui-linkbutton" iconCls="icon-undo">导出</button>
            </form>
        </div>
        <%--pagination:true 启用分页工具栏--%>
        <table id="employeeDataGrid" class="easyui-datagrid" fit=true
               data-options="rownumbers:true,singleSelect:true,url:'/employee/page',method:'post',fitColumns:true,toolbar:'#toolbar1',pagination:true,pageSize:2,pageList:[1,2,3,4]">
            <thead>
            <tr>
                <!-- 列的属性:
                    field: 字段 和数据的属性对应
                    formatter:格式化(如果要显示的数据和原数据格式不一致，就使用它)--其实就是修改显示该字段的HTML代码
                -->
                <%--此列隐藏,不显示,只提供id值--%>
                <th data-options="field:'id',width:40" hidden=true>ID</th>
                <th data-options="field:'username',width:40">用户名</th>
                <th data-options="field:'age',width:20,align:'center'">年龄</th>
                <th data-options="field:'headImg',width:20,align:'center',formatter:imageformat">头像</th>
                <th data-options="field:'email',width:80,align:'center'">邮箱</th>
                <th data-options="field:'phone',width:80,align:'center'">手机号</th>
                <th data-options="field:'member',width:80,align:'center',formatter:member">用户类型</th>
            </tr>
            </thead>
        </table>
    </div>
    <div id="exportCou" style="width: 100%;height:50%">
        <div id="toolbar2" style="padding:2px 5px;">
            <span class="exportTitle">导出课程（搜索课程并导出）</span>
            <%--搜索框--%>
            <form id="exportCouForm" action="/export/exportCouXlsx" method="post">
                课程名: <input class="easyui-textbox" style="width:110px" id="courseName" name="courseName">
                原价: <input class="easyui-textbox" style="width:110px" id="courseCost" name="courseCost">
                <%--<a href="#" data-method="exportCou" class="easyui-linkbutton" iconCls="icon-search">导出</a>--%>
                <a href="#" data-method="couSearch" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
                <!-- button不加type属性就是提交 -->
                <button class="easyui-linkbutton" iconCls="icon-undo">导出</button>
            </form>
        </div>
        <%--pagination:true 启用分页工具栏--%>
        <table id="courseDataGrid" fit=true
               data-options="rownumbers:true,method:'post',toolbar:'#toolbar2',pagination:true,pageSize:2,pageList:[1,2,3,4]">
        </table>
    </div>
</body>
<style type="text/css">
    .exportTitle{
        font-size: large;
        font-weight: bolder;
    }
</style>
</html>
