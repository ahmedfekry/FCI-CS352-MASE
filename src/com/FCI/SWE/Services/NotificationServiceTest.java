package com.FCI.SWE.Services;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NotificationServiceTest {
	
	// providers
	@DataProvider(name = "sendFriendRequestProvider")
	public static Object[][] sendFriendRequestProvider()
	{
		return new Object[][]{
				{"hadad","m" , "123", "OK"},
				{"hadad","hadad" , "123", "Failed, two users are the same"},	
				{"hadad","hh" , "123", "Failed, incorrect data"},
				{"hadad","m" , "12", "Failed, wrong username or password"},
				
				{"hadad","m" , "123", "Failed, they are already friends or "
						+ "request has been sent befor."}				
				// test when they are friends
		};
	}
	
	//////////////////////////////////////////////
	@Test
	public void SendConversationMessage() {
		
	}

	@Test
	public void addToConversation() {

	}

	@Test
	public void createConversation() {

	}

	@Test
	public void createMessage() {

	}

	@Test
	public void deleteFriendRequest() {

	}

	@Test
	public void getAllMessages() {

	}

	@Test
	public void getConversationMessages() {

	}

	@Test
	public void getFriendRequestByID() {

	}

	@Test
	public void getMessagesByID() {

	}

	@Test
	public void getUnseenConversationMessages() {

	}

	@Test
	public void getUnseenFriendRequests() {

	}

	@Test
	public void getUnseenMessages() {

	}

	@Test(dataProvider = "sendFriendRequestProvider")
	public void sendFriendRequest(String sUser, String fUser, String password, String status) {

	}
}
