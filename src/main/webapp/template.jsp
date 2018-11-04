<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize var="isFullyAuth" access="isFullyAuthenticated()"></sec:authorize>
<c:if test="${!isFullyAuth}">
    <c:redirect url="/login"/>
</c:if>
<html>
<head>
    <title>${username} | Create wager</title>
    <%@include file="includes.jspf"%>
</head>
<body>
<%@include file="menutop.jspf"%>
<div class="container">
    <div class="page">
        <div class="row">
            <div class="hidden-xs hidden-sm col-md-2 col-lg-3">

            </div>
            <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
                <div class="test"></div>
            </div>
            <div class="hidden-xs hidden-sm col-md-2 col-lg-3">

            </div>
        </div>
    </div>
</div>

</body>
</html>
