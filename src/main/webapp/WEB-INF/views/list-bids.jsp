<%--
  Created by IntelliJ IDEA.
  User: sakil
  Date: 5/5/25
  Time: 6:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Bid List</title>
</head>
<body>
<h2>Bid List</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Post ID</th>
        <th>Bidder</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="bid" items="${bids}">
        <tr>
            <td>${bid.id}</td>
            <td>${bid.postId}</td>
            <td>${bid.bidder}</td>
            <td>
                <c:choose>
                    <c:when test="${bid.accepted}">
                        Accepted
                    </c:when>
                    <c:otherwise>
                        <form action="<c:url value='/bids/${bid.id}/${bid.postId}/accept'/>" method="post">
                            <button type="submit">Accept</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty bids}">
    <p>No bids found.</p>
</c:if>
</body>
</html>
