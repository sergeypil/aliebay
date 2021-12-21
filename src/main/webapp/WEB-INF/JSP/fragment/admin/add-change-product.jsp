<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="login d-flex justify-content-center">
        <div class="col-lg-4">
            <h1 class="d-flex justify-content-center page-header">
                <c:choose>
                    <c:when test="${action=='change-product'}">
                        <fmt:message key="header.change.product"/> ${editedProduct.id}
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="header.add.product"/>
                    </c:otherwise>
                </c:choose>
            </h1>
            <div class="register-form">
                <form action="${action}" method="post" enctype="multipart/form-data">
                    <div class="col-md-8">
                        <label><fmt:message key="admin.product.name"/></label>
                        <input class="form-control" type="text" name="product-name" value="${editedProduct.name}"
                               maxlength="45">
                    </div>
                    <c:if test="${isWrongName}">
                        <div class="alert alert-danger"><fmt:message key="product.error.name"/></div>
                    </c:if>
                    <div class="col-md-12">
                        <label><fmt:message key="admin.product.description"/></label>
                        <textarea class="form-control" name="description" cols="100"
                                  rows="10">${editedProduct.description}</textarea>
                    </div>
                    <c:if test="${isWrongDescription==true}">
                        <div class="alert alert-danger"><fmt:message key="product.error.description"/></div>
                    </c:if>
                    <div class="col-md-8">
                        <label><fmt:message key="admin.product.price"/></label>
                        <input class="form-control floatNumberField" type="number" step="0.01" name="price" lang="en-US"
                               placeholder="0.00" maxlength="6" value="${editedProduct.price}">
                    </div>
                    <c:if test="${isWrongPrice==true}">
                        <div class="alert alert-danger"><fmt:message key="product.error.price"/></div>
                    </c:if>
                    <div class="col-md-8">
                        <label><fmt:message key="category"/></label>
                        <select name="category">
                            <c:forEach var="category" items="${leafCategories}">
                                <option value="${category.id}"
                                        <c:if test="${category.id==editedProduct.category.id}">
                                            selected="selected"
                                        </c:if>
                                >${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-8 margin-top-bottom">
                        <div>
                            <label><fmt:message key="producer"/></label>
                        </div>
                        <select name="producer">
                            <c:forEach var="producer" items="${allProducers}">
                                <option value="${producer.id}"
                                >${producer.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-8">
                        <label><fmt:message key="count"/></label>
                        <input class="form-control" type="number" name="count" value="${editedProduct.count}"
                               maxlength="9">
                    </div>
                    <c:if test="${isWrongCount==true}">
                        <div class="alert alert-danger"><fmt:message key="product.error.count"/></div>
                    </c:if>
                    <div class="col-md-8">
                        <label for="file"><fmt:message key="image"/></label>
                        <input class="form-control" type="file" name="image" id="file">
                    </div>
                    <c:if test="${isEmptyImage==true}">
                        <div class="alert alert-danger"><fmt:message key="product.error.empty.image"/></div>
                    </c:if>
                    <c:if test="${isWrongImage==true}">
                        <div class="alert alert-danger"><fmt:message key="product.error.image"/></div>
                    </c:if>
                    <div class="col-md-12">
                        <c:choose>
                            <c:when test="${action=='change-product'}">
                                <button class="btn" type="submit"><fmt:message key="admin.edit.save.changes"/></button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn" type="submit"><fmt:message key="admin.product.save"/></button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form>
            </div>
        </div>
    </div>
</fmt:bundle>