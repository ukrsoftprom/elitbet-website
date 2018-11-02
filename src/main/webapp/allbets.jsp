<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <title>All wagers of ${username}</title>
</head>
<body>
<table>
    <tr>
        <th scope="col">Event Description</th>
        <th scope="col">Bet Type</th>
        <th scope="col">Bet Value</th>
        <th scope="col">Bet Status</th>
    </tr>
    <c:if test="${not empty wagers}">
        <c:forEach items="${wagers}" var="wager">
            <tr>
                <th scope="col">${wager.outcome.event.description} </th>
                <th scope="col">${wager.outcome.outcomeType.description} </th>
                <th scope="col">${wager.value} </th>
                <th scope="col">${wager.status} </th>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>

</html>