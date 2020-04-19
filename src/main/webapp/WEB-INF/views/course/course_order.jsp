<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Title</title>
        <%--引入公共的JSP,以引入Easyui的各种CSS、JS--%>
        <%@include file="/WEB-INF/views/head.jsp"%>
        <script type="text/javascript" src="/js/model/course_order.js"></script>
    </head>
    <body>
        <div id="toolbar" style="padding:2px 5px;">
            <%--搜索框--%>
            <form id="searchForm" action="/courseOrder/search" method="post">
                课程名: <input class="easyui-textbox" style="width:80px" id="courseName" name="courseName" value="${search}">
                <%--<a href="/courseOrder/search" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>--%>
                <button class="easyui-linkbutton" iconCls="icon-search">搜索</button>
            </form>
        </div>
        <c:forEach items="${courses}" var="course">
            <form id="oederForm${course.id}" method="get">
                <div class="co">
                    <div class="co_img">
                        <img src="${course.pic}" style="width: 150px;height:150px"/>
                    </div>
                    <div class="co_title">
                        <input type="hidden" id="orderId" name="orderId" >
                        <p>${course.courseName}</p>
                    </div>
                    <div class="co_desc">
                        <span>${course.descs}</span>
                    </div>
                    <div class="co_sell">
                        <p><span>￥${course.courseCost} 积分 </span><span>时长: ${course.courseTime} 月</span></p>
                        <a href="/courseOrder/order?id=${course.id}" class="easyui-linkbutton" plain="true">订购</a>
                    </div>
                </div>
            </form>
        </c:forEach>

    </body>
    <style type="text/css">
        *{
            margin:0;
            padding:0;
        }
        .co{
            width: 150px;
            text-align: center;
            float: left;
            margin-top: 5px;
            margin-left: 10px;
            font-size: small;
        }
        .co_title{
            width: 150px;
            margin: 5px 0;
            text-align: center;
        }
        .co_desc{
            width: 150px;
            height: 40px;
            overflow: hidden;
        }
        .co_sell{
            margin-top: 5px;
        }
        .co_order{
            width: 60px;
        }
    </style>
</html>
