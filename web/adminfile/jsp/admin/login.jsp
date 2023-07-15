<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/6/22
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>登录页面</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminfile/css/admin/login.css'/>">
    <%--引入jQuery--%>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/adminfile/js/admin/login.js'/>"></script>
</head>
<body>
<div class="loginArea">
    <div class="title">
        <label class="title">管理员登录</label>
    </div>
    <div class="login-table">
        <form action="<c:url value='/AdminServlet'/>" method="post">
            <input type="hidden" name="method" value="login">
            <table id="tableForm">
                <tr>
                    <td class="tdText">用户名：</td>
                    <td class="tdInput">
                        <input type="text" name="adminName" id="adminname" class="inputClass" value="${formAdmin.adminName}" placeholder="用户名长度为3-20">
                    </td>
                </tr>
                <tr>
                    <td class="tdError"></td>
                    <td class="tdError"><label class="errorClass" id="adminnameError">${errors.adminName}${message}</label></td>
                </tr>
                <tr>
                    <td class="tdText">密码：</td>
                    <td class="tdInput">
                        <input type="password" name="password" id="password" class="inputClass" placeholder="密码长度为6-30">
                    </td>
                </tr>
                <tr>
                    <td class="tdError"></td>
                    <td><label class="errorClass" id="passwordError">${errors.password}</label></td>
                </tr>
                <tr>
                    <td class="tdText">验证码：</td>
                    <td class="tdInput">
                        <input type="text" name="verifyCode" id="verifycode" class="inputClass">
                        <img src="<c:url value='/AdminCheckServlet'/>" id="imgVerifyCode" onclick="changeVerifyCode()"/>
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
            </table>
        </form>
    </div>
</div>
</body>
</html>
