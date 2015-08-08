<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>Register Page</title>
    <style>
        body {
         background: #EEE8AA;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>

<c:if test="${not empty message}">
    <p style="color:red">${message}</p>
</c:if>

<section class="container">
    <div class="register">
        <h1>Register on Coin collector</h1>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="register">
            <p><input type="text" name="username" value="" placeholder="Username"></p>
            <p><input type="password" name="password" value="" placeholder="Password"></p>
            <p class="submit"><input type="submit" name="commit" value="register"></p>
        </form>
    </div>
</section>

<br>
If you are registered user, please <a href="login.jsp">login</a>.

<jsp:include page="footer.html"></jsp:include>

</body>
</html>
