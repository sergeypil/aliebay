<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
<!-- Checkout Start -->
<div class="checkout">
    <div class="container-fluid">
        <form action="${hostName}/main/make-order" method="post">
        <div class="row">
            <div class="col-lg-8">
                <div class="checkout-inner">
                    <div class="billing-address">
                        <div class="row">
                            <h2 class="col-md-12"><fmt:message key="checkout.card.info"/></h2>
                            <div class="col-md-9">
                                <label><fmt:message key="checkout.card.number"/></label>
                                <input class="form-control" type="tel" maxlength="16" name="card-number">
                                <c:if test="${isWrongCardNumber}">
                                    <div class="alert alert-danger"><fmt:message key="checkout.error.card.number"/></div>
                                </c:if>
                            </div>
                            <div class="col-md-3">
                                <label><fmt:message key="checkout.cvv"/></label>
                                <input class="form-control" type="password" name="security-code" maxlength="4">
                                <c:if test="${isWrongSecurityCode}">
                                    <div class="alert alert-danger"><fmt:message key="checkout.error.security.code"/></div>
                                </c:if>
                            </div>
                            <div class="col-md-12">
                                <label><fmt:message key="checkout.name"/></label>
                                <input class="form-control" type="text"
                                       name="card-holder" placeholder="<fmt:message key="checkout.name.placeholder"/>">
                                <c:if test="${isWrongCardHolder}">
                                    <div class="alert alert-danger"><fmt:message key="checkout.error.name"/></div>
                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <label><fmt:message key="checkout.date"/></label>
                                <input class="form-control" type="month" name="date">
                                <c:if test="${isWrongDate}">
                                    <div class="alert alert-danger"><fmt:message key="checkout.error.date"/></div>
                                </c:if>
                            </div>

                            <h2 class="col-md-12"><fmt:message key="checkout.address.info"/></h2>
                            <div class="col-md-12">
                                <label><fmt:message key="checkout.address"/></label>
                                <input class="form-control" type="text" name="address"
                                       placeholder="<fmt:message key="checkout.address.placeholder"/>">
                                <c:if test="${isWrongAddress}">
                                    <div class="alert alert-danger"><fmt:message key="checkout.error.address"/></div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="cart-page-inner">
                    <div class="col-md-12">
                        <div class="cart-summary">
                            <div class="cart-content">
                                <h1><fmt:message key="cart.summary"/></h1>
                                <p><fmt:message key="cart.count"/><span> ${currentShoppingCart.totalCount}</span></p>
                                <p><fmt:message key="cart.cost"/><span> $${currentShoppingCart.totalCost}</span></p>
                            </div>
                            <div>
                                <button><fmt:message key="checkout.pay"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
</div>
<!-- Checkout End -->
</fmt:bundle>