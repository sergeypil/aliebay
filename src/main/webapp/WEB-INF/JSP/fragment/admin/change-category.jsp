<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="login d-flex justify-content-center">
        <div class="col-lg-4">
            <div class="register-form">
                <form action="change-category" method="post" enctype="multipart/form-data">
                    <div class="col-md-8">
                        <label for="file"><fmt:message key="image"/></label>
                        <input class="form-control" type="file" name="image" id="file">
                    </div>
                    <div class="col-md-8 margin-top-bottom">
                        <label><fmt:message key="admin.add.category.parent"/></label>
                        <select name="parent-category">
                            <c:forEach var="category" items="${allCategories}">
                                <option value="${category.id}"
                                        <c:if test="${category.id==editedCategories[0].id}">
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
                            <c:forEach var="category" items="${editedCategories}">
                            <c:if test="${language.id==category.language.id}">
                                value="${category.name}"
                            </c:if>
                            </c:forEach>
                             >
                        </c:forEach>
                    </div>
                    <div class="col-md-12">
                        <button class="btn" type="submit"><fmt:message key="account.save.changes"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</fmt:bundle>