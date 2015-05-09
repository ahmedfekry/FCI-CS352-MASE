package com.FCI.SWE.ServicesModels;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.Notification;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class MessageEntity extends Notification{

	String message;


	public MessageEntity(String sender, String receiver, String commandUrl,
			Date date, boolean seen, int id, String message) {
		super(sender, receiver, commandUrl, date, seen, id);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * parse JSON object represents a message to a message entity
	 * @param json string represents a JSON object has a message information
	 * @return Message that has been parsed from JSON string
	 */
	public static MessageEntity parseMessage (String json)
	{
		JSONParser parser = new JSONParser();
		MessageEntity m = null;
		try {
			JSONObject object = (JSONObject) parser.parse(json);

			m = new MessageEntity(object.get("sender").toString(),
					object.get("receiver").toString(),
					object.get("commandurl").toString(),
					new Date(),
					(boolean)object.get("seen"),
					Integer.parseInt(object.get("id").toString()),
					object.get("message").toString()

					);
			//currentActiveUser.setId(Long.parseLong(object.get("id").toString()));
			return m;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


	}
	////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param sender username of message sender user
	 * @param receiver username of message receiver user
	 * @param message represents the message itself 
	 * @return status OK or Failed
	 */
	public static boolean saveMessage(String sender, String receiver, String message)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		try{
			Query gaeQuery = new Query("Messages");
			PreparedQuery pq = datastore.prepare(gaeQuery);

			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			long id =1;
			if(list.size()!=0)
				id =list.get(list.size()-1).getKey().getId()+ 1;

			Entity mes = new Entity("Messages", id);

			mes.setProperty("sender", sender);
			mes.setProperty("receiver", receiver);
			mes.setProperty("seen", false);
			mes.setProperty("date", new Date());
			mes.setProperty("message", message);
			mes.setProperty("id", id);

			datastore.put(mes);
		}
		catch(Exception e)
		{
			return false;
		}

		return true;
	}
	///////////////////////////////////////////////
	/**
	 * @param username username of user wants to create conversation 
	 * @return JSON array with all messages
	 */
	public static JSONArray getAllMessages(String username)
	{
		JSONArray array ;
		array = new JSONArray();
		JSONObject ob = new JSONObject();

		ob.put("Status", "OK");
		array.add(ob);


		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("Messages");
		PreparedQuery prepare = dataStore.prepare(geo);


		for(Entity entity: prepare.asIterable()){

			String reciver = entity.getProperty("receiver").toString();

			if(reciver.equals(username) ){
				boolean seen = (boolean)entity.getProperty("seen");
				seen = true;
				entity.setProperty("seen", seen);

				JSONObject obj = new JSONObject();
				obj.put("sender",entity.getProperty("sender") );
				obj.put("receiver",reciver );
				obj.put("commandurl", "social/viewMessageByID");
				obj.put("date", "date");
				obj.put("seen",seen );
				obj.put("id",entity.getProperty("id").toString() );
				obj.put("message", entity.getProperty("message").toString());

				dataStore.put(entity);
				array.add(obj);
			}
		}

		return array;

	}
	/////////////////////////////////////////
	/**
	 * @param username username of user wants to create conversation 
	 * @return JSON array with all unseen friend requests
	 */
	public static JSONArray getUnseenMessages(String username)
	{
		JSONArray array = new JSONArray();
		JSONObject ob = new JSONObject();

		ob.put("Status", "OK");
		array.add(ob);

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("Messages");
		PreparedQuery prepare = dataStore.prepare(geo);


		for(Entity entity: prepare.asIterable()){

			String reciver = entity.getProperty("receiver").toString();
			boolean seen = (boolean)entity.getProperty("seen");

			if(reciver.equals(username) && !seen){
				seen = true;
				entity.setProperty("seen", seen);

				JSONObject obj = new JSONObject();
				obj.put("sender",entity.getProperty("sender") );
				obj.put("receiver",reciver );
				obj.put("commandurl", "social//viewFriendRequestByID");
				obj.put("date", "date");
				obj.put("seen",seen );
				obj.put("id",entity.getProperty("id").toString() );
				obj.put("message", entity.getProperty("message").toString());

				dataStore.put(entity);
				array.add(obj);
			}
		}

		return array;

	}
	////////////////////////////////////////
	/**
	 * @param username username of user wants to create conversation 
	 * @param id message id
	 * @return JSON object represent the message
	 */
	public static JSONObject getMessagesByID(String username, String id){

		JSONObject object = new JSONObject();

		object.put("Status", "OK");

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("Messages");
		PreparedQuery prepare = dataStore.prepare(geo);


		for(Entity entity: prepare.asIterable()){

			String ID = entity.getProperty("id").toString();
			String reciver = entity.getProperty("receiver").toString();
			String sender = entity.getProperty("sender").toString();

			if( (reciver.equals(username) || sender.equals(username) ) && ID.equals(id) ){

				boolean seen = true;
				entity.setProperty("seen", seen);

				object.put("sender",entity.getProperty("sender") );
				object.put("receiver",reciver );
				object.put("commandurl", "social/getMessageByID");
				object.put("date", "date");
				object.put("seen",seen );
				object.put("id",entity.getProperty("id").toString() );
				object.put("message", entity.getProperty("message").toString());

				dataStore.put(entity);
			}
		}


		return object;
	}
}
