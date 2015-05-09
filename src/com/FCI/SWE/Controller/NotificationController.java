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
// connection 
// link
//String serviceUrl = "http://fci-swe-apps.appspot.com/rest/RegistrationService";
//String urlParameters = "uname=" + uname + "&email=" + email
//		+ "&password=" + pass;
//String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
//		"application/x-www-form-urlencoded;charset=UTF-8");











import com.FCI.SWE.Models.Notification;
import com.FCI.SWE.ServicesModels.FriendRequestEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;
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
		
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/createMessage";
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
		Vector<MessageEntity>messages = new Vector<MessageEntity>();
		Map<String, Vector<MessageEntity> >map = new HashMap<String, Vector<MessageEntity> >();
		
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/getAllMessages";
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
				messages.add(MessageEntity.parseMessage(o.toJSONString()));
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
		Vector<MessageEntity>messages = new Vector<MessageEntity>();
		Map<String, Vector<MessageEntity> >map = new HashMap<String, Vector<MessageEntity> >();
		
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/getMessagesByID";
		String urlParameters = "username=" + username + "&password=" + password+ "&id=" + id;
				
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONObject object = new JSONObject();
		JSONParser parser = new JSONParser();
		
		try {
			//System.out.println(retJson);
			object= (JSONObject)parser.parse(retJson);
			
			messages.add(MessageEntity.parseMessage(object.toJSONString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		map.put("messages", messages);
		return Response.ok(new Viewable("/jsp/viewMessages",map)).build();
	}
	
	//////////////////////////////////////////
	/**
	 * @param username user wants to get friend request
	 * @param password user password
	 * @param id friend request id
	 * @return response to FriendRequests jsp page with the friend request
	 */
	@Path("/getFriendRequestByID")
	@POST
	public Response getFriendRequestByID(@FormParam("username")String username, 
			@FormParam("password")String password, @FormParam("id")String id	)
	{
		Vector<FriendRequestEntity>requsts = new Vector<FriendRequestEntity>();
		Map<String, Vector<FriendRequestEntity> >map = new HashMap<String, Vector<FriendRequestEntity> >();
		
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/getFriendRequestByID";
		String urlParameters = "username=" + username + "&password=" + password+ "&id=" + id;
				
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONObject object = new JSONObject();
		JSONParser parser = new JSONParser();
		
		try {
			//System.out.println(retJson);
			object= (JSONObject)parser.parse(retJson);
			
			requsts.add(FriendRequestEntity.parseFriendRequest(object.toJSONString()));
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
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/getUnseenMessages";
		String urlParameters = "username=" + username + "&password=" + password;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Map<String, Vector<Notification>> result = new HashMap<String, Vector<Notification>>();
		Vector<Notification> notifications = new Vector<Notification>();
		try {
			JSONArray array = (JSONArray)parser.parse(retJson);
			for (int i = 1; i < array.size(); i++) {
				JSONObject obj = (JSONObject)array.get(i);
				notifications.add(MessageEntity.parseMessage(obj.toJSONString()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/getUnseenFriendRequests";
		retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		parser = new JSONParser();
		try {
			JSONArray array = (JSONArray)parser.parse(retJson);
			for (int i = 1; i < array.size(); i++) {
				JSONObject obj = (JSONObject)array.get(i);
				notifications.add(FriendRequestEntity.parseFriendRequest(obj.toJSONString()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("notifications", notifications);
		return Response.ok(new Viewable("/jsp/ViewNotifications",result)).build();
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 
	 * @param username username of user wants to create conversation 
	 * @param password password of user
	 * @param name conversation name
	 * @return
	 */
	@Path("/CreateConversation")

	@POST
	public Response createConversation( @FormParam("username")String username, 
			@FormParam("password")String password, @FormParam("name")String name	)
	{
		
		String serviceUrl = "http://localhost:8888/rest/createConversation";
		String urlParameters = "username=" + username + "&password=" + password + "&name=" + name ;
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
		result.put("name", name);
		return Response.ok(new Viewable("/jsp/conversation" ,result) ).build();
	}
	////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param owner username of user wants to add afriend to conversation
	 * @param password password of owner
	 * @param id conversation id
	 * @param friend username of a friend to be added to conversation
	 * @return JSON object represents Status OK, Failed & reason
	 */
	@Path("/AddToConversation")
	@POST
	public Response addToConversation(@FormParam("username")String owner, 
			@FormParam("password")String password, @FormParam("id")String id,
			@FormParam("friend")String friend, @FormParam("conversationName")String conversationName)
	{
		
		String serviceUrl = "http://localhost:8888/rest/addToConversation";
		String urlParameters = "username=" + owner + "&password=" + password +
				"&id=" + id + "&friend=" + friend;
		System.out.println("conv controller: username= "+owner + "   password= "+password);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Map<String, String > result = new HashMap<String, String>();
		
		try {
			JSONObject object = (JSONObject)parser.parse(retJson);
			
			result.put("Status", object.get("Status").toString());
			if(!object.get("Status").toString().equals("OK"))
				return Response.ok(new Viewable("/jsp/Error" ,result) ).build();
			
			result.put("id", id);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("Status", "Friend has been added successfully");
		result.put("name", conversationName);
		return Response.ok(new Viewable("/jsp/conversation" ,result) ).build();
	}
	
	////////////////////////////////////////////////////////////////////////////
	@Path("/SendConversationMessage")

	@POST
	public Response sendConversationMessage( @FormParam("sender")String sender, @FormParam("password")String password,
			 @FormParam("message")String message,@FormParam("conversationID")int conversationID,
			 @FormParam("conversationName")String conversationName)
	{
			

		String serviceUrl = "http://localhost:8888/rest/SendConversationMessage";
		String urlParameters = "sender=" + sender + "&password=" + password +
				"&message=" + message + "&conversationID=" + conversationID ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		System.out.println("message in controller = "+message);
		JSONParser parser = new JSONParser();
		Map<String, String > result = new HashMap<String, String>();
		
		try {
			JSONObject object = (JSONObject)parser.parse(retJson);
			
			result.put("Status", object.get("Status").toString());
			
			if(!object.get("Status").toString().equals("OK"))
				return Response.ok(new Viewable("/jsp/Error" ,result) ).build();
			
			System.out.println("conv name= "+conversationName);
			
			result.put("id", object.get("id").toString());
			result.put("name", conversationName);
			result.put("Status", "Message has been sent correctly");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/conversation" ,result) ).build();
	}
///////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param username
	 * @param password
	 * @param conversationID
	 * @return
	 */
	@Path("/getCoversationMessages")
	@POST
	public Response getCoversationMessages(@FormParam("username")String username, 
			@FormParam("password")String password, @FormParam("id")String conversationID	)
	{
		Vector<MessageEntity>conversarionMssages = new Vector<MessageEntity>();
		Map<String, Vector<MessageEntity> >map = new HashMap<String, Vector<MessageEntity> >();
		
		String serviceUrl = "http://2-dot-socialnetwork-mase.appspot.com/rest/getCoversationMessages";
		String urlParameters = "username=" + username + "&password=" + password + "&id=" + conversationID;
				
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
				conversarionMssages.add(MessageEntity.parseMessage(o.toJSONString()));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		map.put("messages", conversarionMssages);
		return Response.ok(new Viewable("/jsp/conversationMessages",map)).build();
	}
	

	
	////////////////////////////////////////////////////////////////////////////////////////////////
}
