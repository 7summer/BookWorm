<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/27
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/user/changePw.css'/>">

    <script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/user/changePw.js'/>"></script>
</head>
<body>
<div class="div0">
    <span>修改密码</span>
</div>
<div class="div1">
    <form action="<c:url value='/UserServlet'/>" method="pos"  target="_top">
        <input type="hidden" name="method" value="updatePassword">
        <table>
            <tr>
                <td class="tdText">原密码：</td>
                <td class="tdInput"><input type="password" name="loginPassword" id="loginpassword" class="inputClass" value="${formUser.loginPassword}"></td>
                <td class="tdError"><label class="errorClass" id="loginpasswordError">${errors.loginPassword}${message}</label></td>
            </tr>
            <tr>
                <td class="tdText">新密码：</td>
                <td><input type="password" name="newLoginPassword" id="newloginpassword" class="inputClass" value="${formUser.newLoginPassword}"></td>
                <td><label class="errorClass" id="newloginpasswordError">${errors.newLoginPassword}</label></td>
            </tr>
            <tr>
                <td class="tdText">确认密码：</td>
                <td><input type="password" name="reLoginPassword" id="reloginpassword" class="inputClass" value="${formUser.reLoginPassword}"></td>
                <td><label class="errorClass" id="reloginpasswordError">${errors.reLoginPassword}</label></td>
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
                    <button id="submitbuttion">修改密码</button>
                </td>
                <td></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
