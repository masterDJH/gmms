<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/check.js"></script>
    </head>
    <body>
        <div id="toolbar" style="padding:2px 5px;">
            <%--搜索框--%>
            <form id="searchForm" method="post">
                标题: <input class="easyui-textbox" style="width:80px" id="title" name="title">
                <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
                <!-- button不加type属性就是提交 -->
                <%--<button class="easyui-linkbutton" iconCls="icon-search">导出</button>--%>
            </form>
        </div>

        <%--pagination:true 启用分页工具栏--%>
        <table id="announcementDataGrid" fit=true
           data-options="rownumbers:true,method:'post',toolbar:'#toolbar',pagination:true">
        </table>

        <%--添加、修改页面--%>
        <div id="editDialog" class="easyui-dialog" data-options="closed:true,modal:true" title="课程" style="width:840px;height: 437px;">
            <div style="padding:10px 60px 20px 40px">
                <form id="editForm" class="easyui-form" method="post" enctype="multipart/form-data" data-options="">
                    <input type="hidden" id="announcementId" name="id" >
                    <table cellpadding="5">
                        <tr>
                            <td>标题:</td>
                            <td><input class="easyui-validatebox cc" type="text" name="title" data-options="required:true">
                                </input>
                            </td>
                        </tr>
                        <tr>
                            <td>公告内容:</td>
                            <td>
                                <div id="editor" class="easyui-texteditor" style="width:600px;height:200px;" ></div>
                                <input type="hidden" name="info" id="info"></input>
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
            width: 800px;
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
