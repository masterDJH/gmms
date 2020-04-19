<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/course.js"></script>
    </head>
    <body>
        <div id="toolbar" style="padding:2px 5px;">
            <%--功能按钮--%>
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" data-method="add" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" data-method="update" plain="true">编辑</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" data-method="remove" plain="true">删除</a>
            <%--搜索框--%>
            <form id="searchForm" method="post">
                课程名: <input class="easyui-textbox" style="width:80px" id="courseName" name="courseName">
                原价: <input class="easyui-textbox" style="width:80px" id="courseCost" name="courseCost">
                <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
                <!-- button不加type属性就是提交 -->
                <%--<button class="easyui-linkbutton" iconCls="icon-search">导出</button>--%>
            </form>
        </div>

        <%--pagination:true 启用分页工具栏--%>
        <table id="courseDataGrid" fit=true
           data-options="rownumbers:true,method:'post',toolbar:'#toolbar',pagination:true">
        </table>

        <%--添加、修改页面--%>
        <div id="editDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="课程" style="width:840px;height: 437px;">
            <div style="padding:10px 60px 20px 40px">
                <form id="editForm" class="easyui-form" method="post" enctype="multipart/form-data" data-options="">
                    <input type="hidden" id="courseId" name="id" >
                    <table cellpadding="5">
                        <tr>
                            <td>课程名:</td>
                            <td><input class="easyui-validatebox cc" type="text" name="courseName" data-options="required:true">
                                </input>
                            </td>
                        </tr>
                        <tr>
                            <td>原价:</td>
                            <td><input class="easyui-numberspinner"
                                       name="courseCost" data-options="required:true" id="courseCostPrice"></input>
                            </td>
                        </tr>
                        <tr>
                            <td>课程时长(月):</td>
                            <td><input class="easyui-numberspinner" name="courseTime"
                                       data-options="required:true" id="courseTime">
                                </input>
                            </td>
                        </tr>
                        <tr>
                            <td>课程图片:</td>
                            <td>
                                <input class="easyui-filebox" name="courseImg" id="pic"
                                       data-options="prompt:'选择图片',buttonText:'选择'" >
                            </td>
                        </tr>
                        <tr>
                            <td>课程描述:</td>
                            <td>
                                <div id="editor" class="easyui-texteditor" style="width:600px;height:200px;" ></div>
                                <input type="hidden" name="descs" id="descs"></input>
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
    <style type="text/css">
        *{
            margin:0;
            padding:0;
        }
        .detail{
            display: inline-block;
            width: 278px;
            margin-top: 0;
        }
        .cc{
            /*优化文字输入框宽度*/
            width: 139px;
            /*优化文字输入框高度*/
            height: 25px;
        }
    </style>
</html>
