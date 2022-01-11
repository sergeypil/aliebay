<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent" prefix="navbar.">
    <div class="nav">
        <div class="container-fluid">
            <nav class="navbar navbar-expand-md bg-dark navbar-dark">
                <a href="#" class="navbar-brand"><fmt:message key="menu"/></a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                    <div class="navbar-nav mr-auto">
                        <a href="${requestScope.hostName}/main/categories" class="nav-item nav-link"><fmt:message
                                key="categories"/></a>
                        <a href="${requestScope.hostName}/main/products" class="nav-item nav-link"><fmt:message
                                key="products"/></a>
                    </div>
                    <div class="navbar-nav ml-auto">
                        <div class="nav-item dropdown">
                            <a href="#" class="nav-link dropdown-toggle"
                               data-toggle="dropdown">${sessionScope.currentLanguage}</a>
                            <div class="dropdown-menu">
                                <c:forEach var="lang" items="${sessionScope.appLanguages}">
                                    <a href="${requestScope.hostName}/main/change-language?lang=${lang.code}"
                                       class="dropdown-item">${lang.name}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="navbar-nav ml-auto">
                    <div class="nav-item dropdown">
                        <c:choose>
                            <c:when test="${sessionScope.currentUser != null}">
                                <a href="#" class="nav-link dropdown-toggle"
                                   data-toggle="dropdown"
                                   style="padding-left: 45px">${sessionScope.currentUser.username}</a>
                                <div class="dropdown-menu">
                                    <a href="${requestScope.hostName}/main/account" class="dropdown-item"><fmt:message
                                            key="account"/></a>
                                    <a href="${requestScope.hostName}/main/logout" class="dropdown-item"><fmt:message
                                            key="logout"/></a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown"><fmt:message
                                        key="account"/></a>
                                <div class="dropdown-menu">
                                    <a href="${requestScope.hostName}/main/login-page"
                                       class="dropdown-item"><fmt:message key="login"/></a>
                                    <a href="${requestScope.hostName}/main/register-page"
                                       class="dropdown-item"><fmt:message key="register"/></a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</fmt:bundle>