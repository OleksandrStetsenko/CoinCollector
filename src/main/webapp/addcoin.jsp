<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>Manage coin page (only for administrator's)</title>
    <style>
        body {
         background: #EEE8AA;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>


   <b> <p style="color:red">${message}</p></b>



<section class="container">
    <div class="addcoin">
        <h1>${head}</h1>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="addCoin">
            <p><input type="hidden" name="coinID" value="${coin.coinID}">   </p>
			<p><input type="text" name="fullname" value="${coin.fullname}"> Full coin name</p>
			<p><input type="text" name="name" value="${coin.name}"> Name</p>
				
			<select name="country" size="1">
				<c:forEach items="${list}" var="country">
				<option 
					<c:if test="${country.countryId==coin.country}">
					selected="selected"
					</c:if>
				
				value="<c:out value="${country.countryId}"></c:out>"><c:out value="${country.countryName}"></c:out></option>
				</c:forEach>
			
			</select>
			
			<p><input type="text" name="year" value="${coin.year}"> Year</p>
			<p><input type="text" name="metall" value="${coin.metall}"> Metall</p>
			<p><input type="text" name="diameter_mm" value="${coin.diameter_mm}"> Diameter(mm)</p>
			<p><input type="text" name="value" value="${coin.value}"> Value</p>
			<p><input type="text" name="weight" value="${coin.weight}"> Weight</p>
            
			<p class="submit"><input type="submit" name="commit" value="Accept"></p>
        </form>
    </div>
</section>

<br>
If you want to cancel, please go <a href="controller?action=showAllCoins">back</a>.

<jsp:include page="footer.html"></jsp:include>

</body>
</html>
