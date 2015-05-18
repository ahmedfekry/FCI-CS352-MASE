package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class PageController {

	@Path("createPage")
	@POST
	
	public Response createPage(@FormParam("ownerName")String ownerName, @FormParam("password")String password,
			@FormParam("pageName")String pageName)
	{
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/createPage";
		String urlParameters = "ownerName=" + ownerName + "&password=" + password +
				"&pageName=" + pageName;
		
		String retJson = Request.sendRequest(serviceUrl,urlParameters,"POST");

		
		JSONParser parser = new JSONParser();
		Map<String, String > result = new HashMap<String, String>();
		
		try {
			JSONObject object = (JSONObject)parser.parse(retJson);
			
			result.put("Status", object.get("Status").toString());
			if(!object.get("Status").toString().equals("OK"))
				return Response.ok(new Viewable("/jsp/Error" ,result) ).build();
			
//			System.out.println("haaaaaay");
//			result.put("pageName", object.get("pageName").toString());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("Status", "Your Page has been created successfully");
		
		
		return Response.ok(new Viewable("/jsp/Page" ,result) ).build();
	}

}
