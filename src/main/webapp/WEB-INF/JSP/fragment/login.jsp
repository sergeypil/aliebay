<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="login d-flex justify-content-center">
        <div class="col-lg-4">
            <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.login"/></h1>
            <div class="login-form ">
                <form action="login-user" method="post">
                    <div class="col-md-8">
                        <label><fmt:message key="login.email"/></label>
                        <input class="form-control" type="text" name="usernameOrEmail" maxlength="45">
                    </div>
                    <c:if test="${isWrongLogin}">
                        <div class="alert alert-danger"><fmt:message key="login.error.login"/></div>
                    </c:if>
                    <div class="col-md-8">
                        <label><fmt:message key="login.password"/></label>
                        <input class="form-control" type="password" name="password"
                               maxlength="45">
                    </div>
                    <c:if test="${isWrongPassword}">
                        <div class="alert alert-danger"><fmt:message key="login.error.password"/> </div>
                    </c:if>
                    <div class="col-md-12">
                        <button class="btn"><fmt:message key="login.submit"/></button>
                    </div>
                </form>
                <c:if test="${isAuthenticationError}">
                    <div class="alert alert-danger error-message"><fmt:message key="login.error.form"/> </div>
                </c:if>
            </div>
        </div>
    </div>
</fmt:bundle>