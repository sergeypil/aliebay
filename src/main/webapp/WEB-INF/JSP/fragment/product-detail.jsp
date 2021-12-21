<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 20.11.2021
  Time: 1:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="product-detail">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-8">
                    <div class="product-detail-top">
                        <div class="row align-items-center">
                            <div class="col-md-5">
                                <img src="${product.image}" alt="Product Image" style="width: 300px; height: auto">
                            </div>
                            <div class="col-md-7">
                                <div class="product-content">
                                    <div class="title"><h2>${product.name}</h2></div>
                                    <div class="price">
                                        <h4><fmt:message key="price"/>:</h4>
                                        <p>$${product.price}</p>
                                    </div>
                                    <div class="producer">
                                        <h4><fmt:message key="producer"/>:</h4>
                                        <a href="${hostName}/main/products?id-producer=${product.producer.id}">${product.producer.name}</a>
                                    </div>
                                    <c:if test="${product.count<=0}">
                                        <div class="alert alert-info">
                                            <fmt:message key="product.no.available"/>
                                        </div>
                                    </c:if>
                                    <c:if test="${product.count>0}">
                                        <div class="quantity">
                                            <h4><fmt:message key="count"/>:</h4>
                                            <div class="qty">
                                                <button class="btn-minus"><i class="fa fa-minus"></i></button>
                                                <input id="count-product" type="text" value="1">
                                                <button class="btn-plus" data-count-available="${product.count}">
                                                    <i class="fa fa-plus"></i></button>
                                            </div>
                                        </div>
                                        <div class="action">
                                            <a class="btn add-to-cart" data-id-product="${product.id}"
                                               data-url-add-to-cart="${hostName}/main/ajax/add-product-to-cart"
                                               data-count-available="${product.count}">
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
                                        ${product.description}
                                </p>
                            </div>
                            <div>
                                <p><fmt:message key="product.available"/>${product.count}
                                    <fmt:message key="product.pieces"/></p>
                            </div>
                        </div>
                    </div>
                    <div class="product">
                        <div class="section-header">
                            <h1><fmt:message key="product.related"/></h1>
                        </div>
                        <div class="row align-items-center product-slider product-slider-3">
                            <c:forEach var="product" items="${products}">
                                <div class="col-md-4">
                                    <div class="product-item">
                                        <div class="product-title">
                                            <a href="${hostName}/main/product?id=${product.id}">${product.name}</a>
                                        </div>
                                        <div class="product-image">
                                            <a href="${hostName}/main/product?id=${product.id}">
                                                <img src="<c:url value="/media/white-square.png"/>"/>
                                                <img class="img-inner" src="${product.image}" alt="Product Image">
                                            </a>
                                        </div>
                                        <div class="product-price">
                                            <h3>$${product.price}</h3>
                                            <a class="btn add-to-cart" href="${hostName}/main/product?id=${product.id}">
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
                                        <a class="nav-link" href="${hostName}/main/products-by-category?id=${category.id}">
                                            <img src="${category.image}"
                                                 style="width: 20px; height: 20px">&nbsp&nbsp&nbsp${category.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                    <div class="sidebar-widget widget-slider">
                        <div class="sidebar-slider normal-slider">
                            <c:forEach var="product" items="${products}">
                                <div class="product-item">
                                    <div class="product-title">
                                        <a href="${hostName}/main/product?id=${product.id}">${product.name}</a>
                                    </div>
                                    <div class="product-image">
                                        <a href="${hostName}/main/product?id=${product.id}">
                                            <img src="<c:url value="/media/white-square.png"/>"/>
                                            <img class="img-inner" src="${product.image}" alt="Product Image">
                                        </a>

                                    </div>
                                    <div class="product-price">
                                        <h3>$${product.price}</h3>
                                        <a class="btn add-to-cart" href="${hostName}/main/product?id=${product.id}">
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
                                <li><a href="${hostName}/main/products-by-producer?id=${producer.id}">${producer.name}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <!-- Side Bar End -->
            </div>
        </div>
    </div>
</fmt:bundle>