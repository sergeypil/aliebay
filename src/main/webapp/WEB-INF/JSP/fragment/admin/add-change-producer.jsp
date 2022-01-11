<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="login d-flex justify-content-center">
        <div class="col-lg-4">
            <h1 class="d-flex justify-content-center page-header">
                <c:choose>
                    <c:when test="${requestScope.action=='change-producer'}">
                        <fmt:message key="header.change.producer"/>&nbsp;${sessionScope.editedProducer.id}
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="header.add.producer"/>
                    </c:otherwise>
                </c:choose>
            </h1>
            <div class="register-form">
                <form action="${requestScope.action}" method="post">
                    <div class="col-md-8">
                        <label><fmt:message key="admin.add.producer.name"/><span class="text-danger"> *</span>
                            <input class="form-control" type="text" name="producer-name" value="${sessionScope.editedProducerName}"
                                   maxlength="45">
                        </label>
                        <c:if test="${requestScope.isWrongName}">
                            <div class="alert alert-danger"><fmt:message key="producer.error.name"/></div>
                        </c:if>
                        <c:if test="${requestScope.isProducerNameExist}">
                            <div class="alert alert-danger"><fmt:message key="producer.error.name.exist"/></div>
                        </c:if>
                    </div>
                    <div class="col-md-12">
                        <c:choose>
                            <c:when test="${requestScope.action=='change-producer'}">
                                <button class="btn" type="submit"><fmt:message key="admin.edit.save.changes"/></button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn" type="submit"><fmt:message key="admin.add.producer.save"/></button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form>
            </div>
        </div>
    </div>
</fmt:bundle>