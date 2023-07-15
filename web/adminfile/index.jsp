<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/6/21
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>管理员主页</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminfile/css/index.css'/>">
</head>
<body>
    <table class="table" align="center">
        <tr>
            <td colspan="2" align="center" height="100px;">
                <iframe frameborder="0" src="<c:url value='/adminfile/jsp/top.jsp'/>" name="top"></iframe>
            </td>
        </tr>
        <tr>
            <td>
                <iframe frameborder="0" src="<c:url value='/adminfile/jsp/body.jsp'/>" name="body"></iframe>
            </td>
        </tr>
    </table>
</body>
</html>

<style>

</style>
