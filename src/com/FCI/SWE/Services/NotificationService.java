package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.ConversationEntity;
import com.FCI.SWE.ServicesModels.ConversationMessageEntity;
import com.FCI.SWE.ServicesModels.FriendRequestEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;


@Path("/")
@Produces("text/html")
public class NotificationService {
	/**
	 * 
	 * @param sender username of message sender user
	 * @param senderPassword password of message sender 
	 * @param receiver username of message receiver user
	 * @param message represents the message itself 
	 * @return status OK or Failed
	 */
	@Path("/createMessage")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String createMessage(@FormParam("sender")String sender, @FormParam("password")String senderPassword,
			@FormParam("receiver")String receiver, @FormParam("message")String message)
	{

		JSONObject object = new JSONObject();

		if(UserEntity.getUser(sender, senderPassword) == null)
		{
			object.put("Status", "Failed, no acces to send message");
		}

		else if(!UserEntity.isExist(receiver) )
		{
			object.put("Status", "Failed, no such user with this username");

		}

		else if(MessageEntity.saveMessage(sender, receiver, message))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");

		return object.toString();
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * @param username username of user wants to create conversation 
	 * @param password password of user
	 * @return JSON array with all messages
	 */

	@Path("/getAllMessages")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String getAllMessages(@FormParam("username")String username, 
			@FormParam("password")String password	)
	{
		JSONArray array ;

		if(UserEntity.getUser(username, password) == null)
		{
			array = new JSONArray();
			JSONObject ob = new JSONObject();
			ob.put("Status", "Failed, wrong username or password");
			array.add(ob);
			return array.toString();
		}

		array = MessageEntity.getAllMessages(username);
		return array.toString();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param username username of user wants to create conversation 
	 * @param password password of user
	 * @return JSON array with all unseen messages
	 */
	@Path("/getUnseenMessages")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String getUnseenMessages(@FormParam("username")String username, 
			@FormParam("password")String password	)
	{
		JSONArray array ;

		if(UserEntity.getUser(username, password) == null)
		{
			array = new JSONArray();
			JSONObject ob = new JSONObject();
			ob.put("Status", "Failed, wrong username or password");
			array.add(ob);
			return array.toString();
		}

		array = MessageEntity.getUnseenMessages(username);
		return array.toString();

	}
	//////////////////////////////////////////////////////////////////
	/**
	 * send friend request from user to another one using their username 
	 * first it checks if they are different users and check access and
	 *  the request doesn't sent before 
	 * @param sUser username of the request sender user
	 * @param fUser username of the request receiver user
	 * @param password password of the request sender user to check access rights 
	 * @return OK if request approved or Failed and reason of failure 
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/FriendRequestService")
	@POST
	public String  sendFriendRequest (@FormParam("senderUser") String sUser, 
			@FormParam("friendUser")String fUser, @FormParam("senderPassword")String password)
	{
		System.out.println("in service");
		System.out.println("sUser = " + sUser + " pass " + password);
		JSONObject object = new JSONObject();
		if(UserEntity.getUser(sUser, password) == null)
		{
			object.put("Status", "Failed, wrong username or password");
			return object.toString();
		}	
		if(fUser.equals(sUser))
		{
			object.put("Status", "Failed, two users are the same");
			return object.toString();
		}
		boolean exists = UserEntity.isExist(fUser);

		// check if friend is sent to exist user , check the access right of sender
		if(!exists || UserEntity.getUser(sUser, password) == null)
			object.put("Status", "Failed, incorrect data");
		//m.put("Status", "Failed");
		else
		{
			if(FriendRequestEntity.saveRequest(sUser, fUser) == true)			
				object.put("Status", "OK");
			else
				object.put("Status", "Failed, they are already friends or "
						+ "request has been sent befor.");
		}

		return object.toString();

	}

	////////////////////////////////////////////////////////////////////////////////
	/**
	 * cancel and delete sent friend request, search database for that request if found delete it  
	 * @param sUser username of the request sender user
	 * @param fUser username of the request receiver user
	 * @param password password of the request sender user to check access rights 
	 * @return OK if request approved or Failed and reason of failure 
	 */
	@POST
	@Path("/CancelFriendRequestService")
	public String deleteFriendRequest(@FormParam("senderUser")String sUser,
			@FormParam("friendUser") String fUser, @FormParam("senderPassword")String password) {
		JSONObject object  = new JSONObject();

		// check access right of sender 
		if(UserEntity.getUser(sUser, password) == null)
		{
			object.put("Status", "Failed, wrong username or password");
			return object.toString();
		}

		if (FriendRequestEntity.deleteRequest(sUser, fUser))
			object.put("Status", "OK");
		else
			object.put("Status", "Failed, no request is sent");

		return object.toString();

	}
	//////////////////////////////////////
	/**
	 * @param username username of user wants to create conversation 
	 * @param password password of user
	 * @return JSON array with all unseen friend requests
	 */
	@Path("/getUnseenFriendRequests")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String getUnseenFriendRequests(@FormParam("username")String username, 
			@FormParam("password")String password	)
	{
		JSONArray array;
		if(UserEntity.getUser(username, password) == null)
		{
			array = new JSONArray();
			JSONObject ob = new JSONObject();
			ob.put("Status", "Failed, wrong username or password");
			array.add(ob);
			return array.toString();
		}
		array = FriendRequestEntity.getUnseenFriendRequests(username);
		
		return array .toJSONString();
	}
	///////////////////////////////////////////////////////////
	/**
	 * @param username username of user wants to create conversation 
	 * @param password password of user
	 * @param id message id
	 * @return JSON object represent the message
	 */
	@Path("/getMessageByID")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String getMessagesByID(@FormParam("username")String username, 
			@FormParam("password")String password, @FormParam("id")String id	)
	{

		JSONObject object ;
		if(UserEntity.getUser(username, password) == null)
		{
			object = new JSONObject();
			object.put("Status", "Failed, wrong username or password");
			return object.toString();
		}
		object = MessageEntity.getMessagesByID(username, id);
		
		return object.toString();
	}
	/////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param username username of user wants to create conversation 
	 * @param password password of user
	 * @param id request id
	 * @return JSON object represents the friend request
	 */
	@Path("/getFriendRequestByID")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String getFriendRequestByID(@FormParam("username")String username, 
			@FormParam("password")String password, @FormParam("id")String id	)
	{
		JSONObject object;
		if(UserEntity.getUser(username, password) == null)
		{
			object = new JSONObject();
			object.put("Status", "Failed, wrong username or password");
			return object.toString();
		}
		
		object = FriendRequestEntity.getFriendRequestByID(username, id);
		
		return object.toString();
	}

	/////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param username username of user wants to create conversation 
	 * @param password password of user
	 * @param name conversation name
	 * @return
	 */
	@Path("/createConversation")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String createConversation( @FormParam("username")String username, 
			@FormParam("password")String password, @FormParam("name")String name	)
	{
		JSONObject object = new JSONObject();

		if(UserEntity.getUser(username, password) == null)
		{
			object.put("Status", "Failed, no acces to create conversation");
			return object.toString();
		}

		long id = ConversationEntity.createConversation(username, name);
		if(id!= -1){
			object.put("Status", "OK");
			object.put("id", id);
		}
		else
			object.put("Status", "Failed");
		
		return object.toString();
	}

	/////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param owner username of user wants to add afriend to conversation
	 * @param password password of owner
	 * @param id conversation id
	 * @param friend username of a friend to be added to conversation
	 * @return JSON object represents Status OK, Failed & reason
	 */
	@Path("/addToConversation")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String addToConversation(@FormParam("username")String owner, 
			@FormParam("password")String password, @FormParam("id")String id, @FormParam("friend")String friend	)
	{
		JSONObject object = new JSONObject();
		if(UserEntity.getUser(owner, password) == null )
		{
			object.put("Status", "Failed, wrong username or password");
			return object.toString();
		}
		if( ! UserEntity.isExist(friend))
		{
			object.put("Status", "Failed, wrong friend username");
			return object.toString();
		}
		
		String Status = ConversationEntity.addToConversation(owner, id, friend);
		
		object.put("Status", Status);

		return object.toString();
	}
	/////////////////////////////////////////////////////////////
	@Path("/SendConversationMessage")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String SendConversationMessage(@FormParam("sender")String sender, @FormParam("password")String password,
			@FormParam("message")String message,@FormParam("conversationID")int conversationID){
		JSONObject object = new JSONObject();
		
		if(UserEntity.getUser(sender, password) == null)
		{
			object.put("Status", "Failed, no such user");
		}
		
		else if(ConversationMessageEntity.SendConversationMessage(sender, message, conversationID))
		{
			object.put("Status", "OK");
			object.put("id", conversationID);
		}
		else
			object.put("Status", "Failed, No conversation with this ID");
		
		return object.toString();
	}
	//////////////////////////////////////////////////////////
	/**
	 * 
	 * @param username
	 * @param password
	 * @param conversationID
	 * @return
	 */
	@Path("/getConversationMessages")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String getConversationMessages(@FormParam("username")String username, 
			@FormParam("password")String password,@FormParam("id")String conversationID	)
	{
		JSONArray array;
		JSONObject ob = new JSONObject();
		if(UserEntity.getUser(username, password) == null )
		{
			array = new JSONArray();
					
			ob.put("Status", "Failed, wrong username or password");
			array.add(ob);
			return array.toString();
		}
		
		if(conversationID == null)
		{
			array = new JSONArray();
			ob.put("Status", "Failed,no conversation message exists");
			array.add(ob);
			return array.toString();
		}
		array = ConversationMessageEntity.getConversationMessages(username, conversationID);

		return array.toString();
	}
	////////////////////////////////////////////////////////

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@Path("/getUnseenConversationMessages")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String getUnseenConversationMessages(@FormParam("username")String username, 
			@FormParam("password")String password	)
	{
		JSONArray array;
		
		if(UserEntity.getUser(username, password) == null)
		{
			array = new JSONArray();
			JSONObject ob = new JSONObject();
			ob.put("Status", "Failed, wrong username or password");
			array.add(ob);
			return array.toString();
		}
		
		array = ConversationMessageEntity.getUnseenConversationMessages(username);

		return array.toString();
	}
	/////////////////////////////////////////////////////////
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
}
