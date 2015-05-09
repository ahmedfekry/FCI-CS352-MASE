package com.FCI.SWE.Services;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.Comment;
import com.FCI.SWE.Models.PostType;
import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@Path("/")
@Produces("text/html")
public class PageServices {

	
	@Path("createPage")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String createPage(@FormParam("ownerName")String ownerName, @FormParam("password")String password,
			@FormParam("pageName")String pageName){
		JSONObject object = new JSONObject();
		User user = UserEntity.getUser(ownerName, password);
		if(user == null){
			object.put("Status", "Unregistered user");
			return object.toJSONString();
		}
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("Pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		long id =1;
		if(list.size()!=0)
			id =list.get(list.size()-1).getKey().getId()+ 1;
		Entity pos = new Entity("Pages", id);
		pos.setProperty("ownerName", ownerName);
		pos.setProperty("pageName",pageName);
		pos.setProperty("id", id);
		ArrayList<String> member = new ArrayList<String>();
		member.add(ownerName);
		pos.setProperty("fans", member);
		datastore.put(pos);
		object.put("Status", "OK");
		return object.toJSONString();
	}
	@Path("likePage")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String likePage(@FormParam("username")String username, @FormParam("password")String password,
			@FormParam("pageName")String pageName){
		
		JSONObject object = new JSONObject();
		User user = UserEntity.getUser(username, password);
		if(user == null){
			object.put("Status", "Unregistered user");
			return object.toJSONString();
		}
		
		object.put("Status", "OK");
		
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query geo = new Query("Pages");
		PreparedQuery prepare = dataStore.prepare(geo);
		for (Entity entity : prepare.asIterable()) {
			String name = entity.getProperty("pageName").toString();
			ArrayList<String> mem = (ArrayList<String>) entity.getProperty("fans");
			
			if(pageName.equals(name)){
				if(mem.contains(username)){
					object.put("Status", "Sorry , you already an fan");
					return object.toJSONString();
				}else{
					mem.add(username);
					entity.setProperty("fans", mem);
					dataStore.put(entity);
					
					DatastoreService Store = DatastoreServiceFactory
							.getDatastoreService();
					Query geo2 = new Query("users");
					PreparedQuery prepare2 = Store.prepare(geo2);
					for(Entity entity2 : prepare2.asIterable()){
						String name1 = entity2.getProperty("name").toString();
						ArrayList<String> mem1 = (ArrayList<String>) entity2.getProperty("pages");
						if(username.equals(name1)){
							mem1.add(pageName);
							entity2.setProperty("pages", mem1);
							Store.put(entity2);
						}
						
					}
					object.put("Status", "OK");
					return object.toString();
				}	
			}
				
		}
		return object.toJSONString();
	}

}
