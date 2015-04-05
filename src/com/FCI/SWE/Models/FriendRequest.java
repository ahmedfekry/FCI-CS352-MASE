package com.FCI.SWE.Models;

import java.util.Date;

public class FriendRequest extends Notification{

	public FriendRequest(String sender, String receiver, String commandUrl,
			Date date, boolean seen, int id) {
		super(sender, receiver, commandUrl, date, seen, id);
		
	}

	

}
