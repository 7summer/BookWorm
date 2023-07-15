<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/27
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1 style="text-align: center">网上书城系统</h1>
<div style="font-size: 10pt;line-height: 10px">
  <c:choose>
    <c:when test="${empty sessionScope.user}">
      <%--_parent在父框架中打开链接地址--%>
      <a href="<c:url value='/jsp/user/login.jsp'/>" target="_parent">用户登录</a>&nbsp;|&nbsp;
      <a href="<c:url value='/jsp/user/register.jsp'/>" target="_parent">用户注册</a>
    </c:when>
    <c:otherwise>
      ${sessionScope.user.userName}&nbsp;&nbsp;|&nbsp;&nbsp;
      <a href="<c:url value='/CollectServlet?method=findByUserId'/>" target="body">我的收藏</a>&nbsp;&nbsp;|&nbsp;&nbsp;
      <a href="<c:url value='/CartItemServlet?method=findByUserId'/>" target="body">我的购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;
      <a href="<c:url value='/OrderServlet?method=findByUserId'/>" target="body">我的订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
      <a href="<c:url value='/jsp/user/changePw.jsp'/>" target="body">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;
      <a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">退出</a>&nbsp;&nbsp;|&nbsp;&nbsp;
      <a href="<c:url value='/images/夏热.png'/>" target="_top">联系我们</a>
    </c:otherwise>
  </c:choose>

</div>
</body>
</html>
