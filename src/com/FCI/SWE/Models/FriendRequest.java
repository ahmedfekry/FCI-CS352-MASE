package com.FCI.SWE.Models;

import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FriendRequest extends Notification{

	public FriendRequest(String sender, String receiver, String commandUrl,
			Date date, boolean seen, int id) {
		super(sender, receiver, commandUrl, date, seen, id);
		
	}
	
	
	public static FriendRequest parseFriendRequest (String json)
	{
		JSONParser parser = new JSONParser();
		FriendRequest m = null;
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			
			m = new FriendRequest(object.get("sender").toString(),
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

}
