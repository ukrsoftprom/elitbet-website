<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize var="isFullyAuth" access="isFullyAuthenticated()"></sec:authorize>
<c:if test="${!isFullyAuth}">
    <c:redirect url="/login"/>
</c:if>
<html>
<head>
    <title>${username} | All events</title>
    <%@include file="includes.jspf" %>
</head>
<body>
<%@include file="menutop.jspf" %>
<div class="container">
    <div class="page">
        <div class="row">
            <div class="hidden-xs hidden-sm col-md-2 col-lg-3">
                <ul class="list-group">
                    <c:forEach items="${tournaments}" var="tournament">
                        <li class="list-group-item">
                            <span class="badge">1</span>
                            ${tournament.description}
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
                <table class="table table-striped">
                    <tr>
                        <th scope="col">Description</th>
                        <th scope="col">1</th>
                        <th scope="col">2</th>
                        <th scope="col">X</th>
                    </tr>
                    <c:if test="${not empty events}">
                        <c:forEach items="${events}" var="event">
                            <tr>
                                <th scope="col">${event.description} </th>
                                <c:forEach items="${event.outcomeList}" var="outcome">
                                    <td scope="col"><a                                           href="${contextPath}/wagers/create?event_result_id=${outcome.outcomeId}&bet_value=10.0">${outcome.odds} </a>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li><a href="/events?page=1&size=20">1</a></li>
                        <li><a href="/events?page=2&size=20">2</a></li>
                        <li><a href="/events?page=3&size=20">3</a></li>
                        <li><a href="/events?page=4&size=20">4</a></li>
                        <li><a href="/events?page=5&size=20">5</a></li>
                        <li>
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="hidden-xs hidden-sm col-md-2 col-lg-3">

            </div>
        </div>
    </div>
</div>

</body>
</html>
