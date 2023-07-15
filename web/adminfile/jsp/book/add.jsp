<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'bookdesc.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<c:url value='/adminfile/css/book/add.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datetimepicker.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datetimepicker.full.js'/>"></script>
<script type="text/javascript">
$(function () {
	$("#publishtime").datetimepicker({
		format:"Y-m-d"
	});

	$("#printtime").datetimepicker({
		format:"Y-m-d"
	});

	$.datetimepicker.setLocale("ch")

	$("#btn").addClass("btn1");
	$("#btn").hover(
		function() {
			$("#btn").removeClass("btn1");
			$("#btn").addClass("btn2");
		},
		function() {
			$("#btn").removeClass("btn2");
			$("#btn").addClass("btn1");
		}
	);
	
	$("#btn").click(function() {
		var bname = $("#bname").val();
		var currPrice = $("#currPrice").val();
		var price = $("#price").val();
		var discount = $("#discount").val();
		var author = $("#author").val();
		var press = $("#press").val();
		var pid = $("#pid").val();
		var cid = $("#cid").val();
		var image_w = $("#image_w").val();
		var image_b = $("#image_b").val();
		var publishtime = $("#publishtime").val()
		var printtime = $("#printtime").val()
		
		if(!bname || !currPrice || !price || !discount || !author || !press || !pid || !cid || !image_w || !image_b
		|| !publishtime || !printtime) {
			alert("图名、当前价、定价、折扣、作者、出版社、出版时间、印刷时间、1级分类、2级分类、大图、小图都不能为空！");
			return false;
		}
		
		if(isNaN(currPrice) || isNaN(price) || isNaN(discount)) {
			alert("当前价、定价、折扣必须是合法小数！");
			return false;
		}

		if(pid=="==请选择1级分类==" || cid=="==请选择2级分类==")
		{
			alert("分类不能为空！")
			return false;
		}

		$("#form").submit();
	});
});

function loadChildren() {
	/*
	1. 获取pid
	2. 发出异步请求，功能之：
	  3. 得到一个数组
	  4. 获取cid元素(<select>)，把内部的<option>全部删除
	  5. 添加一个头（<option>请选择2级分类</option>）
	  6. 循环数组，把数组中每个对象转换成一个<option>添加到cid中
	*/
	// 1. 获取pid
	var parentId = $("#pid").val();
	// 2. 发送异步请求
	$.ajax({
		async:true,
		cache:false,
		url: getRootPath() + "/admin/AdminBookServlet",
		data:{method:"ajaxFindChildren", parentId:parentId},
		type:"POST",
		dataType:"json",
		success:function(arr) {
			// 3. 得到cid，删除它的内容
			$("#cid").empty();//删除元素的子元素
			$("#cid").append($("<option>==请选择2级分类==</option>"));//4.添加头
			// 5. 循环遍历数组，把每个对象转换成<option>添加到cid中
			for(var i = 0; i < arr.length; i++) {
				var option = $("<option>").val(arr[i].categoryId).text(arr[i].categoryName);
				$("#cid").append(option);
			}
		}
	});
}

/**
 * 获取web项目根目录
 * @returns {string}
 */
function getRootPath() {
	//获取当前网址
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址
	var localhostPath = curWwwPath.substring(0, pos);
	//获取带"/"的项目名
	var projectName = pathName.substring(0, pathName.substring(1).indexOf('/') + 1);
	return (localhostPath + projectName);
}

</script>
  </head>
  
  <body>
  <div>
   <p style="font-weight: 900; color: red;">${msg}</p>
   <form action="<c:url value='/admin/AdminAddBookServlet'/>" enctype="multipart/form-data" method="post" id="form">
    <div>
	    <ul>
	    	<li>书名：　<input id="bname" type="text" name="bookName" value="${book.bookName}" style="width:500px;"/></li>
	    	<li>大图：　<input id="image_w" type="file" name="image_w"/></li>
	    	<li>小图：　<input id="image_b" type="file" name="image_b"/></li>
	    	<li>当前价：<input id="currPrice" type="text" name="currentPrice" value="${book.currentPrice}" style="width:50px;"/></li>
	    	<li>定价：　<input id="price" type="text" name="price" value="${book.price}" style="width:50px;"/>
	    	折扣：<input id="discount" type="text" name="discount" value="${book.discount}" style="width:30px;"/>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table>
			<tr>
				<td colspan="3">
					作者：　　<input type="text" id="author" name="author" value="${book.author}" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					出版社：　<input type="text" name="publishHouse" id="press" value="${book.publishHouse}" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">出版时间：<input type="text" id="publishtime" name="publishTime" value="${book.publishTime}" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>版次：　　<input type="text" name="edition" id="edition" value="${book.edition}" style="width:40px;"/></td>
				<td>页数：　　<input type="text" name="pageNum" id="pageNum" value="${book.pageNum}" style="width:50px;"/></td>
				<td>字数：　　<input type="text" name="wordNum" id="wordNum" value="${book.wordNum}" style="width:80px;"/></td>
			</tr>
			<tr>
				<td width="250">印刷时间：<input type="text" name="printTime" id="printtime" value="${book.printTime}" style="width:100px;"/></td>
				<td width="250">开本：　　<input type="text" name="bookSize" id="booksize" value="${book.bookSize}" style="width:30px;"/></td>
				<td>纸张：　　<input type="text" name="paper" id="paper" value="${book.paper}" style="width:80px;"/></td>
			</tr>
			<tr>
				<td>
					一级分类：<select name="parentId" id="pid" onchange="loadChildren()">
						<option value="">==请选择1级分类==</option>
						<c:forEach items="${parents}" var="parent">
							<option value="${parent.categoryId}">${parent.categoryName}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					二级分类：<select name="childId" id="cid">
						<option value="">==请选择2级分类==</option>
					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					<input type="button" id="btn" class="btn" value="新书上架">
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>
