<%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="c" 
       uri="http://java.sun.com/jsp/jstl/core" %>
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
				Feeling:  
				<select name = "feeling">
				  <option value="Happy">Happy</option>
				  <option value="Sad">Sad</option>
				  <option value="Mad">Mad</option>
				</select> <br><br>
				<input type ="submit" value="Create"><br>	
			</form>
			
		</fieldset><br><br>
		
	
	<c:forEach items="${it.Posts_}" var = "PostIterator">
		<fieldset>
			
			<p>Post Owner:  <c:out value= "${PostIterator.owner }"> </c:out> <br></p>
			<p>POST:<br>	<c:out value="${PostIterator.post}"><br></c:out> 		</p>

			<p>Privacy:	<c:out value="${PostIterator.privacy}"><br></c:out> 		</p>
			
			<p>at <c:out value="${ PostIterator.date }"><br></c:out> 	<br><br></p>
			
			
			
			<form action="/social/LikePostByID" method = "POST">
			<input type = "hidden"  name = "username" value="<%= request.getSession(true).getAttribute("name")%>"  >
			<input type = "hidden"  name = "password" value="<%= request.getSession(true).getAttribute("password")%>"  >
			<input type = "hidden"  name = "id" value = "${ PostIterator.id }" >
			
			<input type ="submit" value="Like">
			</form>
			
			<form action="/social/SharePostByID" method = "POST">
			<input type = "hidden"  name = "username" value="<%= request.getSession(true).getAttribute("name")%>"  >
			<input type = "hidden"  name = "password" value="<%= request.getSession(true).getAttribute("password")%>"  >
			<input type = "hidden"  name = "id" value = "${ PostIterator.id }" >
			
			<input type ="submit" value="Share">
			</form>
		</fieldset>
	</c:forEach>
	
	
				<!--  <p>Feeling:	<c:out value="${PostIterator.feeling}"><br></c:out> 		</p>-->
	
	</body>

</html>