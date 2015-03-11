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
javax.ws.rs.core.UriBuilder , com.google.appengine.labs.repackaged.org.json.JSONArray, 
org.glassfish.jersey.client.ClientConfig , org.json.simple.parser.*"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Test</title>
</head>
<%
	JSONArray array = null;
	try {
		
		ClientConfig config1 = new ClientConfig();
		Client client = ClientBuilder.newClient(config1);
		
		WebTarget target = client.target(UriBuilder.fromUri(
				"http://localhost:8888").build());
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(target.path("social").path("test")
				.request()
				.accept(MediaType.TEXT_PLAIN).get(String.class)
				.toString());
	
		
		out.println("obj->"+obj.toString());
		out.println("array0->"+array);
		array=(JSONArray)obj;
		
		out.println("array->"+array);
		for(int i=0;i < array.length();i++)
			out.println(array.get(i));
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<body>
	
	
	
	
</body>
</html>