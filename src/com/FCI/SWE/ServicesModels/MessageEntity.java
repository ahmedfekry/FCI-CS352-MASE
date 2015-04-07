package com.FCI.SWE.ServicesModels;

import java.sql.Date;
import java.util.Vector;

import javax.xml.stream.events.Comment;

import com.FCI.SWE.Models.Message;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

//public Message(String sender, String receiver, String commandUrl,
//		Date date, boolean seen, int id, String message) {
//	super(sender, receiver, commandUrl, date, seen, id);
//	this.message = message;
//}
public class MessageEntity {
	private String sender;
	private String receiver;
	private String commandUrl;
	private Date date;
	private int id;
	private String message;
	private boolean seen;
	
	public MessageEntity(String sender,String receiver,String commandurl,Date date,boolean seen,int id,String message){
		this.commandUrl = commandurl;
		this.date = date;
		this.id = id;
		this.message = message;
		this.receiver = receiver;
		this.sender = sender;
		this.seen = seen;
	}
	
	public Vector<MessageEntity> getMessages(String uname){
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("Messages");
		PreparedQuery prepare = dataStore.prepare(geo);
		
		
		Vector<MessageEntity> msg = new Vector<MessageEntity>();
		for(Entity entity: prepare.asIterable()){
			String reciver = entity.getProperty("receiver").toString();
			if(reciver.equals(uname)){
				MessageEntity msg1 = new MessageEntity(entity.getProperty("sender").toString(), entity.getProperty("reciver").toString(), entity.getProperty("commandurl").toString(), (Date)entity.getProperty("date"), (boolean)entity.getProperty("seen"), (int)entity.getProperty("id"), entity.getProperty("message").toString());
				msg.add(msg1);
			}
		}
		
		return msg;
	}
}
