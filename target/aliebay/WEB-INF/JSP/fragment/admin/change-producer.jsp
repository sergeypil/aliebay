<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${currentLocale}"/>
<fmt:bundle basename="pagecontent">
    <div class="login d-flex justify-content-center">
        <div class="col-lg-4">
            <div class="register-form">
                <form action="change-producer" method="post">
                    <div class="col-md-8">
                        <label><fmt:message key="admin.add.producer.name"/></label>
                        <input class="form-control" type="text" name="producer-name" value="${editedProducer.name}"
                               maxlength="45">
                    </div>
                    <div class="col-md-12">
                        <button class="btn" type="submit"><fmt:message key="admin.edit.save.changes"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</fmt:bundle>