<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Meesage</title>
</head>
<body>



<fieldset>
	<form action="/social/sendMessage" method = "POST">
	<input type = "hidden"  value="<%= request.getSession(true).getAttribute("name")%>" name = "sender" >
	<input type = "hidden"  value="<%= request.getSession(true).getAttribute("password")%>" name = "password" >
	<input type = "text" value = "Enter username" name = "receiver"><br>
	 <textarea rows="4" cols="50" name = "message"> Enter your message here</textarea> <br>
	<input type ="submit" value="Send">
</form>

</fieldset>


</body>
</html>