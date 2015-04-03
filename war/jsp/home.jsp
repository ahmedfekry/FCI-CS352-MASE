<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
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
<!--  <P><B>Send Friend Request <B><P><br>

<form action="/social/FriendRequest" method="post">
	<input type="hidden" name="senderUser"  value = <%request.getSession(true).getAttribute("name"); %> />
	<input type="hidden" name="senderPassword"  value = <%request.getSession(true).getAttribute("password"); %> />
 	TO : <input type="text" name="friendUser" /> <br>
  	<input type="submit" value="Send Request">
</form>

<Button  name="Sign out" onClick="" /> Signout </Button>

<br>
<br>
<form action="/social/signout" method="get">
	<input type ="submit" value="Signout">
</form>
<br>
-->
<form action="/social/MyFriends" method="POST">
	<input type ="submit" value="View Friends ">
	<input type = "text"  value="<%= request.getSession(true).getAttribute("name")%>" name = "uName" >
	<input type = "text"  value="<%= request.getSession(true).getAttribute("password")%>" name = "password" >
	
</form>

<%
	out.println("Name "+User.getCurrentActiveUser().getName()+"_");
	out.println("pass "+User.getCurrentActiveUser().getPass()+"_");
	out.println("Name "+User.getCurrentActiveUser().getName()+"_");
	out.println("pass "+User.getCurrentActiveUser().getPass()+"_");
%>
<!--
<fieldset>
	<form action="/social/ReceivedFriendRequests" method = "POST">
	<input type = "text"  value="<%= User.getCurrentActiveUser().getName() %>" name = "uName" >
	<input type = "text"  value="<%= User.getCurrentActiveUser().getPass() %>" name = "password" >
	<input type ="submit" value="view Requests">
	</form>
</fieldset>
-->
	<a href="/social/test">Test</a>
</body>
</html>