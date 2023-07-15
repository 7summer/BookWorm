<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/6/21
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminfile/css/top.css'/>">
</head>
<body>
    <h1 style="text-align: center; line-height: 30px;">传智播客网上书城系统后台管理</h1>
    <div style="line-height: 10px;">
        <c:choose>
            <c:when test="${empty sessionScope.admin}">
                <a href="<c:url value='/adminfile/jsp/admin/login.jsp'/>" target="_parent">管理员登录</a>
            </c:when>
            <c:otherwise>
                <span>管理员：${sessionScope.admin.adminName}</span>
                <a target="_top" href="<c:url value='/AdminServlet?method=quit'/>">退出</a>
                <span style="padding-left:50px;">
                    <a href="<c:url value='/admin/AdminCategoryServlet?method=findAll'/>" target="body">分类管理</a>
                    <a href="<c:url value='/adminfile/jsp/book/main.jsp'/>" target="body">图书管理</a>
                    <a href="<c:url value='/admin/AdminOrderServlet?method=findAll'/>" target="body">订单管理</a>
                </span>
            </c:otherwise>
        </c:choose>

    </div>
</body>
</html>
