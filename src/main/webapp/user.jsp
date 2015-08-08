<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>User Page</title>
    <style>
        body {
         background: #EEE8AA;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>

<section class="container">
    <div class="edit">
        <h1>User information</h1>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="saveUserChanges">
            <p><input type="text" name="userID" readonly="readonly" value="${user.userID}" placeholder="User ID"></p>
            <p><input type="text" name="username" value="${user.username}" placeholder="Username"></p>
            <p><input type="password" name="password" value="${user.password}" placeholder="Password"></p>
            <p class="is_admin">
                <label>
                    <input type="checkbox" name="admin" id="admin" ${user.admin ? 'checked' : ''}>
                    Is admin
                </label>
            </p>
            <p class="submit"><input type="submit" name="commit" value="Save"></p>
        </form>
    </div>
</section>

<jsp:include page="footer.html"></jsp:include>

</body>
</html>