package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class Conversation {
	
	private String name;	
	private String owner;
	private int id;
	private Date date;
	private Vector<String>members;

	
	public Conversation(String name, String owner, int id, Date date,
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
	
	public static Conversation getConversation(int conversationID){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("Conversation");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
		//	System.out.println(entity.getProperty("Conversation").toString());
			if (entity.getProperty("id").toString().equals(String.valueOf(conversationID)) ) {
				
				Vector <String>members = new Vector<String>((ArrayList<String>) entity.getProperty("members"));
				Conversation returned = new Conversation(entity.getProperty(
						"name").toString(), entity.getProperty("owner")
						.toString(), Integer.parseInt(entity.getProperty("id").toString()),
						(Date)entity.getProperty("date"),
						members);
			
				return returned;
			}
		}
		return null;
	}
	
}
