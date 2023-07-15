//添加购物车
function addCart(bookId)
{
	var value = $("#cnt").val()
	var ret = /^[1-9]+.?[0-9]*/;

	if(!ret.test(value))
	{
		alert("请输入正整数")
	}
	else
	{
		$.ajax({
			url: getRootPath() + "/CartItemServlet",
			data: {method:"add", bookId:bookId, quantity:value},
			type: "post",
			dataTypes: "json",
			async: false,
			cache: false,
			success: function (result, status, xhr) {
				if(result == "true")
				{
					showInfo("已加入购物车")
				}
				else
				{
					showInfo("未登录")
				}
			}
		})
	}
}

//添加收藏
function addCollect(bookId)
{
	$.ajax({
		url: getRootPath() + "/CollectServlet",
		data: {method:"addCollect", bookId:bookId},
		type: "post",
		dataTypes: "json",
		async: false,
		cache: false,
		success: function (result, status, xhr) {
			if(result == "true")
			{
				showInfo("已收藏")
			}
			else
			{
				showInfo("未登录")
			}
		}
	})
}

//提醒信息
var tid = null
function showInfo(text){
	var info = document.getElementsByClassName("info")[0]

	info.innerText = text
	info.style.opacity = 1

	//清除上一个延时
	if(tid) clearTimeout(tid)

	tid = setTimeout(function(){
		info.style.opacity = 0
	},500)
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