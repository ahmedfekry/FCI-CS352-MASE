<html>
	<head>
		<title>Conversation</title>
	</head>
	
	<body>
	${it.Status}	<br>
	Conversation Name: ${it.name}	<br><br>	
	<fieldset>
		Add Member To Conversation<br>
		<form action="/social/AddToConversation" method="POST">
			<input type = "hidden"  name = "username" value="<%= request.getSession(true).getAttribute("name")%>" >
			<input type = "hidden"  name = "password" value="<%= request.getSession(true).getAttribute("password")%>" >		
			<input type = "hidden"  name = "id" value= ${it.id} >		
			<input type = "hidden"  name = "conversationName" value=${it.name} >		
			
			Enter Member name: <input type="text" name = "friend" ><br>
			<input type ="submit" value="Add"><br>	
		</form><br>
	</fieldset>
	
	<fieldset>
		Send Message<br>
		<form action="/social/SendConversationMessage" method="POST">
			<input type = "hidden"  name = "sender" value="<%= request.getSession(true).getAttribute("name")%>" >
			<input type = "hidden"  name = "password" value="<%= request.getSession(true).getAttribute("password")%>" >		
			<input type = "hidden"  name = "conversationID" value= ${it.id} >		
			<input type = "hidden"  name = "conversationName" value=${it.name} >		
			<textarea rows="10" cols="40" name = "message" > Write your message here</textarea><br><br>
				
			
			<input type ="submit" value="Send"><br>	
		</form><br>
	</fieldset>
	
	</body>

</html>