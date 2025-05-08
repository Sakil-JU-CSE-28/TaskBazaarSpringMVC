<%--
  Created by IntelliJ IDEA.
  User: sakil
  Date: 5/4/25
  Time: 12:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form:form action="${pageContext.request.contextPath}/register" method="post" modelAttribute="user">
  <div class="form-group">
    <label>Username:</label>
    <form:input path="username" class="form-control"/>
    <form:errors path="username" cssClass="text-danger"/>
  </div>

  <div class="form-group">
    <label>Password:</label>
    <form:password path="password" class="form-control"/>
    <form:errors path="password" cssClass="text-danger"/>
  </div>

  <div class="form-group">
    <label>Confirm Password:</label>
    <form:password path="confirmPassword" class="form-control"/>
    <form:errors path="confirmPassword" cssClass="text-danger"/>
  </div>

  <div class="form-group">
    <label>Email:</label>
    <form:input path="email" type="email" class="form-control"/>
    <form:errors path="email" cssClass="text-danger"/>
  </div>

  <div class="form-group">
    <label>Full Name:</label>
    <form:input path="fullName" class="form-control"/>
    <form:errors path="fullName" cssClass="text-danger"/>
  </div>

  <!-- Role Selection Dropdown -->
  <div class="form-group">
    <label>Role:</label>
    <form:select path="role" class="form-control">
      <form:option value="" label="-- Select Role --" disabled="true" selected="true"/>
      <form:options items="${availableRoles}" itemValue="name" itemLabel="name"/>
    </form:select>
    <form:errors path="role" cssClass="text-danger"/>
  </div>

  <button type="submit" class="btn btn-primary">Register</button>
</form:form>

<div class="mt-3">
  <p>Already have an account? <a href="${pageContext.request.contextPath}/login-form">Login here</a></p>
</div>

<c:if test="${not empty successMessage}">
  <div class="alert alert-success">${successMessage}</div>
</c:if>
<c:if test="${not empty errorMessage}">
  <div class="alert alert-danger">${errorMessage}</div>
</c:if>
