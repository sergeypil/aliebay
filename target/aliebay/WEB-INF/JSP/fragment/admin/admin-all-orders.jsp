<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.orders"/></h1>
    <div class="table-responsive cart-page">
        <table class="table table-bordered">
            <thead class="thead-dark">
            <tr>
                <th><fmt:message key="admin.order.id"/></th>
                <th><fmt:message key="admin.order.status"/></th>
                <th><fmt:message key="admin.order.date"/></th>
                <th><fmt:message key="admin.user.id"/></th>
                <th><fmt:message key="admin.order.cost"/></th>
                <th><fmt:message key="admin.order.address"/></th>
                <th><fmt:message key="admin.order.product.product"/></th>
                <th><fmt:message key="admin.order.product.count"/></th>
                <th><fmt:message key="admin.order.product.price"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${allOrders}">
                <tr>
                <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.id}</td>
                <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">
                    <c:choose>
                        <c:when test="${order.status.id!=4}">
                            <div class="nav-item ">
                                <a href="#" class="nav-link dropdown-toggle" id="order-status${order.id}"
                                   data-toggle="dropdown">${order.status.name}</a>
                                <div class="dropdown-menu">
                                    <c:forEach var="orderStatus" items="${orderStatuses}">
                                        <a href="#"
                                           data-url-change-order-status="${hostName}/main/ajax/change-order-status"
                                           data-id-order="${order.id}"
                                           data-id-order-status="${orderStatus.id}"
                                           class="change-order-status dropdown-item">${orderStatus.name}</a>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            ${order.status.name}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.created}</td>
                <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.idUser}</td>
                <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">$${order.cost}</td>
                <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.address}</td>
                <c:forEach var="item" items="${order.items}" varStatus="loop">
                    <c:if test="${!loop.first}">
                        <tr>
                    </c:if>
                    <td>
                        <div class="img">
                            <a href="${hostName}/main/product?id=${item.product.id}">
                                <img src="${item.product.image}" alt="Image"></a>
                            <p>${item.product.name}</p>
                        </div>
                    </td>
                    <td>${item.count}</td>
                    <td>$${item.retainedProductPrice}</td>
                    <c:if test="${!loop.first}">
                        </tr>
                    </c:if>
                </c:forEach>
                <c:if test="${fn:length(order.items) == 0}">
                    <td></td>
                    <td></td>
                    <td></td>
                </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</fmt:bundle>