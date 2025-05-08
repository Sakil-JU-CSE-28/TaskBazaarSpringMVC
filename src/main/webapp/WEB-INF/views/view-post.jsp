<%--
  Created by IntelliJ IDEA.
  User: sakil
  Date: 4/20/25
  Time: 4:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>View Post</title>
</head>
<body>
<h1>${post.get().id}</h1>
<p>${post.get().content}</p>
<a href="${pageContext.request.contextPath}/posts/edit/${post.get().id}">Edit</a> |
<a href="${pageContext.request.contextPath}/posts">Back to List</a>
</body>
</html>