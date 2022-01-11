<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="login d-flex justify-content-center">
        <div class="col-lg-4">
            <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.registration"/></h1>
            <div class="register-form">
                <form action="${requestScope.hostName}/main/register-user" method="post">
                    <jsp:include page="registerForm.jsp"/>
                    <div class="col-md-10">
                        <label><fmt:message key="login.password"/><span class="text-danger"> *</span>
                            <input class="form-control" type="password" name="password" maxlength="45">
                        </label>
                    </div>
                    <c:if test="${requestScope.isWrongPassword}">
                        <div class="alert alert-danger"><fmt:message key="register.error.password"/></div>
                    </c:if>
                    <div class="col-md-10">
                        <label><fmt:message key="register.confirm"/><span class="text-danger"> *</span>
                            <input class="form-control" type="password" name="confirm-password" maxlength="45">
                        </label>
                    </div>
                    <c:if test="${requestScope.isWrongConfirmedPassword}">
                        <div class="alert alert-danger"><fmt:message key="register.error.confirm.password"/></div>
                    </c:if>
                    <div class="col-md-10">
                        <button class="btn" type="submit"><fmt:message key="register.submit"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</fmt:bundle>