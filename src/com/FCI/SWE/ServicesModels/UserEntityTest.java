package com.FCI.SWE.ServicesModels;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;


public class UserEntityTest {


	private final LocalServiceTestHelper helper =
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@BeforeMethod
	public void setUp() {
		helper.setUp();
	}

	@AfterMethod
	public void tearDown() {
		helper.tearDown();
	}
	////////////////////////////////////////////////////////////
	//			{"","" ,"" , },
	//			{"","" ,"" , }
	// data providers
	@DataProvider(name = "saveProvider")
	public static Object [][]saveProvider(){
		return new Object[][]{
				{"hadad","hadad.com" , "123", false},	
				{"hadad2","hadad2.com" ,"123" ,true }

		};
	}

	@DataProvider(name = "existProvider")
	public static Object [][]existProvider(){
		return new Object[][]{
				{"hadad", true},	
				{"hhh",false }

		};
	}

	@DataProvider(name = "getUserProvider")
	public static Object [][]getUserProvider(){
		return new Object[][]{
				{"hadad", "123", "hadad", "123"},	
				{"hadad", "1234", null , null},
				{"hhh", "123", null , null}
		};
	}
	////////////////////////////////////////////
	@Test(dataProvider = "saveProvider")
	public void saveUser(String name, String email, String password, boolean expected) {

		UserEntity user = new UserEntity(name, email, password);
		Assert.assertEquals(user.saveUser(), expected);

	}

	@Test(dataProvider ="existProvider")
	public void isExist(String name, boolean expected) {
		Assert.assertEquals(UserEntity.isExist(name), expected);
	}

	@Test(dataProvider ="getUserProvider")
	public void getUser(String name, String password, String username, String pass) {
		User u = UserEntity.getUser(name, password);
		Assert.assertEquals(u.getName(), username);
		Assert.assertEquals(u.getPass(), pass);

	}

	@Test
	public void addFriend() {

	}

	@Test
	public void getFriends() {

	}



}
;