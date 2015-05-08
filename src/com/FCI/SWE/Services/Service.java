package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.jws.soap.SOAPBinding.Use;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.FriendRequestEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		User user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	
	
	////////////////////////////////////////////////////////////////////
	/**
	 * cancel and delete sent friend request, search database for that request if found delete it  
	 * @param sUser username of the friend request sender user
	 * @param fUser username of the friend request receiver user (who accepts the request)
	 * @param password password of the request accepter user to check access rights 
	 * @return OK if request approved or Failed and reason of failure 
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	@Path("/AddFriendService")
	public String addFriend(@FormParam("senderUser")String sUser,
		@FormParam("friendUser") String fUser,
		@FormParam("receiverPassword")String password) {
		
		JSONObject object = new JSONObject();
		
		if(UserEntity.getUser(fUser, password) == null)
		{
			object.put("Status", "Failed, wrong username or password");
			return object.toString();
			
		}

		if(UserEntity.addFriend(sUser, fUser))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed, there is no sent friendrequest ");

		return object.toString();
	}
	
	//////////////////////////////////////////////////////
	// first element of the array is the status
	/**
	 * Get all friends for a user 
	 * @param uName  username of a user
	 * @param password user account password
	 * 
	 * */
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	@Path("/GetFriends")
	public String getFriends(@FormParam("uName")String uName, 
			@FormParam("password")String password) {
		
		JSONArray object;
		System.out.println("test");
		if(UserEntity.getUser(uName, password) == null)
		{
			object = new JSONArray();
			object.add( "Failed, wrong username or password");
			return object.toJSONString();			
		}
		
		object = UserEntity.getFriends(uName);
		
		System.out.println("obj" + object);
		return object.toJSONString();
	}
	//////////////////////////////////////////////////////////////////
	/**
	 * Get all received friend requests for a user 
	 * @param uName  username of a user
	 * @param password user account password
	 * */
	@POST
	@Path("/GetFriendRequests")
	public String getFriendRequests(@FormParam("uName")String uName, 
			@FormParam("password")String password) {
	//	System.out.println("F R S username_"+ uName + "_pass "+ password);
		JSONArray object; 
		
		if(UserEntity.getUser(uName, password) == null)
		{
			object = new JSONArray();
			object.add( "Failed");
			return object.toJSONString();
		}
		
		object = FriendRequestEntity.getRequests(uName);
		return object.toJSONString();
	}
	
	///////////////////////////////////////////////////////////////////////
	
	
}