package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;


@Path("/")
@Produces("text/html")
public class PostServices {
	
	public String createPost(@FormParam("owner")String owner, @FormParam("password")String password,
			@FormParam("post")String post)
	{
		JSONObject object = new JSONObject();
		
		User user = UserEntity.getUser(owner, password);
		
		if(user == null)
		{
			object.put("Status", "Failed, not authorized user ");
			return object.toJSONString();
		}
		
		return object.toJSONString();
	}
	
	
	
}
