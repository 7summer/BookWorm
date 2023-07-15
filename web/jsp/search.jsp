<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/27
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/search.css'/>">
</head>
<body>
<form action="<c:url value='/BookServlet'/>" method="get" target="body" id="tableForm">
    <input type="hidden" name="method" value="findByBookName">
    <input type="text" name="bookName">
    <span>
        <a href="javascript:document.getElementById('tableForm').submit();">
            <img align="top" border="0" src="<c:url value='/images/btn.bmp'/>"/>
        </a>
        <a href="<c:url value='/jsp/advanceSearch.jsp'/>" style="font-size: 10pt; color: #404040" target="body">高级搜索</a>
    </span>
</form>
</body>
</html>
