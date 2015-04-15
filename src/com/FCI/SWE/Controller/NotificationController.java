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

import sun.text.normalizer.CharTrie.FriendAgent;

import com.FCI.SWE.Models.FriendRequest;
import com.FCI.SWE.Models.Message;
import com.FCI.SWE.Models.Notification;
import com.google.appengine.labs.repackaged.org.json.JSONException;
//import com.google.appengine.repackaged.com.google.gson.JsonArray;
// connection 
// link
//String serviceUrl = "http://fci-swe-apps.appspot.com/rest/RegistrationService";
//String urlParameters = "uname=" + uname + "&email=" + email
//		+ "&password=" + pass;
//String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
//		"application/x-www-form-urlencoded;charset=UTF-8");






import com.FCI.SWE.Models.FriendRequest;
import com.FCI.SWE.Models.Message;
import com.FCI.SWE.Models.Notification;
import com.google.appengine.labs.repackaged.org.json.JSONException;



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
	
	@Path("/getAllMessages")
	@POST
	public Response getAllMessages(@FormParam("username")String username, 
			@FormParam("password")String password	)
	{
		Vector<Message>messages = new Vector<Message>();
		Map<String, Vector<Message> >map = new HashMap<String, Vector<Message> >();
		
		String serviceUrl = "http://localhost:8888/rest/getAllMessages";
		String urlParameters = "username=" + username + "&password=" + password;
				
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONArray array = new JSONArray();
		JSONParser parser = new JSONParser();
		
		try {
			//System.out.println(retJson);
			array= (JSONArray)parser.parse(retJson);
			
			
			for (int i = 1; i < array.size(); i++) {
			//	System.out.println("i " + i);
				JSONObject o = (JSONObject)array.get(i);
			//	System.out.println(o);
				messages.add(Message.parseMessage(o.toJSONString()));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		map.put("messages", messages);
		return Response.ok(new Viewable("/jsp/viewMessages",map)).build();
	}
	
	/////////////////////////////////////
	
	@Path("/getMessageByID")
	@POST
	public Response getMessagesByID(@FormParam("username")String username, 
			@FormParam("password")String password, @FormParam("id")String id	)
	{
		Vector<Message>messages = new Vector<Message>();
		Map<String, Vector<Message> >map = new HashMap<String, Vector<Message> >();
		
		String serviceUrl = "http://localhost:8888/rest/getMessagesByID";
		String urlParameters = "username=" + username + "&password=" + password+ "&id=" + id;
				
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONObject object = new JSONObject();
		JSONParser parser = new JSONParser();
		
		try {
			//System.out.println(retJson);
			object= (JSONObject)parser.parse(retJson);
			
			messages.add(Message.parseMessage(object.toJSONString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		map.put("messages", messages);
		return Response.ok(new Viewable("/jsp/viewMessages",map)).build();
	}
	
	//////////////////////////////////////////
	
	@Path("/getFriendRequestByID")
	@POST
	public Response getFriendRequestByID(@FormParam("username")String username, 
			@FormParam("password")String password, @FormParam("id")String id	)
	{
		Vector<FriendRequest>requsts = new Vector<FriendRequest>();
		Map<String, Vector<FriendRequest> >map = new HashMap<String, Vector<FriendRequest> >();
		
		String serviceUrl = "http://localhost:8888/rest/getFriendRequestByID";
		String urlParameters = "username=" + username + "&password=" + password+ "&id=" + id;
				
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONObject object = new JSONObject();
		JSONParser parser = new JSONParser();
		
		try {
			//System.out.println(retJson);
			object= (JSONObject)parser.parse(retJson);
			
			requsts.add(FriendRequest.parseFriendRequest(object.toJSONString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		map.put("FriendRequests", requsts);
		return Response.ok(new Viewable("/jsp/FriendRequests",map)).build();
	}
	//////////////////////////////////////////////////////////////////////////////
	// Get Notifications 
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
			JSONArray array = (JSONArray)parser.parse(retJson);
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = (JSONObject)array.get(i);
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
			JSONArray array = (JSONArray)parser.parse(retJson);
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = (JSONObject)array.get(i);
				notifications.add(FriendRequest.parseFriendRequest(obj.toJSONString()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("notifications", notifications);
		return Response.ok(new Viewable("/jsp/ViewNotifications",result)).build();
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	
	
	
	
}
