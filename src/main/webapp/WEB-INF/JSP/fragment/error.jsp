<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1><fmt:message key="error.oops"/></h1>
                <h2><fmt:message key="${requestScope.errorTitle}"/></h2>
                <div class="error-details">
                    <fmt:message key="${requestScope.errorMessage}"/>
                </div>
                <c:choose>
                    <c:when test="${pageContext.response.status == 401}">
                        <div class="error-actions">
                            <a href="${requestScope.hostName}/main/login-page" class="btn btn-primary btn-lg margin-top-bottom">
                                <fmt:message key="navbar.login"/>
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="error-actions">
                            <a href="${requestScope.hostName}/main/" class="btn btn-primary btn-lg margin-top-bottom">
                                <i class="fa fa-home"></i> <fmt:message key="error.button.takeMeHome"/>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</fmt:bundle>