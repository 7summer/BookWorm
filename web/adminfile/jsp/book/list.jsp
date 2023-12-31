<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书分类</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" type="text/css" href="<c:url value='/adminfile/css/book/list.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/page.css'/>" />

<script type="text/javascript" src="<c:url value='/adminfile/js/book/list.js'/>"></script>
  </head>
  
  <body>
<div class="divBook">
<ul>


<c:forEach items="${pageBean.beanList}" var="book">
 <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/admin/AdminBookServlet?method=findByBookId&bookId=${book.bookId}'/>">
		<img style="text-align: center" src="<c:url value='/${book.image_b }'/>" border="0"/>
	</a>
    <p class="price" >
		<span class="price_n">&yen;${book.currentPrice }</span>
		<span class="price_r">&yen;${book.price}</span>
		(<span class="price_s">${book.discount}折</span>)
	</p>
	<c:url value="/admin/AdminBookServlet" var="authorUrl">
		<c:param name="method" value="findByAuthor"/>
		<c:param name="author" value="${book.author }"/>
	</c:url>
	<c:url value="/admin/AdminBookServlet" var="pressUrl">
		<c:param name="method" value="findByPublishHouse"/>
		<c:param name="publishHouse" value="${book.publishHouse}"/>
	</c:url>
	<div class="introduce">
		<a id="bookname" title="${book.bookName}" href="<c:url value='/admin/AdminBookServlet?method=findByBookId&bookId=${book.bookId}'/>">${book.bookName}</a>
	</div>
	<p><a href="${authorUrl}" name='P_zz' title='${book.author}'>${book.author}</a></p>
	<p class="publishing">
		<span>出版社：<a href="${pressUrl}">${book.publishHouse}</a></span>
	</p>
  </div>
 </li>
</c:forEach>


</ul>
</div>
<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsp/page/page.jsp" %>
</div>
  </body>
 
</html>

