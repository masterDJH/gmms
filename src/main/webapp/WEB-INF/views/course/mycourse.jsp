<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
    <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
    <%@include file="/WEB-INF/views/head.jsp"%>
    <script type="text/javascript" src="/js/model/mycourse.js"></script>
</head>
<body>
<div id="toolbar" style="padding:2px 5px;">
    <%--搜索框--%>
    <form id="searchForm" method="post">
        课程名: <input class="easyui-textbox" style="width:80px" id="courseName" name="courseName">
        <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" data-method="remove" plain="true">退订</a>
    </form>
</div>
<%--pagination:true 启用分页工具栏--%>
<table id="myCourseDataGrid" class="easyui-datagrid" fit=true
       data-options="rownumbers:true,singleSelect:true,url:'/course/mypage',method:'post',fitColumns:true,toolbar:'#toolbar',pagination:true,pageSize:5,pageList:[5,10,15]">
    <thead>
    <tr>
        <!-- 列的属性:
            field: 字段 和数据的属性对应
            formatter:格式化(如果要显示的数据和原数据格式不一致，就使用它)--其实就是修改显示该字段的HTML代码
        -->
        <%--此列隐藏,不显示,只提供id值--%>
        <th data-options="field:'id',width:40" hidden=true>ID</th>
        <th data-options="field:'courseName',width:40">课程名</th>
        <th data-options="field:'courseCost',width:20,align:'center'">原价</th>
        <th data-options="field:'courseTime',width:20,align:'center'">课程时长(月)</th>
        <th data-options="field:'pic',width:20,align:'center',formatter:imageformat">课程图片</th>
        <th data-options="field:'descs',width:80,align:'center'">课程描述</th>
        <%--<th data-options="field:'scDate',width:80,align:'center',sortable:true">订购日期</th>--%>
    </tr>
    </thead>
</table>
</body>
</html>
