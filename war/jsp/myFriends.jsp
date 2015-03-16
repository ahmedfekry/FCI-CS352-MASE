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
<title>myFriends</title>
</head>
<body>

<c:forEach items="${it.myFriends}">
<p> Friends <c:out value="${myFriends.uName }"></c:out> </p>
<br></br> 
</c:forEach>
</body>
</html>
