<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <!-- My Account Start -->
    <div class="my-account">
        <div class="container-fluid">
            <c:if test="${currentUser.status.id==2}">
            <div class="alert alert-danger"><fmt:message key="account.ban"/></div>
            </c:if>
            <div class="row">
                <div class="col-md-3">
                    <div class="nav flex-column nav-pills" role="tablist" aria-orientation="vertical">
                        <a class="nav-link<c:if test="${activeTab==null}">
                            active
                            </c:if>"
                           id="orders-nav" data-toggle="pill" href="#orders-tab" role="tab">
                            <i class="fa fa-shopping-bag"></i><fmt:message key="account.orders"/></a>
                        <a class="nav-link" id="dashboard-nav" data-toggle="pill" href="#dashboard-tab"
                           role="tab"><i class="fa fa-tachometer-alt"></i><fmt:message key="account.info"/></a>
                        <a class="nav-link
                            <c:if test="${activeTab=='update'}">
                            active
                            </c:if>" id="update-nav" data-toggle="pill" href="#update-tab" role="tab"><i
                                class="fa fa-user"></i><fmt:message key="account.update"/></a>
                        <a class="nav-link
                         <c:if test="${activeTab=='changePassword'}">
                            active
                            </c:if>"
                           id="account-nav" data-toggle="pill" href="#account-tab" role="tab"><i
                                class="fa fa-key"></i><fmt:message key="account.change.password"/></a>
                        <a class="nav-link" href="${hostName}/main/logout"><i
                                class="fa fa-sign-out-alt"></i><fmt:message key="navbar.logout"/></a>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="tab-content cart-page">
                        <div class="tab-pane fade
                            <c:if test="${activeTab==null}">
                            show active
                            </c:if>" id="orders-tab" role="tabpanel" aria-labelledby="orders-nav">
                            <c:if test="${fn:length(currentUserOrders) == 0}">
                                <div class="product-view-top">
                                    <h1 class="d-flex justify-content-center page-header"><fmt:message key="no.orders"/></h1>
                                </div>
                            </c:if>
                            <c:if test="${fn:length(currentUserOrders) != 0}">
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
                                    <c:forEach var="order" items="${currentUserOrders}">
                                        <tr>
                                        <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.id}</td>
                                        <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}" id="td-order-status">
                                            <c:choose>
                                            <c:when test="${order.status.id==1}">
                                            <div class="nav-item">
                                                <a href="#" class="nav-link dropdown-toggle" id="order-status${order.id}" data-toggle="dropdown">${order.status.name}</a>
                                                <div class="dropdown-menu">
                                                    <a href="#" data-url-change-order-status="${hostName}/main/ajax/change-order-status" data-id-order="${order.id}"
                                                       data-page="account" data-id-order-status=4 data-id-current-order-status="${order.status.id}"
                                                       class="change-order-status dropdown-item"><fmt:message key="account.cancel.order"/></a>
                                                </div>
                                            </div>
                                            </c:when>
                                                <c:otherwise>
                                                    ${order.status.name}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.created}</td>
                                        <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">$${order.cost}</td>
                                        <td ROWSPAN="${fn:length(order.items)!=0 ? fn:length(order.items) : 1}">${order.address}</td>
                                        <c:forEach var="item" items="${order.items}" varStatus="loop">
                                            <c:if test="${!loop.first}">
                                                <tr>
                                            </c:if>
                                            <td>
                                                <div class="img">
                                                    <a href="${hostName}/main/product?id=${item.product.id}"><img src="${item.product.image}" alt="Image"></a>
                                                    <p>${item.product.name}</p>
                                                </div>
                                            </td>
                                            <td>${item.count}</td>
                                            <td>$${item.retainedProductPrice}</td>
                                            <c:if test="${!loop.first}">
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${fn:length(order.items) == 0}">
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </c:if>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            </c:if>
                        </div>
                        <div class="tab-pane fade" id="dashboard-tab" role="tabpanel"
                             aria-labelledby="dashboard-nav">
                            <h4><fmt:message key="account.info"/></h4>
                            <p><fmt:message key="register.username"/>: <span>${currentUser.username}</span></p>
                            <p><fmt:message key="register.first.name"/>: <span>${currentUser.firstName}</span></p>
                            <p><fmt:message key="register.last.name"/>: <span>${currentUser.lastName}</span></p>
                            <p><fmt:message key="register.date"/>: <span>${currentUser.birthDate}</span></p>
                            <p><fmt:message key="register.phone"/>: <span>${currentUser.phoneNumber}</span></p>
                            <p><fmt:message key="register.email"/>: <span>${currentUser.email}</span></p>
                            <p><fmt:message key="account.status"/>: <span>${currentUser.status.name}</span></p>

                        </div>
                        <div class="tab-pane fade
                            <c:if test="${activeTab=='update'}">
                            show active
                            </c:if>" id="update-tab" role="tabpanel" aria-labelledby="update-nav">
                            <h4><fmt:message key="account.update"/></h4>
                            <form action="update-user" method="post">
                                <div class="col-md-8">
                                    <label><fmt:message key="register.first.name"/></label>
                                    <input class="form-control" type="text" name="first-name"
                                           value="${currentUser.firstName}" maxlength="45">
                                </div>
                                <c:if test="${isWrongFirstName}">
                                    <div class="alert alert-danger"><fmt:message key="register.error.first.name"/></div>
                                </c:if>
                                <div class="col-md-8">
                                    <label><fmt:message key="register.last.name"/></label>
                                    <input class="form-control" type="text" name="last-name" value="${currentUser.lastName}"
                                           maxlength="45">
                                </div>
                                <c:if test="${isWrongLastName}">
                                    <div class="alert alert-danger"><fmt:message key="register.error.last.name"/></div>
                                </c:if>
                                <div class="col-md-8">
                                    <label><fmt:message key="register.username"/></label>
                                    <input class="form-control" type="text" placeholder="user123" name="username"
                                           value="${currentUser.username}" maxlength="45">
                                </div>
                                <c:if test="${isWrongUsername}">
                                    <div class="alert alert-danger"><fmt:message key="register.error.login"/></div>
                                </c:if>
                                <div class="col-md-8">
                                    <label><fmt:message key="register.date"/></label>
                                    <input class="form-control" type="date"  name="birth-date" min="1920-01-01"
                                           value="${currentUser.birthDate}">
                                </div>
                                <c:if test="${isWrongBirthDate}">
                                    <div class="alert alert-danger"><fmt:message key="register.error.date"/></div>
                                </c:if>
                                <div class="col-md-8">
                                    <label><fmt:message key="register.phone"/></label>
                                    <input class="form-control" type="text" name="phone-number"
                                           value="${currentUser.phoneNumber}" maxlength="45">
                                </div>
                                <c:if test="${isWrongPhoneNumber}">
                                    <div class="alert alert-danger"><fmt:message key="register.error.phone"/></div>
                                </c:if>
                                <div class="col-md-8">
                                    <label><fmt:message key="register.email"/></label>
                                    <input class="form-control" type="email" name="email" value="${currentUser.email}"
                                           maxlength="45">
                                </div>
                                <c:if test="${isWrongEmail}">
                                    <div class="alert alert-danger"><fmt:message key="register.error.email"/></div>
                                </c:if>

                                <div class="col-md-12">
                                    <button class="btn" type="submit"><fmt:message key="account.save.changes"/></button>
                                    <button class="btn"><a href="${hostName}/main/account"><fmt:message key="cancel"/></a></button>
                                </div>
                            </form>
                        </div>
                        <div class="tab-pane fade
                            <c:if test="${activeTab=='changePassword'}">
                            show active
                            </c:if>"
                             id="account-tab" role="tabpanel" aria-labelledby="account-nav">
                            <h4><fmt:message key="account.change.password"/></h4>
                            <div class="row">
                                <form action="${hostName}/main/change-password" method="post">
                                    <div class="col-md-12">
                                        <label><fmt:message key="account.current.password"/></label>
                                        <input class="form-control" type="password" name="current-password">
                                    </div>
                                    <c:if test="${isWrongCurrentPassword}">
                                        <div class="alert alert-danger"><fmt:message key="account.error.current.password"/> </div>
                                    </c:if>
                                    <div class="col-md-12">
                                        <label><fmt:message key="account.new.password"/></label>
                                        <input class="form-control" type="password" name="new-password">
                                    </div>
                                    <c:if test="${isWrongPassword}">
                                        <div class="alert alert-danger"><fmt:message key="register.error.password"/> </div>
                                    </c:if>
                                    <div class="col-md-12">
                                        <label><fmt:message key="register.confirm"/></label>
                                        <input class="form-control" type="password" name="confirm-password">
                                    </div>
                                    <c:if test="${isWrongConfirmedPassword}">
                                        <div class="alert alert-danger"><fmt:message key="register.error.confirm.password"/> </div>
                                    </c:if>
                                    <div class="col-md-12">
                                        <button class="btn"><fmt:message key="account.change"/></button>
                                        <button class="btn"><a href="${hostName}/main/account"><fmt:message key="cancel"/></a></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- My Account End -->
</fmt:bundle>