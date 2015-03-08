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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;












import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
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
		UserEntity user = UserEntity.getUser(uname, pass);
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
	
	//////////////////////////////////////////////////////////////////

	@Produces(MediaType.TEXT_PLAIN)
	@Path("/FriendRequestService")
	@POST
	public String  sendFriendRequest (@FormParam("friendUser") String fUser, 
									@FormParam("senderUser")String sUser)
	{
		JSONObject object = new JSONObject();
		if(fUser.equals(sUser))
		{
			object.put("Status", "Failed");
			return object.toString();
		}
		boolean b = UserEntity.isExist(fUser);
		//Map<String, String >m = new HashMap<String , String>();
		if(!b)
			object.put("Status", "Failed");
			//m.put("Status", "Failed");
		else
		{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Query gaeQuery = new Query("friendRequests");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			List<Entity> l = pq.asList(FetchOptions.Builder.withDefaults());
		
			// check if Friend request is sent before
			
			for(Entity e : l)
			{
				if(e.getProperty("sender").equals(sUser) && e.getProperty("receiver").equals(fUser) )
				{
					object.put("Status", "Failed");
					return object.toString();
				}	
			}
			
			Entity fRequest = new Entity("friendRequests", l.get(l.size()-1).getKey().getId()+1);
			fRequest.setProperty("sender", sUser);
			fRequest.setProperty("receiver", fUser);
			datastore.put(fRequest);
			object.put("Status", "OK");
			//m.put("Status", "OK");
		}
		
		return object.toString();
		//return Response.ok(new Viewable("/jsp/result", m)).build();
	}
	
	////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * */
	@POST
	@Path("/DeleteFriendRequestService")
	public String deleteFriendRequest(@FormParam("senderUser")String sUser,
			@FormParam("friendUser") String fUser) {
		JSONObject object = new JSONObject();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("friendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		object.put("Status", "Failed");
		for(Entity e : pq.asIterable())
		{
			if(e.getProperty("sender").equals(sUser) && e.getProperty("receiver").equals(fUser) )
			{
				datastore.delete(e.getKey());
				object.put("Status", "OK");
				break;
			}	
		}
		return object.toString();

	}
	////////////////////////////////////////////////////////////////////
	@POST
	@Path("/AddFriendService")
	public String addFriend(@FormParam("senderUser")String sUser,
			@FormParam("friendUser") String fUser) {
		JSONObject object ;
		JSONObject returnObject = new JSONObject();
		String serviceUrl = "http://localhost:8888/rest/DeleteFriendRequestService";
		
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "senderUser=" + sUser + "&friendUser=" + fUser;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			object = (JSONObject) obj;
			
			if (object.get("Status").equals("Failed"))
			{
				returnObject.put("Status", "Failed");
				return returnObject.toString();
			}	
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();

			Query gaeQuery = new Query("Friends");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			List<Entity> l = pq.asList(FetchOptions.Builder.withDefaults());
			
			Entity friend = new Entity("Friends", l.get(l.size()-1).getKey().getId()+1);
			
			friend.setProperty("user", sUser);
			friend.setProperty("friendTo", fUser);
			datastore.put(friend);
			
			object.put("Status", "OK");
			
			
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return returnObject.toString();
	}
	
	//////////////////////////////////////////////////////

	
}