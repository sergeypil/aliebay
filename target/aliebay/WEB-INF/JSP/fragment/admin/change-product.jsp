<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="login d-flex justify-content-center">
        <div class="col-lg-4">
            <div class="register-form">
                <form action="change-product" method="post" enctype="multipart/form-data">
                    <div class="col-md-8">
                        <label><fmt:message key="admin.product.name"/></label>
                        <input class="form-control" type="text" name="product-name" value="${editedProduct.name}"
                               maxlength="45">
                    </div>
                    <div class="col-md-12">
                        <label><fmt:message key="admin.product.description"/></label>
                        <textarea class="form-control" name="description" cols="100" rows="10"
                                  maxlength="45">${editedProduct.description}</textarea>
                    </div>
                    <div class="col-md-8">
                        <label><fmt:message key="admin.product.price"/></label>
                        <input class="form-control floatNumberField" type="number" step="0.01" name="price" lang="en-US"
                               placeholder="0.00" maxlength="45" value="${editedProduct.price}">
                    </div>
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
                                        <c:if test="${producer.id==editedProduct.producer.id}">
                                            selected="selected"
                                        </c:if>
                                >${producer.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-8">
                        <label><fmt:message key="count"/></label>
                        <input class="form-control" type="number" name="count"
                               maxlength="45" value="${editedProduct.count}">
                    </div>
                    <div class="col-md-8">
                        <label for="file"><fmt:message key="image"/></label>
                        <input class="form-control" type="file" name="image" id="file">
                    </div>
                    <div class="col-md-12">
                        <button class="btn" type="submit"><fmt:message key="admin.edit.save.changes"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</fmt:bundle>