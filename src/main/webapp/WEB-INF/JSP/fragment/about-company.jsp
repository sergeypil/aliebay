<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent" prefix="about.">
<div class="about col-sm">
        <h1 class="header"><fmt:message key="welcome"/></h1><br>
        <ul>
            <li><h3><fmt:message key="comment1"/></h3></li><hr>
            <li><h3><fmt:message key="comment2"/></h3></li><hr>
            <li><h3><fmt:message key="comment3"/></h3></li><hr>
            <li><h3><fmt:message key="comment4"/></h3></li><hr>
            <li><h3><fmt:message key="comment5"/></h3></li>
        </ul>
</div>
</fmt:bundle>