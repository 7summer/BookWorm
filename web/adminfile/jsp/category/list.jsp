<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>分类列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminfile/css/category/list.css'/>">
  </head>
  
  <body>
    <h2 style="text-align: center;">分类列表</h2>
    <table align="center" border="1" cellpadding="0" cellspacing="0">
    	<caption class="captionAddOneLevel">
    	  <a href="<c:url value='/adminfile/jsp/category/add.jsp'/>">添加一级分类</a>
    	</caption>
    	<tr class="trTitle">
    		<th>分类名称</th>
    		<th>描述</th>
    		<th>操作</th>
    	</tr>
    	
		<c:forEach items="${parents }" var="parent">
				<tr class="trOneLevel">
					<td width="200px;">${parent.categoryName}</td>
					<td>${parent.description}</td>
					<td width="200px;">
					  <a href="<c:url value='/admin/AdminCategoryServlet?method=addChildBefore&parentId=${parent.categoryId}'/>">
						  添加二级分类
					  </a>
					  <a href="<c:url value='/admin/AdminCategoryServlet?method=editParentBefore&parentId=${parent.categoryId}'/>">
						  修改
					  </a>
					  <a onclick="return confirm('您是否真要删除该一级分类？')" href="<c:url value='/admin/AdminCategoryServlet?method=deleteParent&parentId=${parent.categoryId}'/>">
						  删除
					  </a>
					</td>
				</tr>
		   <c:forEach items="${parent.children}" var="child">
				<tr class="trTwoLevel">
					<td>${child.categoryName}</td>
					<td>${child.description}</td>
					<td width="200px;" align="right">
					  <a href="<c:url value='/admin/AdminCategoryServlet?method=editChildBefore&childId=${child.categoryId}'/>">
						  修改
					  </a>
					  <a onclick="return confirm('您是否真要删除该二级分类？')" href="<c:url value='/admin/AdminCategoryServlet?method=deleteChild&childId=${child.categoryId}'/>">删除</a>
					</td>
				</tr>
		   </c:forEach>
		</c:forEach>

    </table>
  </body>
</html>
