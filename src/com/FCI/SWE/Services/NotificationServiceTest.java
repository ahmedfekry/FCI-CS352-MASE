package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Controller.Connection;
import com.FCI.SWE.Services.ServiceTest;

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

	@DataProvider(name = "getFriendRequestsProvider")
	public static Object[][] getFriendRequestsProvider()
	{
		return new Object[][]{
				{"m" , "123", "[\"OK\",\"hadad\"]"},
				{"hadad" , "123", "[\"OK\"]"},	
				{"hadad","120", "[\"Failed\"]"}

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

	@Test(dataProvider = "sendFriendRequestProvider" )
	public void sendFriendRequest(String sUser, String fUser, String password, String status) {
		System.out.println("in test");
		System.out.println("sUser = " + sUser + " pass " + password);
		String serviceUrl = "http://localhost:8888/rest/FriendRequestService";
		String urlParameters = "senderUser=" + sUser + "&friendUser=" + fUser
				+ "&senderPassword=" + password ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			Assert.assertEquals(object.get("Status"), status);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(dataProvider = "getFriendRequestsProvider" ,dependsOnMethods = {"sendFriendRequest"})

	public void getFriendRequests(String uName, String password, String status) {
		String serviceUrl = "http://localhost:8888/rest/GetFriendRequests";
		String urlParameters = "uName=" + uName + "&password=" + password ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONArray object = (JSONArray) obj;

			Assert.assertEquals(object.toString(), status);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
