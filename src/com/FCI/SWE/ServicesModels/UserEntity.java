package com.FCI.SWE.ServicesModels;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;

	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static UserEntity getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new UserEntity(object.get("name").toString(), object.get(
					"email").toString(), object.get("password").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static User getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				User returnedUser = new User(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());

				return returnedUser;
			}
		}

		return null;
	}

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		long id =1;
		if(list.size()!=0)
			id =list.get(list.size()-1).getKey().getId()+ 1;
		Entity employee = new Entity("users", id);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		datastore.put(employee);

		return true;

	}
	//////////////////////////////////////////////////////////////////////////
	public static boolean isExist(String userName)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for (Entity user: pq.asIterable()) {
			if(user.getProperty("name").equals(userName))
				return true;
		}

		return false;
	}
	///////////////////////////////////////////////
	public static void addPostToUsers(Vector<String>IDs)
	{

	}
	///////////////////////////////////////////////////////////

	public static boolean addFriend (String sUser, String fUser)
	{

		// try to delete sent friend request from friendRequests table
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();


		boolean t = FriendRequestEntity.deleteRequest(sUser, fUser);
		if(!t)
		{				
			return false;
		}

		// add friend request in Friends entity


		Query gaeQuery2 = new Query("Friends");
		PreparedQuery pq2 = datastore.prepare(gaeQuery2);
		List<Entity> l = pq2.asList(FetchOptions.Builder.withDefaults());

		long id = 1;
		if(l.size()>0)
			id = l.get(l.size()-1).getKey().getId()+1 ;

		Entity friend = new Entity("Friends", id);

		friend.setProperty("user", sUser);
		friend.setProperty("friendTo", fUser);
		datastore.put(friend);



		return true;
	}
	/////////////////////////////////////////////////////////
	public static JSONArray getFriends(String uName)
	{
		JSONArray object = new JSONArray();
		
		object.add( "OK");
		System.out.println("user exists");
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("Friends");
		PreparedQuery pq = datastore.prepare(gaeQuery);	

		List<Entity> l = pq.asList(FetchOptions.Builder.withDefaults());
		for(int i=0;i<l.size();i++)
		{
			Entity e = l.get(i).clone();

			if(e.getProperty("user").equals(uName)  )
			{
				object.add(e.getProperty("friendTo"));
			}	
			else if(e.getProperty("friendTo").equals(uName) )
			{
				object.add(e.getProperty("user") );
			}
		}
		
		return object;
	}

}
