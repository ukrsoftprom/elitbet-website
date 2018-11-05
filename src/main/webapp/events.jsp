<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize var="isFullyAuth" access="isFullyAuthenticated()"></sec:authorize>
<c:if test="${!isFullyAuth}">
    <c:redirect url="/login"/>
</c:if>
<html>
<head>
    <title>${name} | All events</title>
    <%@include file="includes.jspf" %>
</head>
<body>
<%@include file="menutop.jspf" %>
<div class="container">
    <div class="page">
        <div class="row">
            <div class="hidden-xs hidden-sm col-md-2 col-lg-3">
                <ul class="list-group">
                    <c:forEach items="${sessionScope.tournaments.keySet()}" var="tournamentKey">
                        <li class="list-group-item">
                            <span class="badge">${sessionScope.tournaments.get(tournamentKey)}</span>
                            ${tournamentKey.description}
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
                <table class="table table-striped">
                    <tr>
                        <th>#</th>
                        <th scope="col">Description</th>
                        <th scope="col">1</th>
                        <th scope="col">X</th>
                        <th scope="col">2</th>
                    </tr>
                    <c:if test="${not empty sessionScope.events}">
                        <c:forEach items="${sessionScope.events}" var="event" varStatus="loop">
                            <tr>
                                <td>${loop.index}</td>
                                <td scope="col">${event.statistic.names()} </td>
                                <c:forEach items="${event.outcomeList}" var="outcome">
                                    <td scope="col"><a                                           href="${contextPath}/wagers/add?outcomeId=${outcome.outcomeId}">${outcome.odds} </a>
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
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">${dataMap["home_name"]} - ${dataMap["away_name"]} ${outcome.outcomeType.description}</h3>
                    </div>
                    <div class="panel-body">
                        <form action="/wagers/add" modelAttribute="wager" method="post">
                            <span id="odds">${outcome.odds}</span>
                            <input type="hidden" name="outcomeId" value="${outcome.outcomeId}">
                            <input id="betValue" name="betValue" type="number" placeholder="Bet value" class="form-control"/>
                            <button type="submit" class="button button-success">Bet</button>
                        </form>
                        <div id="earn"><div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $("#betValue").keyup(function(){
            var odds = $("#odds").html();
            var earn = odds*$(this).val();
            $("#earn").html("You will earn " + earn);
        });
    });
</script>
</body>
</html>
