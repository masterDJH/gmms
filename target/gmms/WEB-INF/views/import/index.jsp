<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>导入</title>
    <%@include file="/WEB-INF/views/head.jsp" %>
</head>
<body>
    <h3>导入新用户（Excel文件）</h3>
    <!-- 上传必需是:post,enctype="multipart/form-data"-->
    <form action="/import/employeeXlsx" method="post" enctype="multipart/form-data">
        <input class="easyui-filebox" name="empFile" style="width:50%"
               data-options="prompt:'选择一个文件...',buttonText: '选择文件'" />
        <button class="easyui-linkbutton" iconCls="icon-redo">导入</button>
        <span style="color: #0fc139">${empResult}</span>
    </form>
    <h3>导入新课程（Excel文件）</h3>
    <!-- 上传必需是:post,enctype="multipart/form-data"-->
    <form action="/import/courseXlsx" method="post" enctype="multipart/form-data">
        <input class="easyui-filebox" name="courseFile" style="width:50%"
               data-options="prompt:'选择一个文件...',buttonText: '选择文件'" />
        <button class="easyui-linkbutton" iconCls="icon-redo">导入</button>
        <span style="color: #0fc139">${couResult}</span>
    </form>
</body>
</html>
