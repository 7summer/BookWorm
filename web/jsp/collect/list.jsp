<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
	  <script type="text/javascript" src="<c:url value='/js/collect/list.js'/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/collect/list.css'/>">

  </head>
  <body>

<c:choose>
	<c:when test="${empty collectList}">
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<span class="spanEmpty">你的收藏夹为空</span>
			</td>
		</tr>
	</table>  
	</c:when>
	<c:otherwise>
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr align="center" bgcolor="#efeae5">
			<td align="left" width="50px">
				<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
			</td>
			<td colspan="2">商品名称</td>
			<td>单价</td>
			<td>操作</td>
		</tr>

	<c:forEach items="${collectList}" var="collect">
		<tr align="center">
			<td align="left">
				<%--复选框的值为购物车条目的id--%>
				<input value="${collect.collectId}" type="checkbox" name="checkboxBtn" checked="checked"/>
			</td>
			<td align="left" width="70px">
				<a class="linkImage" href="<c:url value='/BookServlet?method=findByBookId&bookId=${collect.book.bookId}'/>">
					<img border="0" width="54" align="top" src="<c:url value='/${collect.book.image_b}'/>"/>
				</a>
			</td>
			<td align="left" width="400px">
				<a href="<c:url value='/BookServlet?method=findByBookId&bookId=${collect.book.bookId}'/>">
					<span>${collect.book.bookName}</span>
				</a>
			</td>
			<td><span>&yen;<span class="currentPrice">${collect.book.currentPrice}</span></span></td>
			<td>
				<a href="<c:url value='/CollectServlet?method=batchDeleteCollect&collectIds=${collect.collectId}'/>">删除</a>
			</td>
		</tr>
	</c:forEach>
		<tr>
			<td colspan="4" class="tdBatchDelete">
				<a href="javascript:batchDeleteCollect();">批量删除</a>
			</td>
		</tr>
	</table>

	</c:otherwise>
</c:choose>
  </body>
</html>
