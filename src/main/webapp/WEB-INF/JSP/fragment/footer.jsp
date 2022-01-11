<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent" prefix="footer.">
    <div class="footer">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="footer-widget">
                        <h2><fmt:message key="contacts"/></h2>
                        <div class="contact-info">
                            <p><i class="fa fa-map-marker"></i>AliEbay, Karaganda</p>
                            <p><i class="fa fa-envelope"></i>aliebay@gmail.com</p>
                            <p><i class="fa fa-phone"></i>+7(777) 777-77-77</p>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6">
                    <div class="footer-widget">
                        <h2><fmt:message key="follow"/></h2>
                        <div class="contact-info">
                            <div class="social">
                                <a href="https://twitter.com/" target="_blank"><i class="fab fa-twitter"></i></a>
                                <a href="https://www.facebook.com/" target="_blank"><i
                                        class="fab fa-facebook-f"></i></a>
                                <a href="https://vk.com/" target="_blank"><i class="fab fa-vk"></i></a>
                                <a href="https://www.instagram.com/" target="_blank"><i
                                        class="fab fa-instagram"></i></a>
                                <a href="https://www.youtube.com/" target="_blank"><i class="fab fa-youtube"></i></a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6">
                    <div class="footer-widget">
                        <h2><fmt:message key="company.info"/></h2>
                        <ul>
                            <li><a href="${requestScope.hostName}/main/about-company"><fmt:message
                                    key="company.about"/></a></li>
                        </ul>
                    </div>
                </div>

                <div class="col-lg-3 col-md-6">
                    <div class="footer-widget">
                        <h2><fmt:message key="purchase.info"/></h2>
                        <ul>
                            <li><a href="${requestScope.hostName}/main/purchase-policy"><fmt:message
                                    key="purchase.policy"/></a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="row payment align-items-center">
                <div class="col-md-6">
                    <div class="payment-method">
                        <h2><fmt:message key="accept"/></h2>
                        <img src="${requestScope.contextPath}/media/payment-method.png" alt="Payment Method"/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="payment-security">
                        <h2><fmt:message key="secure"/></h2>
                        <img src="${requestScope.contextPath}/media/norton.png" alt="Payment Security"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</fmt:bundle>
