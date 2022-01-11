<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="bottom-bar">
        <div class="container-fluid">
            <div class="row align-items-center">
                <div class="col-md-3">
                    <div class="logo">
                        <a href="${requestScope.hostName}/main/">
                            <img src="${requestScope.contextPath}/media/logo_aliebay.jpg" alt="Logo">
                        </a>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="search">
                        <form action="${requestScope.hostName}/main/products" method="GET">
                            <input type="text" placeholder="<fmt:message key="bottombar.search"/>" name="search"
                                   value="${requestScope.searchParam}">
                            <button type="submit"><i class="fa fa-search"></i></button>
                        </form>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="user">
                        <c:if test="${sessionScope.currentUser.role == 'admin'}">
                            <a href="${requestScope.hostName}/main/admin" class="btn"
                               title="<fmt:message key="header.admin"/>">
                                <i class="fa fa-user-lock"></i>
                            </a>
                        </c:if>
                        <a href="${requestScope.hostName}/main/shopping-cart" class="btn cart">
                            <i class="fa fa-shopping-cart"></i>
                            <c:if test="${sessionScope.currentShoppingCart == null}">
                                <span id="count-products-cart">(0)</span>
                            </c:if>
                            <c:if test="${sessionScope.currentShoppingCart != null}">
                                <span id="count-products-cart">(${sessionScope.currentShoppingCart.totalCount})</span>
                            </c:if>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</fmt:bundle>