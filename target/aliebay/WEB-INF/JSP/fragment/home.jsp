<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent" prefix="home.">
    <div class="row">
        <jsp:include page="aside-categories.jsp"/>
        <jsp:include page="header-slider.jsp"/>
    </div>
    <div class="brand">
        <div class="container-fluid">
            <div class="brand-slider">
                <div class="brand-item"><img src="${contextPath}/media/brand-1.png" alt=""></div>
                <div class="brand-item"><img src="${contextPath}/media/brand-2.png" alt=""></div>
                <div class="brand-item"><img src="${contextPath}/media/brand-3.png" alt=""></div>
                <div class="brand-item"><img src="${contextPath}/media/brand-4.png" alt=""></div>
                <div class="brand-item"><img src="${contextPath}/media/brand-5.png" alt=""></div>
                <div class="brand-item"><img src="${contextPath}/media/brand-6.png" alt=""></div>
            </div>
        </div>
    </div>
    <div class="feature">
        <div class="container-fluid">
            <div class="row align-items-center">
                <div class="col-lg-3 col-md-6 feature-col">
                    <div class="feature-content">
                        <i class="fab fa-cc-mastercard"></i>
                        <h2><fmt:message key="service1"/></h2>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 feature-col">
                    <div class="feature-content">
                        <i class="fa fa-sync-alt"></i>
                        <h2><fmt:message key="service2"/></h2>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 feature-col">
                    <div class="feature-content">
                        <i class="fa fa-truck"></i>
                        <h2><fmt:message key="service3"/></h2>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 feature-col">
                    <div class="feature-content">
                        <i class="fa fa-comments"></i>
                        <h2><fmt:message key="service4"/></h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
</fmt:bundle>