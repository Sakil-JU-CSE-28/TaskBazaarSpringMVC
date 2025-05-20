<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<form:form action="${pageContext.request.contextPath}/posts/save" method="post" modelAttribute="post">
    <label>Title:</label><br>
    <form:input path="title" />
    <form:errors path="title" cssClass="error" /><br><br>

    <label>Content:</label><br>
    <form:textarea path="content" rows="5" cols="50" />
    <form:errors path="content" cssClass="error" /><br><br>

    <input type="submit" value="Save">
</form:form>
<a href="${pageContext.request.contextPath}/posts">Back to List</a>

<style>
    .error {
        color: red;
    }
</style>
</body>
</html>


