<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <title>All bets of ${username}</title>
</head>
<body>
<table>
    <tr>
        <th scope="col">Event Description</th>
        <th scope="col">Bet Type</th>
        <th scope="col">Bet Value</th>
        <th scope="col">Bet Status</th>
    </tr>
    <c:if test="${not empty bets}">
        <c:forEach items="${bets}" var="bet">
            <tr>
                <th scope="col">${bet.eventResult.event.description} </th>
                <th scope="col">${bet.eventResult.betType.description} </th>
                <th scope="col">${bet.value} </th>
                <th scope="col">${bet.status} </th>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>

</html>