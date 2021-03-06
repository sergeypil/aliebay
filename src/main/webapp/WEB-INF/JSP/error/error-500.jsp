<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:bundle basename="pagecontent"  prefix="error.">
    <!DOCTYPE html>

    <head>
        <title>AlieBay - online store</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Pet project online-shop AlieBay" name="description">

        <!-- Favicon -->
        <link href="<c:url value="/media/favicon.png"/>" rel="icon">

        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400|Source+Code+Pro:700,900&display=swap" rel="stylesheet">

        <!-- CSS Libraries -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="<c:url value="/static/lib/slick/slick.css"/>" rel="stylesheet">
        <link href="<c:url value="/static/lib/slick/slick-theme.css"/>" rel="stylesheet">

        <!-- Local Stylesheet -->
        <link href="<c:url value="/static/css/style.css"/>" rel="stylesheet">
    </head>
    <body>
    <jsp:include page="../fragment/nav-bar.jsp" />
    <jsp:include page="../fragment/bottom-bar.jsp" />
    <div class="header">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="error-template">
                        <h1><fmt:message key="oops"/></h1>
                        <h2><fmt:message key="500.title"/></h2>
                        <div class="error-details">
                            <fmt:message key="500.message"/>
                        </div>
                        <div class="error-actions">
                            <a href="<c:url value="/"/>" class="btn btn-primary btn-lg margin-top-bottom">
                                <span class="glyphicon glyphicon-home"></span> <fmt:message key="button.takeMeHome"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer class="footer">
        <jsp:include page="../fragment/footer.jsp" />
        <jsp:include page="../fragment/footer-bar.jsp" />
    </footer>

    <!-- JavaScript Libraries -->
    <script src="<c:url value="/static/lib/jquery-3.4.1.min.js"/>"></script>
    <script src="<c:url value="/static/lib/bootstrap.bundle.min.js"/>"></script>
    <script src="<c:url value="/static/lib/easing/easing.min.js"/>"></script>
    <script src="<c:url value="/static/lib/slick/slick.min.js"/>"></script>
    <script src="<c:url value="/static/js/app.js"/>"></script>

    <!-- Template Javascript -->
    <script src="<c:url value="/static/js/main.js"/>"></script>
    </body>
</fmt:bundle>