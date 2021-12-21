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
                    <c:when test="${action=='change-category'}">
                        <fmt:message key="header.change.category"/> ${langToCategory[(1).intValue()].id}
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="header.add.category"/>
                    </c:otherwise>
                </c:choose>
            </h1>
            <div class="register-form">
                <form action="${action}" method="post" enctype="multipart/form-data">
                    <div class="col-md-10">
                        <label for="file"><fmt:message key="image"/></label>
                        <input class="form-control" type="file" name="image" id="file">
                    </div>
                    <c:if test="${isEmptyImage}">
                        <div class="alert alert-danger"><fmt:message key="product.error.empty.image"/></div>
                    </c:if>
                    <c:if test="${isWrongImage}">
                        <div class="alert alert-danger"><fmt:message key="product.error.image"/></div>
                    </c:if>
                    <div class="col-md-8 margin-top-bottom">
                        <label><fmt:message key="admin.add.category.parent"/></label>
                        <select name="parent-category">
                            <option value="0">
                                <fmt:message key="admin.add.category.parent.id"/>
                            </option>
                            <c:forEach var="category" items="${allCategories}">
                                <option value="${category.id}"
                                        <c:if test="${category.id==langToCategory[(1).intValue()].parentCategoryId}">
                                            selected="selected"
                                        </c:if>
                                >${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-8">
                        <c:forEach var="language" items="${appLanguages}">
                            <label><fmt:message key="admin.add.category.name"/> ${language.name}</label>
                            <input class="form-control" type="text" name="category-lang-${language.id}"
                            <c:forEach var="entry" items="${langToCategory}">
                            <c:if test="${language.id==entry.key}">
                                   value="${entry.value.name}"
                            </c:if>
                            </c:forEach>
                            >
                            <c:if test="${langToWrongName[language.id]}">
                                <div class="alert alert-danger"><fmt:message key="category.error.name"/></div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="col-md-12">
                        <c:choose>
                            <c:when test="${action=='change-category'}">
                                <button class="btn" type="submit"><fmt:message key="admin.edit.save.changes"/></button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn" type="submit"><fmt:message key="admin.add.category.save"/></button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form>
            </div>
        </div>
    </div>
</fmt:bundle>