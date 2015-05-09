package com.FCI.SWE.Controller;

import java.util.Date;
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

import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.PostType;



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
			@FormParam("post")String post, @FormParam("privacy")String privacy,
			@FormParam("feeling")String feeling)
	{
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/createUserPost";
		String urlParameters = "owner=" + owner + "&password=" + password +
				"&post=" + post  + "&privacy=" + privacy + "&feeling=" + feeling;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Map<String, String > result = new HashMap<String, String>();
		
		try {
			JSONObject object = (JSONObject)parser.parse(retJson);
			
			result.put("Status", object.get("Status").toString());
			if(!object.get("Status").toString().equals("OK"))
				return Response.ok(new Viewable("/jsp/Error" ,result) ).build();
			
			//result.put("id", object.get("id").toString());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("Status", "Your post has been posted successfully");
		Map<String, Vector<Post> >map = new HashMap<String, Vector<Post> >();
		System.out.println();
		Vector<Post> posts = PostController.getUserPosts(owner, password);
		System.out.println("number of posts " + posts.size());
		for(Post p : posts)
		{
			System.out.println("p");
		}
		map.put("posts", posts);
		return Response.ok(new Viewable("/jsp/timeline" ,map) ).build();
	}
	//////////////////////////////////////////////////////////////////////////////
	
	
	public static Vector<Post> getUserPosts(String owner,String password)
	{
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/getUserPosts";
		String urlParameters = "owner=" + owner + "&password=" + password ;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Vector<Post> posts = null;
		try {
			JSONArray array = (JSONArray)parser.parse(retJson);
			posts = new Vector<Post>();
			
			for (int i = 1; i < array.size(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				
				Post p = new Post(object.get("owner").toString(),
						Integer.parseInt(object.get("id").toString()),
						object.get("post").toString(),
						new Date(),
						object.get("privacy").toString(),
						PostType.valueOf(object.get("type").toString() ),
						object.get("feeling").toString());
				System.out.println("feeling "+p.feeling);
				
				posts.add(p);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return posts;
	}
	
	
}
