package com.FCI.SWE.Services;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import sun.org.mozilla.javascript.ObjArray;

import com.FCI.SWE.ServicesModels.*;
import com.FCI.SWE.Models.*;
import com.FCI.SWE.Services.*;
import com.FCI.SWE.Controller.*;
import com.FCI.SWE.ServicesModels.UserEntity;
//import com.google.appengine.repackaged.com.google.protobuf.TextFormat.ParseException;

public class NotificationServiceTest {
	
	UserEntity user;
	@DataProvider (name= "UserProvider")
	public static Object[][] UserProvider(){
		return new Object[][]{
				{"fekry","123","hadad","hello","OK"}
				
		};
	}
	
		//////////////////////////////////////////////////
  @Test
  public void SendConversationMessage() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void addToConversation() {
//    throw new RuntimeException("Test not implemented");
  }
  ///////////////////////////////////////////////////////////////////
  @DataProvider (name= "createConversationProvider")
	public static Object[][] createConversationProvider(){
		return new Object[][]{
				{"fekry","123","conversation","OK"},
				{"fekry","123","conversation","Failed, no acces to create conversation"}
		};
	}
  
  @Test(dataProvider = "createConversationProvider")
  public void createConversation(String username,String pass,String status) {
	  user = new UserEntity("ahmed", "ahmed@mail.com", "123");
	  user.saveUser();
	
	  	String serviceUrl = "http://localhost:8888/rest/createConversation";
		String urlParameters = "uname=" + username + "&password=" + pass ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
	  
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			Assert.assertEquals(object.get("Status"), status);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
  }
  //////////////////////////////////////////////////////////////////
  @Test(dataProvider = "UserProvider",dependsOnMethods = {"saveUser"})
  public void createMessage(String name,String pass,String reciver,String msg,String status){
	  UserEntity user = new UserEntity("hadad", "ha@had.com", "123");
	  user.saveUser();
	  user = new UserEntity("fekry", "fekry@fk.com", "123");
	  user.saveUser();
	  
	  String serviceUrl = "http://localhost:8888/rest/createMessage";
		String urlParameters = "uname=" + name + "&password=" + pass +  "&receiver=" + reciver + "&message=" + msg;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
	  
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			Assert.assertEquals(object.get("Status"), status);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }

  @Test
  public void deleteFriendRequest() {
//    throw new RuntimeException("Test not implemented");
  }
/////////////////////////////////////////////////////////////
  
  @DataProvider (name= "getAllMessagesProvider")
	public static Object[][] getAllMessagesProvider(){
		return new Object[][]{
				{"ahmed","123","OK"},
				{"ahmed","11","Failed, wrong username or password"},
				{"fekry","124","Failed, wrong username or password"}
		};
	}
  
  @Test(dataProvider = "getAllMessagesProvider")
  public void getAllMessages(String name,String pass,String status) {
	  user = new UserEntity("ahmed", "ahmed.@mal.com", "123");
	  user.saveUser();
	  user = new UserEntity("hadad", "hadad.@mal.com", "123");
	  
	  MessageEntity msg = new MessageEntity("hadad", "ahmed", "showMessage",null, true, 1, "hello");
	  msg.saveMessage(msg.getSender(), msg.getReceiver(), msg.getMessage());
	  String serviceUrl = "http://localhost:8888/rest/getAllMessages";
	  String urlParameters = "uname=" + name + "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
	  
		try {
			
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			Assert.assertEquals(object.get("Status"), status);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
/////////////////////////////////////////////////////////////
  
  @Test
  public void getConversationMessages() {
	  
  }
/////////////////////////////////////////////////////////////
  @Test
  public void getFriendRequestByID() {
//    throw new RuntimeException("Test not implemented");
  }
//////////////////////////////////////////////////////
  @DataProvider (name= "getMessageByIDProvider")
	public static Object[][] getMessageByIDProvider(){
		return new Object[][]{
				{"ahmed","123","1","OK"},
				{"fekry","124","2","Failed, wrong username or password"}
		};
	}
  @Test (dataProvider = "getMessageByIDProvider",dependsOnMethods = {"saveUser","saveMessage"})
  public void getMessagesByID(String name,String pass,String id,String status) {
	  user = new UserEntity("ahmed", "ahmed.@mal.com", "123");
	  user.saveUser();
	  user = new UserEntity("hadad", "hadad.@mal.com", "123");
	  
	  MessageEntity msg = new MessageEntity("hadad", "ahmed", "showMessage",null, true, 1, "hello");
	  msg.saveMessage(msg.getSender(), msg.getReceiver(), msg.getMessage());
	  
	  String serviceUrl = "http://localhost:8888/rest/getMessageByID";
	  String urlParameters = "username=" + name + "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
	  
		try {
			
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			Assert.assertEquals(object.get("Status"), status);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
/////////////////////////////////////////////////////
  @Test
  public void getUnseenConversationMessages() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getUnseenFriendRequests() {
//    throw new RuntimeException("Test not implemented");
  }
/////////////////////////////////////////////////////////////////////
  @DataProvider (name= "getUnsenMessagesProvider")
  public static Object[][] getUnsenMessagesProvider(){
	  return new Object[][]{
			{"ahmed","123","OK"},
			{"fekry","124","Failed, wrong username or password"}
	  };
  }
  
  @Test (dataProvider = "getUnsenMessagesProvider",dependsOnMethods = {"saveUser","saveMessage"})
  public void getUnseenMessages(String username,String password,String status) {
	  user = new UserEntity("ahmed", "ahmed.@mal.com", "123");
	  user.saveUser();
	  MessageEntity msg = new MessageEntity("hadad", "ahmed", "showMessage",null, false, 1, "hello");
	  msg.saveMessage(msg.getSender(), msg.getReceiver(), msg.getMessage());
	  
	  String serviceUrl = "http://localhost:8888/rest/getMessageByID";
	  String urlParameters = "username=" + username + "&password=" + password;
	  String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
	  JSONParser parser = new JSONParser();
		Object obj;
	  
		try {
			
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			Assert.assertEquals(object.get("Status"), status);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
  }
////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////
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
  @Test(dataProvider = "sendFriendRequestProvider")
	public void sendFriendRequest(String sUser, String fUser, String password, String status) {
	  	UserEntity user1 = new UserEntity("ahmed", "email@email.com", "123");
	}
}
