<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent" prefix="purchase.">
    <div class="about col-sm">
        <h1 ><fmt:message key="steps"/></h1><br>
        <ol>
            <li><fmt:message key="step1"/></li><hr>
            <li><fmt:message key="step2"/></li><hr>
            <li><fmt:message key="step3"/></li><hr>
            <li><fmt:message key="step4"/></li><hr>
            <li><fmt:message key="step5"/></li><hr>
            <li><fmt:message key="step6"/></li><hr>
            <li><fmt:message key="step7"/></li>
        </ol>
    </div>
</fmt:bundle>