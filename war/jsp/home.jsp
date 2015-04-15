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


<p> Welcome b2a ya ${it.name} </p>
<p> Mail ${it.email} </p>
<P><B>Send Friend Request <B><P><br>

<form action="/social/FriendRequest" method="post">
	<input type="hidden" name="senderUser"  value = "<%= request.getSession(true).getAttribute("name")%>" >
	<input type="hidden" name="senderPassword"  value = "<%= request.getSession(true).getAttribute("password")%>" >
 	TO : <input type="text" name="friendUser" > <br>
  	<input type="submit" value="Send Request">
</form>



<br>
<br>
<fieldset>
<form action="/social/signout" method="get">
	<input type ="submit" value="Signout">
</form>
<br>
</fieldset>

<fieldset>
<form action="/social/MyFriends" method="POST">
	<input type ="submit" value="View Friends ">
	<input type = "hidden"  value="<%= request.getSession(true).getAttribute("name")%>" name = "uName" >
	<input type = "hidden"  value="<%= request.getSession(true).getAttribute("password")%>" name = "password" >
	
</form>

</fieldset>

<fieldset>
	<form action="/social/ReceivedFriendRequests" method = "POST">
	<input type = "hidden"  value="<%= request.getSession(true).getAttribute("name")%>" name = "uName" >
	<input type = "hidden"  value="<%= request.getSession(true).getAttribute("password")%>" name = "password" >
	<input type ="submit" value="view Requests">
</form>

</fieldset>
	<a href="/social/viewMessagePage">Send Message</a>
<fieldset>
<form action="/social/getAllNotifications" method = "POST">
	<input type = "hidden"  value="<%= request.getSession(true).getAttribute("name")%>" name = "username" >
	<input type = "hidden"  value="<%= request.getSession(true).getAttribute("password")%>" name = "password" >
	<input type ="submit" value="View All Notifications">
</form>
</fieldset>


</body>
</html>