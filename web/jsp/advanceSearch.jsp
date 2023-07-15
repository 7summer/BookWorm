<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/28
  Time: 8:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/css/advanceSearch.css'/>">
</head>
<body>
    <form action="<c:url value='/BookServlet'/>" method="get">
        <input type="hidden" name="method" value="findByCombination">
        <table align="center" style="color: #404040;font-size: 10pt" id="tableForm">
            <tr>
                <td class="tdText">书名：</td>
                <td class="tdInput">
                    <input type="text" name="bookName" class="inputClass"/>
                </td>
            </tr>
            <tr>
                <td class="tdText">作者：</td>
                <td>
                    <input type="text" name="author" class="inputClass"/>
                </td>
            </tr>
            <tr>
                <td class="tdText">出版社：</td>
                <td>
                    <input type="text" name="publishHouse" class="inputClass"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="submit" value="提交">
                    <input type="reset" value="重新填写"/>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
