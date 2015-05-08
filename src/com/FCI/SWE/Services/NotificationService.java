package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.FCI.SWE.Models.Conversation;
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
		JSONObject object = new JSONObject();
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

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("Conversation");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		long id =1;
		if(list.size()!=0)
			id =list.get(list.size()-1).getKey().getId()+ 1;

		Entity conv = new Entity("Conversation", id);

		conv.setProperty("owner", username);
		conv.setProperty("name", name);
		conv.setProperty("date", new Date());
		conv.setProperty("id", id);
		Vector<String>members = new Vector<String>();
		members.add(username);
		conv.setProperty("members", members);

		datastore.put(conv);

		object.put("Status", "OK");
		object.put("id", id);

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
		System.out.println("conv service: username= "+owner + "   password= "+password);
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
		// if(! UserEntity.areFriends(String owner, String friend)
		//		{
		//			object.put("Status", "Failed, You are not friends");
		//			return object.toString();
		//		}
		object.put("Status", "OK");

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("Conversation");
		PreparedQuery prepare = dataStore.prepare(geo);


		for(Entity entity: prepare.asIterable()){

			String ID = entity.getProperty("id").toString();
			String ownerUser = entity.getProperty("owner").toString();
			ArrayList<String>members = (ArrayList<String>)entity.getProperty("members");


			if( (members.contains(ownerUser) ) && ID.equals(id) ){
				if(members.contains(friend))
				{
					object.put("Status", "Failed, this username is already member in the conversation");
				}
				else
				{
					members.add(friend);
					entity.setProperty("members", members);

					dataStore.put(entity);

					object.put("Status", "OK");

				}
				return object.toString();
			}
		}
		object.put("Status", "Failed, no conversation with this id");
		return object.toString();
	}
	/////////////////////////////////////////////////////////////
	@Path("/SendConversationMessage")
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public String SendConversationMessage(@FormParam("sender")String sender, @FormParam("password")String password,
			@FormParam("message")String message,@FormParam("conversationID")int conversationID){
		JSONObject object = new JSONObject();
		System.out.println("message in service = "+ message);
		Conversation c = Conversation.getConversation(conversationID);

		if(c == null)
		{
			object.put("Status", "Failed, no conversation with this ID");
			return object.toString();
		}
		if(UserEntity.getUser(sender, password) == null)
		{
			object.put("Status", "Failed, no such user");
			return object.toString();
		}
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("ConversationMessages");

		PreparedQuery pq = datastore.prepare(gaeQuery);

		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		long id =1;
		if(list.size()!=0)
			id =list.get(list.size()-1).getKey().getId()+ 1;


		Vector<String>members = c.getMembers();

		for(int i=0;i<members.size();i++)
		{
			String reciver = members.get(i);
			if(reciver.equals(sender))
				continue;

			Entity mes = new Entity("ConversationMessages", id);

			mes.setProperty("sender", sender);
			mes.setProperty("reciver", reciver);
			mes.setProperty("seen", false);
			mes.setProperty("date", new Date());
			mes.setProperty("message", message);
			mes.setProperty("id", id);
			mes.setProperty("commendUrl", "ssssss");
			mes.setProperty("conversationID", conversationID);

			datastore.put(mes);
			id++;
		}




		object.put("Status", "OK");
		object.put("id", conversationID);

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
		JSONArray array = new JSONArray();
		JSONObject ob = new JSONObject();
		if(UserEntity.getUser(username, password) == null )
		{
			ob.put("Status", "Failed, wrong username or password");
			array.add(ob);
			return array.toString();
		}
		if(conversationID == null)
		{
			ob.put("Status", "Failed,no conversation message exists");
			array.add(ob);
			return array.toString();
		}
		ob.put("Status", "OK");
		array.add(ob);

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("Conversation");
		PreparedQuery prepare = dataStore.prepare(geo);

		for(Entity entity: prepare.asIterable()){

			String conv = entity.getProperty("Conversation").toString();
			//new Entity("Conversation", conversationID);
			if(conv.equals(conversationID) ){
				boolean seen = (boolean)entity.getProperty("seen");
				seen = true;
				entity.setProperty("seen", seen);

				JSONObject obj = new JSONObject();
				obj.put("owner",entity.getProperty("username") );
				obj.put("commandurl", "social/viewMessageByID");
				obj.put("date", "date");
				obj.put("seen",seen );
				obj.put("id",entity.getProperty("conversationID").toString() );
				obj.put("message", entity.getProperty("message").toString());

				dataStore.put(entity);
				array.add(obj);
			}
		}

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
		JSONArray array = new JSONArray();
		JSONObject ob = new JSONObject();
		if(UserEntity.getUser(username, password) == null)
		{
			ob.put("Status", "Failed, wrong username or password");
			array.add(ob);
			return array.toString();
		}
		ob.put("Status", "OK");
		array.add(ob);

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("Conversation");
		PreparedQuery prepare = dataStore.prepare(geo);


		for(Entity entity: prepare.asIterable()){

			String reciver = entity.getProperty("receiver").toString();
			boolean seen = (boolean)entity.getProperty("seen");

			if(reciver.equals(username) && !seen ){
				seen = true;
				entity.setProperty("seen", seen);

				JSONObject obj = new JSONObject();
				obj.put("sender",entity.getProperty("sender") );
				obj.put("receiver",reciver );
				obj.put("commandurl", "social//viewFriendRequestByID");
				obj.put("date", "date");
				obj.put("seen",seen );
				obj.put("id",entity.getProperty("conversationID").toString() );
				obj.put("message", entity.getProperty("message").toString());

				dataStore.put(entity);
				array.add(obj);
			}
		}

		return array.toString();
	}
	/////////////////////////////////////////////////////////
}
