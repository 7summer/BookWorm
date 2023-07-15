<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>book_desc.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<c:url value='/adminfile/css/book/desc.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datetimepicker.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datetimepicker.full.js'/>"></script>
<script type="text/javascript" src="<c:url value='/adminfile/js/book/desc.js'/>"></script>
  </head>
  
  <body>
    <input type="checkbox" id="box"><label for="box">编辑或删除</label>
    <br/>
    <br/>
  <div id="show">
    <div class="sm">${book.bookName}</div>
    <img align="top" src="<c:url value='/${book.image_w }'/>" class="tp"/>
    <div id="book" style="float:left;">
	    <ul>
	    	<li>商品编号：${book.bookId}</li>
	    	<li>当前价：<span class="price_n">&yen;${book.currentPrice }</span></li>
	    	<li>定价：<span style="text-decoration:line-through;">&yen;${book.price }</span>　折扣：<span style="color: #c30;">${book.discount }</span>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<tr>
				<td colspan="3">
					作者：${book.author }著
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：${book.publishHouse}
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：${book.publishTime}</td>
			</tr>
			<tr>
				<td>版次：${book.edition}</td>
				<td>页数：${book.pageNum}</td>
				<td>字数：${book.wordNum}</td>
			</tr>
			<tr>
				<td width="180">印刷时间：${book.printTime}</td>
				<td>开本：${book.bookSize}开</td>
				<td>纸张：${book.paper}</td>
			</tr>
		</table>
	</div>
  </div>
  
  
  <div id='formDiv'>
   <div class="sm">&nbsp;</div>
   <form action="<c:url value='/admin/AdminBookServlet'/>" method="post" id="form">
    <input type="hidden" name="method" id="method"/>
   	<input type="hidden" name="bookId" value="${book.bookId}"/>
    <img align="top" src="<c:url value='/${book.image_w }'/>" class="tp"/>
    <div style="float:left;">
	    <ul>
	    	<li>商品编号：${book.bookId}</li>
	    	<li>书名：　<input id="bname" type="text" name="bookName" value="${book.bookName}" style="width:500px;"/></li>
	    	<li>当前价：<input id="currPrice" type="text" name="currentPrice" value="${book.currentPrice }" style="width:50px;"/></li>
	    	<li>定价：　<input id="price" type="text" name="price" value="${book.price }" style="width:50px;"/>
	    	折扣：<input id="discount" type="text" name="discount" value="${book.discount }" style="width:30px;"/>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<tr>
				<td colspan="3">
					作者：　　<input id="author" type="text" name="author" value="${book.author}" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：　<input id="press" type="text" name="publishHouse" value="${book.publishHouse}" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：<input id="publishtime" type="text" name="publishTime" value="${book.publishTime }" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>版次：　　<input id="edition" type="text" name="edition" value="${book.edition }" style="width:40px;"/></td>
				<td>页数：　　<input id="pageNum" type="text" name="pageNum" value="${book.pageNum }" style="width:50px;"/></td>
				<td>字数：　　<input id="wordNum" type="text" name="wordNum" value="${book.wordNum }" style="width:80px;"/></td>
			</tr>
			<tr>
				<td width="250px">印刷时间：<input id="printtime" type="text" name="printTime" value="${book.printTime}" style="width:100px;"/></td>
				<td width="250px">开本：　　<input id="booksize" type="text" name="bookSize" value="${book.bookSize }" style="width:30px;"/></td>
				<td>纸张：　　<input id="paper" type="text" name="paper" value="${book.paper}" style="width:80px;"/></td>
			</tr>
			<tr>
				<td>
					一级分类：<select name="parentId" id="pid" onchange="loadChidren()">
						<option value="">==请选择1级分类==</option>
						<c:forEach items="${parents }" var="parent">
						  <option value="${parent.categoryId}" <c:if test="${book.parent.parent.categoryId eq parent.categoryId}">selected="selected"</c:if> >${parent.categoryName}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					二级分类：<select name="childId" id="cid">
						<option value="">==请选择2级分类==</option>
						<c:forEach items="${children}" var="child">
						  <option value="${child.categoryId}" <c:if test="${book.parent.categoryId eq child.categoryId}">selected="selected"</c:if>>${child.categoryName}</option>
						</c:forEach>
					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="2">
					<input onclick="editForm()" type="button" name="method" id="editBtn" class="btn" value="编　　辑">
					<input onclick="delForm()" type="button" name="method" id="delBtn" class="btn" value="删　　除">
				</td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>
