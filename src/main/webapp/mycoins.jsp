<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="US-ASCII">
    <title>${username} Coins Page</title>
    <style>
        body {
         background: #EEE8AA;
        }
        .style{
            height: 20px;
            width: 100px; 
            text-align: center;
        }
   
    </style>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>
<table width="100%">
    <tr >
        <td  ></td>
        <td align="right" height="80"><jsp:include page="search.jsp"></jsp:include></td>
        
    </tr>
    <tr>
        <th align="center"><br/>
        <jsp:include page="countryTree.jsp"></jsp:include></th>
    <th align="center" >

<table border="5">
    <caption><h2>List of ${username}'s coins</h2></caption>
    <thead>
    <tr>
        <th class="style">CoinID</th>
        <th class="style">Name</th>
        <th class="style">Value</th>
        <th class="style">Country</th>
        <th class="style">Year</th>
        <th class="style">Metall</th>
        <th class="style">diameter(mm)</th>
        <th class="style">Manage</th>

    </tr>
    </thead>
    <c:forEach items="${list}" var="coin">
        <tr>
            <td class="style"><c:out value="${coin.coinID}"></c:out></td>
            <td class="style"><c:out value="${coin.name}"></c:out></td>
            <td class="style"><c:out value="${coin.value}"></c:out></td>
            <td class="style"><c:out value="${coin.country}"></c:out></td>
            <td class="style"><c:out value="${coin.year}"></c:out></td>
            <td class="style"><c:out value="${coin.metall}"></c:out></td>
            <td class="style"><c:out value="${coin.diameter_mm}"></c:out></td>
            <td>
                <form action="controller" method="post">
                    <input type="submit" value="Remove from my collection">
                    <input type="hidden" name="action" value="removeCoinFromMyCollection">
                    <input type="hidden" name="coinID" value="${coin.coinID}">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</th>
</tr>
</table>


<jsp:include page="footer.html"></jsp:include>

</body>
</html>