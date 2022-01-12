<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="product-view">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-8">
                    <div class="row">
                        <c:if test="${fn:length(requestScope.products) != 0}">
                            <div class="col-md-12">
                                <div class="product-view-top">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="product-price-range">
                                                <div class="dropdown">
                                                    <div class="dropdown-toggle" data-toggle="dropdown"><fmt:message
                                                            key="sort"/>
                                                    </div>
                                                    <div class="dropdown-menu dropdown-menu-right">
                                                        <a href="${requestScope.hostName}/main/products?sort=price&search=${requestScope.searchParam}&id-category=${requestScope.idCategoryParam}&id-producer=${requestScope.idProducerParam}"
                                                           class="dropdown-item"><fmt:message key="sort.price"/> </a>
                                                        <a href="${requestScope.hostName}/main/products?search=${requestScope.searchParam}&id-category=${requestScope.idCategoryParam}&id-producer=${requestScope.idProducerParam}"
                                                           class="dropdown-item"><fmt:message key="sort.new"/></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:forEach var="product" items="${requestScope.products}">
                            <div class="col-md-4">
                                <div class="product-item">
                                    <div class="product-title">
                                        <a href="${requestScope.hostName}/main/product?id=${product.id}">
                                            <c:if test="${fn:length(product.name) > 20}">
                                                ${fn:substring(product.name, 0, 20)}...
                                            </c:if>
                                            <c:if test="${fn:length(product.name) <= 20}">
                                                ${product.name}
                                            </c:if>
                                        </a>
                                    </div>
                                    <div class="product-image">
                                        <a href="${requestScope.hostName}/main/product?id=${product.id}">
                                            <img src="<c:url value="/media/white-square.png"/>"/>
                                            <img class="img-inner" src="${product.image}" alt="Product Image">
                                        </a>
                                    </div>
                                    <div class="product-price">
                                        <p>$<fmt:formatNumber value="${product.price}"/></p>
                                        <a class="btn add-to-cart"
                                           href="${requestScope.hostName}/main/product?id=${product.id}">
                                            <fmt:message key="product.view"/></a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- Pagination Start -->
                    <c:if test="${fn:length(requestScope.products) == 0}">
                        <div class="product-view-top">
                            <h1 class="d-flex justify-content-center page-header"><fmt:message key="no.products"/></h1>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(requestScope.products) != 0}">
                        <div class="col-md-12">
                            <nav aria-label="Page navigation example">
                                <ul class="pagination justify-content-center">
                                    <li class="page-item"
                                            <c:if test="${requestScope.numberOfPage == 1}">
                                                style="pointer-events: none; opacity: 0.6"
                                            </c:if>>
                                        <a class="page-link"
                                           href="${requestScope.hostName}/main/products?number-of-page=1&sort=${requestScope.sortParam}&search=${requestScope.searchParam}&id-category=${requestScope.idCategoryParam}&id-producer=${requestScope.idProducerParam}">
                                            <fmt:message key="start"/>
                                        </a>
                                    </li>
                                    <li class="page-item"
                                            <c:if test="${requestScope.numberOfPage == 1}">
                                                style="pointer-events: none; opacity: 0.6"
                                            </c:if>>
                                        <a class="page-link"
                                           href="${requestScope.hostName}/main/products?number-of-page=${requestScope.numberOfPage - 1}&sort=${requestScope.sortParam}&search=${requestScope.searchParam}&id-category=${requestScope.idCategoryParam}&id-producer=${requestScope.idProducerParam}"
                                           tabindex="-1"><i class="fa fa-angle-left"></i></a>
                                    </li>

                                    <c:forEach begin="${requestScope.startPage}" end="${requestScope.endPage}" var="i">
                                        <li class="page-item
                                  <c:if test="${requestScope.numberOfPage == i}">
                                  active
                                 </c:if>
                                "><a class="page-link"
                                     href="${requestScope.hostName}/main/products?number-of-page=${i}&sort=${requestScope.sortParam}&search=${requestScope.searchParam}&id-category=${requestScope.idCategoryParam}&id-producer=${requestScope.idProducerParam}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <li class="page-item"
                                            <c:if test="${requestScope.numberOfPage == requestScope.endPage}">
                                                style="pointer-events: none; opacity: 0.6"
                                            </c:if>>
                                        <a class="page-link"
                                           href="${requestScope.hostName}/main/products?number-of-page=${requestScope.numberOfPage + 1}&sort=${requestScope.sortParam}&search=${requestScope.searchParam}&id-category=${requestScope.idCategoryParam}&id-producer=${requestScope.idProducerParam}"><i
                                                class="fa fa-angle-right"></i></a>
                                    </li>
                                    <li class="page-item"
                                            <c:if test="${requestScope.numberOfPage == requestScope.endPage}">
                                                style="pointer-events: none; opacity: 0.6"
                                            </c:if>>
                                        <a class="page-link"
                                           href="${requestScope.hostName}/main/products?number-of-page=${requestScope.endPage}&sort=${requestScope.sortParam}&search=${requestScope.searchParam}&id-category=${requestScope.idCategoryParam}&id-producer=${requestScope.idProducerParam}">
                                            <fmt:message key="end"/>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </c:if>
                    <!-- Pagination Start -->
                </div>
                <!-- Side Bar Start -->
                <div class="col-lg-4 sidebar">
                    <div class="sidebar-widget category">
                        <h2 class="title"><fmt:message key="product.categories"/></h2>
                        <nav class="navbar bg-light">
                            <ul class="navbar-nav">
                                <c:forEach var="category" items="${categoriesOnRightPanel}">
                                    <li class="nav-item">
                                        <a class="nav-link"
                                           href="${requestScope.hostName}/main/products?id-category=${category.id}">
                                            <img src="${category.image}"
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
                                        <a href="${requestScope.hostName}/main/product?id=${product.id}">${product.name}</a>
                                    </div>
                                    <div class="product-image">
                                        <a href="${requestScope.hostName}/main/product?id=${product.id}">
                                            <img src="<c:url value="/media/white-square.png"/>"/>
                                            <img class="img-inner" src="${product.image}" alt="Product Image">
                                        </a>
                                    </div>
                                    <div class="product-price">
                                        <h3>$<fmt:formatNumber value="${product.price}"/></h3>
                                        <a class="btn add-to-cart"
                                           href="${requestScope.hostName}/main/product?id=${product.id}">
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
                                <li>
                                    <a href="${requestScope.hostName}/main/products?id-producer=${producer.id}">${producer.name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <!-- Side Bar End -->
            </div>
        </div>
    </div>
</fmt:bundle>