<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="d-flex justify-content-center" style="padding-bottom: 80px">
        <div class="col-lg-8" style="background-color: white">
            <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.admin"/></h1>
            <div class="row justify-content-center">
                <div class="col-lg-2 margin-left-right margin-top-bottom">
                    <a href="${requestScope.hostName}/main/admin/all-users">
                        <i class="fa fa-users" style="font-size: 150px"></i>
                        <div><fmt:message key="admin.view.users"/></div>
                    </a>
                </div>
                <div class="col-lg-2 margin-left-right margin-top-bottom">
                    <a href="${requestScope.hostName}/main/admin/all-orders">
                        <i class="fa fa-list" style="font-size: 150px"></i>
                        <div><fmt:message key="admin.view.orders"/></div>
                    </a>
                </div>
                <div class="col-lg-2 margin-left-right margin-top-bottom">
                    <a href="${requestScope.hostName}/main/admin/all-products">
                        <i class="fa fa-list-alt" style="font-size: 150px"></i>
                        <div><fmt:message key="admin.view.products"/></div>
                    </a>
                </div>
                <div class="col-lg-2 margin-left-right margin-top-bottom">
                    <a href="${requestScope.hostName}/main/admin/all-producers">
                        <i class="fa fa-list-ul" style="font-size: 150px"></i>
                        <div><fmt:message key="admin.view.producers"/></div>
                    </a>
                </div>
                <div class="col-lg-2 margin-left-right margin-top-bottom">
                    <a href="${requestScope.hostName}/main/admin/all-categories">
                        <i class="fa fa-th-list" style="font-size: 150px"></i>
                        <div><fmt:message key="admin.view.categories"/></div>
                    </a>
                </div>
                <div class="col-md-2 margin-left-right margin-top-bottom">
                    <a href="${requestScope.hostName}/main/admin/add-product-page">
                        <i class="fa fa-shopping-bag" style="font-size: 150px"></i>
                        <div><fmt:message key="admin.add.product"/></div>
                    </a>
                </div>
                <div class="col-lg-2 margin-left-right margin-top-bottom">
                    <a href="${requestScope.hostName}/main/admin/add-category-page">
                        <i class="fa fa-list-alt" style="font-size: 150px"></i>
                        <div><fmt:message key="admin.add.category"/></div>
                    </a>
                </div>
                <div class="col-lg-2 margin-left-right margin-top-bottom">
                    <a href="${requestScope.hostName}/main/admin/add-producer-page">
                        <i class="fa fa-university" style="font-size: 150px"></i>
                        <div><fmt:message key="admin.add.producer"/></div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</fmt:bundle>