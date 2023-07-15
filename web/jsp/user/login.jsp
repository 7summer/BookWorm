<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/27
  Time: 8:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>登录页面</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/user/login.css'/>">
    <%--引入jQuery--%>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/user/login.js'/>"></script>

    <script type="text/javascript">
        $(function (){
            //获取cookie中的用户名
            var userName = window.decodeURI("${cookie.userName.value}");
            if("${formUser.userName}")
            {
                userName = "${formUser.userName}";
            }
            $("#username").val(userName)
        })
    </script>
</head>
<body>
<div class="container">
    <div class="bg">
        <img src="<c:url value='/images/loninBg.jpg'/>"/>
    </div>
    <div class="loginArea">
        <div class="title">
            <label class="title">用户登录</label>
        </div>
        <div class="login-table">
            <form action="<c:url value='/UserServlet'/>" method="post">
                <input type="hidden" name="method" value="login">
                <table id="tableForm">
                    <tr>
                        <td class="tdText">用户名：</td>
                        <td class="tdInput">
                            <input type="text" name="userName" id="username" class="inputClass" placeholder="用户名长度为3-20">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdError"></td>
                        <td class="tdError"><label class="errorClass" id="usernameError">${errors.userName}${message}</label></td>
                    </tr>
                    <tr>
                        <td class="tdText">密码：</td>
                        <td class="tdInput">
                            <input type="password" name="loginPassword" id="loginpassword" class="inputClass" placeholder="密码长度为6-30">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdError"></td>
                        <td><label class="errorClass" id="loginpasswordError">${errors.loginPassword}</label></td>
                    </tr>
                    <tr>
                        <td class="tdText">验证码：</td>
                        <td class="tdInput">
                            <input type="text" name="verifyCode" id="verifycode" class="inputClass">
                            <img src="<c:url value='/CheckServlet'/>" id="imgVerifyCode" onclick="changeVerifyCode()"/>
                            <a href="javascript:changeVerifyCode()">换一张</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdError"></td>
                        <td class="tdError"><label class="errorClass" id="verifycodeError">${errors.verifyCode}</label></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;"><button id="submitbuttion">登录</button></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <a href="<c:url value='/jsp/user/register.jsp'/>">注册</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>
