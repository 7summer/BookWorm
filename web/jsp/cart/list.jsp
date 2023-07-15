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
	  <script type="text/javascript" src="<c:url value='/js/cart/list.js'/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/cart/list.css'/>">

  </head>
  <body>

<c:choose>
	<c:when test="${empty cartItemList}">
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
			</td>
			<td>
				<span class="spanEmpty">您的购物车中暂时没有商品</span>
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
			<td>数量</td>
			<td>小计</td>
			<td>操作</td>
		</tr>

	<c:forEach items="${cartItemList}" var="cartItem">
		<tr align="center">
			<td align="left">
				<%--复选框的值为购物车条目的id--%>
				<input value="${cartItem.cartItemId}" type="checkbox" name="checkboxBtn" checked="checked"/>
			</td>
			<td align="left" width="70px">
<%--				<a class="linkImage" href="<c:url value='/jsp/book/desc.jsp'/>">--%>
				<a class="linkImage" href="<c:url value='/BookServlet?method=findByBookId&bookId=${cartItem.book.bookId}'/>">
					<img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.image_b}'/>"/>
				</a>
			</td>
			<td align="left" width="400px">
<%--				<a href="<c:url value='/jsp/book/desc.jsp'/>"><span>${cartItem.book.bookName}</span></a>--%>
				<a href="<c:url value='/BookServlet?method=findByBookId&bookId=${cartItem.book.bookId}'/>">
					<span>${cartItem.book.bookName}</span>
				</a>
			</td>
			<td><span>&yen;<span class="currentPrice">${cartItem.book.currentPrice}</span></span></td>
			<td>
				<%--减--%>
				<a class="subtract" id="${cartItem.cartItemId}Subtract"></a>
				<%--商品数量--%>
				<input class="quantity" readonly="readonly" id="${cartItem.cartItemId}Quantity" type="text" value="${cartItem.quantity}"/>
				<%--加--%>
				<a class="add" id="${cartItem.cartItemId}Add"></a>
			</td>
			<td width="100px">
				<span class="price_n">
					<%--小计的id等于购物车条目id+Subtotal--%>
					&yen;<span class="subTotal" id="${cartItem.cartItemId}Subtotal">${cartItem.subtotal}</span>
				</span>
			</td>
			<td>
				<a href="<c:url value='/CartItemServlet?method=batchDeleteCartItem&cartItemIds=${cartItem.cartItemId}'/>">删除</a>
			</td>
		</tr>
	</c:forEach>

	
		<tr>
			<td colspan="4" class="tdBatchDelete">
				<a href="javascript:batchDeleteCartItem();">批量删除</a>
			</td>
			<td colspan="3" align="right" class="tdTotal">
				<span>总计：</span>
				<span class="price_t">
					&yen;<span id="total"></span>
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="7" align="right">
				<%--结算按钮--%>
				<a href="javascript:settlement();" id="settlement" class="settlement"></a>
			</td>
		</tr>
	</table>
	<form id="settlementForm" action="<c:url value='/CartItemServlet'/>" method="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="total" id="hiddenTotal"/>
		<input type="hidden" name="method" value="loadCartItems"/>
	</form>

	</c:otherwise>
</c:choose>
  </body>
</html>
