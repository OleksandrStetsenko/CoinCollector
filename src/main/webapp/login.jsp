<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>Login Page</title>
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
    <div class="login">
        <h1>Log on to Coin collector</h1>

        <form action="controller" method="post">
            <input type="hidden" name="action" value="login">

            <p><input type="text" name="username" value="" placeholder="Username"></p>

            <p><input type="password" name="password" value="" placeholder="Password"></p>

            <%--<p class="remember_me">--%>
                <%--<label>--%>
                    <%--<input type="checkbox" name="remember_me" id="remember_me">--%>
                    <%--Remember me on this computer--%>
                <%--</label>--%>
            <%--</p>--%>

            <p class="submit"><input type="submit" name="commit" value="login"></p>
        </form>
    </div>
   
</section>

<br>
If you are new user, please <a href="register.jsp">register</a>.

 

<jsp:include page="footer.html"></jsp:include>


</body>
</html>