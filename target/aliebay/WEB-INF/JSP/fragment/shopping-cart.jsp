<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent" prefix="cart.">
    <div class="cart-page">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-8">
                    <div class="cart-page-inner">
                        <c:if test="${currentShoppingCart == null || currentShoppingCart.totalCount==0}">
                            <h1 class="base-color"><fmt:message key="empty"/> <i class="fa fa-shopping-cart"></i></h1>
                        </c:if>
                        <c:if test="${currentShoppingCart.totalCount!=0}">
                            <div class="table-responsive">
                                <table class="table table-bordered">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th><fmt:message key="product"/></th>
                                        <th><fmt:message key="price"/></th>
                                        <th><fmt:message key="quantity"/></th>
                                        <th><fmt:message key="total"/></th>
                                        <th><fmt:message key="remove"/></th>
                                    </tr>
                                    </thead>
                                    <tbody class="align-middle">
                                    <c:forEach var="entry" items="${cartProducts}">
                                        <tr id="tr${entry.key.id}">
                                            <td>
                                                <div class="img">
                                                    <a href="${hostName}/main/product?id=${entry.key.id}"><img
                                                            src="${entry.key.image}" alt="Image"></a>
                                                    <p>${entry.key.name}</p>
                                                </div>
                                            </td>
                                            <td>$${entry.key.price}</td>
                                            <td>${entry.value.count}</td>
                                                <%--<m:multiply price="${entry.key.price}" count="${entry.value}"/>--%>
                                            <td>$${entry.value.cost}</td>
                                            <td>
                                                <button><i class="fa fa-trash remove-from-cart"
                                                           data-id-product="${entry.key.id}"
                                                           data-url-remove-from-cart="${hostName}/main/ajax/remove-product-from-cart">
                                                </i></button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="cart-page-inner">
                        <div class="col-md-12">
                            <div class="cart-summary">
                                <div class="cart-content">
                                    <h1><fmt:message key="summary"/></h1>
                                    <p><fmt:message key="count"/><span
                                            id="total-count">${currentShoppingCart.totalCount}</span></p>
                                    <p><fmt:message key="cost"/><span
                                            id="total-cost">$${currentShoppingCart.totalCost}</span></p>
                                </div>
                                <c:if test="${currentShoppingCart.totalCount>0}">
                                    <div class="cart-btn">
                                        <button><a href="${hostName}/main/checkout"><fmt:message key="make.order"/></a>
                                        </button>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</fmt:bundle>