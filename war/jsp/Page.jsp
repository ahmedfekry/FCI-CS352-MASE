<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Home</title>
</head>
<body>


<%@page import="com.FCI.SWE.Models.User
				,org.json.simple.*
				,org.json.simple.parser.JSONParser
				,org.json.simple.parser.ParseException
				,java.util.*"
 %>


<%
	request.getSession(true);
	if(request.getSession().getAttribute("name") == null)
		response.sendRedirect("/entryPoint");
	
	if(request.getSession().getAttribute("email") == null)
		response.sendRedirect("/entryPoint");
	
	if(request.getSession().getAttribute("password") == null)
		response.sendRedirect("/entryPoint");
		
	
%>


<p>${it.Status}<p>

</body>
</html>