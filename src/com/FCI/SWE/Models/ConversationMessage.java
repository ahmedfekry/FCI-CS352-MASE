package com.FCI.SWE.Models;

import java.util.Date;

public class ConversationMessage extends Message {
	private int conversationID;
	
	public ConversationMessage(String sender, String receiver,
			String commandUrl, Date date, boolean seen, int id, String message, int conversationID) {
		super(sender, receiver, commandUrl, date, seen, id, message);
		this.conversationID = conversationID;
	}
	
}
