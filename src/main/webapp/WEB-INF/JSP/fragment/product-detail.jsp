<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="product-detail">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-8">
                    <div class="product-detail-top">
                        <div class="row align-items-center">
                            <div class="col-md-5">
                                <img src="${requestScope.product.image}" alt="Product Image" style="width: 300px; height: auto">
                            </div>
                            <div class="col-md-7">
                                <div class="product-content">
                                    <div class="title"><h2>${requestScope.product.name}</h2></div>
                                    <div class="price">
                                        <h4><fmt:message key="price"/>:</h4>
                                        <p>$<fmt:formatNumber value="${requestScope.product.price}"/></p>
                                    </div>
                                    <div class="producer">
                                        <h4><fmt:message key="producer"/>:</h4>
                                        <a href="${requestScope.hostName}/main/products?id-producer=${requestScope.product.producer.id}">${requestScope.product.producer.name}</a>
                                    </div>
                                    <c:if test="${requestScope.availableCountToAddToCart<=0}">
                                        <div class="alert alert-info">
                                            <fmt:message key="product.no.available"/>&nbsp;
                                        </div>
                                    </c:if>
                                    <c:if test="${requestScope.availableCountToAddToCart>0}">
                                        <div class="quantity">
                                            <h4><fmt:message key="count"/>:</h4>
                                            <div class="qty">
                                                <button class="btn-minus"><i class="fa fa-minus"></i></button>
                                                <input id="count-product" type="text" value="1">
                                                <button class="btn-plus" data-count-available="${requestScope.availableCountToAddToCart}">
                                                    <i class="fa fa-plus"></i></button>
                                            </div>
                                        </div>
                                        <div class="action">
                                            <a class="btn add-to-cart" id="btn-add" data-id-product="${requestScope.product.id}"
                                               data-url-add-to-cart="${requestScope.hostName}/main/ajax/add-product-to-cart"
                                               data-count-available="${requestScope.availableCountToAddToCart}">
                                                <i class="fa fa-shopping-cart"></i><fmt:message key="product.add"/></a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row product-detail-bottom">
                        <div class="col-lg-12">
                            <div id="description">
                                <h4><fmt:message key="product.description"/></h4>
                                <p>
                                        ${requestScope.product.description}
                                </p>
                            </div>
                            <div>
                                <p><fmt:message key="product.available"/>&nbsp;${requestScope.product.count}
                                    <fmt:message key="product.pieces"/></p>
                            </div>
                        </div>
                    </div>
                    <div class="product">
                        <div class="section-header">
                            <h1><fmt:message key="product.related"/></h1>
                        </div>
                        <div class="row align-items-center product-slider product-slider-3">
                            <c:forEach var="product" items="${requestScope.products}">
                                <div class="col-md-4">
                                    <div class="product-item">
                                        <div class="product-title">
                                            <a href="${requestScope.hostName}/main/product?id=${requestScope.product.id}">${requestScope.product.name}</a>
                                        </div>
                                        <div class="product-image">
                                            <a href="${requestScope.hostName}/main/product?id=${requestScope.product.id}">
                                                <img src="<c:url value="/media/white-square.png"/>" alt="product"/>
                                                <img class="img-inner" src="${requestScope.product.image}" alt="Product Image">
                                            </a>
                                        </div>
                                        <div class="product-price">
                                            <p>$<fmt:formatNumber value="${requestScope.product.price}"/></p>
                                            <a class="btn add-to-cart" href="${requestScope.hostName}/main/product?id=${requestScope.product.id}">
                                                <fmt:message key="product.view"/></a>

                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <!-- Side Bar Start -->
                <div class="col-lg-4 sidebar">
                    <div class="sidebar-widget category">
                        <h2 class="title"><fmt:message key="product.categories"/></h2>
                        <nav class="navbar bg-light">
                            <ul class="navbar-nav">
                                <c:forEach var="category" items="${categoriesOnRightPanel}">
                                    <li class="nav-item">
                                        <a class="nav-link" href="${requestScope.hostName}/main/products-by-category?id=${category.id}">
                                            <img src="${category.image}" alt="Category image"
                                                 style="width: 20px; height: 20px">&nbsp&nbsp&nbsp${category.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                    <div class="sidebar-widget widget-slider">
                        <div class="sidebar-slider normal-slider">
                            <c:forEach var="product" items="${requestScope.products}">
                                <div class="product-item">
                                    <div class="product-title">
                                        <a href="${requestScope.hostName}/main/product?id=${requestScope.product.id}">${requestScope.product.name}</a>
                                    </div>
                                    <div class="product-image">
                                        <a href="${requestScope.hostName}/main/product?id=${requestScope.product.id}">
                                            <img src="<c:url value="/media/white-square.png"/>"/>
                                            <img class="img-inner" src="${requestScope.product.image}" alt="Product Image">
                                        </a>

                                    </div>
                                    <div class="product-price">
                                        <h3>$<fmt:formatNumber value="${requestScope.product.price}"/></h3>
                                        <a class="btn add-to-cart" href="${requestScope.hostName}/main/product?id=${requestScope.product.id}">
                                            <fmt:message key="product.view"/></a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="sidebar-widget brands">
                        <h2 class="title"><fmt:message key="product.brands"/></h2>
                        <ul>
                            <c:forEach var="producer" items="${producersOnRightPanel}">
                                <li><a href="${requestScope.hostName}/main/products?id-producer=${producer.id}">${producer.name}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <!-- Side Bar End -->
            </div>
        </div>
    </div>
</fmt:bundle>