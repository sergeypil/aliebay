<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent" prefix="purchase.">
    <div class="about col-sm">
        <h1 ><fmt:message key="steps"/></h1><br>
        <ol>
            <h5><li><fmt:message key="step1"/></li></h5><hr>
            <h5><li><fmt:message key="step2"/></li></h5><hr>
            <h5><li><fmt:message key="step3"/></li></h5><hr>
            <h5><li><fmt:message key="step4"/></li></h5><hr>
            <h5><li><fmt:message key="step5"/></li></h5><hr>
            <h5><li><fmt:message key="step6"/></li></h5><hr>
            <h5><li><fmt:message key="step7"/></li></h5>
        </ol>
    </div>
</fmt:bundle>