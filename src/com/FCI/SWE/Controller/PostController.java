package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



@Path("/")
@Produces("text/html")
public class PostController {
	
	/**
	 * 
	 * @param owner username of user wants to create the post
	 * @param password user password
	 * @param post the post to be published
	 * @return JSON Object of Status OK or Failed and reason
	 */
	@Path("CreateUserPost")
	@POST
	
	public Response createUserPost(@FormParam("owner")String owner, @FormParam("password")String password,
			@FormParam("post")String post, @FormParam("privacy")String privacy)
	{
		String serviceUrl = "http://localhost:8888/rest/createUserPost";
		String urlParameters = "owner=" + owner + "&password=" + password +
				"&post=" + post  + "&privacy=" + privacy;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Map<String, String > result = new HashMap<String, String>();
		
		try {
			JSONObject object = (JSONObject)parser.parse(retJson);
			
			result.put("Status", object.get("Status").toString());
			if(!object.get("Status").toString().equals("OK"))
				return Response.ok(new Viewable("/jsp/Error" ,result) ).build();
			
			result.put("id", object.get("id").toString());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("Status", "Your post has been posted successfully");
		
		
		return Response.ok(new Viewable("/jsp/timeline" ,result) ).build();
	}
	
	
}
