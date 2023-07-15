<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/25
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        String code = (String) request.getAttribute("code");
        String message = (String) request.getAttribute("message");
        String imgPath = null;

        if(code.equals("成功"))
        {
            imgPath = "/images/tick.jpg";
        }
        else if(code.equals("错误"))
        {
            imgPath = "/images/fork.jpg";
        }
    %>
    <div class="msg">
        <div class="title"><%=code%></div>
        <div class="info">
            <img src="<c:url value='<%=imgPath%>'/>" class="pict"/>
            <p class="information"><%=message%></p>
            <a href="<c:url value='/adminfile/index.jsp'/>" class="info-a" target="_top">主页</a>
        </div>
    </div>
</body>
</html>

<style>
    *{
        box-sizing: border-box;
    }

    .msg{
        width: 800px;
        height: 300px;

        border: 2px solid gray;

        margin: auto;
    }

    .title{
        width: 800px;
        height: 50px;

        line-height: 50px;

        font-weight: bold;

        padding-left: 10px;

        background-color: gray;
    }

    .info{
        width: 800px;
        height: 250px;
    }
    .pict{
        width: 200px;
        height: 200px;

        float: left;
    }
    .information{
        font-size: 30px;
        color: orange;

        margin-left: 220px;
    }
    .info-a{
        text-decoration: none;

        margin: 20px;
    }
</style>
