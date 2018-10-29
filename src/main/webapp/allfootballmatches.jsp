<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <title>All football matches</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
</body>
<table>
    <tr>
        <th scope="col">Description    </th>
        <th scope="col">1    </th>
        <th scope="col">2    </th>
        <th scope="col">X    </th>
    </tr>
<c:if test="${not empty events}">
    <c:forEach items="${events}" var="event">
        <tr>
            <th scope="col">${event.description}    </th>
            <c:forEach items="${event.resultList}" var="result">
                <th scope="col"><a href="${contextPath}/bets/create?event_result_id=${result.eventResultId}&bet_value=10.0">${result.coefficient}    </a></th>
            </c:forEach>
        </tr>
    </c:forEach>
</c:if>
</table>
</html>