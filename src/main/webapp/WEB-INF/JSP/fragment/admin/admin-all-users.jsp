<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <c:forEach var="entry" items="${roleToListOfUsers}">
        <h1 class="d-flex justify-content-center page-header">
            <c:choose>
                <c:when test="${entry.key=='admin'}">
                    <fmt:message key="header.admins"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="header.customers"/>
                </c:otherwise>
            </c:choose>
        </h1>
        <div class="table-responsive cart-page">
            <table class="table table-bordered">
                <thead class="thead-dark">
                <tr>
                    <th><fmt:message key="admin.user.id"/></th>
                    <th><fmt:message key="account.status"/></th>
                    <th><fmt:message key="admin.user.username"/></th>
                    <th><fmt:message key="register.first.name"/></th>
                    <th><fmt:message key="register.last.name"/></th>
                    <th><fmt:message key="register.date"/></th>
                    <th><fmt:message key="register.phone"/></th>
                    <th><fmt:message key="admin.user.email"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${entry.value}">
                    <tr>
                        <td>${user.id}</td>
                        <c:if test="${entry.key=='admin'}">
                            <td>${user.status.name}</td>
                        </c:if>
                        <c:if test="${entry.key=='customer'}">
                            <td>
                                <div class="nav-item ">
                                    <a href="#" class="nav-link dropdown-toggle" id="user-status${user.id}"
                                       data-toggle="dropdown">${user.status.name}</a>
                                    <div class="dropdown-menu">
                                        <c:forEach var="userStatus" items="${userStatuses}">
                                            <a href="#"
                                               data-url-change-user-status="${hostName}/main/ajax/change-user-status"
                                               data-id-user="${user.id}"
                                               data-id-user-status="${userStatus.id}"
                                               class="change-user-status dropdown-item">${userStatus.name}</a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </td>
                        </c:if>
                        <td>${user.username}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.birthDate}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.email}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:forEach>
</fmt:bundle>