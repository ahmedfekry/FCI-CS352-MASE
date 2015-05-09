package com.FCI.SWE.ServicesModels;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.FormParam;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class ConversationMessageEntity extends MessageEntity {
	private int conversationID;
	
	public ConversationMessageEntity(String sender, String receiver,
			String commandUrl, Date date, boolean seen, int id, String message, int conversationID) {
		super(sender, receiver, commandUrl, date, seen, id, message);
		this.conversationID = conversationID;
	}
	/////////////////////////////////////////////////////////
	
	public static boolean SendConversationMessage(String sender, String message,
			int conversationID){
		
		ConversationEntity c = ConversationEntity.getConversation(conversationID);

		if(c == null)
		{
			return false;
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
		
		return true;
	}
			
	////////////////////////////////////////////////////////
	
	public static JSONArray getConversationMessages(String username, String conversationID)
	{
		JSONArray array = new JSONArray();
		JSONObject ob = new JSONObject();
		
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
		
		return array;
	}
	////////////////////////////////////////////////////////
	public static JSONArray getUnseenConversationMessages(String username)
	{
		JSONArray array;
		array = new JSONArray();
		JSONObject ob = new JSONObject();
		
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
		return array;
	}
	
	
}
