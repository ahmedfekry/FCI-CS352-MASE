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

<c:forEach items="${it.Message}" var="user">
<p>sender name  <c:out value="${ sender }"><br></c:out>			</p>
<p>message		<c:out value="${ message }"><br></c:out> 		</p>
<p>at date		<c:out value="${ date }"><br></c:out> 			</p>
</c:forEach>
</body>
</html>