<!DOCTYPE html>
<html>

<head>
    <style>
        #header {
            background-color:darkcyan;
            color:white;
            text-align:center;
            padding:5px;
        }
	  #links {
            background-color:darkcyan;
            color:white;
            text-align:left;
            padding:5px;
        }

    </style>
</head>

<body>

<div id="header">
    <h1>Coin collector</h1>

</div>

<div id="links">
<p style="color:black">

<%@ page import="ua.edu.sumdu.lab3.group11.obj.User" %>
<%
	 
	if (request.getSession().getAttribute("currentUser")!=null){
		User u = (User) request.getSession().getAttribute("currentUser");
            request.setAttribute("username", u.getUsername());//TREGUB
            out.print(" Wellcome, "+u.getUsername());
            
             if (u.isAdmin()) {
                request.setAttribute("isadmin", "1");//TREGUB
                out.println(", you have Administrator's  rights on this site.<br>");
                out.print("<a href=\"controller?action=prepCoin\">[Add new coin] </a>");
                out.print("<a href=\"controller?action=listUser\">[Show user list] </a>");
             }
             
             out.print("<a href=\"controller?action=showAllCoins\"> [Show all coins]</a>");
             out.print("<a href=\"controller?action=showUserCoins\"> [Show my coins]</a>");
             
             out.print("<a href=\"controller?action=logout\"> [Logout]</a>");
   } else{
   request.setAttribute("username", null);
   }
%>

</div>


</body>
</html>
