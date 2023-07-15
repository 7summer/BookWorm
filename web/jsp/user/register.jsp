<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/22
  Time: 7:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>注册页面</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/user/register.css'/>">
    <%--引入jQuery--%>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/user/register.js'/>"></script>
</head>
<body>
<div id="divMain">
    <div id="divTitle">
        <span id="spanTitle">新用户注册</span>
    </div>
    <div id="divBody">
        <form action="<c:url value='/UserServlet'/>" method="post">
            <input type="hidden" name="method" value="regist">
            <table id="tableForm">
                <tr>
                    <td class="tdText">用户名：</td>
                    <td class="tdInput"><input type="text" name="userName" id="username" class="inputClass" value="${ formUser.userName}" placeholder="用户名长度为3-20"/></td>
                    <td class="tdError"><label class="errorClass" id="usernameError">${errors.userName}</label></td>
                </tr>
                <tr>
                    <td class="tdText">登录密码：</td>
                    <td><input type="password" name="loginPassword" id="loginpassword" class="inputClass" value="${ formUser.loginPassword}" placeholder="密码长度为6-30"/></td>
                    <td><label class="errorClass" id="loginpasswordError">${errors.loginPassword}</label></td>
                </tr>
                <tr>
                    <td class="tdText">确认密码：</td>
                    <td><input type="password" name="reLoginPassword" id="reloginpassword" class="inputClass" value="${ formUser.reLoginPassword}" /></td>
                    <td><label class="errorClass" id="reloginpasswordError">${errors.reLoginPassword}</label></td>
                </tr>
                <tr>
                    <td class="tdText">邮箱：</td>
                    <td><input type="text" name="email" id="email" class="inputClass" value="${ formUser.email}" /></td>
                    <td><label class="errorClass" id="emailError">${errors.email}</label></td>
                </tr>
                <tr>
                    <td class="tdText">验证码：</td>
                    <td><input type="text" name="verifyCode" id="verifycode" class="inputClass"/></td>
                    <td><label class="errorClass" id="verifycodeError">${errors.verifyCode}</label></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <!-- 模仿上次给老师演示的项目 -->
                        <div id="divVerifyCode"><img id="imgVerifyCode" src="<c:url value='/CheckServlet'/>"/></div>
                    </td>
                    <td>
                        <label>
                            <a href="javascript:changeVerifyCode()">换一张</a>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <button id="submitbuttion">立即注册</button>
                    </td>
                    <td></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
