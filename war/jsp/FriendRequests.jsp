
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.FCI.SWE.Models.User
				,org.json.simple.*
				,org.json.simple.parser.JSONParser
				,org.json.simple.parser.ParseException
				,org.json.simple.JSONObject
				,java.util.*"
		
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Friend Requests</title>
</head>
<body>

<c:forEach items="${it.FriendRequests}" var = "user">
<fieldset>
	<form action="/social/AddFriend" method = "POST">
	<input type = "text"  value="${user}" name = "senderUser" readonly="readonly">
  	<input type = "hidden"  value="<%User.getCurrentActiveUser().getName(); %>" name = "friendUser" >
	<input type = "hidden"  value="<%User.getCurrentActiveUser().getPass(); %>" name = "friendPassword" >
	
	<input type ="submit" value="Accept">
		
	</form>
	</fieldset>
</c:forEach>


</body>
</htsssssssssssssssssssssssssss                                                                                                ml>