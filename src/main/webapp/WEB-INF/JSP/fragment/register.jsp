<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
<div class="login d-flex justify-content-center">
    <div class="col-lg-4">
        <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.registration"/></h1>
        <div class="register-form">
            <form action="register-user" method="post">
                <div class="col-md-10">
                    <label><fmt:message key="register.first.name"/><span class="text-danger"> *</span></label>
                    <input class="form-control" type="text" name="first-name" value="${editedUser.firstName}"
                           maxlength="45">
                </div>
                <c:if test="${isWrongFirstName}">
                    <div class="alert alert-danger"><fmt:message key="register.error.first.name"/>*</div>
                </c:if>
                <div class="col-md-10">
                    <label><fmt:message key="register.last.name"/><span class="text-danger"> *</span></label>
                    <input class="form-control" type="text" name="last-name" value="${editedUser.lastName}"
                           maxlength="45">
                </div>
                <c:if test="${isWrongLastName}">
                    <div class="alert alert-danger"><fmt:message key="register.error.last.name"/></div>
                </c:if>
                <div class="col-md-10">
                    <label><fmt:message key="register.username"/><span class="text-danger"> *</span></label>
                    <input class="form-control" type="text" placeholder="user123" name="username" value="${editedUser.username}"
                           maxlength="45">
                </div>
                <c:if test="${isWrongUsername}">
                    <div class="alert alert-danger"><fmt:message key="register.error.username"/></div>
                </c:if>
                <c:if test="${isUsernameExist}">
                    <div class="alert alert-danger"><fmt:message key="register.error.username.exist"/></div>
                </c:if>
                <div class="col-md-10">
                    <label><fmt:message key="register.date"/><span class="text-danger"> *</span></label>
                    <input class="form-control" type="date"  name="birth-date" min="1920-01-01" value="${editedUser.birthDate}">
                </div>
                <c:if test="${isWrongBirthDate}">
                    <div class="alert alert-danger"><fmt:message key="register.error.date"/></div>
                </c:if>
                <div class="col-md-10">
                    <label><fmt:message key="register.phone"/><span class="text-danger"> *</span></label>
                    <input class="form-control" type="text" name="phone-number" value="${editedUser.phoneNumber}"
                           maxlength="45">
                </div>
                <c:if test="${isWrongPhoneNumber}">
                    <div class="alert alert-danger"><fmt:message key="register.error.phone"/></div>
                </c:if>
                <div class="col-md-10">
                    <label><fmt:message key="register.email"/><span class="text-danger"> *</span></label>
                    <input class="form-control" type="email" name="email" value="${editedUser.email}"
                           maxlength="45">
                </div>
                <c:if test="${isWrongEmail}">
                    <div class="alert alert-danger"><fmt:message key="register.error.email"/></div>
                </c:if>
                <c:if test="${isEmailExist}">
                    <div class="alert alert-danger"><fmt:message key="register.error.email.exist"/></div>
                </c:if>
                <div class="col-md-10">
                    <label><fmt:message key="login.password"/><span class="text-danger"> *</span></label>
                    <input class="form-control" type="password" name="password"
                           maxlength="45">
                </div>
                <c:if test="${isWrongPassword}">
                    <div class="alert alert-danger"><fmt:message key="register.error.password"/> </div>
                </c:if>
                <div class="col-md-10">
                    <label><fmt:message key="register.confirm"/><span class="text-danger"> *</span></label>
                    <input class="form-control" type="password" name="confirm-password"
                           maxlength="45">
                </div>
                <c:if test="${isWrongConfirmedPassword}">
                    <div class="alert alert-danger"><fmt:message key="register.error.confirm.password"/> </div>
                </c:if>
                <div class="col-md-10">
                    <button class="btn" type="submit"><fmt:message key="register.submit"/></button>
                </div>
            </form>
        </div>


    </div>
</div>
</fmt:bundle>