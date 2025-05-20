<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edit Post</title>
</head>
<body>
<h1>Edit Post</h1>
<form:form action="${pageContext.request.contextPath}/posts/update" method="post" modelAttribute="post">
    <form:hidden path="id"/>
<label>Title:</label><br>
    <form:input path="title"/>
    <form:errors path="title" cssClass="error"/><br><br>

<label>Content:</label><br>
    <form:textarea path="content" rows="5" cols="50"/>
    <form:errors path="content" cssClass="error"/><br><br>

<input type="submit" value="Update">
</form:form>
<a href="${pageContext.request.contextPath}/posts">Back to List</a>

<style>
    .error {
        color: red;
    }
</style>
</html>
