<%@page import = "java.util.* " %>
<%@include file="User.jsp" %>

<%!
	//String name =  ${it.message} ; 
	//String email=		
//	String password = 
	
//	if(User.currentActive == null)
//		User.currentActive = new User(name,email,password);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p> Welcome b2a ya <%=user.name %> </p>
<p> Mail <%=user.email %> </p>
<P><B>Send Friend Request <B><P><br>

<form action="/social/FriendRequest" method="post">
	<input type="hidden" name="senderUser"  value = <%=user.name %> /> <br>
 	TO : <input type="text" name="friendUser" /> <br>
  	<input type="submit" value="Send Request">
</form>
<input type="button" name="Sign out" onClick=<%=user.signout() %> />
</body>
</html>