<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p> Welcome b2a ya ${it.name} </p>
<p> Mail ${it.email} </p>
<P><B>Send Friend Request <B><P><br>

<form action="/social/FriendRequest" method="post">
	<input type="hidden" name="senderUser"  value = "" /> <br>
 	TO : <input type="text" name="friendUser" /> <br>
  	<input type="submit" value="Send Request">
</form>
<Button  name="Sign out" onClick="" /> Signout </Button>
</body>
</html>