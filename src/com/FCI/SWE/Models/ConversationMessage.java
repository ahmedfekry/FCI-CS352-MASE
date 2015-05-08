package com.FCI.SWE.Models;

import java.util.Date;

import com.FCI.SWE.ServicesModels.MessageEntity;

public class ConversationMessage extends MessageEntity {
	private int conversationID;
	
	public ConversationMessage(String sender, String receiver,
			String commandUrl, Date date, boolean seen, int id, String message, int conversationID) {
		super(sender, receiver, commandUrl, date, seen, id, message);
		this.conversationID = conversationID;
	}
	
}
