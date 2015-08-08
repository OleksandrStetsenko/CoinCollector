<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<html>
<head>
    <style>
        table { border-collapse: collapse }
        thead { background: #eee }
        td, th {
            text-aligh: left;
            border: 1px solid #ccc;
            padding: .2em .5em;
        }
        table caption {
            text-align: left;
            font-size: large;
            font-weight: bold;
            margin-bottom: .5em;
        }
        body {
         background: #EEE8AA;
        }
    
    </style>

    <title>Userlist</title>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>

<table>
    <caption><h2>List of users</h2></caption>
    <thead>
        <tr>
            <th>UserID</th>
            <th>Username</th>
            <th>Password</th>
            <th>Admin</th>
            <th colspan=2>Action</th>
        </tr>
    </thead>
    <c:forEach items="${list}" var="user">
        <tr>
            <td><c:out value="${user.userID}"></c:out></td>
            <td><c:out value="${user.username}"></c:out></td>
            <td><c:out value="${user.password}"></c:out></td>
            <td><c:out value="${user.admin}"></c:out></td>
            <td><a href="controller?action=edit&userID=<c:out value="${user.userID}"/>">[Edit]</a></td>
            <td><a href="controller?action=delete&userID=<c:out value="${user.userID}"/>">[Delete]</a></td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="footer.html"></jsp:include>

</body>
</html>