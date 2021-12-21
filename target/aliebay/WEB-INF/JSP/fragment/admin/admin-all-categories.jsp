<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.categories"/></h1>
    <div class="table-responsive cart-page">
        <table class="table table-bordered">
            <thead class="thead-dark">
            <tr>
                <th><fmt:message key="admin.category.id"/></th>
                <th><fmt:message key="image"/></th>
                <th><fmt:message key="admin.add.category.parent"/></th>
                <th><fmt:message key="actions"/></th>
                <th><fmt:message key="language"/></th>
                <th><fmt:message key="admin.category.name"/></th>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${idToCategories}">
                <tr>
                    <td rowspan="${fn:length(entry.value)}">${entry.key}</td>
                    <td rowspan="${fn:length(entry.value)}">
                        <div class="img">
                            <img src="${entry.value[1].image}" alt="Image">
                        </div>
                    </td>
                    <td rowspan="${fn:length(entry.value)}">${entry.value[1].parentCategoryId}</td>
                    <td rowspan="${fn:length(entry.value)}">
                        <div>
                        <a href="${hostName}/main/change-category-page?id=${entry.value[1].id}">
                            <i class="fa fa-edit"></i>
                            <fmt:message key="admin.edit"/>
                        </a>
                        </div>
                        <div>
                        <a href="${hostName}/main/remove-category?id=${entry.value[1].id}">
                            <i class="fa fa-times"></i>
                            <fmt:message key="admin.remove"/>
                        </a>
                        </div>
                    </td>
                    <c:forEach var="category" items="${entry.value}" varStatus="loop">
                        <c:if test="${!loop.first}">
                            <tr>
                        </c:if>
                        <td>${category.language.name}</td>
                        <td>${category.name}</td>
                        <c:if test="${!loop.first}">
                            </tr>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</fmt:bundle>