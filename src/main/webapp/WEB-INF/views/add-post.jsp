<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sakil
  Date: 4/20/25
  Time: 4:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Post</title>
</head>
<body>
<h1>Add New Post</h1>
<form action="save" method="post">
    <label>Title:</label><br>
    <c:if test="${not empty errors}">
        <c:if test="${not empty errors['Title']}">
            <span style="color: red">${errors['Title']}</span><br>
        </c:if>
        <c:if test="${not empty errors['Title_len']}">
            <span style="color: red">${errors['Title_len']}</span><br>
        </c:if>
    </c:if>
    <input type="text" name="title" required><br><br>

    <label>Content:</label><br>
    <c:if test="${not empty errors}">
        <c:if test="${not empty errors['Content']}">
            <span style="color: red">${errors['Content']}</span><br>
        </c:if>
        <c:if test="${not empty errors['Content_len']}">
            <span style="color: red">${errors['Content_len']}</span><br>
        </c:if>
    </c:if>
    <textarea name="content" rows="5" cols="50" required></textarea><br><br>

    <input type="submit" value="Save">
</form>
<a href="${pageContext.request.contextPath}/posts">Back to List</a>
</body>
</html>

