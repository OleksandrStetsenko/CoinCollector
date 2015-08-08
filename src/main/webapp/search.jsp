<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Search</title>
        <style>
            .block1 { 
                width: 400px; 
                height: 70px;
                background-color: #EEE8AA;
                padding: 5px;
                padding-right: 20px; 
                border: solid 1px black; 
                
            }
            table{
                
            }
        </style>
    </head>
    <body>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="search">
            <table class="block1">
                <tr>
                    <th>Country :</th>
                    <th>Year :</th>
                    <th>Metall :</th>
                    <th></th>
                </tr>
                <tr>
                    <th>
                        <select name="country" size="1">
                           
                            <option selected value ="allCountries"> -- all -- </option>
                        <c:forEach items="${listCountrys}" var="country">
                            <option  value="${country.countryId}">${country.countryName}</option>
                        </c:forEach>
                        </select>
                    </th>
                    <th>
                        <select name="year" size="1">
                        <option selected value ="allYears"> -- all -- </option>
                        <c:forEach items="${listYears}" var="year">
                        <option value="${year}">${year}</option>
                        </c:forEach>
                        </select>
                    </th>
                    <th>
                        <select name="metall" size="1">
                            <option selected value ="allMetalls"> -- all -- </option>
                            <c:forEach items="${listMetalls}" var="metall">
                            <option value="${metall}">${metall}</option>
                            </c:forEach>
                        </select>
                    </th>
                    <th>
                        <input type="submit" name="executeSearch" value="Search">
                    </th>
                </tr>
            </table>

        </form>
    </body>
</html>
