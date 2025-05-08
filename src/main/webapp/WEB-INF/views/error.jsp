<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sakil
  Date: 4/22/25
  Time: 2:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Something went wrong</title>
</head>
<body>
<c:if test="${not empty requestScope.errorMessage}">
    <div class="alert alert-danger">
            ${requestScope.errorMessage}
    </div>
</c:if>
</body>
</html>
