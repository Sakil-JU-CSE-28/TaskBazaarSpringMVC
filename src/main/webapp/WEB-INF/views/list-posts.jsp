<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: sakil
  Date: 4/20/25
  Time: 4:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.PostDto" %>
<html>
<head>
    <title>All Posts</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 20px;
        }

        .header {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            color: white;
            padding: 2rem;
            margin-bottom: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .post-table {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            overflow: hidden;
        }

        .action-btns .btn {
            margin: 0 3px;
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
        }

        .add-btn {
            margin-bottom: 20px;
        }

        .table th {
            background-color: #f1f3fa;
            color: #495057;
            font-weight: 600;
        }

        .table-hover tbody tr:hover {
            background-color: rgba(106, 17, 203, 0.05);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header text-center">
        <h1><i class="fas fa-blog me-2"></i> TaskBazaar Posts</h1>
    </div>

    <sec:authorize access="hasRole('BUYER')">
        <a href="${pageContext.request.contextPath}/posts/add" class="btn btn-primary add-btn">
            <i class="fas fa-plus-circle me-2"></i>Add New Post
        </a>
    </sec:authorize>

    <div class="post-table">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Title</th>
                <th scope="col" class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<PostDto> posts = (List<PostDto>) request.getAttribute("posts");
                if (posts != null) {
                    for (PostDto post : posts) {
            %>
            <tr>
                <td><%= post.getId() %>
                </td>
                <td><%= post.getTitle() %>
                </td>
                <td class="text-end action-btns">
                    <a href="${pageContext.request.contextPath}/posts/get/<%= post.getId() %>"
                       class="btn btn-sm btn-info">
                        <i class="fas fa-eye"></i> View
                    </a>
                    <sec:authorize access="(hasRole('BUYER'))">
                        <a href="${pageContext.request.contextPath}/posts/edit/<%= post.getId() %>"
                           class="btn btn-sm btn-warning">
                            <i class="fas fa-edit"></i> Edit
                        </a>
                    </sec:authorize>
                    <sec:authorize access="(hasRole('ADMIN'))">
                        <a href="${pageContext.request.contextPath}/posts/delete/<%= post.getId() %>"
                           class="btn btn-sm btn-danger">
                            <i class="fas fa-trash-alt"></i> Delete
                        </a>
                    </sec:authorize>
                    <sec:authorize access="(hasRole('FREELANCER'))">
                        <a href="/bids/add/<%= post.getId()%>/<sec:authentication property="principal.username"/>"
                           class="btn btn-sm btn-info">
                            <i class="fas fa-edit"></i> Bid
                        </a>
                    </sec:authorize>
                    <sec:authorize access="(hasRole('BUYER'))">
                        <a href="/bids/getAll/<%= post.getId()%>"
                           class="btn btn-sm btn-info">
                            <i class="fas fa-edit"></i> Bidders
                        </a>
                    </sec:authorize>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="3" class="text-center text-muted">No posts found</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <sec:authorize access="isAuthenticated()">
        <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
            <p>
                Logged in as: <strong><sec:authentication property="principal.username"/></strong>
                <br>
                Roles:
                <strong>
                    <c:forEach var="authority" items="${pageContext.request.userPrincipal.authorities}">
                        ${authority.authority.replace('ROLE_', '')}<c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </strong>
            </p>
            <button type="submit" class="btn btn-sm btn-outline-danger">
                <i class="fas fa-sign-out-alt"></i> Logout
            </button>
        </form>
    </sec:authorize>
</div>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
</c:if>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>

<!-- Bootstrap 5 JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>