$(function() {
    /*
    给全选添加click事件
    */
    $("#selectAll").click(function() {
        /*
        1. 获取全选的状态
        */
        var bool = $("#selectAll").prop("checked");
        /*
        2. 让所有条目的复选框与全选的状态同步
        */
        setItemCheckBox(bool);
    });

    /*
    给所有条目的复选框添加click事件
    */
    $(":checkbox[name=checkboxBtn]").click(function() {
        var all = $(":checkbox[name=checkboxBtn]").length;//所有条目的个数
        //filter()方法来获取所有被选择的复选框
        var select = $(":checkbox[name=checkboxBtn]").filter(":checked").length;//获取所有被选择条目的个数

        if(all == select) {//全都选中了
            $("#selectAll").prop("checked", true);//勾选全选复选框
        }
        else if(select == 0) {//谁都没有选中
            $("#selectAll").prop("checked", false);//取消全选
        }
        else {
            $("#selectAll").prop("checked", false);//取消全选
        }
    });

});

function round(num,dec){
    var strNum = num + '';/*把要转换的小数转换成字符串*/
    var index = strNum.indexOf("."); /*获取小数点的位置*/
    if(index < 0) {
        return num;/*如果没有小数点，那么无需四舍五入，返回这个整数*/
    }
    var n = strNum.length - index -1;/*获取当前浮点数，小数点后的位数*/
    if(dec < n){
        /*把小数点向后移动要保留的位数，把需要保留的小数部分变成整数部分，只留下不需要保留的部分为小数*/
        var e = Math.pow(10, dec);
        num = num * e;
        /*进行四舍五入，只保留整数部分*/
        num = Math.round(num);
        /*再把原来小数部分还原为小数*/
        return num / e;
    } else {
        return num;/*如果当前小数点后的位数等于或小于要保留的位数，那么无需处理，直接返回*/
    }
}

/*
 * 统一设置所有条目的复选按钮
 */
function setItemCheckBox(bool) {
    // $(":checkbox[name=checkboxBtn]").attr("checked", bool);
    $(":checkbox[name=checkboxBtn]").prop("checked", bool);
}


/*
 * 批量删除收藏条目
 */
function batchDeleteCollect() {
    // 1. 获取所有被选中条目的复选框
    // 2. 创建一数组，把所有被选中的复选框的值添加到数组中
    // 3. 指定location为CartItemServlet，参数method=batchDelete，参数cartItemIds=数组的toString()
    var collectIdArray = new Array();
    $(":checkbox[name=checkboxBtn]").filter(":checked").each(function() {
        collectIdArray.push($(this).val());//把复选框的值添加到数组中
    });
    location = getRootPath() + "/CollectServlet?method=batchDeleteCollect&collectIds=" + collectIdArray;
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