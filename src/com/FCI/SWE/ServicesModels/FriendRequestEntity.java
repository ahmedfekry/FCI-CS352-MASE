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

public class FriendRequestEntity extends Notification{

	public FriendRequestEntity(String sender, String receiver, String commandUrl,
			Date date, boolean seen, int id) {
		super(sender, receiver, commandUrl, date, seen, id);

	}


	public static FriendRequestEntity parseFriendRequest (String json)
	{
		JSONParser parser = new JSONParser();
		FriendRequestEntity m = null;
		try {
			JSONObject object = (JSONObject) parser.parse(json);

			m = new FriendRequestEntity(object.get("sender").toString(),
					object.get("receiver").toString(),
					object.get("commandurl").toString(),
					new Date(),
					(boolean)object.get("seen"),
					Integer.parseInt(object.get("id").toString())	

					);
			//currentActiveUser.setId(Long.parseLong(object.get("id").toString()));
			return m;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	////////////////////////////////////////////////
	/**
	 * send friend request from user to another one using their username 
	 * first it checks if they are different users and check access and
	 *  the request doesn't sent before 
	 * @param sUser username of the request sender user
	 * @param fUser username of the request receiver user
	 * @return OK if request approved or Failed and reason of failure 
	 */
	public static boolean saveRequest (String sUser, String fUser)
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
				return false;
			}	
		}
		// check if they are friends 
		Query guery = new Query("Friends");
		PreparedQuery pq2 = datastore.prepare(gaeQuery);

		for(Entity e : pq2.asIterable())
		{
			if((e.getProperty("user").equals(sUser) && e.getProperty("friendTo").equals(fUser) ) || 
					(e.getProperty("friendTo").equals(sUser) && e.getProperty("user").equals(fUser) ) )
			{
				return false;
			}
		}
		//////////////
		long id = 1;
		if(l.size()>0)
			id = l.get(l.size()-1).getKey().getId()+1 ;
		Date d = new Date();

		Entity fRequest = new Entity("friendRequests", id);
		fRequest.setProperty("sender", sUser);
		fRequest.setProperty("receiver", fUser);
		fRequest.setProperty("seen", false);
		fRequest.setProperty("date", d);
		fRequest.setProperty("id", id);


		datastore.put(fRequest);


		return true;
	}
	/////////////////////////////////////////////
	/**
	 * Get all received friend requests for a user 
	 * @param uName  username of a user
	 * */
	public static JSONArray getRequests (String uName){
		JSONArray object = new JSONArray();

		object.add( "OK");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("friendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);	

		for(Entity e : pq.asIterable())
		{
			if(e.getProperty("receiver").equals(uName)  )
			{
				object.add(e.getProperty("sender"));
				e.setProperty("seen", "true");
				datastore.put(e);
			}	

		}
		return object;		
	}
	///////////////////////////////////////////////
	/**
	 * cancel and delete sent friend request, search database for that request if found delete it  
	 * @param sUser username of the request sender user
	 * @param fUser username of the request receiver user
	 * @return OK if request approved or Failed and reason of failure 
	 */
	public static boolean deleteRequest (String sUser,String fUser)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("friendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for(Entity e : pq.asIterable())
		{
			if(e.getProperty("sender").equals(sUser) && e.getProperty("receiver").equals(fUser) )
			{
				datastore.delete(e.getKey());
				return true;
			}	
		}

		return false;
	}
	///////////////////////////////////////////////////
	/**
	 * @param username username of user wants to create conversation 
	 * @return JSON array with all unseen friend requests
	 */
	public static JSONArray getUnseenFriendRequests(String username){

		JSONArray array = new JSONArray();
		JSONObject ob = new JSONObject();

		ob.put("Status", "OK");
		array.add(ob);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("friendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);	

		for(Entity entity : pq.asIterable())
		{
			boolean seen = (boolean) entity.getProperty("seen");
			String reciver = (String) entity.getProperty("receiver");

			if(reciver.equals(username) && !seen )
			{
				seen = true;
				entity.setProperty("seen",seen );
				datastore.put(entity);

				JSONObject obj = new JSONObject();

				obj.put("sender",entity.getProperty("sender") );
				obj.put("receiver",reciver );
				obj.put("commandurl", "social/viewMessageByID");
				obj.put("date", "date");
				obj.put("seen",seen );
				obj.put("id",entity.getProperty("id").toString() );				

				array.add(obj);

			}	

		}	
		return array;
	}
	///////////////////////////////////////////////////
	/**
	 * 
	 * @param username username of user wants to create conversation 
	 * @param id request id
	 * @return JSON object represents the friend request
	 */
	public static JSONObject getFriendRequestByID(String username, String id){

		JSONObject object = new JSONObject();

		object.put("Status", "OK");

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("friendRequests");
		PreparedQuery prepare = dataStore.prepare(geo);


		for(Entity entity: prepare.asIterable()){

			String ID = entity.getProperty("id").toString();
			String reciver = entity.getProperty("receiver").toString();


			if( (reciver.equals(username) ) && ID.equals(id) ){
				String sender = entity.getProperty("sender").toString();
				boolean seen = true;
				entity.setProperty("seen", seen);

				object.put("sender",sender );
				object.put("receiver",reciver );
				object.put("commandurl", "social/getFriendRequestByID");
				object.put("date", "date");
				object.put("seen",seen );
				object.put("id",entity.getProperty("id").toString() );


				dataStore.put(entity);
			}
		}
		return object;
	}

	/////////////////////////////////
}
