<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="col-md-10">
        <label><fmt:message key="register.first.name"/><span class="text-danger"> *</span>
            <input class="form-control" type="text" name="first-name" value="${requestScope.registerDto.firstName}"
                   maxlength="45">
        </label>
    </div>
    <c:if test="${requestScope.isWrongFirstName}">
        <div class="alert alert-danger"><fmt:message key="register.error.first.name"/></div>
    </c:if>
    <div class="col-md-10">
        <label><fmt:message key="register.last.name"/><span class="text-danger"> *</span>
            <input class="form-control" type="text" name="last-name" value="${requestScope.registerDto.lastName}"
                   maxlength="45">
        </label>
    </div>
    <c:if test="${requestScope.isWrongLastName}">
        <div class="alert alert-danger"><fmt:message key="register.error.last.name"/></div>
    </c:if>
    <div class="col-md-10">
        <label><fmt:message key="register.username"/><span class="text-danger"> *</span></label>
        <input class="form-control" type="text" placeholder="user123" name="username"
               value="${requestScope.registerDto.username}"
               maxlength="45">
    </div>
    <c:if test="${requestScope.isWrongUsername}">
        <div class="alert alert-danger"><fmt:message key="register.error.username"/></div>
    </c:if>
    <c:if test="${requestScope.isUsernameExist}">
        <div class="alert alert-danger"><fmt:message key="register.error.username.exist"/></div>
    </c:if>
    <div class="col-md-10">
        <label><fmt:message key="register.date"/><span class="text-danger"> *</span>
            <input class="form-control" type="date" name="birth-date" min="1920-01-01"
                   value="${requestScope.registerDto.birthDate}">
        </label>
    </div>
    <c:if test="${requestScope.isWrongBirthDate}">
        <div class="alert alert-danger"><fmt:message key="register.error.date"/></div>
    </c:if>
    <div class="col-md-10">
        <label><fmt:message key="register.phone"/><span class="text-danger"> *</span>
            <input class="form-control" type="text" name="phone-number" value="${requestScope.registerDto.phoneNumber}"
                   maxlength="45">
        </label>
    </div>
    <c:if test="${requestScope.isWrongPhoneNumber}">
        <div class="alert alert-danger"><fmt:message key="register.error.phone"/></div>
    </c:if>
    <div class="col-md-10">
        <label><fmt:message key="register.email"/><span class="text-danger"> *</span>
            <input class="form-control" type="email" name="email" value="${requestScope.registerDto.email}"
                   maxlength="45">
        </label>
    </div>
    <c:if test="${requestScope.isWrongEmail}">
        <div class="alert alert-danger"><fmt:message key="register.error.email"/></div>
    </c:if>
    <c:if test="${requestScope.isEmailExist}">
        <div class="alert alert-danger"><fmt:message key="register.error.email.exist"/></div>
    </c:if>
</fmt:bundle>