<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="login d-flex justify-content-center">
        <div class="col-lg-4">
            <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.login"/></h1>
            <div class="login-form ">
                <form action="${requestScope.hostName}/main/login-user" method="post">
                    <div class="col-md-10">
                        <label><fmt:message key="login.email"/>
                        <input class="form-control" type="text" name="usernameOrEmail" maxlength="45">
                        </label>
                    </div>
                    <c:if test="${requestScope.isWrongLogin}">
                        <div class="alert alert-danger"><fmt:message key="login.error.login"/></div>
                    </c:if>
                    <div class="col-md-10">
                        <label><fmt:message key="login.password"/>
                        <input class="form-control" type="password" name="password" maxlength="45">
                        </label>
                    </div>
                    <c:if test="${requestScope.isWrongPassword}">
                        <div class="alert alert-danger"><fmt:message key="login.error.password"/> </div>
                    </c:if>
                    <div class="col-md-12">
                        <button class="btn"><fmt:message key="login.submit"/></button>
                    </div>
                </form>
                <c:if test="${requestScope.isAuthenticationError}">
                    <div class="alert alert-danger error-message"><fmt:message key="login.error.form"/> </div>
                </c:if>
            </div>
        </div>
    </div>
</fmt:bundle>