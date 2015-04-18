<html>
	<head>
		<title>Timeline</title>
	</head>

	<body>
		<fieldset>
		${it.Status} <br> 
		
			Create Post <br>
			<form action="/social/CreateUserPost" method="POST">
				<input type = "hidden"  name = "owner" value="<%= request.getSession(true).getAttribute("name")%>" >
				<input type = "hidden"  name = "password" value="<%= request.getSession(true).getAttribute("password")%>" >
				
				<textarea rows="10" cols="40" name = "post">Write your post here</textarea><br><br>
				Privacy: 
				<select name = "privacy">
				  <option value="Public">Public</option>
				  <option value="Private">Private</option>
				  <option value="Friends">Friends</option>
				</select> <br><br>
				<input type ="submit" value="Create"><br>	
			</form>
			
		</fieldset>
		
	
	
	
	
	
	</body>

</html>