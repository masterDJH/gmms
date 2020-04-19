<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/index/index.js"></script>
        <script type="text/javascript" src="/js/model/contextmenu.js"></script>
    </head>
    <body class="easyui-layout">
        <div data-options="region:'north'" style="height:80px;background-color: rgb(49, 146, 190);
                background: url(/images/main_head.jpg) no-repeat;background-size: cover;">
            <span style="font-size: x-large">健身房会员管理系统</span>
            <%--头像--%>
            <div style="width: 50px;height: 50px;margin-right:68px;float: right;">  <%--float: right;--%>
                <img id="head" src="" style="width: 50px;height: 50px;display:inline-block;border-radius:25px;" >
            </div>
            <div style="text-align: right;margin-top: 20px; margin-right:10px;">
                欢迎 <span style="color: rgb(40, 123, 222)"><shiro:principal property="username"/></span> 登录 <a href="/logout" style="color: rgb(214, 48, 219)">注销</a>
            </div>
        </div>
        <div data-options="region:'west',title:'菜单',split:true" style="width:150px;">
            <ul id="menuTree" class="easyui-tree"></ul>
        </div>
        </div>
        <div data-options="region:'center'" id="euiTabs" class="easyui-tabs" style="padding:0px;background:rgb(232, 239, 243);">
            <div title="主页" data-options="closable:true" style="padding:20px;display:none;">
                <h2>欢迎使用健身房会员管理系统</h2>
            </div>
        </div>
        <div id="rcmenu" class="easyui-menu" style="">
            <div id="closeall">关闭全部标签</div>
            <div id="closeother">关闭其他标签</div>
            <div id="closeleft">关闭左侧标签</div>
            <div id="closeright">关闭右侧标签</div>
        </div>
    </body>
    <script type="text/css">
        .back{
            background: url(/images/login.jpg) no-repeat;
            background-size: cover;
        }
    </script>
</html>
