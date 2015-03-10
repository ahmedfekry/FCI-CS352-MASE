<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>User class</title>
<%!
	public static class User{
		public String name = null;
		public String email = null;
		public String password = null;
		public static User currentActive= null;
		
		public User(String name , String email , String password){
			this.name = name;
			this.email = email;
			this.password = password;
			loggedIn( name, email, password);
		}
		
		public static void loggedIn(String name,String mail,String password){
			currentActive = new User(name,mail,password);
		}
		
		public static void signout(){
			User.currentActive = null; %>
			<%
   			response.sendRedirect("/social");
			%>
			<%!
			// go to entry point  local address /social
		}
		
		
		
}
%>
</head>
<body>

</body>
</html>