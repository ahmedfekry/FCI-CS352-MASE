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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sun.text.normalizer.CharTrie.FriendAgent;

import com.FCI.SWE.Models.FriendRequest;
import com.FCI.SWE.Models.Message;
import com.FCI.SWE.Models.Notification;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
//import com.google.appengine.repackaged.com.google.gson.JsonArray;
// connection 
// link
//String serviceUrl = "http://fci-swe-apps.appspot.com/rest/RegistrationService";
//String urlParameters = "uname=" + uname + "&email=" + email
//		+ "&password=" + pass;
//String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
//		"application/x-www-form-urlencoded;charset=UTF-8");



@Path("/")
@Produces("text/html")
public class NotificationController {
	

	@GET
	@Path("/viewMessagePage")
	
	public Response viewMessagePage ()
	{	
		return Response.ok(new Viewable("/jsp/messagePage")).build();
	}
	/////////////////////////////////////////////////////////////////////////////////

	
	@POST
	@Path("/sendMessage")
	
	public Response sendMessage (@FormParam("sender")String sender, @FormParam("password")String senderPassword,
	@FormParam("receiver")String receiver, @FormParam("message")String message)
	{

		Map<String, String> map = new HashMap<String, String>();
		
		String serviceUrl = "http://localhost:8888/rest/createMessage";
		String urlParameters = "sender=" + sender + "&password=" + senderPassword
				+ "&receiver=" + receiver + "&message=" + message;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				 map.put("message", "Message has been sent Successfully");
			
			else
				 map.put("message", (String) object.get("Status"));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok(new Viewable("/jsp/messageResponse",map)).build();
	}
	/////////////////////////////////////////////////////////////////////////////////
	
	@POST
	@Path("/getAllNotifications")
	public Response viewAllNotification(@FormParam("username")String username,@FormParam("password") String password) throws JSONException{
		String serviceUrl = "http://localhost:8888/rest/getUnseenMessages";
		String urlParameters = "username=" + username + "&password=" + password;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Map<String, Vector<Notification>> result = new HashMap<String, Vector<Notification>>();
		Vector<Notification> notifications = new Vector<Notification>();
		try {
			JSONArray Array = (JSONArray)parser.parse(retJson);
			for (int i = 0; i < Array.length(); i++) {
				JSONObject obj = (JSONObject)Array.get(i);
				notifications.add(Message.parseMessage(obj.toJSONString()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serviceUrl = "http://localhost:8888/rest/getUnseenFriendRequests";
		retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		parser = new JSONParser();
		try {
			JSONArray Array = (JSONArray)parser.parse(retJson);
			for (int i = 0; i < Array.length(); i++) {
				JSONObject obj = (JSONObject)Array.get(i);
				notifications.add(FriendRequest.parseFriendRequest(obj.toJSONString()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("notifications", notifications);
		return Response.ok(new Viewable("/jsp/messageResponse",result)).build();
	}
}
