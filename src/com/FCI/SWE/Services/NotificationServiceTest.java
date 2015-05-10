package com.FCI.SWE.Services;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Controller.Connection;
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
  @Test
  public void SendConversationMessage() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void addToConversation() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void createConversation() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test(dataProvider = "UserProvider",dependsOnMethods = {"saveUser"})
  public void createMessage(String name,String pass,String reciver,String msg,String status) throws org.json.simple.parser.ParseException {
//	  UserEntity user = new UserEntity("hadad", "ha@had.com", "123");
//	  user.saveUser();
//	  user = new UserEntity("fekry", "fekry@fk.com", "123");
//	  user.saveUser();
	  
	  String serviceUrl = "http://localhost:8888/rest/LoginService";
		String urlParameters = "uname=" + name + "&password=" + pass +  "&receiver=" + reciver + "&message=" + msg;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
	  
		obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		Assert.assertEquals(object.get("Status"), status);
  }

  @Test
  public void deleteFriendRequest() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getAllMessages() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getConversationMessages() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getFriendRequestByID() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getMessagesByID() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getUnseenConversationMessages() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getUnseenFriendRequests() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getUnseenMessages() {
//    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void sendFriendRequest() {
//    throw new RuntimeException("Test not implemented");
  }
}
