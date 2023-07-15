<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/21
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>主页</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/css/index.css'/>">
</head>
<body>
<table class="table" align="center">
    <tr class="trTop">
        <td colspan="2" class="tdTop">
            <iframe frameborder="0" src="<c:url value='/jsp/top.jsp'/>" name="top"></iframe>
        </td>
    </tr>
    <tr>
        <td class="tdLeft" rowspan="2">
            <iframe frameborder="0" src="<c:url value='/CategoryServlet?method=findAll'/>" name="left"></iframe>
        </td>
        <td class="tdSearch" style="border-bottom-width: 0px">
            <iframe frameborder="0" src="<c:url value='/jsp/search.jsp'/>" name="search"></iframe>
        </td>
    </tr>
    <tr>
        <td class="tdBody" style="border-top-width: 0px">
            <iframe frameborder="0" src="<c:url value='/jsp/body.jsp'/>" name="body"></iframe>
        </td>
    </tr>
</table>
</body>
</html>
