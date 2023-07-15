$(function () {
	// 操作和显示切换
	$("#box").prop("checked", false);
	$("#formDiv").css("display", "none");
	$("#show").css("display", "");
	$("#box").click(function() {
		if($(this).prop("checked")) {
			$("#show").css("display", "none");
			$("#formDiv").css("display", "");
		} else {
			$("#formDiv").css("display", "none");
			$("#show").css("display", "");
		}
	});

	// 日期框
	$("#publishtime").datetimepicker({
		format:"Y-m-d"
	});
	$("#printtime").datetimepicker({
		format:"Y-m-d"
	});
	$.datetimepicker.setLocale("ch")

	// 编辑和删除按钮样式
	$("#editBtn").addClass("editBtn1");
	$("#delBtn").addClass("delBtn1");
	$("#editBtn").hover(
		function() {
			$("#editBtn").removeClass("editBtn1");
			$("#editBtn").addClass("editBtn2");
		},
		function() {
			$("#editBtn").removeClass("editBtn2");
			$("#editBtn").addClass("editBtn1");
		}
	);
	$("#delBtn").hover(
		function() {
			$("#delBtn").removeClass("delBtn1");
			$("#delBtn").addClass("delBtn2");
		},
		function() {
			$("#delBtn").removeClass("delBtn2");
			$("#delBtn").addClass("delBtn1");
		}
	);
});

//根据一级分类的选择加载二级分类
function loadChidren() {
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
	// 2. 校验pid是否为空
	if(parentId == "==请选择1级分类==")
	{
		alert("1级分类不能为空！")
		return;
	}
	// 3. 发送异步请求
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

function editForm() {
	var bname = $("#bname").val();
	var currPrice = $("#currPrice").val();
	var price = $("#price").val();
	var discount = $("#discount").val();
	var author = $("#author").val();
	var press = $("#press").val();
	var pid = $("#pid").val();
	var cid = $("#cid").val();
	
	if(!bname || !currPrice || !price || !discount || !author || !press || !pid || !cid) {
		alert("图书名、当前价、定价、折扣、作者、出版社、1级分类、2级分类不能为空！");
		return false;
	}

	if(pid == "==请选择1级分类==" || cid == "==请选择2级分类==")
	{
		alert("分类不能为空")
		return false;
	}
	
	if(isNaN(currPrice) || isNaN(price) || isNaN(discount)) {
		alert("当前价、定价、折扣必须是合法小数！");
		return false;
	}

	$("#method").val("editBook");
	$("#form").submit();
}

function delForm() {
	$("#method").val("deleteBook");
	$("#form").submit();
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