<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <h1 class="d-flex justify-content-center page-header"><fmt:message key="header.producers"/></h1>
    <div class="table-responsive cart-page">
        <table class="table table-bordered">
            <thead class="thead-dark">
            <tr>
                <th><fmt:message key="admin.producer.id"/></th>
                <th><fmt:message key="admin.producer.name"/></th>
                <th><fmt:message key="actions"/></th>

            </thead>
            <tbody>
            <c:forEach var="producer" items="${allProducers}">
                <tr>
                    <td>${producer.id}</td>
                    <td>${producer.name}</td>
                    <td>
                        <div>
                            <a href="${hostName}/main/change-producer-page?id=${producer.id}">
                                <i class="fa fa-edit"></i>
                                <fmt:message key="admin.edit"/>
                            </a>
                        </div>
                        <div>
                            <a href="${hostName}/main/remove-producer?id=${producer.id}">
                                <i class="fa fa-times"></i>
                                <fmt:message key="admin.remove"/>
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</fmt:bundle>