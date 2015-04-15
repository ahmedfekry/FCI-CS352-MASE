package com.FCI.SWE.Models;

import java.util.Date;
import java.util.Vector;

public class Conversation extends Notification{
	
	private String name;
	private Vector<String>members;
	
	public Conversation(String sender, String receiver, String commandUrl,
			Date date, boolean seen, int id, String name, Vector<String>members) {
		
		super(sender, receiver, commandUrl, date, seen, id);
		this.name = name;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<String> getMembers() {
		return members;
	}

	public void setMembers(Vector<String> members) {
		this.members = members;
	}
	
	
	
}
