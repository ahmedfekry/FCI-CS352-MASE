<%
	request.getSession(true);
	
	if(request.getSession().getAttribute("param1") == null)
		out.print("null value");
	else 
		out.print(request.getSession().getAttribute("param1"));
		

%>	
<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page
	
	import="java.net.URI , 
javax.ws.rs.client.Client , 
javax.ws.rs.client.ClientBuilder , 
javax.ws.rs.client.WebTarget , 
javax.ws.rs.core.MediaType , 
javax.ws.rs.core.Response ,
javax.ws.rs.core.UriBuilder , org.json.simple.JSONArray, 
org.glassfish.jersey.client.ClientConfig , org.json.simple.parser.*"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Test</title>
</head>
<%

	//out.println("param2 "+request.getSession().getAttribute("param2"));
	JSONArray array = null;
	try {
		
		ClientConfig config1 = new ClientConfig();
		Client client = ClientBuilder.newClient(config1);
		
		WebTarget target = client.target(UriBuilder.fromUri(
				"http://localhost:8888").build());
		//out.print("test");
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(target.path("social").path("test")
				.request()
				.accept(MediaType.TEXT_PLAIN).get(String.class)
				.toString());
	
		
		out.print("test " + obj.toString());
		array=(JSONArray)obj;
		out.println("array ->"+array);
		

		for(int i=0;i < array.size();i++)
			out.println(array.get(i));
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<body>

	
	
	
</body>
</html>