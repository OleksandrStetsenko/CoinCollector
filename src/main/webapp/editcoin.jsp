<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>Add new coin page (only for administrator's)</title>
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
    <div class="addcoin">
        <h1>Create new coin</h1>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="addCoin">
            
			<p><input type="text" name="fullname" value="1" placeholder="Full name"></p>
			<p><input type="text" name="name" value="1" placeholder="Name"></p>
				
			<select name="country" size="1">
			
				<c:forEach items="${list}" var="country">
				<option value="<c:out value="${country.countryId}"></c:out>">
				<c:out value="${country.countryName}"></c:out></option>
				</c:forEach>
			
			</select>
			
			<p><input type="text" name="year" value="1" placeholder="Year"></p>
			<p><input type="text" name="metall" value="1" placeholder="Metall"></p>
			<p><input type="text" name="diameter_mm" value="1" placeholder="Diameter(mm)"></p>
			<p><input type="text" name="value" value="1" placeholder="Value"></p>
			<p><input type="text" name="weight" value="1" placeholder="weight"></p>
            
			<p class="submit"><input type="submit" name="commit" value="addCoin"></p>
        </form>
    </div>
</section>

<br>
If you want to cancel, please go <a href="controller?action=showAllCoins">back</a>.

<jsp:include page="footer.html"></jsp:include>

</body>
</html>
