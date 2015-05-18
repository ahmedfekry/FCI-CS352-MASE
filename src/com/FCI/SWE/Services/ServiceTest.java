package com.FCI.SWE.Services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Controller.Connection;
import com.FCI.SWE.Services.NotificationServiceTest;


public class ServiceTest {
	
	// providers 
	@DataProvider(name = "registerProvider")
	public static Object[][] registerProvider()
	{
		return new Object[][]{
				{"hadad","hadad.com" , "123", "OK"},
				{"m","m.com" , "123", "OK"},
				{"hadad","hadad.com" , "123", "Failed"}	
				
		};
	}
	
	@DataProvider(name = "loginProvider" )
	public static Object[][] loginProvider()
	{
		return new Object[][]{
				{"hadad" , "123", "OK"},
				{"m" , "123", "OK"},
				{"h","123" ,"Failed" },
				{"hadad","12" ,"Failed" }
				
		};
	}
	/////////////////////////////////////
  @Test
  public void addFriend() {
    
  }

 

  @Test
  public void getFriends() {
    
  }

  @Test(dataProvider = "loginProvider", dependsOnMethods = { "registrationService" })
  public void loginService(String uname,String pass, String status ) {
	  String serviceUrl = "http://localhost:8888/rest/LoginService";
		String urlParameters = "uname=" + uname + "&password=" + pass ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			Assert.assertEquals(object.get("Status"), status);
			if(status.equals("OK")){
				Assert.assertEquals(object.get("name"), uname);
				Assert.assertEquals(object.get("password"), pass);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

  @Test(dataProvider = "registerProvider")
  public void registrationService(String uname, String email, String pass, String status) {
	  String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass ;
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
}
