package com.FCI.SWE.Models;

import java.util.ArrayList;

public class Page {
	String pageName;
	String ownerName;
	int id;
	ArrayList<String> fans; // username of users how follow this page 
	
	public Page() {
		this.fans = new ArrayList<String>();
	}
	public Page(String pageName,String ownerName,ArrayList<String> fans){
		this.pageName = pageName;
		this.ownerName = ownerName;
		this.fans = new ArrayList<>();
		for (int i = 0; i < fans.size(); i++) {
			this.fans.add(fans.get(i));
		}
	}
	

}
