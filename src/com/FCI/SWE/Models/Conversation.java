package com.FCI.SWE.Models;

import java.util.Date;
import java.util.Vector;

public class Conversation {
	
	private String name;	
	private String owner;
	private int id;
	private Date date;
	private Vector<String>members;

	
	public Conversation(String name, String owner, int id, Date date,
			Vector<String> members) {
		super();
		this.name = name;
		this.owner = owner;
		this.id = id;
		this.date = date;
		this.members = members;
	}
	

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
