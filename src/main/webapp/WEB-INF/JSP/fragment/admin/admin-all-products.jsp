<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="col-md-12">
        <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.products"/></h1>
        <div class="table-responsive cart-page">
            <table class="table table-bordered">
                <thead class="thead-dark">
                <tr>
                    <th><fmt:message key="admin.product.id"/></th>
                    <th><fmt:message key="admin.product.name"/></th>
                    <th><fmt:message key="admin.product.description"/></th>
                    <th><fmt:message key="admin.product.price"/></th>
                    <th><fmt:message key="admin.product.category.name"/></th>
                    <th><fmt:message key="admin.product.producer.name"/></th>
                    <th><fmt:message key="image"/></th>
                    <th><fmt:message key="count"/></th>
                    <th><fmt:message key="actions"/></th>
                </thead>
                <tbody>
                <c:forEach var="product" items="${allProducts}">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.description}</td>
                        <td>$${product.price}</td>
                        <td>${product.category.name}</td>
                        <td>${product.producer.name}</td>
                        <td>
                            <div class="img">
                                <img src="${product.image}" alt="Image">
                            </div>
                        </td>
                        <td>${product.count}</td>
                        <td>
                            <div>
                            <a href="${hostName}/main/change-product-page?id=${product.id}">
                                <i class="fa fa-edit"></i>
                                <fmt:message key="admin.edit"/>
                            </a>
                            </div>
                            <div>
                            <a href="${hostName}/main/remove-product?id=${product.id}">
                                <i class="fa fa-times"></i>
                                <fmt:message key="admin.remove"/>
                            </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</fmt:bundle>