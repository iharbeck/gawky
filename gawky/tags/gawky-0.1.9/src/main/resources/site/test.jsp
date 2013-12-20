
<%
    request.setAttribute("r_scope", "aus dem request");
    session.setAttribute("s_scope", "aus der session");
%>

${ r_scope } <br> 
${ s_scope } 

