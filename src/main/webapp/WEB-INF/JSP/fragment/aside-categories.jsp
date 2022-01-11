<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<div class="col-md-3">
    <nav class="navbar bg-light">
        <ul class="navbar-nav">
            <c:forEach var="category" items="${requestScope.categoriesOnLeftPanel}">
                <li class="nav-item">
                    <a class="nav-link" href="${requestScope.hostName}/main/categories?id=${category.id}">
                        <img src="${category.image}" alt="Category image" style="width: 20px; height: 20px">&nbsp&nbsp&nbsp${category.name}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>
