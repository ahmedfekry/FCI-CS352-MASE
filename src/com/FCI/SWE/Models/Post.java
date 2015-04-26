package com.FCI.SWE.Models;

import java.util.Date;
import java.util.Vector;

public class Post {
	private String owner;
	private int id;
	private String post;
	private Date date;
	private String privacy;
	private PostType type;
	private Vector<String>likes;
	private Vector<Comment>comments;
	public Post(String owner, int id, String post, Date date, String privacy,
			PostType type) {
		super();
		this.owner = owner;
		this.id = id;
		this.post = post;
		this.date = date;
		this.privacy = privacy;
		this.type = type;
		
		likes = new  Vector<String>();
		comments = new Vector<Comment>();
		
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
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public PostType getType() {
		return type;
	}
	public void setType(PostType type) {
		this.type = type;
	}
	public Vector<String> getLikes() {
		return likes;
	}
	public void setLikes(Vector<String> likes) {
		this.likes = likes;
	}
	public Vector<Comment> getComments() {
		return comments;
	}
	public void setComments(Vector<Comment> comments) {
		this.comments = comments;
	}
	
	
}
