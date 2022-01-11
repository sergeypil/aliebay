<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="d-flex justify-content-center">
    <div class="col-lg-8" style="background-color: white">
        <div class="row justify-content-center">
                <c:forEach var="category" items="${requestScope.categoriesForCategoriesPage}">
                    <div class="col-lg-2 margin-left-right margin-top-bottom">
                        <a href="${requestScope.hostName}/main/categories?id=${category.id}">
                            <img src="${category.image}" alt="Category image" style="width: 150px; height: 150px">
                            <div>
                                    ${category.name}
                            </div>
                        </a>
                    </div>
                </c:forEach>
        </div>
    </div>
</div>