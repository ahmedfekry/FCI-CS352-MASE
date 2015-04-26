package com.FCI.SWE.Models;

import java.util.Date;

public class Comment {
	private String owner;
	private int id;
	private Date date;
	private String comment;
	public Comment(String owner, int id, Date date, String comment) {
		super();
		this.owner = owner;
		this.id = id;
		this.date = date;
		this.comment = comment;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
