package com.FCI.SWE.Models;

import java.util.Date;

public class Notification {
	
	String sender;
	String receiver;
	String commandUrl;
	Date date;
	boolean seen;
	public Notification(String sender, String receiver, String commandUrl,
			Date date, boolean seen) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.commandUrl = commandUrl;
		this.date = date;
		this.seen = seen;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getCommandUrl() {
		return commandUrl;
	}
	public void setCommandUrl(String commandUrl) {
		this.commandUrl = commandUrl;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isSeen() {
		return seen;
	}
	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	
	
	
}
