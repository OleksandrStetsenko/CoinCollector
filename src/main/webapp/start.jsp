
<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Start Page</title>
    </head>
    <body>
    <!--    <h1>Hello World!</h1> -->

        <form action="controller" method="post">
            <input type="hidden" name="action" value="listCountry">
             <p ><input type="submit" name="showCountries" value="List of Countries"></p>
         </form>
    </body>
</html>
