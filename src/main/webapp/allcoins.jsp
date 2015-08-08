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

        .style {
            height: 20px;
            width: 100px;
            text-align: center;
        }

    </style>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>
<table width="100%">
    <tr>
        <td></td>
        <td align="right" height="80">
            <jsp:include page="search.jsp"></jsp:include>
        </td>

    </tr>
    <tr>
        <th align="center"><br/>
            <jsp:include page="countryTree.jsp"></jsp:include>
        </th>
        <th align="center">
            <c:if test="${not empty message}">
                <p style="color:red">${message}</p>
            </c:if>
            <form action="controller" method="post">
                <input type="hidden" name="action" value="addCoinToMyCollection">

                <table border="5">
                    
                    <c:if test="${page eq 'myCoins'}">
                        <caption><h2>List of ${username}'s coins</h2></caption>
                    </c:if>
                    <c:if test="${page eq 'allCoins'}">
                        <caption><h2>List of all database coins</h2></caption>
                    </c:if>
                    <thead>
                    <tr>
                        <th class="style">Name</th>
                        <th class="style">Full Name</th>
                        <th class="style">Value</th>

                        <th class="style">Year</th>
                        <th class="style">Metall</th>
                        <th class="style">Weight (mg)</th>
                        <th class="style">Diameter(mm)</th>
                        <c:if test="${page eq 'allCoins'}">
                            <c:if test="${isadmin==1}">
                                <th colspan=2>Change</th>
                            </c:if>
                        </c:if>
                        <th>
                            <c:if test="${page eq 'allCoins'}">
                                <input type="submit" name="commit" value="Add selected">
                            </c:if>
                            <c:if test="${page eq 'myCoins'}">
                                Manage
                            </c:if>
                        </th>
                    </tr>
                    </thead>
                    <c:forEach items="${list}" var="coin">
                        <tr>
                            <td class="style"><c:out value="${coin.name}"></c:out></td>
                            <td class="style"><c:out value="${coin.fullname}"></c:out></td>
                            <td class="style"><c:out value="${coin.value}"></c:out></td>
                            <td class="style"><c:out value="${coin.year}"></c:out></td>
                            <td class="style"><c:out value="${coin.metall}"></c:out></td>
                            <td class="style"><c:out value="${coin.weight}"></c:out></td>
                            <td class="style"><c:out value="${coin.diameter_mm}"></c:out></td>
                            <c:if test="${page eq 'allCoins'}">
                                <c:if test="${isadmin==1}">
                                    <td>
                                        <a href="controller?action=prepCoin&coinID=<c:out value="${coin.coinID}"/>">[Edit]</a>
                                    </td>
                                    <td>
                                        <a href="controller?action=deleteCoin&coinID=<c:out value="${coin.coinID}"/>">[Delete]</a>
                                    </td>
                                </c:if>
                                <td align="center">
                                    <input type="checkbox" name="selectedItems"
                                           value="<c:out value="${coin.coinID}"/>"/>
                                </td>
                            </c:if>
                            <c:if test="${page eq 'myCoins'}">
                                <td>
                                    <a href="controller?action=removeCoinFromMyCollection&coinID=<c:out value="${coin.coinID}"/>">[Remove
                                        from my collection]</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </form>

        </th>
    </tr>

</table>


<jsp:include page="footer.html"></jsp:include>

</body>
</html>