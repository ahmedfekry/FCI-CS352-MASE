package com.FCI.SWE.Models;
import java.util.*;
import javax.ws.rs.*;
import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.Message;
import com.FCI.SWE.Models.Notification;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;

public class FriendRequest extends Notification{

	public FriendRequest(String sender, String receiver, String commandUrl,
			Date date, boolean seen, int id) {
		super(sender, receiver, commandUrl, date, seen, id);
		
	}
	
	public static FriendRequest parseFriendRequest (String json) throws org.json.simple.parser.ParseException
	{
		JSONParser parser = new JSONParser();
		FriendRequest m = null;
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			
			m = new FriendRequest(object.get("sender").toString(),
					object.get("receiver").toString(),
					object.get("commandUrl").toString(),
					(Date)object.get("date"),
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
	

}
