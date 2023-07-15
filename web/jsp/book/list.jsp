<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>图书列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/page.css'/>" />
	<script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/book/list.js'/>"></script>
  </head>
  
  <body>

<ul>
<c:forEach items="${pageBean.beanList }" var="book">
  <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/BookServlet?method=findByBookId&bookId=${book.bookId}'/>">
		<img style="text-align: center" src="<c:url value='/${book.image_b}'/>" border="0"/>
	</a>
    <p class="price">
		<span class="price_n">&yen;${book.currentPrice}</span>
		<span class="price_r">&yen;${book.price}</span>
		(<span class="price_s">${book.discount}折</span>)
	</p>
	<div class="introduce">
		<a id="bookname" title="${book.bookName}" href="<c:url value='/BookServlet?method=findByBookId&bookId=${book.bookId}'/>">${book.bookName}</a>
	</div>
	<%-- url标签会自动对参数进行url编码 --%>
	<c:url value="/BookServlet" var="authorUrl">
		<c:param name="method" value="findByAuthor"/>
		<c:param name="author" value="${book.author}"/>
	</c:url>
	<c:url value="/BookServlet" var="publishHouseUrl">
		<c:param name="method" value="findByPublishHouse"/>
		<c:param name="publishHouse" value="${book.publishHouse}"/>
	</c:url>
	<p><a href="${authorUrl}" name='P_zz' title='${book.author}'>${book.author}</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="${publishHouseUrl}">${book.publishHouse}</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>${book.publishTime}</p>
  </div>
  </li>
</c:forEach>

</ul>

<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsp/page/page.jsp" %>
</div>

  </body>
 
</html>

