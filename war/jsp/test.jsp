
<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page
	
	import="java.net.URI , 
javax.ws.rs.client.Client , 
javax.ws.rs.client.ClientBuilder , 
javax.ws.rs.client.WebTarget , 
javax.ws.rs.core.MediaType , 
javax.ws.rs.core.Response ,
com.FCI.SWE.Models.User,
javax.ws.rs.core.UriBuilder , org.json.simple.JSONArray, 
org.glassfish.jersey.client.ClientConfig , org.json.simple.parser.*"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Test</title>

<body>

<form action="/social/MyFriends" method="POST">
	<input type ="submit" value="View Friends ">
	<input type = "text"  value="<%= request.getSession(true).getAttribute("name")%>" name = "uName" >
	<input type = "text"  value="<%= request.getSession(true).getAttribute("password")%>" name = "password" >
	
</form>

	
	
	
</body>
</html>