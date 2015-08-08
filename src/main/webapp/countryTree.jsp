 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="tree"%>
<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>JSP Page</title>
        <style>
            .block { 
                width: 150px; 
                background-color: #EEE8AA;
                padding: 20px;
                padding-right: 10px; 
                border: solid 1px black; 
                float: left;
                text-indent: 30px;
                text-align: left;
            }
            .block2{
                
            }
            .ul {
                text-indent: 50px;
            }
        </style>
    </head>
    <body>
        
        <form id="listCountry"  action="controller" method="post"> 
            <h2>Countries :</h2> 
            <div class="block"> 
                <a href="controller?action=getListCoins&countryID=0">  All Countries</a>  
                <c:forEach items="${listTreeCountry}" var = "countryNode">
                <c:choose >
                    <c:when test="${countryNode.countryType == 'partOfWorld'}">
                    <div ><a href="controller?action=getListCoins&countryID=${countryNode.countryId}"> 
                        ${countryNode.countryName}</a></div>
                    </c:when>
                    <c:when test="${countryNode.countryType =='country'}">
                        <div id="country" class="ul">
                        <a href="controller?action=getListCoins&countryID=${countryNode.countryId}">
                            ${countryNode.countryName}</a></div>
                    </c:when>
                </c:choose>
                </c:forEach>
             </div>
         </form>
        
    </body>
</html>
