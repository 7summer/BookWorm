$(function() {
    showTotal();//计算总计

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
        /*
        3. 让结算按钮与全选同步
        */
        setSettlement(bool);
        /*
        4. 重新计算总计
        */
        showTotal();
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
            setSettlement(true);//让结算按钮有效
        }
        else if(select == 0) {//谁都没有选中
            $("#selectAll").prop("checked", false);//取消全选
            setSettlement(false);//让结算失效
        }
        else {
            $("#selectAll").prop("checked", false);//取消全选
            setSettlement(true);//让结算有效
        }
        showTotal();//重新计算总计
    });

    /*
    给减号添加click事件
    */
    $(".subtract").click(function() {
        // 获取cartItemId
        var id = $(this).prop("id").substring(0, 32);
        // 获取输入框中的数量
        var quantity = $("#" + id + "Quantity").val();
        // 判断当前数量是否为1，如果为1,那就不是修改数量了，而是要删除了。
        if(quantity == 1) {
            if(confirm("您是否真要删除该条目？")) {
                location = getRootPath() + "/CartItemServlet?method=batchDeleteCartItem&cartItemIds=" + id;
            }
        } else {
            sendUpdateQuantity(id, Number(quantity)-1);
        }
    });

    // 给加号添加click事件
    $(".add").click(function() {
        // 获取cartItemId
        var id = $(this).prop("id").substring(0, 32);
        // 获取输入框中的数量
        var quantity = $("#" + id + "Quantity").val();
        sendUpdateQuantity(id, Number(quantity)+1);
    });
});

// 请求服务器，修改数量。
function sendUpdateQuantity(id, quantity) {
    location = getRootPath() + "/CartItemServlet?method=updateQuantity&cartItemId=" + id +
        "&quantity=" + quantity
    // $.ajax({
    //     async:false,
    //     cache:false,
    //     url: getRootPath() + "/CartItemServlet",
    //     data:{method:"updateQuantity",cartItemId:id,quantity:quantity},
    //     type:"POST",
    //     dataType:"json",
    //     success:function(result) {
    //         //1. 修改数量
    //         $("#" + id + "Quantity").val(result.quantity);
    //         //2. 修改小计
    //         $("#" + id + "Subtotal").text(result.subtotal);
    //         //3. 重新计算总计
    //         showTotal();
    //     }
    // });
}

/*
 * 计算总计
 */
function showTotal() {
    var total = 0;
    /*
    1. 获取所有的被勾选的条目复选框！循环遍历之
    */
    $(":checkbox[name=checkboxBtn]").filter(":checked").each(function() {
        //2. 获取复选框的值，即其他元素的前缀
        var id = $(this).val();
        //3. 再通过前缀找到小计元素，获取其文本
        var text = $("#" + id + "Subtotal").text();
        //4. 累加计算
        total += Number(text);
    });
    // 5. 把总计显示在总计元素上
    $("#total").text(round(total, 2));//round()函数的作用是把total保留2位
}

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
 * 设置结算按钮样式
 */
function setSettlement(bool) {
    if(bool) {
        $("#settlement").removeClass("kill").addClass("settlement");
        //撤消当前元素止所有click事件
        $("#settlement").unbind("click");
    } else {
        //让结算按钮废掉
        //换了一张图片
        $("#settlement").removeClass("settlement").addClass("kill");
        //return false会使超链接失效
        $("#settlement").click(function() {return false;});
    }

}

/*
 * 批量删除购物车条目
 */
function batchDeleteCartItem() {
    // 1. 获取所有被选中条目的复选框
    // 2. 创建一数组，把所有被选中的复选框的值添加到数组中
    // 3. 指定location为CartItemServlet，参数method=batchDelete，参数cartItemIds=数组的toString()
    var cartItemIdArray = new Array();
    $(":checkbox[name=checkboxBtn]").filter(":checked").each(function() {
        cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
    });
    location = getRootPath() + "/CartItemServlet?method=batchDeleteCartItem&cartItemIds=" + cartItemIdArray;
}

/*
 * 结算
 */
function settlement() {
    // 1. 获取所有被选择的条目的id，放到数组中
    var cartItemIdArray = new Array();
    $(":checkbox[name=checkboxBtn]").filter(":checked").each(function() {
        cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
    });
    // 2. 把数组的值toString()，然后赋给表单的cartItemIds这个hidden
    $("#cartItemIds").val(cartItemIdArray.toString());
    // 把总计的值，也保存到表单中
    $("#hiddenTotal").val($("#total").text());
    // 3. 提交这个表单
    $("#settlementForm").submit();
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