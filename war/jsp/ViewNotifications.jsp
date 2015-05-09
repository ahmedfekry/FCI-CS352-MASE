<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.json.simple.JSONObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>viewMessages</title>
</head>
<body>

<c:forEach items="${it.notifications}" var="notification">
	<fieldset>
		<p>sender name  <c:out value="${ notification.sender }"><br></c:out>	</p>
		<form action="${notification.commandUrl}" method = "POST">
			<input type = "hidden"  value="<%= request.getSession(true).getAttribute("name")%>" name = "username" >
			<input type = "hidden"  value="<%= request.getSession(true).getAttribute("password")%>" name = "password" >
			<input type = "hidden"  value="${notification.id}" name = "id" >
			<input type ="submit" value="View">
		</form>
	</fieldset>
</c:forEach>
</body>
</html>