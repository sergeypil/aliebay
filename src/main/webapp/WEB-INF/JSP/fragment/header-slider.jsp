<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent" prefix="slider.">
    <div class="col-md-6">
        <div class="header-slider normal-slider">
            <div class="header-slider-item">
                <img src="${requestScope.contextPath}/media/games_main.jpg" alt="Slider Image"
                     style="width: 800px; height: 400px "/>
                <div class="header-slider-caption">
                    <p><fmt:message key="ad1"/></p>
                    <a class="btn" href="${requestScope.hostName}/main/products"><i class="fa fa-shopping-cart"></i><fmt:message
                            key="shop.button"/></a>
                </div>
            </div>
            <div class="header-slider-item">
                <img src="${requestScope.contextPath}/media/online_shopping.jpg" alt="Slider Image"
                     style="width: 800px; height: 400px "/>
                <div class="header-slider-caption">
                    <p><fmt:message key="ad2"/></p>
                    <a class="btn" href="${requestScope.hostName}/main/products"><i class="fa fa-shopping-cart"></i><fmt:message
                            key="shop.button"/></a>
                </div>
            </div>
            <div class="header-slider-item">
                <img src="${requestScope.contextPath}/media/laptops_main.jpg" alt="Slider Image"
                     style="width: 800px; height: 400px "/>
                <div class="header-slider-caption">
                    <p><fmt:message key="ad3"/></p>
                    <a class="btn" href="${requestScope.hostName}/main/products"><i
                            class="fa fa-shopping-cart"></i><fmt:message key="shop.button"/></a>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="header-img">
            <div class="img-item">
                <img src="${requestScope.contextPath}/media/epam.jpg" alt="epam"/>
                <a class="img-text" href="">
                    <p><fmt:message key="ad4"/></p>
                </a>
            </div>
            <div class="img-item">
                <img src="${requestScope.contextPath}/media/java.jpg" alt="java"/>
                <a class="img-text" href="">
                    <p><fmt:message key="ad5"/></p>
                </a>
            </div>
        </div>
    </div>
</fmt:bundle>
