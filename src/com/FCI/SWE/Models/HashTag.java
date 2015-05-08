package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class HashTag {
	
	public String hashtag;
	public ArrayList<Integer> posts;
	public HashTag() {
		// TODO Auto-generated constructor stub
	}
	public HashTag(String hashtag, ArrayList<Integer> posts){
		this.hashtag = hashtag;
		this.posts = posts;
	}
	public boolean AddToHashTag(String hash,int postID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Query gaeQuery = new Query("HashTag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		boolean flag = false;
		for(Entity entity: pq.asIterable()){
			String name = entity.getProperty("hashtag").toString();
			ArrayList<Integer> mem = (ArrayList<Integer>) entity.getProperty("posts");
			
			if(name.equals(hashtag)){
				flag = true;
				mem.add(postID);
				entity.setProperty("posts", mem);
				datastore.put(entity);
				break;
			}
		}
		if(!flag){
			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			long id =1;
			if(list.size()!=0)
				id =list.get(list.size()-1).getKey().getId()+ 1;
			Entity ent = new Entity("HashTag",id);
			ent.setProperty("hashtag", hashtag);
			ArrayList<Integer> mem = new ArrayList<Integer>();
			mem.add(postID);
			ent.setProperty("posts", mem);
			datastore.put(ent);
			return true;
		}
		return false;
	}

}
