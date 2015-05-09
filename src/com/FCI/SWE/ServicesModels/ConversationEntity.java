package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class ConversationEntity {

	private String name;	
	private String owner;
	private int id;
	private Date date;
	private Vector<String>members;


	public ConversationEntity(String name, String owner, int id, Date date,
			Vector<String> members) {
		super();
		this.name = name;
		this.owner = owner;
		this.id = id;
		this.date = date;
		this.members = members;
	}


	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<String> getMembers() {
		return members;
	}

	public void setMembers(Vector<String> members) {
		this.members = members;
	}
	////////////////////////////////////////////////////////////////////////////

	public static ConversationEntity getConversation(int conversationID){

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("Conversation");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			//	System.out.println(entity.getProperty("Conversation").toString());
			if (entity.getProperty("id").toString().equals(String.valueOf(conversationID)) ) {

				Vector <String>members = new Vector<String>((ArrayList<String>) entity.getProperty("members"));
				ConversationEntity returned = new ConversationEntity(entity.getProperty(
						"name").toString(), entity.getProperty("owner")
						.toString(), Integer.parseInt(entity.getProperty("id").toString()),
						(Date)entity.getProperty("date"),
						members);

				return returned;
			}
		}
		return null;
	}
	////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param username username of user wants to create conversation 
	 * @param name conversation name
	 * @return
	 */

	public static long  createConversation(String username, String name){

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		try{
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
			return id;
		}
		catch(Exception e){
			return -1;
		}

	}

	////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param owner username of user wants to add afriend to conversation
	 * @param id conversation id
	 * @param friend username of a friend to be added to conversation
	 * @return String represents Status OK, Failed & reason
	 */

	public static String addToConversation(String owner, String id, String friend	)
	{
		String status = "";
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
					status =  "Failed, this username is already member in the conversation";
				}
				else
				{
					members.add(friend);
					entity.setProperty("members", members);

					dataStore.put(entity);

					status = "OK";

				}
				return status;
			}
		}
		status = "Failed, no conversation with this id";

		return status;
	}
	////////////////////////////////////////////////////////////////////////////


	////////////////////////////////////////////////////////////////////////////


}
