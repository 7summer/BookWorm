$(function(){
    //得到所有的错误信息，循环遍历之，调用一个方法来确定是否显示错误信息
    $(".errorClass").each(function(){
        showError($(this)); //遍历每个元素，使用每个元素来调用showError方法
    })

    //输入框得到焦点，隐藏错误信息
    $(".inputClass").focus(function (){
        var labelId = $(this).prop("id") + "Error" //通过输入框找到对应的label的id
        $("#" + labelId).text("")
        showError($("#" + labelId))
    })

    //输入框失去焦点，进行校验
    $(".inputClass").blur(function (){
        var id = $(this).prop("id")
        //得到校验的函数名
        var funName = "validate" + id.substring(0,1).toUpperCase() + id.substring(1) + "()"
        //将字符串当作java代码处理
        eval(funName)
    })
});

/**
 判断当前元素是否存在内容，如果存在显示，如果不存在不显示！
 */
function showError(ele){
    var text = ele.text()

    if(!text)
    {
        ele.css("display", "none"); //隐藏元素
    }
    else
    {
        ele.css("display", ""); //显示元素
    }
}

/**
 * 换一张验证码
 */
function changeVerifyCode(){
    /**
     获取<img>元素
     重新设置它的src
     使用毫秒来添加参数
     */
    var img = $("#imgVerifyCode")
    var date = new Date().getTime();

    img.prop("src", getRootPath() + "/CheckServlet?" + date)

    //图片加载完成后验证验证码
    var imgVerifyCode = document.getElementById('imgVerifyCode')
    //为该元素的 onload 和 onreadystatechange 事件分配一个匿名函数。这个函数将在图像加载完成或元素状态发生变化时触发
    imgVerifyCode.onload = imgVerifyCode.onreadystatechange = function(){
        //检查元素的 readyState 属性。如果 readyState 为 undefined、'loaded' 或 'complete'，则调用 validateVerifycode() 函数。
        // 这意味着当图像加载完成或元素状态变为已加载或已完成时，将执行 validateVerifycode() 函数。
        if(!this.readyState||this.readyState=='loaded'||this.readyState=='complete'){
            validateVerifycode()
        }
    };
    //总之，这段代码的主要目的是在图像验证码加载完成或状态变化时，执行 validateVerifycode() 函数以验证验证码
}

/**
 * 校验旧密码
 */
function validateLoginpassword(){
    var id = "loginpassword"
    var value = $("#" + id).val() //获取输入框内容

    var errorId = $("#" + id + "Error")
    //登录密码是否为空
    if(!value || value.trim().length == 0)
    {
        errorId.text("旧密码不能为空")
        showError(errorId)
        return false
    }
    //登录密码长度为6-30
    if(value.length<6 || value.length>30)
    {
        errorId.text("旧密码长度为6-30")
        showError(errorId)
        return false
    }
    return true
}

/**
 * 校验新密码
 */
function validateNewloginpassword(){
    var id = "newloginpassword"
    var value = $("#" + id).val() //获取输入框内容

    var errorId = $("#" + id + "Error")
    //新密码是否为空
    if(!value || value.trim().length == 0)
    {
        errorId.text("新密码不能为空")
        showError(errorId)
        return false
    }
    //新密码长度为6-30
    if(value.length<6 || value.length>30)
    {
        errorId.text("新密码长度为6-30")
        showError(errorId)
        return false
    }

    var reloginpassword = $("#reloginpassword");
    var reloginpasswordError = $("#reloginpasswordError")
    if(reloginpassword.val())
    {
        //防止输入完确认密码后，修改密码
        if(reloginpassword.val() != value)
        {
            reloginpasswordError.text("两次输入密码不同！");
            showError(reloginpasswordError)
            return false
        }
        else
        {
            reloginpasswordError.text("")
            showError(reloginpasswordError)
        }
    }
    return true
}
/**
 * 确认密码校验
 */
function validateReloginpassword(){
    var id = "reloginpassword"
    var value = $("#" + id).val() //获取输入框内容

    var errorId = $("#" + id + "Error")
    //确认密码是否为空
    if(!value|| value.trim().length == 0)
    {
        errorId.text("确认密码不能为空")
        showError(errorId)
        return false
    }
    //密码与确认密码是否相等
    if(value != $("#newloginpassword").val())
    {
        errorId.text("两次输入密码不同")
        showError(errorId)
        return false
    }
    return true
}
/**
 * 验证码校验
 */
function validateVerifycode(){
    var id = "verifycode"
    var value = $("#" + id).val() //获取输入框内容

    var errorId = $("#" + id + "Error")
    //验证码是否为空
    if(!value)
    {
        errorId.text("验证码不能为空")
        showError(errorId)
        return false
    }
    //验证码长度为4
    if(value.length != 4)
    {
        errorId.text("验证码长度为4！")
        showError(errorId)
        return false
    }
    //验证码验证
    $.ajax({
        url: getRootPath() + "/UserServlet", //需要请求的serlvet
        data:{method:"ajaxValidateVerifyCode", verifyCode:value},
        type:"POST",
        dataTypes:"json", //需要导包
        async: false, //是否异步请求，如果是异步，不会等服务器返回结果，这个函数就向下运行了
        cache: false,
        // result表示服务器返回的结果
        success: function (result, status, xhr){
            var obj = JSON.parse(result)
            if(obj == false)
            {
                errorId.text("验证码错误");
                showError(errorId)
                return false
            }
        }
    })
    return true
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