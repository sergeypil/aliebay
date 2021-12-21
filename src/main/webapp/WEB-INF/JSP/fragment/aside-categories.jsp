<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="col-md-3">
    <nav class="navbar bg-light">
        <ul class="navbar-nav">
            <c:forEach var="category" items="${categoriesOnLeftPanel}">
                <li class="nav-item">
                    <%--<a class="nav-link" href="${hostName}/main/category?id=${category.id}">--%>
                    <a class="nav-link" href="${hostName}/main/categories?id=${category.id}">
                        <img src="${category.image}" style="width: 20px; height: 20px">&nbsp&nbsp&nbsp${category.name}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>
</body>
</html>
