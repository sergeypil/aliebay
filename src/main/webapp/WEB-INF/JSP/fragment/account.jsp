<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="my-account">
        <div class="container-fluid">
            <c:if test="${sessionScope.currentUser.status.id==2}">
                <div class="alert alert-danger"><fmt:message key="account.ban"/></div>
            </c:if>
            <div class="row">
                <div class="col-md-3">
                    <div class="nav flex-column nav-pills" role="tablist" aria-orientation="vertical">
                        <a class="nav-link<c:if test="${requestScope.activeTab==null}">
                            active
                            </c:if>"
                           id="orders-nav" data-toggle="pill" href="#orders-tab" role="tab">
                            <i class="fa fa-shopping-bag"></i><fmt:message key="account.orders"/></a>
                        <a class="nav-link" id="dashboard-nav" data-toggle="pill" href="#dashboard-tab"
                           role="tab"><i class="fa fa-tachometer-alt"></i><fmt:message key="account.info"/></a>
                        <a class="nav-link
                            <c:if test="${requestScope.activeTab=='update'}">
                            active
                            </c:if>" id="update-nav" data-toggle="pill" href="#update-tab" role="tab"><i
                                class="fa fa-user"></i><fmt:message key="account.update"/></a>
                        <a class="nav-link
                         <c:if test="${requestScope.activeTab=='changePassword'}">
                            active
                            </c:if>"
                           id="account-nav" data-toggle="pill" href="#account-tab" role="tab"><i
                                class="fa fa-key"></i><fmt:message key="account.change.password"/></a>
                        <a class="nav-link" href="${requestScope.hostName}/main/logout"><i
                                class="fa fa-sign-out-alt"></i><fmt:message key="navbar.logout"/></a>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="tab-content cart-page">
                        <div class="tab-pane fade
                            <c:if test="${requestScope.activeTab==null}">
                            show active
                            </c:if>" id="orders-tab" role="tabpanel" aria-labelledby="orders-nav">
                            <c:if test="${fn:length(sessionScope.currentUserOrders) == 0}">
                                <div class="product-view-top">
                                    <h1 class="d-flex justify-content-center page-header"><fmt:message
                                            key="no.orders"/></h1>
                                </div>
                            </c:if>
                            <c:if test="${fn:length(sessionScope.currentUserOrders) != 0}">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead class="thead-dark">
                                        <tr>
                                            <th><fmt:message key="admin.order.id"/></th>
                                            <th><fmt:message key="admin.order.status"/></th>
                                            <th><fmt:message key="admin.order.date"/></th>
                                            <th><fmt:message key="admin.order.cost"/></th>
                                            <th><fmt:message key="admin.order.address"/></th>
                                            <th><fmt:message key="admin.order.product.product"/></th>
                                            <th><fmt:message key="admin.order.product.count"/></th>
                                            <th><fmt:message key="admin.order.product.price"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="order" items="${sessionScope.currentUserOrders}">
                                            <tr>
                                            <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.id}</td>
                                            <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}"
                                                id="td-order-status">
                                                <c:choose>
                                                    <c:when test="${order.status.id==1}">
                                                        <div class="nav-item">
                                                            <a href="#" class="nav-link dropdown-toggle"
                                                               id="order-status${order.id}"
                                                               data-toggle="dropdown">${order.status.name}</a>
                                                            <div class="dropdown-menu">
                                                                <a href="#"
                                                                   data-url-change-order-status="${requestScope.hostName}/main/ajax/change-order-status"
                                                                   data-id-order="${order.id}"
                                                                   data-page="account" data-id-order-status=4
                                                                   data-id-current-order-status="${order.status.id}"
                                                                   class="change-order-status dropdown-item"><fmt:message
                                                                        key="account.cancel.order"/></a>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${order.status.name}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.created}</td>
                                            <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">
                                                $<fmt:formatNumber value="${order.cost}"/></td>
                                            <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.address}</td>
                                            <c:forEach var="item" items="${order.items}" varStatus="loop">
                                                <c:if test="${!loop.first}">
                                                    <tr>
                                                </c:if>
                                                <td>
                                                    <div class="img">
                                                        <a href="${requestScope.hostName}/main/product?id=${item.product.id}"><img
                                                                src="${item.product.image}" alt="Image"></a>
                                                        <p>${item.product.name}</p>
                                                    </div>
                                                </td>
                                                <td>${item.count}</td>
                                                <td>$<fmt:formatNumber value="${item.retainedProductPrice}"/></td>
                                                <c:if test="${!loop.first}">
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${fn:length(order.items) == 0}">
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                        </div>
                        <div class="tab-pane fade" id="dashboard-tab" role="tabpanel"
                             aria-labelledby="dashboard-nav">
                            <h4><fmt:message key="account.info"/></h4>
                            <p><fmt:message key="register.username"/>: <span>${sessionScope.currentUser.username}</span>
                            </p>
                            <p><fmt:message key="register.first.name"/>:
                                <span>${sessionScope.currentUser.firstName}</span></p>
                            <p><fmt:message key="register.last.name"/>:
                                <span>${sessionScope.currentUser.lastName}</span></p>
                            <p><fmt:message key="register.date"/>: <span>${sessionScope.currentUser.birthDate}</span>
                            </p>
                            <p><fmt:message key="register.phone"/>: <span>${sessionScope.currentUser.phoneNumber}</span>
                            </p>
                            <p><fmt:message key="register.email"/>: <span>${sessionScope.currentUser.email}</span></p>
                            <p><fmt:message key="account.status"/>: <span>${sessionScope.currentUser.status.name}</span>
                            </p>
                        </div>
                        <div class="tab-pane fade
                            <c:if test="${requestScope.activeTab=='update'}">
                            show active
                            </c:if>" id="update-tab" role="tabpanel" aria-labelledby="update-nav">
                            <h4><fmt:message key="account.update"/></h4>
                            <form action="${requestScope.hostName}/main/update-user" method="post">
                                <jsp:include page="registerForm.jsp"/>

                                <div class="col-md-12">
                                    <button class="btn" type="submit"><fmt:message key="account.save.changes"/></button>
                                    <button class="btn"><a href="${requestScope.hostName}/main/account"><fmt:message
                                            key="cancel"/></a></button>
                                </div>
                            </form>
                        </div>
                        <div class="tab-pane fade
                            <c:if test="${requestScope.activeTab=='changePassword'}">
                            show active
                            </c:if>"
                             id="account-tab" role="tabpanel" aria-labelledby="account-nav">
                            <h4><fmt:message key="account.change.password"/></h4>
                            <form action="${requestScope.hostName}/main/change-password" method="post">
                                <div class="col-md-8">
                                    <label><fmt:message key="account.current.password"/>
                                        <input class="form-control" type="password" name="current-password">
                                    </label>
                                </div>
                                <c:if test="${requestScope.isWrongCurrentPassword}">
                                    <div class="alert alert-danger"><fmt:message
                                            key="account.error.current.password"/></div>
                                </c:if>
                                <div class="col-md-8">
                                    <label><fmt:message key="account.new.password"/>
                                        <input class="form-control" type="password" name="new-password">
                                    </label>
                                </div>
                                <c:if test="${requestScope.isWrongPassword}">
                                    <div class="alert alert-danger"><fmt:message key="register.error.password"/></div>
                                </c:if>
                                <div class="col-md-8">
                                    <label><fmt:message key="register.confirm"/>
                                        <input class="form-control" type="password" name="confirm-password">
                                    </label>
                                </div>
                                <c:if test="${requestScope.isWrongConfirmedPassword}">
                                    <div class="alert alert-danger"><fmt:message
                                            key="register.error.confirm.password"/></div>
                                </c:if>
                                <div class="col-md-12">
                                    <button class="btn"><fmt:message key="account.change"/></button>
                                    <button class="btn"><a href="${requestScope.hostName}/main/account"><fmt:message
                                            key="cancel"/></a></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</fmt:bundle>