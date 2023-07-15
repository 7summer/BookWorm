<%--
  Created by IntelliJ IDEA.
  User: 33278
  Date: 2023/5/27
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/left.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/menu/mymenu.css'/>" media="all">

    <script type="text/javascript" src="<c:url value='/jquery/jquery-3.7.0.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>

    <script language="javascript">
        /*
         * 1. 对象名必须与第一个参数相同！
           2. 第二个参数是显示在菜单上的大标题
         */
        var bar = new Q6MenuBar("bar", "网上书城");
        $(function() {
            bar.colorStyle = 4;//指定配色样式，一共0,1,2,3,4
            bar.config.imgDir = "<c:url value='/menu/img/'/>";//小工具所需图片的路径
            bar.config.radioButton=true;//是否排斥，多个一级分类是否排斥

            /*
            1. 程序设计：一级分类名称
            2. Java Javascript：二级分类名称
            3. /goods/jsps/book/list.jsp：点击二级分类后链接到的URL
            4. body:链接的内容在哪个框架页中显示
            */
            <c:forEach items="${parents}" var="parent">
                <c:forEach items="${parent.children}" var="child">
                    bar.add("${parent.categoryName}", "${child.categoryName}", "<c:url value='/BookServlet?method=findByCategoryId&categoryId=${child.categoryId}'/>", "body");
                    <%--bar.add("${parent.categoryName}", "${child.categoryName}", "/BookWorm/BookServlet?method=findByCategoryId&categoryId=${child.categoryId}", "body");--%>
                </c:forEach>
            </c:forEach>

            $("#menu").html(bar.toString());
        });
    </script>
</head>
<body>
    <div id="menu"></div>
</body>
</html>

