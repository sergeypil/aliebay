<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1><fmt:message key="error.oops"/></h1>
                <h2><fmt:message key="${errorTitle}"/></h2>
                <div class="error-details">
                    <fmt:message key="${errorMessage}"/>
                </div>
                <div class="error-actions">
                    <a href="${hostName}/main/" class="btn btn-primary btn-lg margin-top-bottom">
                        <i class="fa fa-home"></i> <fmt:message key="error.button.takeMeHome"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</fmt:bundle>