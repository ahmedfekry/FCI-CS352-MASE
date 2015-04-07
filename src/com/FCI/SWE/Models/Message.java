package com.FCI.SWE.Models;

import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Message extends Notification{
	
	String message;
	
	
	public Message(String sender, String receiver, String commandUrl,
			Date date, boolean seen, int id, String message) {
		super(sender, receiver, commandUrl, date, seen, id);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static Message parseMessage (String json)
	{
		JSONParser parser = new JSONParser();
		Message m = null;
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			
			m = new Message(object.get("sender").toString(),
					object.get("receiver").toString(),
					object.get("commandurl").toString(),
					new Date(),
					(boolean)object.get("seen"),
					Integer.parseInt(object.get("id").toString()),
					object.get("message").toString()
					
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
