package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import java.util.Date;
import java.util.List;

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
			return object.toString();
		}
		
		if(!UserEntity.isExist(receiver) )
		{
			object.put("Status", "Failed, no such user with this username");
			return object.toString();
		}
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
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
		
		object.put("Status", "OK");
		
		return object.toString();
	}

	///////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
}
