package com.FCI.SWE.Models;

import java.util.Date;

public class Message extends Notification{
	
	String message;
	
	public Message(String sender, String receiver, String commandUrl,
			Date date, boolean seen, String message) {
		super(sender, receiver, commandUrl, date, seen);
		
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
