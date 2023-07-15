<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改分类</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#categoryName").val()) {
				alert("分类名不能为空！");
				return false;
			}
			if(!$("#parentId").val() || $("#parentId").val() == "===选择1级分类===") {
				alert("一级分类不能为空！");
				return false;
			}
			if(!$("#description").val()) {
				alert("分类描述不能为空！");
				return false;
			}
			return true;
		}
	</script>
	<style type="text/css">
		body {background: rgb(254,238,189);}
	</style>
  </head>
  
  <body>
    <h3>修改2级分类</h3>
    <h1></h1>
    <p style="font-weight: 900; color: red">${msg}</p>
    <form action="<c:url value='/admin/AdminCategoryServlet'/>" method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="method" value="editChild"/>
    	<input type="hidden" name="categoryId" value="${child.categoryId}"/>
    	分类名称：<input type="text" name="categoryName" value="${child.categoryName}" id="categoryName"/><br/>
    	一级分类：<select name="parentId" id="parentId">
    		<option value="">===选择1级分类===</option>
			<c:forEach items="${parents}" var="parent">
				<option value="${parent.categoryId}" <c:if test="${parent.categoryId eq child.parent.categoryId}">selected="selected"</c:if> >${parent.categoryName}</option>
			</c:forEach>
    	</select><br/>
    	分类描述：<textarea rows="5" cols="50" name="description" id="description">${child.description}</textarea><br/>
    	<input type="submit" value="修改二级分类"/>
    	<input type="button" value="返回" onclick="history.go(-1)"/>
    </form>
  </body>
</html>
