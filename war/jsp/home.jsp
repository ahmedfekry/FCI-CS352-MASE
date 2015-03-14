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
	
	String name = "" , email = "" , password = "";
	name += request.getSession().getAttribute("name");
	email += request.getSession().getAttribute("email");
	password += request.getSession().getAttribute("password");
	
	
	
	Map<String , String> map = new HashMap<String,String>();
	map.put("name",(String)request.getSession().getAttribute("name"));
	map.put("email",(String)request.getSession().getAttribute("email"));
	map.put("password",(String)request.getSession().getAttribute("password"));
	JSONObject object = new JSONObject();
	object.putAll(map);
	User.getUser(object.toString());
%>


<p> Welcome b2a ya ${it.name} </p>
<p> Mail ${it.email} </p>
<P><B>Send Friend Request <B><P><br>

<form action="/social/FriendRequest" method="post">
	<input type="hidden" name="senderUser"  value = "s" />
	<input type="hidden" name="senderPassword"  value = "123" />
 	TO : <input type="text" name="friendUser" /> <br>
  	<input type="submit" value="Send Request">
</form>

<Button  name="Sign out" onClick="" /> Signout </Button>

<br>
<br>
<form action="/social/signout" method="get">
	<input type ="submit" value="Signout">
</form>

</body>
</html>